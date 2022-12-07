package com.adobe.aem.guides.wknd.core.servlets;


import com.adobe.aem.guides.wknd.core.services.NodeManager;
import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
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
import java.util.Optional;

import static com.adobe.aem.guides.wknd.core.servlets.WKNDServletWithUser.SERVLET_PATH;

@Component(service = Servlet.class,
        property = {"sling.servlet.paths=" + SERVLET_PATH, "sling.servlet.methods=" + HttpConstants.METHOD_GET})
@Designate(ocd = WKNDServletWithUser.ServletConfiguration.class)
public class WKNDServletWithUser extends SlingAllMethodsServlet {

    private ServletConfiguration configuration;
    public static final String SERVLET_PATH = "/bin/allowPage";

    @Reference
    private NodeManager nodeManager;

    @Activate
    @Modified
    public void activate(ServletConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        if (!configuration.isActive()) {
            response.setStatus(HttpStatus.SC_SERVICE_UNAVAILABLE);
            return;
        }

        int httpStatus = Optional.of(nodeManager)
                .map(NodeManager::createNode)
                .map(isCreated -> {
                    if (isCreated) { return HttpStatus.SC_OK; }
                    return HttpStatus.SC_NOT_FOUND;
                }).orElse(HttpStatus.SC_NOT_FOUND);
        response.setStatus(httpStatus);
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Servlet Configuration Is Off", description = "Servlet configuration")
    public @interface ServletConfiguration {
        @AttributeDefinition(
                name = "ServletIsActive",
                type = AttributeType.BOOLEAN)
        boolean isActive();
    }
}

