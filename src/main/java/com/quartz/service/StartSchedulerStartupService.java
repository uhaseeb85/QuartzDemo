/**
 * 
 */
package com.quartz.service;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.quartz.job.SampleJob;
import com.quartz.model.JobData;

/**
 * The listener interface for receiving myApplication events.
 * The class that is interested in processing a myApplication
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMyApplicationListener<code> method. When
 * the myApplication event occurs, that object's appropriate
 * method is invoked.
 *
 * @author Haseeb
 */

class StartSchedulerStartupService {

	/** The scheduler. */
	@Autowired
	private Scheduler scheduler;

	/**
	 * On application event.
	 *
	 * @param event the event
	 */
	//@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("ApplicationListener#onApplicationEvent()");

		List<JobData> listOfJobs = getListOfJobs();
		listOfJobs.forEach(jobData -> {
			JobDetail jobDetail=newJob(jobData);
			try {
				if (!scheduler.checkExists(jobDetail.getKey())){
					if ("CRON".equals(jobData.getJobfrequencyUnit())){
						scheduler.scheduleJob(jobDetail, createCronTrigger(jobDetail,jobData.getCronExpression()));
					} else {
						scheduler.scheduleJob(jobDetail, createSimpleTrigger(jobDetail,jobData.getJobfrequency(),jobData.getJobfrequencyUnit()));
					}
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		});
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the list of jobs.
	 *
	 * @return the list of jobs
	 */
	private List<JobData> getListOfJobs() {
		JobData j1 = new JobData("job1", 5, "jobInfo1","MINUTE");
		JobData j2 = new JobData("job2", 10, "jobInfo2","SECOND");
		JobData j3 = new JobData("job3", 3, "jobInfo3","SECOND");
		JobData j4 = new JobData("job4", 30, "jobInfo4","SECOND");
		JobData j5 = new JobData("job5", "0 * * ? * *", "jobInfo5","CRON");
		List<JobData> listOfJobs = new ArrayList<>();
		listOfJobs.add(j1);
		listOfJobs.add(j2);
		listOfJobs.add(j3);
		listOfJobs.add(j4);
		listOfJobs.add(j5);
		return listOfJobs;
	}
	
    /**
     * New job.
     *
     * @param job the job
     * @return the job detail
     */
    private JobDetail newJob(JobData job) {
        return JobBuilder.newJob().ofType(SampleJob.class).storeDurably()
                .withIdentity(JobKey.jobKey(job.getJobname()))
                .withDescription(job.getJobinfo())
                .build();
    }
    
    /**
     * Creates the cron trigger.
     *
     * @param jobDetail the job detail
     * @param cronExpression the cron expression
     * @return the cron trigger
     */
    private CronTrigger createCronTrigger(JobDetail jobDetail, String cronExpression) {
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
    private SimpleTrigger createSimpleTrigger(JobDetail jobDetail, int frequency, String frequencyUnit) {
    	SimpleScheduleBuilder schedule = null;
    	switch (frequencyUnit) {
    	case "SECOND":
    		schedule = SimpleScheduleBuilder.repeatSecondlyForever(frequency);
    		break;
    	case "MINUTE":
    		schedule =SimpleScheduleBuilder.repeatMinutelyForever(frequency);
    		break;
    	case "HOUR":
    		schedule = SimpleScheduleBuilder.repeatHourlyForever(frequency);
    		break;
    	}
    	SimpleTrigger trigger =  TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
                .withSchedule(schedule.withMisfireHandlingInstructionNowWithExistingCount())
                .build();
    	return trigger;
    }

}
