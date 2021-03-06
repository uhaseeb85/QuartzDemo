package com.quartz.app.rest.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quartz.app.model.IntegrationTask;
import com.quartz.app.service.IntegrationTaskService;

/**
 * The Class IntegrationTaskController.
 */
@RestController
@RequestMapping("/api/v1")
public class IntegrationTaskController {

	@Autowired
	private IntegrationTaskService integrationTaskService;

	/**
	 * Gets the all tasks.
	 *
	 * @return the all tasks
	 */
	@GetMapping("/tasks")
	public List<IntegrationTask> getAllTasks() {
		return integrationTaskService.getAllTasks();
	}

	/**
	 * Gets the task by id.
	 *
	 * @param taskId the task id
	 * @return the task by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/tasks/{id}")
	public IntegrationTask getTaskById(@PathVariable(value = "id") Long taskId)
			throws ResourceNotFoundException {
		return integrationTaskService.getTaskById(taskId);
	}

	/**
	 * Creates the employee.
	 *
	 * @param integrationTask the integration task
	 * @return the integration task
	 * @throws SchedulerException
	 */
	@PostMapping("/scheduleTask")
	public IntegrationTask createTask(@Valid @RequestBody IntegrationTask integrationTask) throws SchedulerException {
		return integrationTaskService.createTask(integrationTask);
	}

	/**
	 * Update task.
	 *
	 * @param taskId      the task id
	 * @param taskDetails the task details
	 * @return the Integration Task
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/tasks/update/jobschedule/{id}")
	public IntegrationTask updateJobSchedule(@PathVariable(value = "id") Long taskId,
			@Valid @RequestBody IntegrationTask taskDetails) throws ResourceNotFoundException {
		return integrationTaskService.updateJobSchedule(taskId, taskDetails);
	}
	
	
	/**
	 * De-Activate task.
	 *
	 * @param taskId      the task id
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/tasks/deactivate/{id}")
	public IntegrationTask deactivateTask(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
		return integrationTaskService.deactivateTask(taskId);
	}
	
	
	/**
	 * Activate task.
	 *
	 * @param taskId      the task id
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/tasks/activate/{id}")
	public IntegrationTask activateTask(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
		return integrationTaskService.activateTask(taskId);
	}

	/**
	 * Delete task.
	 *
	 * @param taskId the task id
	 * @return the map
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@DeleteMapping("/tasks/{id}")
	public Map<String, Boolean> deleteTask(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
		return integrationTaskService.deleteTask(taskId);
	}
}
