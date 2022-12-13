package com.adobe.aem.guides.wknd.core.servlets;


import com.adobe.aem.guides.wknd.core.services.SearchManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;

import static com.adobe.aem.guides.wknd.core.servlets.SearchThreeWaysServlet.SERVLET_PATH;

@Component(service = Servlet.class,
        property = {"sling.servlet.paths=" + SERVLET_PATH,
                "sling.servlet.methods=" + HttpConstants.METHOD_GET})
public class SearchThreeWaysServlet extends SlingAllMethodsServlet {

    public static final String SERVLET_PATH = "/bin/searchthreeway";
    private static final Logger LOG = LoggerFactory.getLogger(SearchThreeWaysServlet.class);

    @Reference
    private SearchManager searchManager;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.getWriter().println(searchManager.findPages());
    }
}










