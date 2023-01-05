package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.config.WKNDOSGiFactoryConfig;
import com.adobe.aem.guides.wknd.core.services.OSGiFactoryConfig;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component(service = OSGiFactoryConfig.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = WKNDOSGiFactoryConfig.class, factory = true)
public class OSGiFactoryConfigImpl implements OSGiFactoryConfig {

    private WKNDOSGiFactoryConfig serviceConfig;
    private List<OSGiFactoryConfig> configsList;

    @Activate
    protected void activate(final WKNDOSGiFactoryConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Reference(service = OSGiFactoryConfig.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindOSGiFactoryConfig(final OSGiFactoryConfig config) {
        if (configsList == null) {
            configsList = new ArrayList<>();
        }
        configsList.add(config);

    }

    public void unbindOSGiFactoryConfig(final OSGiFactoryConfig config) {
        configsList.remove(config);
    }

    @Override
    public String getServiceName() {
        return serviceConfig.serviceName();
    }

    @Override
    public int getServiceCount() {
        return serviceConfig.getServiceCount();
    }

    @Override
    public boolean isLiveData() {
        return serviceConfig.getLiveData();
    }

    @Override
    public String[] getCountries() {
        return serviceConfig.getCountries();
    }

    @Override
    public String getRunModes() {
        return serviceConfig.getRunMode();
    }
}
