package com.adobe.aem.guides.wknd.core.listeners;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Component(service = {EventHandler.class},
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
                EventConstants.EVENT_FILTER + "=(path=/content/wknd/us/en/servlest-demo/*)"
        })
public class WKNDJobCreater implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WKNDJobCreater.class);

    @Reference
    JobManager jobManager;

    @Override
    public void handleEvent(Event event) {
        try {
            Map<String, Object> jobProperties = new HashMap<String, Object>();
            jobProperties.put("event", event.getTopic());
            jobProperties.put("path", event.getProperty(SlingConstants.PROPERTY_PATH));
            jobProperties.put("heropage", "heroPage");
            Job job = jobManager.addJob("wknd/job", jobProperties);
        } catch (Exception e) {
            LOG.error("\n Exception is : {} ", e.getMessage());
        }
    }
}
