package com.adobe.aem.guides.wknd.core.services.impl;


import com.adobe.aem.guides.wknd.core.services.OSGIWithFewRealization;
import com.adobe.aem.guides.wknd.core.services.OSGIWithoutConfig;
import com.adobe.aem.guides.wknd.core.services.OSGiWithFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


@Component(service = OSGIWithoutConfig.class, immediate = true)
public class OSGIWithoutConfigImpl implements OSGIWithoutConfig {

    private static final Logger LOG = LoggerFactory.getLogger(OSGIWithoutConfigImpl.class);

    @Reference(target = "(component.name=com.adobe.aem.guides.wknd.core.services.impl.OSGIWithFewRealizationFirstImpl)")
    OSGIWithFewRealization osgiWithFewRealizationFirst;

    @Reference(target = "(component.name=com.adobe.aem.guides.wknd.core.services.impl.OSGIWithFewRealizationSecondImpl)")
    OSGIWithFewRealization osgiWithFewRealizationSecond;

    @Reference
    OSGIWithFewRealization osgiWithFewRealization;

    @Reference(target = "(serviceValue=factory value 1)")
    OSGiWithFactory osGiWithFactoryFirst;

    @Reference(target = "(serviceValue=factory value 2)")
    OSGiWithFactory osGiWithFactorySecond;

    @Reference
    OSGiWithFactory osGiWithFactory;

    @Override
    public String getWord() {
        return "hello" +
                "\n" + osgiWithFewRealizationFirst.getServiceValue() +
                "\n" + osgiWithFewRealizationSecond.getServiceValue() +
                "\n List " +
                "\n Factory: " + osGiWithFactoryFirst.getServiceValue() + ", " + osGiWithFactorySecond.getServiceValue()
                + "\n List factory: "  + osGiWithFactory.getAllConfigs();
    }
}
