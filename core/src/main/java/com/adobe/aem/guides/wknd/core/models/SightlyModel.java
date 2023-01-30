package com.adobe.aem.guides.wknd.core.models;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SightlyModel {

    @ValueMapValue
    private String title;

    @RequestAttribute
    private String requestAttribute;

    @ValueMapValue
    private List<String> words;

    public String getTitle() {
        return title + "  "  + requestAttribute;
    }

    public List<String> getWords() {
        return words;
    }
}
