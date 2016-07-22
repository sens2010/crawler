package cn.cnic.datapub.n.utils;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.DateBuilder.IntervalUnit.SECOND;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.job.ListParseJob;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.model.SubJob;

import com.alibaba.fastjson.JSONObject;

@Service
public class SchedulerUtils
{
	Scheduler scheduler;
	private Map<Integer, Map<Integer, String[]>> configs = new HashMap<Integer, Map<Integer, String[]>>();
	
	public boolean addConfig(Job job)
	{
		int jid = job.getId();
		Map<Integer, String[]> config = new HashMap<Integer, String[]>();
		config.put(-1, new String[] { job.toJSONString(), null });
		this.configs.put(jid, config);
		return true;
	}
	
	public boolean removeConfig(int id)
	{
		Map<Integer, String[]> config = this.configs.get(id);
		for (Integer key : config.keySet())
		{
			if (key != -1)
			{
				String detail = config.get(key)[0];
				SubJob job = JSONObject.parseObject(detail, SubJob.class);
				deleteJob(job);
			}
		}
		return true;
	}
	
	public boolean addJob(SubJob subjob, Parser parser)
	{
		Map<Integer, String[]> config = configs.get(subjob.getJobid());
		if (config == null)
		{
			return false;
		} else
		{
			if (initScheduler() || this.scheduler != null)
			{
				try
				{
					Job job = JSONObject.parseObject(config.get(-1)[0],
							Job.class);
					JobDetail jobdetail = newJob(ListParseJob.class)
							.withIdentity(job.getId() + "", subjob.getId() + "")
							.usingJobData("listcss", parser.isListcss())
							.usingJobData("listscript", parser.isListjs())
							.usingJobData("url", subjob.getUrl())
							.usingJobData("relative", parser.isUrlrelativer())
							.usingJobData("listmatch", parser.getListparser())
							.usingJobData("nextmatch", parser.getNextparser())
							.usingJobData("jobid", job.getId())
							.usingJobData("subjobid", subjob.getId()).build();
					
					TriggerKey tk = new TriggerKey(job.getId() + "",
							job.getId() + "");
					Trigger trigger = scheduler.getTrigger(tk);
					if (trigger == null)
					{
						trigger = newTrigger()
								.withIdentity(
										triggerKey(job.getId() + "",
												job.getId() + ""))
								.withSchedule(PlanUtils.parse(job.getPlan()))
								.startAt(futureDate(10, SECOND)).build();
					}
					scheduler.scheduleJob(jobdetail, trigger);
					config.put(
							subjob.getId(),
							new String[] { subjob.toJSONString(),
									parser.toJSONString() });
					return true;
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return false;
		}
	}
	
	public boolean deleteJob(SubJob subjob)
	{
		Map<Integer, String[]> config = configs.get(subjob.getJobid());
		if (config != null)
		{
			if (initScheduler() || this.scheduler != null)
			{
				try
				{
					JobKey jk = new JobKey(subjob.getJobid() + "",
							subjob.getId() + "");
					scheduler.deleteJob(jk);
					config.remove(subjob.getId());
					return true;
				} catch (SchedulerException e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public boolean updateJob(Job job)
	{
		Map<Integer, String[]> config = configs.get(job.getId());
		if (config != null)
		{
			Job oldjob = JSONObject.parseObject(config.get(-1)[0], Job.class);
			if (oldjob.getPlan().equals(job.getPlan()))
			{
				config.get(-1)[0] = job.toJSONString();
			} else
			{
				TriggerKey tk = new TriggerKey(job.getId() + "", job.getId()
						+ "");
				try
				{
					if (scheduler.checkExists(tk))
					{
						Trigger newtrigger = newTrigger()
								.withIdentity(
										triggerKey(job.getId() + "",
												job.getId() + ""))
								.withSchedule(PlanUtils.parse(job.getPlan()))
								.startAt(futureDate(10, SECOND)).build();
						scheduler.rescheduleJob(tk,newtrigger);
						
					} else
					{
						
					}
				} catch (Exception ee)
				{
					ee.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public boolean updateJob(SubJob subjob)
	{
		Map<Integer, String[]> config = configs.get(subjob.getJobid());
		if (config != null)
		{
			String[] subconfig = config.get(subjob.getId());
			SubJob oldsubjob = JSONObject.parseObject(subconfig[0],SubJob.class);
			if(oldsubjob.getUrl().equals(subjob.getUrl()))
			{
				subconfig[0] = subjob.toJSONString();
			}
			else
			{
				JobKey jk = new JobKey(subjob.getJobid()+"",subjob.getId()+"");
				TriggerKey tk = new TriggerKey(subjob.getJobid()+"",subjob.getJobid()+"");
				try
				{
				if(scheduler.checkExists(jk)&&scheduler.checkExists(tk))
				{
					Parser parser = JSONObject.parseObject(subconfig[1],Parser.class);
					JobDetail jobdetail = newJob(ListParseJob.class)
							.withIdentity(subjob.getJobid() + "", subjob.getId() + "")
							.usingJobData("listcss", parser.isListcss())
							.usingJobData("listscript", parser.isListjs())
							.usingJobData("url", subjob.getUrl())
							.usingJobData("relative", parser.isUrlrelativer())
							.usingJobData("listmatch", parser.getListparser())
							.usingJobData("nextmatch", parser.getNextparser())
							.usingJobData("jobid", subjob.getJobid())
							.usingJobData("subjobid", subjob.getId()).build();
					scheduler.deleteJob(jk);
					scheduler.scheduleJob(jobdetail, scheduler.getTrigger(tk));
					subconfig[0] = subjob.toJSONString();
				}
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
			}
		}
		return false;
	}
	public boolean updateJob(SubJob subjob,Parser parser)
	{
		Map<Integer, String[]> config = configs.get(subjob.getJobid());
		if (config != null)
		{
			String[] subconfig = config.get(subjob.getId());
			Parser oldparser = JSONObject.parseObject(subconfig[1],Parser.class);
			if(oldparser.getListparser().equals(parser.getListparser())
					&&oldparser.isListcss()==parser.isListcss()
					&&oldparser.isListjs()==parser.isListjs()
					&&oldparser.getNextparser().equals(parser.getNextparser())
					&&oldparser.isUrlrelativer()==parser.isUrlrelativer())
			{
				subconfig[1] = parser.toJSONString();
			}
			else
			{
				JobKey jk = new JobKey(subjob.getJobid()+"",subjob.getId()+"");
				TriggerKey tk = new TriggerKey(subjob.getJobid()+"",subjob.getJobid()+"");
				try
				{
				if(scheduler.checkExists(jk)&&scheduler.checkExists(tk))
				{
					JobDetail jobdetail = newJob(ListParseJob.class)
							.withIdentity(subjob.getJobid() + "", subjob.getId() + "")
							.usingJobData("listcss", parser.isListcss())
							.usingJobData("listscript", parser.isListjs())
							.usingJobData("url", subjob.getUrl())
							.usingJobData("relative", parser.isUrlrelativer())
							.usingJobData("listmatch", parser.getListparser())
							.usingJobData("nextmatch", parser.getNextparser())
							.usingJobData("jobid", subjob.getJobid())
							.usingJobData("subjobid", subjob.getId()).build();
					scheduler.deleteJob(jk);
					scheduler.scheduleJob(jobdetail, scheduler.getTrigger(tk));
					subconfig[1] = parser.toJSONString();
				}
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
			}
		}
		return false;
	}
	
	
	public boolean initScheduler()
	{
		try
		{
			this.scheduler = StdSchedulerFactory.getDefaultScheduler();
			this.scheduler.start();
			return true;
		} catch (SchedulerException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
