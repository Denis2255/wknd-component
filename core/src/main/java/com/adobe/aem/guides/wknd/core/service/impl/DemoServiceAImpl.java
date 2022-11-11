package com.adobe.aem.guides.wknd.core.service.impl;


import com.adobe.aem.guides.wknd.core.service.DemoService;
import com.day.cq.wcm.api.Page;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

@Component(service = DemoService.class,immediate = true)
public class DemoServiceAImpl implements DemoService {
    private static final Logger LOGGER= LoggerFactory.getLogger(DemoServiceAImpl.class);
    @Activate
    public void activate(ComponentContext componentContext){
        LOGGER.info("_________Inside activate__________");
        LOGGER.info("\n {} = {}", componentContext.getBundleContext().getBundle().getBundleId(),componentContext.getBundleContext().getBundle().getSymbolicName());
    }
    @Deactivate
    public void deactivate(){
        LOGGER.info("_________Inside deactivate__________");
    }
    @Modified
    public void modified(ComponentContext componentContext){
        LOGGER.info("_________Inside modified__________");
    }

    @Override
    public Iterator<Page> getPages() {
        return null;
    }
}
