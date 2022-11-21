package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.OSGiComponentFour;
import com.adobe.aem.guides.wknd.core.services.OSGiFourOneConfig;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
adapters = OSGiComponentFour.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OSGiComponentFourImpl implements OSGiComponentFour {

    @OSGiService
   private List<OSGiFourOneConfig> osgiFourOneConfig;

    @Override
    public List<OSGiFourOneConfig> getServiceList() {
        return osgiFourOneConfig;
    }
}
