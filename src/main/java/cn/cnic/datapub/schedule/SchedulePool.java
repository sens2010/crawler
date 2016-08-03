package cn.cnic.datapub.schedule;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.springframework.stereotype.Service;

//@Service
@Deprecated
public class SchedulePool
{
	@Resource
	SchedulerFactory schedulerFactory;
	
	private static Logger logger = Logger.getLogger(SchedulePool.class);
	
	private Map<String,Scheduler> schedulestore = new HashMap<String,Scheduler>();
	
	public void addScheduler(String id,Scheduler scheduler)
	{
		this.schedulestore.put(id, scheduler);
		logger.info("scheduler "+id+" is added!");
	}
	
	public Scheduler getScheduler(String id)
	{
		Scheduler scheduler = this.schedulestore.get(id);
		if(scheduler==null)
		{
			logger.warn("scheduler "+id+" does not exist!");
		}
		return scheduler;
		
	}
	
	public void deleteScheduler(String id)
	{
		Scheduler scheduler = this.schedulestore.get(id);
		if(scheduler==null)
		{
			logger.warn("scheduler "+id+" does not exist!");
		}
		else
		{	
			this.schedulestore.remove(id);
			logger.info("scheduler "+id+" is removed!");
		}
	}
	
	public void updateScheduler(String id, Scheduler scheduler)
	{
		if(this.schedulestore.get(id)!=null)
		{
			this.schedulestore.remove(id);
		}
		else
		{
			logger.warn("scheduler "+id+" does not exist!");
		}
		this.schedulestore.put(id, scheduler);
		logger.info("scheduler "+id+" is updated!");
	}
	
	public Map<String,Scheduler> getSchedulestore()
	{
		return this.schedulestore;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
