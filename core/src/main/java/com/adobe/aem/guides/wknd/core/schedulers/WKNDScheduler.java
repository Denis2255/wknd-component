package com.adobe.aem.guides.wknd.core.schedulers;


import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = Runnable.class, immediate = true)
@Designate(ocd = WKNDScheduler.ServiceConfigurationWKNDScheduler.class)
public class WKNDScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(WKNDScheduler.class);

    private int schedulerId;

    @Reference
    private Scheduler scheduler;

    @Activate
    @Modified
    protected void activate(ServiceConfigurationWKNDScheduler config) {
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    private void addScheduler(ServiceConfigurationWKNDScheduler config) {
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());
        scheduleOptions.name(String.valueOf(schedulerId));
        scheduleOptions.canRunConcurrently(false);
        scheduler.schedule(this, scheduleOptions);
        LOG.info("\n ________________________________Scheduler added_______________________________");
        ScheduleOptions scheduleOptionsNow = scheduler.NOW(3, 5);
        scheduler.schedule(this, scheduleOptionsNow);
    }

    @Deactivate
    protected void deactivate(ServiceConfigurationWKNDScheduler config) {
        removeScheduler();
    }

    private void removeScheduler() {
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    @Override
    public void run() {
LOG.info("_____________________________RUN METHOD_______________________________________");
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Scheduler Configuration")
    public @interface ServiceConfigurationWKNDScheduler {
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
