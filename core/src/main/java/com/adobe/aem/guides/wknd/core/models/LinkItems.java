package com.adobe.aem.guides.wknd.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;



@Model(adaptables = {Resource.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LinkItems {

    @ValueMapValue
    String firstNameLink;
    @ValueMapValue
    String secondNameLink;
    @ValueMapValue
    String thirdNameLink;

    public String getFirstNameLink() {
        return firstNameLink;
    }

    public String getSecondNameLink() {
        return secondNameLink;
    }

    public String getThirdNameLink() {
        return thirdNameLink;
    }
}
