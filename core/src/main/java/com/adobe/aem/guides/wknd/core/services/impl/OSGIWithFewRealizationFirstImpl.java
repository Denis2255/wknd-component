package com.adobe.aem.guides.wknd.core.services.impl;


import com.adobe.aem.guides.wknd.core.services.OSGIWithFewRealization;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = OSGIWithFewRealization.class, immediate = true)
@Designate(ocd = OSGIWithFewRealizationFirstImpl.ConfigFewRealizationFirst.class)
public class OSGIWithFewRealizationFirstImpl implements OSGIWithFewRealization {

    private ConfigFewRealizationFirst configuration;

    @Activate
    @Modified
    public void activate(ConfigFewRealizationFirst configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getServiceValue() {
        return configuration.serviceValue();
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Config With Few Realization First")
    public @interface ConfigFewRealizationFirst {
        @AttributeDefinition(
                name = "Service value",
                type = AttributeType.STRING)
        String serviceValue();
    }
}
