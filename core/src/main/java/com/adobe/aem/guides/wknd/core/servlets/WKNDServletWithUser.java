package com.adobe.aem.guides.wknd.core.servlets;


import com.adobe.aem.guides.wknd.core.services.ServiceResolverForServlet;
import com.adobe.aem.guides.wknd.core.services.impl.ServiceResolverForServletImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

import static javax.servlet.http.HttpServletResponse.SC_NOT_ACCEPTABLE;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.OPTIONAL,
        property = {"sling.servlet.paths=" + "/bin/allowPage",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET
        })
public class WKNDServletWithUser extends SlingAllMethodsServlet {

    private static final String PATHNODE = "/content/wknd/us/en/1";
    private static final String PROPERTY = "secondTitle";
    private static final String NEWVALUE = "qwe12";
    private static final String OLDVALUE = "rty";
    private static final Logger log = LoggerFactory.getLogger(WKNDServletWithUser.class);
    @Reference
    private ServiceResolverForServlet serviceResolverForServlet;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try (ResourceResolver resourceResolver = serviceResolverForServlet.getServiceResourceResolver()) {
            Resource resource = resourceResolver.getResource(PATHNODE);
            Node node = null;
            if (resource != null) {
                node = resource.adaptTo(Node.class);
            }
            if (node != null) {
                Node newNode = node.getNode("jcr:content");
                newNode.setProperty(PROPERTY, NEWVALUE);
                resourceResolver.commit();
            }
            response.getWriter().println(SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(SC_NOT_ACCEPTABLE);
        }
    }
}

