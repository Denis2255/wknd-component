package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
adapters = Share.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
resourceType = {ShareImpl.RESOURCE_TYPE})
public class ShareImpl implements Share{
    static final String RESOURCE_TYPE="wknd/components/sharecomponent";
    @ValueMapValue
    private String title;

    @Inject
    @Via("resource")
   private List<LinkItems> links;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<LinkItems> getLinks() {
        return links;
    }
}
