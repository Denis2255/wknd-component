package com.adobe.aem.guides.wknd.core.servlets;


import com.adobe.aem.guides.wknd.core.services.SlingSearchService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;

import static com.adobe.aem.guides.wknd.core.servlets.SearchPageServlet.SERVLET_PATH;


@Component(service = Servlet.class,
        property = {"sling.servlet.paths=" + SERVLET_PATH, "sling.servlet.methods=" + HttpConstants.METHOD_GET})
public class SearchPageServlet extends SlingAllMethodsServlet {

    public static final String SERVLET_PATH = "/bin/searchPage";

    @Reference
    SlingSearchService searchService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.getWriter().println(searchService.searchChildren());
    }
}
