/**
 * 
 */
package com.quartz.app.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Haseeb
 *
 */
@Entity
@Table(name = "integration_task")
public class IntegrationTask {
	
	
	private long taskId;
	private String sourceSystem;
	private String integrationId;
	private String status;
	private Timestamp nextRun;
	private String frequency;
	private String frequencyunit;
	private String currentStatus;
	private Timestamp runStart;
	private Timestamp runEnd;
	/**
	 * @return the taskId
	 */
	@Id
	@Column(name = "task_id", nullable = false)
	public long getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	/**
	 * @return the sourceSystem
	 */
	@Column(name = "source_system", nullable = false)
	public String getSourceSystem() {
		return sourceSystem;
	}
	/**
	 * @param sourceSystem the sourceSystem to set
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	/**
	 * @return the integrationId
	 */
	@Column(name = "integration_id", nullable = false)
	public String getIntegrationId() {
		return integrationId;
	}
	/**
	 * @param integrationId the integrationId to set
	 */
	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}
	/**
	 * @return the status
	 */
	@Column(name = "status", nullable = true)
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the nextRun
	 */
	@Column(name = "next_run", nullable = true)
	public Timestamp getNextRun() {
		return nextRun;
	}
	/**
	 * @param nextRun the nextRun to set
	 */
	public void setNextRun(Timestamp nextRun) {
		this.nextRun = nextRun;
	}
	/**
	 * @return the frequency
	 */
	@Column(name = "frequency", nullable = false)
	public String getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the frequencyunit
	 */
	@Column(name = "frequency_unit", nullable = false)
	public String getFrequencyunit() {
		return frequencyunit;
	}
	/**
	 * @param frequencyunit the frequencyunit to set
	 */
	public void setFrequencyunit(String frequencyunit) {
		this.frequencyunit = frequencyunit;
	}
	/**
	 * @return the currentStatus
	 */
	@Column(name = "current_status", nullable = true)
	public String getCurrentStatus() {
		return currentStatus;
	}
	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	/**
	 * @return the runStart
	 */
	@Column(name = "run_start", nullable = true)
	public Timestamp getRunStart() {
		return runStart;
	}
	/**
	 * @param runStart the runStart to set
	 */
	public void setRunStart(Timestamp runStart) {
		this.runStart = runStart;
	}
	/**
	 * @return the runEnd
	 */
	@Column(name = "run_end", nullable = true)
	public Timestamp getRunEnd() {
		return runEnd;
	}
	/**
	 * @param runEnd the runEnd to set
	 */
	public void setRunEnd(Timestamp runEnd) {
		this.runEnd = runEnd;
	}
	

}
