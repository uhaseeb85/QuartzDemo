/**
 * 
 */
package com.quartz.app.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author Haseeb
 *
 */
@Component
@DisallowConcurrentExecution
public class SampleJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		try {
			Thread.sleep(10*1000);
		} catch (Exception e) {
			JobExecutionException e2 = new JobExecutionException(e);
            e2.refireImmediately();
            throw e2;
		}
		System.out.println("Running ..."+context.getJobDetail().getDescription());
	}

}
