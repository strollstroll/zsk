package com.farm.quartz.server;

import java.util.Date;

import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class DemoJobExecutionContext implements JobExecutionContext {
	private JobDataMap dataMap;

	public DemoJobExecutionContext(JobDataMap data) {
		dataMap = data;
	}

	@Override
	public Object get(Object arg0) {
		return null;
	}

	@Override
	public Calendar getCalendar() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public String getFireInstanceId() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public Date getFireTime() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public JobDetail getJobDetail() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public Job getJobInstance() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public long getJobRunTime() {
		// TODO1 Auto-generated method stub
		return 0;
	}

	@Override
	public JobDataMap getMergedJobDataMap() {
		return dataMap;
	}

	@Override
	public Date getNextFireTime() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public Date getPreviousFireTime() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public TriggerKey getRecoveringTriggerKey() throws IllegalStateException {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public int getRefireCount() {
		// TODO1 Auto-generated method stub
		return 0;
	}

	@Override
	public Object getResult() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public Date getScheduledFireTime() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public Scheduler getScheduler() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public Trigger getTrigger() {
		// TODO1 Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRecovering() {
		// TODO1 Auto-generated method stub
		return false;
	}

	@Override
	public void put(Object arg0, Object arg1) {
		// TODO1 Auto-generated method stub

	}

	@Override
	public void setResult(Object arg0) {
		// TODO1 Auto-generated method stub

	}

}
