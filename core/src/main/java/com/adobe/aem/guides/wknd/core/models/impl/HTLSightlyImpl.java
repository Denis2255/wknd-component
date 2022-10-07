package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.HTLSightly;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


import javax.inject.Inject;
import java.util.*;


@Model(adaptables = SlingHttpServletRequest.class,
        adapters = HTLSightly.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class HTLSightlyImpl implements HTLSightly {
    @Inject
    Resource resource;
    @ValueMapValue
    private List<String> books;


    @Override
    public String[] getBooksArray() {
        String[] bookArray = {"JAVA", "AEM", "Os", "NETWORK"};
        return bookArray;
    }

    @Override
    public List<String> getBooks() {
        if (books != null) {
            return new ArrayList<String>(books);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, String> getBooksMap() {
        Map<String, String> mapBooks = new HashMap<>();
        mapBooks.put("attribute1", "value1");
        mapBooks.put("attribute2", "value2");
        mapBooks.put("attribute3", "value3");
        mapBooks.put("attribute4", "value4");
        mapBooks.put("attribute5", "value5");
        return mapBooks;
    }

    @Override
    public List<Map<String, String>> getBookDetailsWithMap() {
        List<Map<String, String>> bookDetailMap = new ArrayList<>();
        try {
            Resource bookDetail = resource.getChild("bookdetailswithmap");
            if (bookDetail != null) {
                for (Resource bool : bookDetail.getChildren()) {
                    Map<String, String> bookMap = new HashMap<>();
                    bookMap.put("bookname", bookDetail.getValueMap().get("bookname", String.class));
                    bookMap.put("booksubject", bookDetail.getValueMap().get("booksubject", String.class));
                    bookDetailMap.add(bookMap);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookDetailMap;
    }
}
