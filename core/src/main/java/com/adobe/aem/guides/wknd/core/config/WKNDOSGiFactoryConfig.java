package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@ObjectClassDefinition(name = "AEM WKND - OSGi Factory",
        description = "He")
public @interface WKNDOSGiFactoryConfig {

    @AttributeDefinition(
            name = "Service ID",
            description = "Enter service ID",
            type = AttributeType.INTEGER)
    int configID();

    @AttributeDefinition(
            name = "Service Name",
            description = "Enter service name",
            type = AttributeType.STRING)
    public String serviceName() default "WKND Service";

    @AttributeDefinition(
            name = "Service URL",
            description = "Enter service url",
            type = AttributeType.STRING)
    String serviceURL() default "localhost";

}
