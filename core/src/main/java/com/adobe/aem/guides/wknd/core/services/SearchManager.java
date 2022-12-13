package com.adobe.aem.guides.wknd.core.services;

import org.apache.sling.api.resource.LoginException;
import org.json.JSONObject;

import java.util.List;

public interface SearchManager {

    List<String> searchWithJava();

    String searchWithSlingQuery();

    JSONObject searchWithBuilder();

    String findPages();
}
