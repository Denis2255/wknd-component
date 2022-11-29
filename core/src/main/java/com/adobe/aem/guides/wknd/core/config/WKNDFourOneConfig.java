package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "WKND - FourOne Config",
        description = "One Config")
public @interface WKNDFourOneConfig {
    @AttributeDefinition(
            name = "Service name",
            description = "Enter name",
            type = AttributeType.STRING)
    public String serviceName() default "First config";
}
