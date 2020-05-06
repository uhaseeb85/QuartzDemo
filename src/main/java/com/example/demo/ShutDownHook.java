/**
 * 
 */
package com.example.demo;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Haseeb
 *
 */
public class ShutDownHook {
	
	/** The scheduler. */
	@Autowired
	private static Scheduler scheduler;
	
	
	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown Hook is running !");
				try {
					scheduler.shutdown(true);
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("Application Terminating ...");
	}
}
