/**
 * 
 */
package com.quartz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import com.quartz.app.dao.IntegrationTaskRepository;
import com.quartz.model.IntegrationTask;
import com.quartz.utils.QuartzUtils;

/**
 * The Class IntegrationTaskService.
 *
 * @author Haseeb
 */
@Component
public class IntegrationTaskService {
	
	/** The integration task repository. */
	@Autowired
	private IntegrationTaskRepository integrationTaskRepository;
	
	/** The scheduler. */
	@Autowired
	private Scheduler scheduler;

	/** The quartz utils. */
	@Autowired
	private QuartzUtils quartzUtils;
	
	/**
	 * Gets the task by id.
	 *
	 * @param taskId the task id
	 * @return the task by id
	 */
	public IntegrationTask getTaskById(Long taskId) {
		return integrationTaskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("integrationTask not found for this id :: " + taskId));
	}
	
	
	/**
	 * Gets the all tasks.
	 *
	 * @return the all tasks
	 */
	public List<IntegrationTask> getAllTasks(){
		return integrationTaskRepository.findAll();
	}
	
	/**
	 * Creates the task.
	 *
	 * @param integrationTask the integration task
	 * @return the integration task
	 * @throws SchedulerException the scheduler exception
	 */
	public IntegrationTask createTask(IntegrationTask integrationTask) throws SchedulerException {
		JobDetail jobDetail = quartzUtils.newJob(integrationTask);
		Trigger trigger;
		if (integrationTask.getFrequencyunit().equals("CRON")) {
			trigger = quartzUtils.createCronTrigger(jobDetail, integrationTask.getFrequency());
		} else {
			trigger = quartzUtils.createSimpleTrigger(jobDetail, Integer.valueOf(integrationTask.getFrequency()),
					integrationTask.getFrequencyunit());
		}
		scheduler.scheduleJob(jobDetail, trigger);
		return integrationTaskRepository.save(integrationTask);
	}
	
	
	/**
	 * Update task.
	 *
	 * @param taskId the task id
	 * @param taskDetails the task details
	 * @return the integration task
	 */
	public IntegrationTask updateTask(Long taskId,IntegrationTask taskDetails) {
		IntegrationTask integrationTask = integrationTaskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("integrationTask not found for this id :: " + taskId));
		integrationTask.setCurrentStatus(taskDetails.getCurrentStatus());
		integrationTask.setFrequency(taskDetails.getFrequency());
		integrationTask.setFrequencyunit(taskDetails.getFrequencyunit());
		integrationTask.setNextRun(taskDetails.getNextRun());
		integrationTask.setRunEnd(taskDetails.getRunEnd());
		integrationTask.setRunStart(taskDetails.getRunStart());
		integrationTask.setSourceSystem(taskDetails.getSourceSystem());
		integrationTask.setStatus(taskDetails.getStatus());
		integrationTask.setTaskId(taskId);
		final IntegrationTask updatedTask = integrationTaskRepository.save(integrationTask);
		JobDetail jobDetail = quartzUtils.newJob(integrationTask);
		try {
			if (scheduler.checkExists(jobDetail.getKey())) {
				System.out.println("Updating Job in scheduler, with taskId :: " + jobDetail.getKey());
				scheduler.deleteJob(jobDetail.getKey());
				Trigger newTrigger;
				if (integrationTask.getFrequencyunit().equals("CRON")) {
					newTrigger = quartzUtils.createCronTrigger(jobDetail, integrationTask.getFrequency());
				} else {
					newTrigger = quartzUtils.createSimpleTrigger(jobDetail,
							Integer.valueOf(integrationTask.getFrequency()), integrationTask.getFrequencyunit());
				}
				scheduler.scheduleJob(jobDetail, newTrigger);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return updatedTask;
	}

	
	/**
	 * Delete task.
	 *
	 * @param taskId the task id
	 * @return the map
	 */
	public Map<String, Boolean> deleteTask(Long taskId){
		IntegrationTask integrationTask = integrationTaskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("integrationTask not found for this id :: " + taskId));
		JobDetail jobDetail = quartzUtils.newJob(integrationTask);
		try {
			if (scheduler.checkExists(jobDetail.getKey())) {
				System.out.println("Removing Job from scheduler, with taskId :: " + jobDetail.getKey());
				scheduler.deleteJob(jobDetail.getKey());
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		integrationTaskRepository.delete(integrationTask);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
