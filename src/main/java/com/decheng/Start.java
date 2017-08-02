package com.decheng;

import org.joda.time.DateTime;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.decheng.job.FetchAllJob;
import com.decheng.job.UpdateByDay;

public class Start {
	private final static Logger logger = LoggerFactory.getLogger(Start.class);

	public static void main(String[] args) {
		
		logger.info("项目启动");
		DateTime torromow = new DateTime().plusDays(1);
		JobDetail jobDetail = JobBuilder.newJob(FetchAllJob.class).withIdentity("job1", "jGroup1").build();
		JobDetail jobDetail1 = JobBuilder.newJob(UpdateByDay.class).withIdentity("job2", "jGroup1").build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "tGroup1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).build();
		Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger2", "tGroup1").startAt(torromow.toDate())
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).repeatForever()).build();

		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.scheduleJob(jobDetail1, trigger1);
			scheduler.start();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
