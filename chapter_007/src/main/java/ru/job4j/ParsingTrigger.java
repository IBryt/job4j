package ru.job4j;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.storage.Config;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class ParsingTrigger {
    private String cronValue;
    private Scheduler sched;
    private static final Logger LOG = LoggerFactory.getLogger(ParsingTrigger.class);

    public ParsingTrigger() {
        this.cronValue = new Config().get("cron.time");
    }

    public void start() {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            sched = sf.getScheduler();
            JobDetail job = newJob(TriggerJob.class)
                    .withIdentity("parsing", "group")
                    .build();

            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger", "group")
                    .withSchedule(cronSchedule(this.cronValue))
                    .build();
            sched.scheduleJob(job, trigger);
            sched.start();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void close() {
        try {
            sched.shutdown(true);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
