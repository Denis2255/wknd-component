package com.adobe.aem.guides.wknd.core.listeners;

import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.crx.JcrConstants;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@Component(service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC,
        })
public class ReplicationEventHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ReplicationEventHandler.class);

    @Reference
    private ServiceResourceResolver serviceResourceResolver;

    @Override
    public void handleEvent(Event event) {
        String statusReplication = "";
        String getPath = ReplicationAction.fromEvent(event).getPath();
//        String getPath = "/content/wknd/us/eqw";
        LOG.info("\n Event Type : {} ", event.getTopic());
        ReplicationActionType replicationType = ReplicationAction.fromEvent(event).getType();
        if (replicationType.equals(ReplicationActionType.ACTIVATE)) {
            statusReplication = ReplicationActionType.ACTIVATE.toString();
            LOG.info("\n Page Published :  {}", ReplicationAction.fromEvent(event).getPath());
        }
        if (replicationType.equals(ReplicationActionType.DEACTIVATE)) {
            statusReplication = ReplicationActionType.DEACTIVATE.toString();
            LOG.info("\n Page Deactivated :  {}", ReplicationAction.fromEvent(event).getPath());
        }
        renameNode(statusReplication, getPath);
    }

    public void renameNode(String statusReplication, String getPath) {
        try (ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver()) {
            String pageJcrContent = getPath + "/" + JcrConstants.JCR_CONTENT;
            Optional.of(resourceResolver)
                    .map(rr -> rr.getResource(pageJcrContent))
                    .map(r -> r.adaptTo(ModifiableValueMap.class))
                    .map(mvm -> mvm.replace("title",
                            (mvm.get("jcr:title") + " " + statusReplication + " " + mvm.get("cq:lastReplicated").toString())));
//            Node node = resource.adaptTo(Node.class);
//            Node jcrNode = node.getNode(JcrConstants.JCR_CONTENT);
//            String nameNode = jcrNode.getProperty("jcr:title").getValue().getString();
//            String timeReplicationNode = jcrNode.getProperty("cq:lastReplicated").getValue().getString();
//            jcrNode.setProperty("title", nameNode + " " + statusReplication + " " + timeReplicationNode);
            resourceResolver.commit();
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
    }
}
