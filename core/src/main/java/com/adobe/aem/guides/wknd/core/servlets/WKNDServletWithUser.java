package com.adobe.aem.guides.wknd.core.servlets;


import com.adobe.aem.guides.wknd.core.services.NodeManager;
import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import com.adobe.aem.guides.wknd.core.services.impl.NodeManagerImpl;
import org.apache.http.HttpStatus;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import javax.servlet.Servlet;
import java.io.IOException;

import static com.adobe.aem.guides.wknd.core.servlets.WKNDServletWithUser.SERVLET_PATH;

@Component(service = Servlet.class,
        property = {"sling.servlet.paths=" + SERVLET_PATH, "sling.servlet.methods=" + HttpConstants.METHOD_GET})
@Designate(ocd = WKNDServletWithUser.ServletConfiguration.class)
public class WKNDServletWithUser extends SlingAllMethodsServlet {

    private ServletConfiguration configuration;
    public static final String SERVLET_PATH = "/bin/allowPage";

    @Reference
    private ServiceResourceResolver serviceResourceResolver;

    @Activate
    @Modified
    public void activate(ServletConfiguration configuration) {
        this.configuration = configuration;
    }

    @Reference
    private NodeManager nodeManager;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        if (!configuration.isOff()) {
            boolean isNodeCreated = nodeManager.createNode();
            if (isNodeCreated) {
                response.setStatus(HttpStatus.SC_OK);
                return;
            }
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return;
        }
        response.setStatus(HttpStatus.SC_NOT_FOUND);
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Servlet Configuration", description = "Servlet configuration")
    public @interface ServletConfiguration {
        @AttributeDefinition(
                name = "Servlet is off",
                type = AttributeType.BOOLEAN)
        boolean isOff() default false;
    }
}

