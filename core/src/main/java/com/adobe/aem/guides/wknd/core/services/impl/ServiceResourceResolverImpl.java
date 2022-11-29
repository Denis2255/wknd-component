package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;
@Component(service = ServiceResourceResolver.class,immediate = true)
public class ServiceResourceResolverImpl implements ServiceResourceResolver {
    public static final String WKND_SERVICE_USER = "serviceuserservlet";
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public ResourceResolver getServiceResourceResolver() throws LoginException {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, WKND_SERVICE_USER);
        ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
        return resolver;
    }
}
