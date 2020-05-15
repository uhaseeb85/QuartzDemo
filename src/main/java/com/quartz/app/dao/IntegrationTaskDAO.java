package com.quartz.app.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.quartz.model.IntegrationTask;

/**
 * The Class IntegrationTaskController.
 */
@Component
public class IntegrationTaskDAO {
	
	/** The integration task repository. */
	@Autowired
	private IntegrationTaskRepository integrationTaskRepository;

	/**
	 * Gets the all tasks.
	 *
	 * @return the all tasks
	 */
	@GetMapping("/tasks")
	public List<IntegrationTask> getAllTasks() {
		return integrationTaskRepository.findAll();
	}

	/**
	 * Gets the task by id.
	 *
	 * @param taskId the task id
	 * @return the task by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/tasks/{id}")
	public ResponseEntity<IntegrationTask> getTaskById(@PathVariable(value = "id") Long taskId)
			throws ResourceNotFoundException {
		IntegrationTask integrationTask = integrationTaskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("integrationTask not found for this id :: " + taskId));
		return ResponseEntity.ok().body(integrationTask);
	}

	/**
	 * Creates the employee.
	 *
	 * @param integrationTask the integration task
	 * @return the integration task
	 */
	@PostMapping("/tasks")
	public IntegrationTask createEmployee(@Valid @RequestBody IntegrationTask integrationTask) {
		return integrationTaskRepository.save(integrationTask);
	}

	/**
	 * Update task.
	 *
	 * @param taskId the task id
	 * @param taskDetails the task details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/tasks/{id}")
	public ResponseEntity<IntegrationTask> updateTask(@PathVariable(value = "id") Long taskId,
			@Valid @RequestBody IntegrationTask taskDetails) throws ResourceNotFoundException {
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
		return ResponseEntity.ok(updatedTask);
	}

	/**
	 * Delete task.
	 *
	 * @param taskId the task id
	 * @return the map
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@DeleteMapping("/tasks/{id}")
	public Map<String, Boolean> deleteTask(@PathVariable(value = "id") Long taskId)
			throws ResourceNotFoundException {
		IntegrationTask integrationTask = integrationTaskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("integrationTask not found for this id :: " + taskId));
		integrationTaskRepository.delete(integrationTask);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
