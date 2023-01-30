package com.adobe.aem.guides.wknd.core.services.impl;


import com.adobe.aem.guides.wknd.core.services.MultipleRealizationService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.util.List;

@Component(service = MultipleRealizationService.class, immediate = true)
@Designate(ocd = MultipleRealizationServiceFirstImpl.ConfigMultipleRealizationFirst.class)
public class MultipleRealizationServiceFirstImpl implements MultipleRealizationService {

    private ConfigMultipleRealizationFirst configuration;

    @Activate
    @Modified
    public void activate(ConfigMultipleRealizationFirst configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getServiceValue() {
        return configuration.serviceValue();
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Config With Few Realization First")
    public @interface ConfigMultipleRealizationFirst {
        @AttributeDefinition(
                name = "Service value",
                type = AttributeType.STRING)
        String serviceValue();
    }
}
