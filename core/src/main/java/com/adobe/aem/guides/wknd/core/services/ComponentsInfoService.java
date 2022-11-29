package com.adobe.aem.guides.wknd.core.services;

import org.apache.sling.api.resource.LoginException;

import java.util.List;
import java.util.Map;

public interface ComponentsInfoService {
    public String getServiceName();
    public String getIdUser() throws LoginException;
}
