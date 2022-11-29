package com.adobe.aem.guides.wknd.core.servlets;


import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_NOT_ACCEPTABLE;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Component(service = Servlet.class,
        property = {"sling.servlet.paths=" + "/bin/allowPage",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET
        })
@Designate(ocd = WKNDServletWithUser.ServletPathConfiguration.class)
public class WKNDServletWithUser extends SlingAllMethodsServlet {
    private ServletPathConfiguration configuration;
    private static final String PROPERTY = "secondTitle";
    private static final String NEWVALUE = "qwe12";
    private static final Logger log = LoggerFactory.getLogger(WKNDServletWithUser.class);
    @Reference
    private ServiceResourceResolver serviceResourceResolver;

    @Activate
    public void activate(ServletPathConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try (ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver()) {
            Resource resource = resourceResolver.getResource(configuration.pathGetNode());
            if (resource != null) {
                Node node = resource.adaptTo(Node.class);
                if (node != null) {
                    Node newNode = node.getNode("jcr:content");
                    newNode.setProperty(PROPERTY, NEWVALUE);
                    resourceResolver.commit();
                    response.getWriter().println(SC_OK);
                }
            } else {
                response.getWriter().println(SC_NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(SC_NOT_ACCEPTABLE);
        }
    }

    @ObjectClassDefinition(name = "WKND - Servlet Path Configurstion", description = "Path node for servlet")
    public @interface ServletPathConfiguration {
        @AttributeDefinition(
                name = " path",
                description = "Servlet path",
                type = AttributeType.STRING)
        public String pathGetNode() default "/content/wknd/us/en/1";
    }
}

