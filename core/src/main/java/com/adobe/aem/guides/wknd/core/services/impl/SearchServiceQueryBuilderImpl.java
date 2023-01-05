package com.adobe.aem.guides.wknd.core.services.impl;


import com.adobe.aem.guides.wknd.core.services.SearchServiceQueryBuilder;
import com.adobe.aem.guides.wknd.core.utils.ResolverUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = SearchServiceQueryBuilder.class, immediate = true)
public class SearchServiceQueryBuilderImpl implements SearchServiceQueryBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(SearchServiceQueryBuilderImpl.class);

    @Reference
    QueryBuilder queryBuilder;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    public Map<String, String> createTextSearchQuery(String searchText, int startResult, int resultPerPage) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("path", "/content/");
        queryMap.put("type", "cq:Page");
        queryMap.put("fulltext", searchText);
        queryMap.put("p.offset", Long.toString(startResult));
        queryMap.put("p.limit", Long.toString(resultPerPage));
        return queryMap;
    }

    @Override
    public JSONObject searchResult(String searchText, int startResult, int resultPerPage) {
        JSONObject searchResult = new JSONObject();
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            final Session session = resourceResolver.adaptTo(Session.class);
            Query query = queryBuilder.createQuery(PredicateGroup.create(createTextSearchQuery(searchText, startResult, resultPerPage)), session);
            SearchResult result = query.getResult();

            int perPageResults = result.getHits().size();
            long totalResults = result.getTotalMatches();
            long startingResult = result.getStartIndex();
            double totalPages = Math.ceil((double) totalResults / (double) resultPerPage);

            searchResult.put("perpageresult", perPageResults);
            searchResult.put("totalresults", totalResults);
            searchResult.put("startingresult", startingResult);
            searchResult.put("pages", totalPages);


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
}
