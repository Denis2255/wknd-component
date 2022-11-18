package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.config.WKNDFourOneConfig;
import com.adobe.aem.guides.wknd.core.config.WKNDOSGiConfig;
import com.adobe.aem.guides.wknd.core.services.OSGiFourOneConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;


@Component(service = OSGiFourOneConfig.class, immediate = true)
@Designate(ocd = WKNDFourOneConfig.class)
public class OSGiFourOneConfigImpl implements OSGiFourOneConfig {
    private String serviceName;

    @Activate
    protected void activate(WKNDFourOneConfig wkndFourOneConfig) {
        serviceName = wkndFourOneConfig.serviceName();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
