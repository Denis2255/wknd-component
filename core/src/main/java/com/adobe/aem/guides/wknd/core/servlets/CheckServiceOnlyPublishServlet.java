package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.services.OSGIWithConfigPublish;
import com.adobe.aem.guides.wknd.core.services.OSGIWithoutConfig;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;


@Component(service = Servlet.class,
        property = {"sling.servlet.paths=" + "/bin/checkservicepublish",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET})
public class CheckServiceOnlyPublishServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(CheckServiceOnlyPublishServlet.class);

    @Reference
    private OSGIWithConfigPublish osgiWithConfigPublish;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println(osgiWithConfigPublish.getWords());
    }
}
