package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.services.SearchServiceSQL;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static com.adobe.aem.guides.wknd.core.servlets.SearchServletSQL.SERVLET_PATH;

@Component(service = Servlet.class,
        property = {"sling.servlet.paths=" + SERVLET_PATH,
                "sling.servlet.methods=" + HttpConstants.METHOD_GET})
public class SearchServletSQL extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SearchServletSQL.class);

    public static final String SERVLET_PATH = "/bin/searchsql";

    @Reference
    SearchServiceSQL searchServiceSQL;

    @Override
    protected void doGet( SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        JSONObject searchResult = null;
        try{
            String searchPath=req.getRequestParameter("searchPath").getString();
            searchResult = searchServiceSQL.searchResultSQL2(searchPath);
        }catch (Exception e){
            LOG.info(e.getMessage(), e);
        }
        resp.setContentType("application/json");
        resp.getWriter().write(searchResult.toString());
    }
}
