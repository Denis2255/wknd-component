package com.adobe.aem.guides.wknd.core.models;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HumanModel {

    @ValueMapValue
    private String firstNameMale;

    @ValueMapValue
    private String lastNameMale;

    @ValueMapValue
    private String firstNameFemale;

    @ValueMapValue
    private String lastNameFemale;

    @ValueMapValue
    private String gender;

    public String getGender() {
        return gender;
    }

    public String getFirstNameMale() {
        return firstNameMale;
    }

    public String getLastNameMale() {

        return "mister " + lastNameMale;
    }

    public String getFirstNameFemale() {
        return firstNameFemale;
    }

    public String getLastNameFemale() {

        return "miss " + lastNameFemale;
    }
}
