package com.adobe.aem.guides.wknd.core.models;

import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import com.day.cq.commons.Externalizer;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.service.component.annotations.Reference;


@Model(adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BannerModel {

    @OSGiService
    private Externalizer externalizer;

    @OSGiService
    private ResourceResolver resourceResolver;
    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String text;

    @ValueMapValue
    private String link;

    @ValueMapValue
    private String title;

    public String getHeading() {
        return heading;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        externalizer = resourceResolver.adaptTo(Externalizer.class);
        return externalizer.authorLink(resourceResolver, link) + ".html";
    }

    public String getTitle() {
        return title;
    }
}