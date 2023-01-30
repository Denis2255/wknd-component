package com.adobe.aem.guides.wknd.core.services.impl;


import com.adobe.aem.guides.wknd.core.services.MultipleRealizationService;
import com.adobe.aem.guides.wknd.core.services.OSGIWithoutConfigService;
import com.adobe.aem.guides.wknd.core.services.FactoryService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Component(service = OSGIWithoutConfigService.class, immediate = true)
public class OSGIWithoutConfigServiceImpl implements OSGIWithoutConfigService {

    private static final Logger LOG = LoggerFactory.getLogger(OSGIWithoutConfigServiceImpl.class);

    @Reference(target = "(component.name=com.adobe.aem.guides.wknd.core.services.impl.MultipleRealizationServiceFirstImpl)")
    private MultipleRealizationService multipleRealizationServiceFirst;

    @Reference(target = "(component.name=com.adobe.aem.guides.wknd.core.services.impl.MultipleRealizationServiceSecondImpl)")
    private MultipleRealizationService multipleRealizationServiceSecond;

    @Reference
    private List<MultipleRealizationService> listOSGiWithFewImpl;

    @Reference
    private MultipleRealizationService multipleRealizationService;

    @Reference(target = "(serviceValue=factory value 1)")
    private FactoryService factoryServiceFirst;

    @Reference(target = "(serviceValue=factory value 2)")
    private FactoryService factoryServiceSecond;

    @Reference
    private List<FactoryService> factoryService;

    @Activate
    public void activate() {
        String s = " ";
    }

    @Override
    public String getWord() {
        return "hello" +
                "\n" + multipleRealizationServiceFirst.getServiceValue() +
                "\n" + multipleRealizationServiceSecond.getServiceValue() +
                "\n Factory: " + factoryServiceFirst.getServiceValue() + ", " + factoryServiceSecond.getServiceValue();
    }
}
