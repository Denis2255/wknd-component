package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.OSGiFourOneConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@Component(service = OSGiFourOneConfig.class, immediate = true)
@Designate(ocd = OSGiFourThreeConfigImpl.SeparateFourThreeConfig.class)
public class OSGiFourThreeConfigImpl implements OSGiFourOneConfig {

    @ObjectClassDefinition(name = "WKND - FourThree Config",
            description = "third")
    public @interface SeparateFourThreeConfig {
        @AttributeDefinition(
                name = "Service name",
                description = "Enter name",
                type = AttributeType.STRING)
        public String serviceName();
    }
    private String serviceName;

    @Activate
    public void activate(SeparateFourThreeConfig separateFourThreeConfig) {
        serviceName = separateFourThreeConfig.serviceName();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
