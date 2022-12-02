package com.adobe.aem.guides.wknd.core.services;

import org.json.JSONObject;

public interface SearchService {
     JSONObject searchResult(String searchText, int startResult, int resultPerPage);
}
