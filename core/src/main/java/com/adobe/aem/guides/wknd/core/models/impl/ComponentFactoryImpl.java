package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.ComponentFactory;
import com.adobe.aem.guides.wknd.core.services.OSGiFactoryConfig;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = SlingHttpServletRequest.class,
adapters = ComponentFactory.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ComponentFactoryImpl implements ComponentFactory {
    @OSGiService
    OSGiFactoryConfig osGiFactoryConfig;

    public String getServiceName() {
        return osGiFactoryConfig.getServiceName();
    }
    public int getServiceCount(){
        return osGiFactoryConfig.getServiceCount();
    }
    public boolean isLiveData(){
        return osGiFactoryConfig.isLiveData();
    }
    public String[] getCountries(){return osGiFactoryConfig.getCountries();
    }
    public String getRunModes(){
        return osGiFactoryConfig.getRunModes();
    }
}
