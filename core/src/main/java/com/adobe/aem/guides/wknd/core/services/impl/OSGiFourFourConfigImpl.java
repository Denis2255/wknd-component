package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.OSGiFourOneConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@Component(service = OSGiFourOneConfig.class, immediate = true)
@Designate(ocd = OSGiFourFourConfigImpl.SeparateFourFourConfig.class)
public class OSGiFourFourConfigImpl implements OSGiFourOneConfig {

    @ObjectClassDefinition(name = "WKND - FourFour Config",
            description = "four")
    public @interface SeparateFourFourConfig {
        @AttributeDefinition(
                name = "Service name",
                description = "Enter name",
                type = AttributeType.STRING)
        public String serviceName();
    }
    private String serviceName;

    @Activate
    public void activate(SeparateFourFourConfig separateFourFourConfig) {
        serviceName = separateFourFourConfig.serviceName();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
