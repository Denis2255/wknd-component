package com.adobe.aem.guides.wknd.core.services.impl;


import com.adobe.aem.guides.wknd.core.services.OSGIWithConfigPublish;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = OSGIWithConfigPublish.class, immediate = true)
@Designate(ocd = OSGIWithConfigPublishImpl.ConfigOnlyPublish.class)
public class OSGIWithConfigPublishImpl implements OSGIWithConfigPublish {

    private static final Logger LOG = LoggerFactory.getLogger(OSGIWithConfigPublishImpl.class);
    private ConfigOnlyPublish configuration;

    @Activate
    @Modified
    public void activate(ConfigOnlyPublish configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getWords() {
        return configuration.serviceValue();
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Config only on Publish instance ")
    public @interface ConfigOnlyPublish {
        @AttributeDefinition(
                name = "value",
                type = AttributeType.STRING)
        String serviceValue();
    }
}
