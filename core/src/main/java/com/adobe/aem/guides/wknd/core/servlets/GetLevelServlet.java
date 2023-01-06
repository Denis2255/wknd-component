package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;


@Component(service = Servlet.class,
        property = {"sling.servlet.resourceTypes=" + "test_servlet_type",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.extensions=" + "html",
        })
public class GetLevelServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(GetLevelServlet.class);

    @Reference
    private ServiceResourceResolver resourceResolver;

    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        try (ResourceResolver resolver = resourceResolver.getServiceResourceResolver()) {

//            PageManager pageManager = resolver.adaptTo(PageManager.class);
            resp.setContentType("application/json");
            resp.getWriter().println(" ");

        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
    }
}
