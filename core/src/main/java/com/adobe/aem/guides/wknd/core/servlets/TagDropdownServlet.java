package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.EmptyDataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,
        property = {"sling.servlet.resourceTypes=" + "/bin/availableThemes",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        })
public class TagDropdownServlet extends SlingSafeMethodsServlet {


    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response) throws ServletException, IOException {
        List<Resource> themes = new ArrayList<Resource>();

        ResourceResolver resolver = request.getResourceResolver();
        request.setAttribute(DataSource.class.getName(),
                EmptyDataSource.instance());
        ValueMap vm = new ValueMapDecorator(new HashMap<>());
        for (int i = 0; i < 5; i++) {
            String value = "value" + i;
            String text = "text" + i;
            vm.put("value", value);
            vm.put("text", text);
            themes.add(new ValueMapResource(resolver, new ResourceMetadata(),
                    "nt:unstructured", vm));
        }
        DataSource dataSource = new SimpleDataSource(themes.iterator());
        request.setAttribute(DataSource.class.getName(), dataSource);
    }
}




