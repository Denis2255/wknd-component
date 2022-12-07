package com.adobe.aem.guides.wknd.core.services;

import org.apache.sling.api.resource.LoginException;
import org.json.JSONObject;

public interface SearchManager {

    String searchWithJava() throws LoginException;

    String searchWithSlingQuery();

    JSONObject searchWithBuilder() throws LoginException;

    String findPage() throws LoginException;
}
