package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;


@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.OPTIONAL,
        property = {"sling.servlet.paths=" + "/bin/pagesServlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET
        })
@Designate(ocd = WKNDPathTypeServlet.ServletPathConfig.class)
public class WKNDPathTypeServlet extends SlingSafeMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(WKNDPathTypeServlet.class);

    private ServletPathConfig configuration;
    @Activate
    public void activate(ServletPathConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {
        Page page = Optional.of(req.getResourceResolver()).map(rr -> rr.adaptTo(PageManager.class)).map(pm -> pm.getPage(configuration.pathGetNode())).orElse(null);
        JSONArray pagesArray = new JSONArray();
        try {
            Iterator<Page> childPages = null;
            if (page != null) {
                childPages = page.listChildren();
            }
            if (childPages != null) {
                while (childPages.hasNext()) {
                    Page childPage = childPages.next();
                    JSONObject pageObject = new JSONObject();
                    pageObject.put(childPage.getTitle(), childPage.getPath().toString());
                    pagesArray.put(pageObject);
                }
            }
        } catch (Exception e) {
            LOG.info("\n ERROR {} ", e.getMessage());
        }
        resp.setContentType("application/json");
        resp.getWriter().write(pagesArray.toString());
    }

    @ObjectClassDefinition(name = "WKND - Servlet Path Config", description = "Path node for servlet")
    public @interface ServletPathConfig {
        @AttributeDefinition(
                name = " path",
                description = "Servlet path",
                type = AttributeType.STRING)
        public String pathGetNode() default "/content/wknd/us/en";
    }
}