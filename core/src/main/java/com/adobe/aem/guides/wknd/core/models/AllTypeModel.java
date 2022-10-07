package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AllTypeModel {

    @ValueMapValue
    private Boolean isChecked;

    @ValueMapValue
    private String example;

    @ValueMapValue
    private String brandSlogan;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String logoPath;

    @Inject
    @Via("resource")
    private List<MultiCountries> countries;

    @ValueMapValue
    private String linkTarget;

    @ValueMapValue
    private String columns;

    @ValueMapValue
    private int ageRestriction;

    @ValueMapValue
    private String fileReferenceDam;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String brandColor;

    @ValueMapValue
    private String brandColors;

    public String getBrandColors() {
        return brandColors;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public String getExample() {
        return example;
    }

    public String getBrandSlogan() {
        return brandSlogan;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public List<MultiCountries> getCountries() {
        return countries;
    }

    public String getLinkTarget() {
        return linkTarget;
    }

    public String getColumns() {
        return columns;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public String getFileReferenceDam() {
        return fileReferenceDam;
    }

    public String getFileReference() {
        return fileReference;
    }

    public String getBrandColor() {
        return brandColor;
    }
}
