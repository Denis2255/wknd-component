package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.OSGiConfigDemo;
import com.adobe.aem.guides.wknd.core.services.OSGiConfig;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = OSGiConfigDemo.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OSGiConfigDemoImpl implements OSGiConfigDemo {
    @OSGiService
    private OSGiConfig osgiConfig;

    @Override
    public String getServiceName() {
        return osgiConfig.getServiceName();
    }

    @Override
    public int getServiceCount() {
        return osgiConfig.getServiceCount();
    }

    @Override
    public boolean isLiveData() {
        return osgiConfig.isLiveData();
    }

    @Override
    public String[] getCountries() {
        return osgiConfig.getCountries();
    }

    @Override
    public String getRunModes() {
        return osgiConfig.getRunModes();
    }
}
