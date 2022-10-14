package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Book {
    @ValueMapValue
    private String title;

    @ValueMapValue
    private String fname;

    @ValueMapValue
    private String lname;

    @Inject
    @Via("resource")
    private List<BookItems> books;


    public String getFname() {
        return fname;
    }


    public String getLname() {
        return lname;
    }


    public String getTitle() {
        return title;
    }


    public List<BookItems> getBooks() {
        return books;
    }
}
