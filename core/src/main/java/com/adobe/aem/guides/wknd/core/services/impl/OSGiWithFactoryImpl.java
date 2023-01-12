package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.OSGiWithFactory;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.util.ArrayList;
import java.util.List;

@Component(service = OSGiWithFactory.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = OSGiWithFactoryImpl.OSGiWithFactoryConfig.class, factory = true)
public class OSGiWithFactoryImpl implements OSGiWithFactory {

    private OSGiWithFactoryConfig serviceConfig;
    private List<OSGiWithFactory> configsList;

    @Activate
    @Modified
    protected void activate(final OSGiWithFactoryConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Reference(service = OSGiWithFactory.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindOSGiFactoryConfig(OSGiWithFactory config) {
        if (configsList == null) {
            configsList = new ArrayList<>();
        }
        configsList.add(config);
    }

    public void unbindOSGiFactoryConfig(final OSGiWithFactory config) {
        configsList.remove(config);
    }

    @Override
    public List<String> getAllConfigs() {
        List<String> allConfigs = new ArrayList<>();
        for ( OSGiWithFactory i: configsList){
            allConfigs.add(i.getServiceValue());
        }
        return allConfigs;
    }

    @Override
    public String getServiceValue() {
        return serviceConfig.serviceValue();
    }


    @ObjectClassDefinition(name = "WKND_CUSTOM - OSGi Factory")
    public @interface OSGiWithFactoryConfig {

        @AttributeDefinition(
                name = "Service Name",
                type = AttributeType.STRING)
        String serviceValue();

    }
}
