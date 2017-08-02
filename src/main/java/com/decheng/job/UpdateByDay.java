package com.decheng.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.decheng.service.FetchService;

public class UpdateByDay implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		FetchService.updateByDay();

	}
}
