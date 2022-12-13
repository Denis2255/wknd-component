package com.adobe.aem.guides.wknd.core.listeners;

import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.util.Optional;


@Component(service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/CHANGED",
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/REMOVED",
                EventConstants.EVENT_FILTER + "=(path=/content/wknd/us/en/1/*)"
        })
public class OSGiEventHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OSGiEventHandler.class);

    @Reference
    private ServiceResourceResolver serviceResourceResolver;

    @Override
    public void handleEvent(Event event) {
        LOG.info("\n Resource event: {} at: {}", event.getTopic(), event.getProperty(SlingConstants.PROPERTY_PATH));
        try (ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver()) {
//            Optional.ofNullable(resourceResolver.getResource(event.getProperty(SlingConstants.PROPERTY_PATH).toString()))
//                    .map(r -> r.adaptTo(Node.class))
//                    .map(n -> n.setProperty("eventhandlertask", "Event" + event.getTopic() + "by" + resourceResolver.getUserID()))
//                    .orElse(null);
//
//
            Resource resource = resourceResolver.getResource(event.getProperty(SlingConstants.PROPERTY_PATH).toString());
            Node node = resource.adaptTo(Node.class);
            node.setProperty("eventhandlertask", "Event" + event.getTopic() + "by" + resourceResolver.getUserID());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
