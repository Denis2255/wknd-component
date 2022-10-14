package com.adobe.aem.guides.wknd.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WordItems {
    @ValueMapValue
    String firstWord;
    @ValueMapValue
    String secondWord;
    @ValueMapValue
    String thirdWord;

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public String getThirdWord() {
        return thirdWord;
    }
}
