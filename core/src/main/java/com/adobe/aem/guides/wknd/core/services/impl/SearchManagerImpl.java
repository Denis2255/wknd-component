package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.SearchManager;
import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;

import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.*;


@Component(service = SearchManager.class, immediate = true)
@Designate(ocd = SearchManagerImpl.ServiceConfigurationThreeWays.class)
public class SearchManagerImpl implements SearchManager {

    private static final Logger LOG = LoggerFactory.getLogger(NodeManagerImpl.class);
    private static final String SEARCH_WITH_JAVA = "Java";
    private static final String SEARCH_WITH_SLING_QUERY = "SlingQuery";
    private static final String SEARCH_WITH_BUILDER = "Builder";


    private ServiceConfigurationThreeWays configuration;

    @Reference
    private QueryBuilder queryBuilder;

    @Reference
    private ServiceResourceResolver serviceResourceResolver;

    @Activate
    @Modified
    public void activate(ServiceConfigurationThreeWays configuration) {
        this.configuration = configuration;
    }


    @Override
    public List<String> searchWithJava() {
        List<String> searchResult = new ArrayList<>();
        try (ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver()) {
            Node node = Optional.ofNullable(resourceResolver.getResource(configuration.ROOT_PATH()))
                    .map(n -> n.adaptTo(Node.class)).orElse(null);
            NodeIterator it = node.getNodes();
            while (it.hasNext()) {
                Node childNode = it.nextNode();
                if (Objects.equals(childNode.getName(), "jcr:content")) {
                    PropertyIterator properties = childNode.getProperties();
                    while (properties.hasNext()) {
                        Property property = properties.nextProperty();
                        String valueProperty = property.getValue().getString();
                        if (Objects.equals(valueProperty, configuration.VALUE_PROPERTY())) {
                            String childPath = childNode.getPath();
                            searchResult.add(childPath);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return searchResult;
    }

//    private void processNode() {
//        processNode();
//    }

    @Override
    public String searchWithSlingQuery() {
        return "SlingQuery";
    }

    @Override
    public JSONObject searchWithBuilder() {
        JSONObject searchResult = new JSONObject();
        try (ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver()) {
            List<Hit> hits = Optional.of(queryBuilder)
                    .map(qb -> qb.createQuery(PredicateGroup.create(createTextSearchQuery()),
                            resourceResolver.adaptTo(Session.class)))
                    .map(Query::getResult).map(SearchResult::getHits).orElse(new ArrayList<>());
            JSONArray resultArray = new JSONArray();
            for (Hit hit : hits) {
                Page page = hit.getResource().adaptTo(Page.class);
                JSONObject resultObject = new JSONObject();
                if (page == null) {
                    LOG.info("Page is null");
                    return searchResult;
                }
                resultObject.put("title", page.getTitle());
                resultObject.put("path", page.getPath());
                resultArray.put(resultObject);
            }
            searchResult.put("results", resultArray);
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return searchResult;
    }

    @Override
    public String findPages() {
        String answer;
        String searchMethod = configuration.SearchMethod();
        if (Objects.equals(searchMethod, SEARCH_WITH_SLING_QUERY)) {
            answer = searchWithSlingQuery();
            return answer;
        }
        if (Objects.equals(searchMethod, SEARCH_WITH_BUILDER)) {
            answer = searchWithBuilder().toString();
            return answer;
        }
        answer = searchWithJava().toString();
        return answer;
    }

    public Map<String, String> createTextSearchQuery() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("type", configuration.TYPE_PROPERTY());
        queryMap.put("path", configuration.ROOT_PATH());
        queryMap.put("1_property", configuration.NAME_PROPERTY());
        queryMap.put("1_property.value", configuration.VALUE_PROPERTY());
        queryMap.put("p.limit", "-1");
        return queryMap;
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Service Configuration Three Ways", description = "Search in three ways")
    public @interface ServiceConfigurationThreeWays {
        @AttributeDefinition(
                name = "SearchMethod",
                description = "Java, SlingQuery, Builder",
                type = AttributeType.STRING)
        String SearchMethod();

        @AttributeDefinition(
                name = "ROOT_PATH",
                type = AttributeType.STRING)
        String ROOT_PATH() default "/content/wknd/us/en/2";

        @AttributeDefinition(
                name = "TYPE_PROPERTY",
                type = AttributeType.STRING)
        String TYPE_PROPERTY() default "cq:Page";

        @AttributeDefinition(
                name = "NAME_PROPERTY",
                type = AttributeType.STRING)
        String NAME_PROPERTY() default "jcr:content/cq:template";

        @AttributeDefinition(
                name = "VALUE_PROPERTY",
                type = AttributeType.STRING)
        String VALUE_PROPERTY() default "/conf/wknd/settings/wcm/templates/page-content";
    }
}
