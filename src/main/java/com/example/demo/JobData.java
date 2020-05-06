/**
 * 
 */
package com.example.demo;

/**
 * @author Haseeb
 *
 */
public class JobData {
	
	private String jobname;
	
	private int jobfrequency;
	
	private String jobfrequencyUnit;
	
	private String cronExpression;
	
	private String jobinfo;

	public JobData(String jobname, int jobfrequency, String jobinfo, String jobfrequencyUnit) {
		this.jobname=jobname;
		this.jobfrequency=jobfrequency;
		this.jobinfo=jobinfo;
		this.jobfrequencyUnit=jobfrequencyUnit;
	}
	
	public JobData(String jobname, String cronExpression, String jobinfo, String jobfrequencyUnit) {
		this.jobname=jobname;
		this.cronExpression=cronExpression;
		this.jobinfo=jobinfo;
		this.jobfrequencyUnit=jobfrequencyUnit;
	}

	/**
	 * @return the jobname
	 */
	public String getJobname() {
		return jobname;
	}

	/**
	 * @param jobname the jobname to set
	 */
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	/**
	 * @return the jobinfo
	 */
	public String getJobinfo() {
		return jobinfo;
	}

	/**
	 * @param jobinfo the jobinfo to set
	 */
	public void setJobinfo(String jobinfo) {
		this.jobinfo = jobinfo;
	}

	/**
	 * @return the jobfrequencyUnit
	 */
	public String getJobfrequencyUnit() {
		return jobfrequencyUnit;
	}

	/**
	 * @param jobfrequencyUnit the jobfrequencyUnit to set
	 */
	public void setJobfrequencyUnit(String jobfrequencyUnit) {
		this.jobfrequencyUnit = jobfrequencyUnit;
	}

	/**
	 * @return the jobfrequency
	 */
	public int getJobfrequency() {
		return jobfrequency;
	}

	/**
	 * @param jobfrequency the jobfrequency to set
	 */
	public void setJobfrequency(int jobfrequency) {
		this.jobfrequency = jobfrequency;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
