package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Objects;
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
            Resource resource = req.getResource();
            String resourcePath = resource.getPath();
            Page page = Optional.ofNullable(resolver.adaptTo(PageManager.class))  //
                    .map(pm -> pm.getContainingPage(resource)).orElse(null);
            if (page == null) {
                LOG.info("Page is null");
                return;
            }
            int valueOfSlash = resolveComponentLevel(resourcePath, page.getPath());
            resp.setContentType("text/html");
            resp.getWriter().println("Nesting level of component: " + valueOfSlash + " " +
                    "Title: " + page.getTitle());
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
    }

    private int resolveComponentLevel(String componentPath, String pathPage) {  //
        String differenceInPath = StringUtils.difference(pathPage, componentPath);
        return StringUtils.countMatches(differenceInPath, '/');

    }
}
