package com.adobe.aem.guides.wknd.core.listeners;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

@Component(service = EventListener.class,immediate = true)
public class JCREventHandler implements EventListener{

    private static final Logger LOG = LoggerFactory.getLogger(JCREventHandler.class);
    private Session session;

    @Reference
    SlingRepository slingRepository;

    @Activate
    @Modified
    public void activate() throws Exception {
        try {
            session = slingRepository.loginService("serviceuserservlet", null);
            session.getWorkspace().getObservationManager().addEventListener(
                    this,
                    Event.NODE_ADDED | Event.PROPERTY_ADDED,
                    "/content/wknd/us/en/1",
                    true,
                    null,
                    null,
                    false
            );
        } catch (RepositoryException e) {
            LOG.info(e.getMessage(), e);
        }
    }

    @Override
    public void onEvent(EventIterator eventIterator) {
        try{
            while (eventIterator.hasNext()){
                LOG.info("\n Path: {}", eventIterator.nextEvent().getPath());
            }
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
        }
    }
}
