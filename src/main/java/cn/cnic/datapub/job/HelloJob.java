package cn.cnic.datapub.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job
{
	private String name;
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.getName();
	}
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException
	{
		System.out.println("execute "+this.name);
	}
}
