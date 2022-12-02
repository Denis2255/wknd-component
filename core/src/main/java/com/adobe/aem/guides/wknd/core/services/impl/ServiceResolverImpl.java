package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.ServiceResolver;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

public class ServiceResolverImpl implements ServiceResolver {
    public static final String WKND_SERVICE_USER = "wkndserviceuser";
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public ResourceResolver getServiceResourceResolver() throws LoginException {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, WKND_SERVICE_USER);
        return resourceResolverFactory.getServiceResourceResolver(paramMap);
    }
}
