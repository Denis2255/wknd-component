package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.ServiceResolverForServlet;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;

public class ServiceResolverForServletImpl implements ServiceResolverForServlet {
    @Override
    public ResourceResolver getServiceResourceResolver() throws LoginException {
        return null;
    }
}
