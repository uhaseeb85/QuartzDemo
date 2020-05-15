/**
 * 
 */
package com.quartz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.quartz.app.dao.IntegrationTaskDAO;
import com.quartz.model.IntegrationTask;
import com.quartz.utils.QuartzUtils;


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
@Component
@Order(0)
class StartSchedulerTasksFromDB implements ApplicationListener<ApplicationReadyEvent> {

	/** The scheduler. */
	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private QuartzUtils quartzUtils;
	
	/** The integration task controller. */
	@Autowired
	private IntegrationTaskDAO integrationTaskDAO;

	/**
	 * On application event.
	 *
	 * @param event the event
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("ApplicationListener#onApplicationEvent()");
		
		removeInactiveJobsFromScheduler();
		List<IntegrationTask> listOfJobs = getValidJobs();
		System.out.println("Number of Active Jobs :: "+listOfJobs.size());
		listOfJobs.forEach(jobData -> {
			JobDetail jobDetail=quartzUtils.newJob(jobData);
			try {
				if (!scheduler.checkExists(jobDetail.getKey())){
					if ("CRON".equals(jobData.getFrequencyunit())){
						scheduler.scheduleJob(jobDetail, quartzUtils.createCronTrigger(jobDetail,jobData.getFrequency()));
					} else {
						scheduler.scheduleJob(jobDetail, quartzUtils.createSimpleTrigger(jobDetail,Integer.valueOf(jobData.getFrequency()),jobData.getFrequencyunit()));
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
     * Removes the inactive jobs from scheduler.
     */
    private void removeInactiveJobsFromScheduler() {
    	List<IntegrationTask> allJobs = integrationTaskDAO.getAllTasks();
    	List<IntegrationTask> inActiveJobs = allJobs.stream().filter(j -> j.getStatus().equals("INACTIVE")).collect(Collectors.toList());
    	inActiveJobs.forEach(job ->{
    		JobDetail jobDetail=quartzUtils.newJob(job);
    		try {
				if (scheduler.checkExists(jobDetail.getKey())) {
					System.out.println("Removing Inactive Jobs from scheduler, with taskId :: "+jobDetail.getKey());
					scheduler.deleteJob(jobDetail.getKey());
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
    	});
	}

	/**
     * Gets the valid jobs.
     *
     * @return the valid jobs
     */
    private List<IntegrationTask> getValidJobs() {
    	List<IntegrationTask> listOfJobs = integrationTaskDAO.getAllTasks();
    	List<IntegrationTask> activeJobs = listOfJobs.stream().filter(j -> j.getStatus().equals("ACTIVE")).collect(Collectors.toList());
    	List<IntegrationTask> validJobs = new ArrayList<>();
    	for (IntegrationTask task : activeJobs) {
    		if (task.getFrequencyunit().equals("CRON")) {
    			if (org.quartz.CronExpression.isValidExpression(task.getFrequency())){
    				validJobs.add(task);
    			}
    		} else {
    			validJobs.add(task);
    		}
    	}
		return validJobs;
	}

}
