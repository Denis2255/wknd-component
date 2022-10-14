package com.adobe.aem.guides.wknd.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


import javax.inject.Named;

@Model(adaptables = {Resource.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BookItems {
    @ValueMapValue
    private String bookName;

    @ValueMapValue
    @Named("yearOfCreation")
    private String year;

    public String getBookName() {return bookName;}

    public String getYearOfCreation(){return year;}
}
