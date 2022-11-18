package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.OSGiFourOneConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = OSGiFourOneConfig.class, immediate = true)
@Designate(ocd = OSGiFourTwoConfigImpl.SeparateFourTwoConfig.class)
public class OSGiFourTwoConfigImpl implements OSGiFourOneConfig {
    @ObjectClassDefinition(name = "WKND - FourTwo Config",
            description = "second")
    public @interface SeparateFourTwoConfig {
        @AttributeDefinition(
                name = "Service name",
                description = "Enter name",
                type = AttributeType.STRING)
        public String serviceName() default "second config";
    }

    private String serviceName;

    @Activate
    public void activate(SeparateFourTwoConfig separateFourTwoConfig) {
        serviceName = separateFourTwoConfig.serviceName();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
