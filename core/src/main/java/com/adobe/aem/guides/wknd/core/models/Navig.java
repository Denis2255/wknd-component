package com.adobe.aem.guides.wknd.core.models;

import java.util.*;
import java.util.Iterator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;

import com.adobe.cq.sightly.WCMUsePojo;

public class Navig extends WCMUsePojo{
    private List<Page> items = new ArrayList<Page>();
    private Page navigationRoot;

    @Override
    public void activate() throws Exception {
        navigationRoot = getCurrentPage().getAbsoluteParent(2);

        if (navigationRoot == null) {
            navigationRoot = getCurrentPage();
        }

        Iterator<Page> childPages = navigationRoot.listChildren(new PageFilter(getRequest()));
        while (childPages.hasNext()) {
            items.add(childPages.next());
        }
    }

    public List<Page> getItems() {
        return items;
    }
    public Page getRoot() {
        return navigationRoot;
    }
}
