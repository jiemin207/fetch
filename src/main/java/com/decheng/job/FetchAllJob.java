package com.decheng.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.decheng.service.FetchService;

public class FetchAllJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FetchService.fetchAll();
	}
}
