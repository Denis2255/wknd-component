package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.services.SearchServiceQueryBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static com.adobe.aem.guides.wknd.core.servlets.SearchServletQueryBuilder.SERVLET_PATH;

@Component(service = Servlet.class,
        property = {"sling.servlet.paths=" + SERVLET_PATH, "sling.servlet.methods=" + HttpConstants.METHOD_GET})
public class SearchServletQueryBuilder extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SearchServletQueryBuilder.class);

    public static final String SERVLET_PATH = "/bin/search";
    @Reference
    SearchServiceQueryBuilder searchServiceQueryBuilder;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        JSONObject searchResult = null;
        try {
            String searchText = req.getRequestParameter("searchText").getString();
            int pageNumber = Integer.parseInt(req.getRequestParameter("pageNumber").getString()) - 1;
            int resultPerPage = Integer.parseInt(req.getRequestParameter("resultPerPage").getString());
            int startResult = pageNumber * resultPerPage;
            searchResult = searchServiceQueryBuilder.searchResult(searchText, startResult, resultPerPage);
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }

        resp.setContentType("application/json");
        resp.getWriter().write(searchResult.toString());
    }
}
