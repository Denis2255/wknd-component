package com.adobe.aem.guides.wknd.core.services.impl;


import com.adobe.aem.guides.wknd.core.services.MultipleRealizationService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = MultipleRealizationService.class, immediate = true)
@Designate(ocd = MultipleRealizationServiceSecondImpl.ConfigMultipleRealizationSecond.class)
public class MultipleRealizationServiceSecondImpl implements MultipleRealizationService {

    private ConfigMultipleRealizationSecond configuration;

    @Activate
    @Modified
    public void activate(ConfigMultipleRealizationSecond configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getServiceValue() {
       return configuration.serviceValue();
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Config With Few Realization Second")
    public @interface ConfigMultipleRealizationSecond {
        @AttributeDefinition(
                name = "Service value",
                type = AttributeType.STRING)
        String serviceValue();
    }
}
