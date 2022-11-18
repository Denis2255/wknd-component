package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;


@ObjectClassDefinition(name = "AEM WKND - OSGi Factory",
        description = "He")
public @interface WKNDOSGiFactoryConfig {

    @AttributeDefinition(
            name = "Service Name",
            description = "Enter service name",
            type = AttributeType.STRING)
    public String serviceName() default "WKND Service";
    @AttributeDefinition(
            name = "Service Count",
            description = "Enter service count",
            type = AttributeType.INTEGER)
    int getServiceCount() default 0;

    @AttributeDefinition(
            name = "Live Data",
            description = "Check this to get live data.",
            type = AttributeType.BOOLEAN)
    boolean getLiveData() default false;
    @AttributeDefinition(
            name = "Countries",
            description = "Add countries locales for which you want to run this service.",
            type = AttributeType.STRING)
    String[] getCountries();

    @AttributeDefinition(
            name = "Run Modes",
            description = "Select Run Mode.",
            options = {
                    @Option(label = "Author", value = "author"),
                    @Option(label = "Publish", value = "publish"),
                    @Option(label = "Both", value = "both")
            },
            type = AttributeType.STRING)
    String getRunMode() default "both";

}
