package cn.cnic.datapub.n.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ListParseJobs extends ParseJob
{
	private int subjobid;
	private int jobid;
	private int parserid;
	
	
	
	
	public int getSubjobid()
	{
		return subjobid;
	}
	
	public int getJobid()
	{
		return jobid;
	}

	public void setJobid(int jobid)
	{
		this.jobid = jobid;
	}

	public void setSubjobid(int subjobid)
	{
		this.subjobid = subjobid;
	}
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException
	{
		System.err.println("id:"+jobid+"_"+subjobid+"任务，执行中,解析器"+parserid);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
	}

	public int getParserid()
	{
		return parserid;
	}

	public void setParserid(int parserid)
	{
		this.parserid = parserid;
	}
	
}
