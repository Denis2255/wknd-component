package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.OSGiConfigDemo;
import com.adobe.aem.guides.wknd.core.models.OSGiResolver;
import com.adobe.aem.guides.wknd.core.services.ComponentsInfoService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;


@Model(adaptables = SlingHttpServletRequest.class,
        adapters = OSGiResolver.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OSGiResovlerImpl implements OSGiResolver {
    @OSGiService
    private ComponentsInfoService osgiConfig;

    @Override
    public String getServiceName() {
        return osgiConfig.getServiceName();
    }

    @Override
    public String getIdUser() throws LoginException {
        return osgiConfig.getIdUser();
    }
}
