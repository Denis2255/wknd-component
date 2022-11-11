package com.adobe.aem.guides.wknd.core.service;

import com.day.cq.wcm.api.Page;

import java.util.Iterator;

public interface DemoService {
    public Iterator<Page> getPages();
}
