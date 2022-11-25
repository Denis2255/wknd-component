package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "WKND - Servlet Path Config",
        description = "One Config")
public @interface ServletPathConfig {
    @AttributeDefinition(
            name = "Service path",
            description = "Enter name",
            type = AttributeType.STRING)
    public String pathGetNode() default "/content/wknd/us/en";
}
