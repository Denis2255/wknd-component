package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.SearchServiceSQL;
import com.adobe.aem.guides.wknd.core.utils.ResolverUtil;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;


public class SearchServiceSQLImpl implements SearchServiceSQL {

    private static final Logger LOG = LoggerFactory.getLogger(SearchServiceSQLImpl.class);
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public JSONObject searchResultSQL2(String searchPath) {
        JSONObject searchResult = new JSONObject();
        try {
            String sql2Query = "SELECT * FROM [cq:PageContent] AS node WHERE ISDESCENDANTNODE (" + searchPath + ") ORDER BY node.[jcr:title]";
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            final Session session = resourceResolver.adaptTo(Session.class);
            final Query  query= session.getWorkspace().getQueryManager().createQuery(sql2Query,Query.JCR_SQL2);
            final QueryResult result = query.execute();
            NodeIterator pages = result.getNodes();
            JSONArray resultArray = new JSONArray();
            while (pages.hasNext()){
                Node page=pages.nextNode();
                if(page.hasProperty("jcr:title")){
                    JSONObject resultObject = new JSONObject();
                    resultObject.put("title",page.getProperty("jcr:title").getString());
                    resultObject.put("name",page.getName());
                    resultObject.put("created", page.getProperty("jcr:created").getString());
                    resultArray.put(resultObject);
                }
            }
            searchResult.put("Pages",resultArray);
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return searchResult;
    }
}
