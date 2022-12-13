package com.adobe.aem.guides.wknd.core.schedulers;


import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component(service = Job.class, immediate = true)
@Designate(ocd = WKNDSchedulerJobs.ServiceConfigurationWKNDSchedulerJob.class)
public class WKNDSchedulerJobs implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(WKNDSchedulerJobs.class);

    private int schedulerJobId;

    @Reference
    private Scheduler scheduler;

    @Activate
    protected void activate(ServiceConfigurationWKNDSchedulerJob config) {
        addSchedulerJob(config);
        schedulerJobId = config.schedulerName().hashCode();
    }

    @Deactivate
    protected void deactivate(ServiceConfigurationWKNDSchedulerJob config) {
        removeSchedulerJob();
    }

    private void removeSchedulerJob() {
        scheduler.unschedule(String.valueOf(schedulerJobId));
    }

    private void addSchedulerJob(ServiceConfigurationWKNDSchedulerJob config) {

        ScheduleOptions in = scheduler.EXPR("0 03 17 1/1 * ? *");
        Map<String, Serializable> inMap = new HashMap<>();
        inMap.put("country", "IN");
        inMap.put("url", "www.in.com");
        in.config(inMap);
        scheduler.schedule(this, in);

        ScheduleOptions de = scheduler.EXPR("0 04 17 1/1 * ? *");
        Map<String, Serializable> deMap = new HashMap<>();
        deMap.put("country", "DE");
        deMap.put("url", "www.de.com");
        de.config(deMap);
        scheduler.schedule(this, de);

        ScheduleOptions us = scheduler.EXPR("0 05 17 1/1 * ? *");
        Map<String, Serializable> usMap = new HashMap<>();
        usMap.put("country", "US");
        usMap.put("url", "www.us.com");
        us.config(usMap);
        scheduler.schedule(this, us);
    }

    @Override
    public void execute(JobContext jobContext) {
        LOG.info("\n _______________________Country {}: URL {} ", jobContext.getConfiguration().get("country"));
    }


    @ObjectClassDefinition(name = "WKND_CUSTOM - Scheduler Job Configuration")
    public @interface ServiceConfigurationWKNDSchedulerJob {
        @AttributeDefinition(
                name = "Scheduler_name",
                description = "Name of scheduler",
                type = AttributeType.STRING)
        String schedulerName() default "Custom";

        @AttributeDefinition(
                name = "Cron_expression",
                type = AttributeType.STRING)
        String cronExpression() default "0/20 * * * * ?";
    }
}
