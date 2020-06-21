/**
 * 
 */
package com.quartz.app.utils;

import java.util.Calendar;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import com.quartz.app.job.SampleJob;
import com.quartz.app.model.IntegrationTask;

/**
 * @author Haseeb
 *
 */
@Component
public class QuartzUtils {
	
	/**
     * New job.
     *
     * @param job the job
     * @return the job detail
     */
    public JobDetail newJob(IntegrationTask job) {
        return JobBuilder.newJob().ofType(SampleJob.class).storeDurably()
                .withIdentity(JobKey.jobKey(String.valueOf(job.getTaskId())))
                .withDescription(job.getIntegrationId())
                .build();
    }

	/**
     * Creates the cron trigger.
     *
     * @param jobDetail the job detail
     * @param cronExpression the cron expression
     * @return the cron trigger
     */
    public CronTrigger createCronTrigger(JobDetail jobDetail, String cronExpression) {
    	CronTrigger crontrigger = TriggerBuilder
				.newTrigger()
				.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
				.startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionFireAndProceed())
				.build();
		return crontrigger;
	}

    /**
     * Creates the simple trigger.
     *
     * @param jobDetail the job detail
     * @param frequency the frequency
     * @param frequencyUnit the frequency unit
     * @return the simple trigger
     */
    public SimpleTrigger createSimpleTrigger(JobDetail jobDetail, int frequency, String frequencyUnit) {
    	SimpleScheduleBuilder schedule = null;
    	Calendar c = Calendar.getInstance();
    	switch (frequencyUnit) {
    	case "SECOND":
    		schedule = SimpleScheduleBuilder.repeatSecondlyForever(frequency);
    		c.add(Calendar.SECOND, frequency);
    		break;
    	case "MINUTE":
    		schedule =SimpleScheduleBuilder.repeatMinutelyForever(frequency);
    		c.add(Calendar.MINUTE, frequency);
    		break;
    	case "HOUR":
    		schedule = SimpleScheduleBuilder.repeatHourlyForever(frequency);
    		c.add(Calendar.HOUR, frequency);
    		break;
    	}
    	SimpleTrigger trigger =  TriggerBuilder.newTrigger().forJob(jobDetail)
    			.startAt(c.getTime())
                .withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
                .withSchedule(schedule.withMisfireHandlingInstructionNowWithExistingCount())
                .build();
    	return trigger;
    }

}
