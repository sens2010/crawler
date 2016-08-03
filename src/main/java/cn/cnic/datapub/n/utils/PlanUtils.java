package cn.cnic.datapub.n.utils;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import org.quartz.ScheduleBuilder;

public class PlanUtils
{
	public static ScheduleBuilder<?> parse(String plan)
	{
		if(plan!=null)
		{
			if(plan.startsWith("cron:"))
			{
				String cron = plan.substring(5);
				return cronSchedule(cron);
			}
			else if(plan.startsWith("intr:"))
			{
				String intr = plan;
				if(plan.endsWith("h"))
				{
					return simpleSchedule().withIntervalInHours(Integer.parseInt(intr.subSequence(5, plan.length()-1).toString()))
					.repeatForever();
				}
				else if(plan.endsWith("m"))
				{
					return simpleSchedule().withIntervalInMinutes(Integer.parseInt(intr.subSequence(5, plan.length()-1).toString()))
							.repeatForever(); 
				}
				else if(plan.endsWith("s"))
				{
					return simpleSchedule().withIntervalInSeconds(Integer.parseInt(intr.subSequence(5, plan.length()-1).toString()))
							.repeatForever();
				}
				else
				{
					return null;
				}
			}
			else if(plan.startsWith("time:"))
			{
				String time = plan.substring(5);
				String[] timer = time.split(":");
				return dailyAtHourAndMinute(Integer.parseInt(timer[0]),Integer.parseInt(timer[1]));
			}
			else
			{
				return null;
			}
		}
		
		return null;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String time="cron:12h";
		System.out.println(time.substring(5,time.length()-1));
	}
	
}
