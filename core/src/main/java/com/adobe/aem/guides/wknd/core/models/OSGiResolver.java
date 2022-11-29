package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.LoginException;

public interface OSGiResolver {
    public String getServiceName();
    public String getIdUser() throws LoginException;
}
