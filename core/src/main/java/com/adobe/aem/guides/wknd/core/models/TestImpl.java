package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        adapters = Test.class)
public class TestImpl implements Test {


    @ValueMapValue
    private List<String> words;

    @ValueMapValue
    private Boolean isChecked;


    @Override
    public Boolean getChecked() {
        return isChecked;
    }

    @Override
    public List<String> getWords() {

        if (isChecked == true) {
            Collections.sort(words);
            return new ArrayList<String>(words);
        } else {
            return new ArrayList<String>(words);
        }
    }
}
