package com.adobe.aem.guides.wknd.core.servlets;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@Component(service = Servlet.class, property = {"sling.servlet.paths=" + "/bin/allowPages",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET
})
public class ReadPageInformationServlet extends SlingAllMethodsServlet {
    @Reference
    private ServiceResourceResolver serviceResourceResolver;

    private static final String PATHNODE = "/content/wknd/us/en/1";
    private static final String PROPERTY = "secondTitile";
    private static final String NEWVALUE = "qwe";
    private static final String OLDVALUE = "rty";
    private static final Logger log = LoggerFactory.getLogger(WKNDServletWithUser.class);
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) {

        ResourceResolver resourceResolver = req.getResourceResolver();
        Resource resource = resourceResolver.getResource(PATHNODE);
        try {
            ModifiableValueMap valueMap = resource.adaptTo(ModifiableValueMap.class);
            if(valueMap.containsKey(PROPERTY)){
            valueMap.replace(PROPERTY, OLDVALUE, NEWVALUE);}
            else {
                valueMap.putIfAbsent(PROPERTY,OLDVALUE);
            }} catch (Exception e) {
            e.printStackTrace();
        }
        Node node = resource.adaptTo(Node.class);
        try {
            node.setProperty("jcr:title",NEWVALUE);
        } catch (RepositoryException e) {
           e.printStackTrace();
        }

        Page page = resource.adaptTo(Page.class);
            ValueMap pageproperties = page.getProperties();
            try {
                resp.getWriter().println("Node_________"+  node.getProperties());
                resp.getWriter().println("Title = " + pageproperties + SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

}
