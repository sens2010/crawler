package cn.cnic.datapub;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.DateBuilder.IntervalUnit.SECOND;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.job.HelloJob;
import cn.cnic.datapub.schedule.SchedulePool;
@SuppressWarnings("deprecation")
@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskTest
{
	@Resource 
	SchedulePool schedulePool;
	
	@Resource 
	StdSchedulerFactory schedulerFactory;
	
	@Test
	public void testSchedual()
	{
		  // Grab the Scheduler instance from the Factory
		
		System.err.println(schedulePool==null);
		Scheduler scheduler;
		try
		{
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			
			schedulePool.addScheduler("1", scheduler);
			
			JobDetail job = newJob(HelloJob.class)
				      .withIdentity("myJob", "group1")
				      .usingJobData("name", "world!")
				      .build();
					  
			Trigger trigger = newTrigger()
					.withIdentity(triggerKey("myTrigger", "myTriggerGroup"))
					.withSchedule(simpleSchedule()
					.withIntervalInSeconds(2)
					.repeatForever())
					.startAt(futureDate(10, SECOND))
					.build();
			scheduler.scheduleJob(job, trigger);
			
			while(true)
			{
				try
				{
					Thread.sleep(100000);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (SchedulerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  // and start it off
		 
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
