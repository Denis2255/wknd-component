package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.SearchManager;
import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
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

import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component(service = SearchManager.class, immediate = true)
@Designate(ocd = SearchManagerImpl.ServiceConfigurationThreeWays.class)
public class SearchManagerImpl implements SearchManager {

    private static final Logger LOG = LoggerFactory.getLogger(NodeManagerImpl.class);
    private final String SEARCH_WITH_JAVA = "Java";
    private final String SEARCH_WITH_SLING_QUERY = "SlingQuery";
    private final String SEARCH_WITH_BUILDER = "Builder";
    private String answer;

    private ServiceConfigurationThreeWays configuration;

    @Reference
    QueryBuilder queryBuilder;

    @Reference
    ServiceResourceResolver serviceResourceResolver;

    @Activate
    @Modified
    public void activate(ServiceConfigurationThreeWays configuration) {
        this.configuration = configuration;
    }



    @Override
    public String searchWithJava() throws LoginException {
        Resource template = null;
        ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver();
        Resource resource = resourceResolver.getResource(configuration.ROOT_PATH());
        while ((resource = resource.getParent()) != null) {
            if (!resource.isResourceType("cq:Page")) {
                continue;
            }
            template = resource.getChild("jcr:content/cq:template");
            if (template != null && "/conf/wknd/settings/wcm/templates/page-content".equals(template.adaptTo(String.class))) {
                break;
            }
        }
        return answer = template.toString();
    }

    @Override
    public String searchWithSlingQuery() {
        return answer = "SlingQuery";
    }

    @Override
    public JSONObject searchWithBuilder() throws LoginException {
        JSONObject searchResult = new JSONObject();
        try {
        ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver();
        final Session session = resourceResolver.adaptTo(Session.class);
        Query query = queryBuilder.createQuery(PredicateGroup.create(createTextSearchQuery()), session);
        SearchResult result = query.getResult();
            List<Hit> hits = result.getHits();
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
    public String findPage() throws LoginException {
        if (Objects.equals(configuration.SearchMethod(), SEARCH_WITH_JAVA)) {
            searchWithJava();
        } else if (Objects.equals(configuration.SearchMethod(), SEARCH_WITH_SLING_QUERY)) {
            searchWithSlingQuery();
        } else if (Objects.equals(configuration.SearchMethod(), SEARCH_WITH_BUILDER)) {
            answer = searchWithBuilder().toString();
        }
        return answer;
    }

    public Map<String, String> createTextSearchQuery() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("type", "cq:Page");
        queryMap.put("path", configuration.ROOT_PATH());
        queryMap.put("1_property", "jcr:content/cq:template");
        queryMap.put("1_property.value", "/conf/wknd/settings/wcm/templates/page-content");
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
        String ROOT_PATH() default "/content";
    }
}
