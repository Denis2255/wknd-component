package com.adobe.aem.guides.wknd.core.servlets;


import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;


@Component(service = Servlet.class,
        property = {"sling.servlet.resourceTypes=" + "cq/Page",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.extensions=" + "json",
                "sling.servlet.selectors=" + "mobile"
        })
public class SearchSelectorServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SearchSelectorServlet.class);

    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        try {
            JSONObject searchResult = new JSONObject();
            RequestPathInfo pathInfo = req.getRequestPathInfo();
            Node node = Optional.of(req.getResource())
                    .map(r -> r.adaptTo(Node.class)).orElse(null);
            if (node == null) {
                LOG.info("Node is null");
                return;
            }
            Node createdNode = node.getNode(JcrConstants.JCR_CONTENT);
            String title = createdNode.getProperty("jcr:title").getString();
            searchResult.put("Title", title);
            searchResult.put("Decomposition", pathInfo);
            resp.setContentType("application/json");
            resp.getWriter().println(searchResult);
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
    }
}
