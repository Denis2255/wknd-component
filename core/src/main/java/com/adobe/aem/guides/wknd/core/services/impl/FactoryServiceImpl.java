package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.FactoryService;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.util.ArrayList;
import java.util.List;

@Component(service = FactoryService.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = FactoryServiceImpl.OSGiWithFactoryConfig.class, factory = true)
public class FactoryServiceImpl implements FactoryService {

    private OSGiWithFactoryConfig serviceConfig;

    @Activate
    @Modified
    protected void activate(final OSGiWithFactoryConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Override
    public String getServiceValue() {
        return serviceConfig.serviceValue();
    }


    @ObjectClassDefinition(name = "WKND_CUSTOM - OSGi Factory")
    public @interface OSGiWithFactoryConfig {

        @AttributeDefinition(
                name = "Service ",
                type = AttributeType.STRING)
        String serviceValue();
    }
}
