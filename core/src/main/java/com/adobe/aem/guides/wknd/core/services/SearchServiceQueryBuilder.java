package com.adobe.aem.guides.wknd.core.services;

import org.json.JSONObject;

public interface SearchServiceQueryBuilder {
     JSONObject searchResult(String searchText, int startResult, int resultPerPage);
}
