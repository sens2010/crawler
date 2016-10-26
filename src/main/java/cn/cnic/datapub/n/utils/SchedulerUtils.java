package cn.cnic.datapub.n.utils;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.DateBuilder.IntervalUnit.SECOND;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.job.ListParseJob;
import cn.cnic.datapub.n.job.ListParseJobs;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.model.SubJob;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class SchedulerUtils
{
	private Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);
	
	Scheduler scheduler;
	private Map<Integer, Map<Integer, String[]>> configs = new HashMap<Integer, Map<Integer, String[]>>();
	
	//String[], String[0]相关属性及对象信息；String[1]任务状态（R=运行，P=暂停）；String[2]子任务id列表，JSONArray对象。
	private Map<Integer,String[]> jobs = new HashMap<Integer,String[]>();
	//String[], String[0]相关属性及对象信息；String[1]任务状态（R=运行，P=暂停）；String[2]解析器相关属性
	private Map<Integer,String[]> subjobs = new HashMap<Integer,String[]>();
	
	//code解释
	//200成功，300操作状态不对，301数据已存在，403操作不允许，404未找到，500处理过程异常
	
	
	public Map<Integer,String[]> getJobs()
	{
		return this.jobs;
	}
	
	public Map<Integer,String[]> getSubJobs()
	{
		return this.subjobs;
	}
	
	
	public String startJob(int jobid)
	{
		JSONObject result = new JSONObject();
		String[] jdetail = jobs.get(jobid);
		if(jdetail==null)
		{
			result.put("code", 404);
			result.put("message", jobid+"未找到相关任务");
		}
		else
		{
			if(jdetail[1].equals("R"))
			{
				result.put("code", 300);
				result.put("message", jobid+"任务已启动");
			}
			else if(jdetail[1].equals("P"))
			{
				JSONArray subjoblist = JSONArray.parseArray(jdetail[2]);
				int count=0;
				for(Object id : subjoblist.toArray())
				{
					String sresult = startSubJob(Integer.parseInt(id.toString()));
					JSONObject jsresult = JSONObject.parseObject(sresult);
					if(jsresult.getInteger("code")==200)
						count++;
				}
				jdetail[1]="R";
				result.put("code", 200);
				result.put("message", jobid+"成功启动"+count+"个子任务");
			}
		}
		return result.toJSONString();
	}
	
	public String stopJob(int jobid)
	{
		JSONObject result = new JSONObject();
		String[] jdetail = jobs.get(jobid);
		if(jdetail==null)
		{
			result.put("code", 404);
			result.put("message", jobid+"未找到相关任务");
		}
		else
		{
			if(jdetail[1].equals("P"))
			{
				result.put("code", 300);
				result.put("message", jobid+"任务已停止");
			}
			else if(jdetail[1].equals("R"))
			{
				JSONArray subjoblist = JSONArray.parseArray(jdetail[2]);
				int count=0;
				for(Object id : subjoblist.toArray())
				{
					String sresult = stopSubJob(Integer.parseInt(id.toString()));
					JSONObject jsresult = JSONObject.parseObject(sresult);
					if(jsresult.getInteger("code")==200)
						count++;
				}
				jdetail[1]="P";
				result.put("code", 200);
				result.put("message", jobid+"成功停止"+count+"个子任务");
			}
		}
		return result.toJSONString();
	}
	
	public String restartJob(int jobid)
	{
		JSONObject result = new JSONObject();
		String[] jdetail = jobs.get(jobid);
		if(jdetail==null)
		{
			result.put("code", 404);
			result.put("message", jobid+"未找到相关任务");
		}
		else
		{
			if(jdetail[1].equals("P"))
			{
				result.put("code", 300);
				result.put("message", jobid+"任务已停止");
			}
			else if(jdetail[1].equals("R"))
			{
				JSONArray subjoblist = JSONArray.parseArray(jdetail[2]);
				int count=0;
				for(Object id : subjoblist.toArray())
				{
					String sresult = stopSubJob(Integer.parseInt(id.toString()));
					JSONObject jsresult = JSONObject.parseObject(sresult);
					if(jsresult.getInteger("code")==200)
						count++;
				}
				int count1=0;
				for(Object id : subjoblist.toArray())
				{
					String sresult = stopSubJob(Integer.parseInt(id.toString()));
					JSONObject jsresult = JSONObject.parseObject(sresult);
					if(jsresult.getInteger("code")==200)
						count1++;
				}
				jdetail[1]="R";
				result.put("code", 200);
				result.put("message", jobid+"成功停止"+count+"个子任务，成功启动"+count1+"个子任务");
			}
		}
		return result.toJSONString();
	}
	
	public String addJob(Job job)
	{
		return addJob(job,null,null);
	}
	
	public String addJob(Job job, List<SubJob> subjoblist,Map<Integer,Parser> parsers)
	{
		int jid = job.getId();

		JSONObject result = new JSONObject();
		
		if(jobs.containsKey(jid))
		{
			result.put("code", 301);
			result.put("message", jid+"任务已存在");
		}
		else
		{
			String config = job.toJSONString();
			String status = "P";
			JSONArray jsubjoblist = new JSONArray();
			int success = 0;
			int failed = 0;
			if(subjoblist!=null)
			{
			for(SubJob sj:subjoblist)
			{
				jsubjoblist.add(sj.getId());
				Parser parser = parsers.get(sj.getParserid());
				if(parser!=null)
				{
					String sjresult = addSubJob(sj,parser);
					JSONObject jsjresult = JSONObject.parseObject(sjresult); 
					if(jsjresult.getInteger("code")==200)
					{
						success++;
					}
					else
					{
						failed++;
					}
				}
				else
				{
					failed++;
				}
			}
			}
			String[] params = new String[]{config,status,jsubjoblist.toJSONString()};
			jobs.put(jid, params);
			result.put("code", 200);
			result.put("message", jid+"任务添加成功,子任务成功添加"+success+"个，失败"+failed+"个");
		}
		return result.toJSONString();
	}
	
	public String deleteJob(int jobid)
	{
		JSONObject result = new JSONObject();
		if(jobs.containsKey(jobid))
		{
			
			String[] jdetail = jobs.get(jobid);
			String status = jdetail[1];
			
			if(status.equals("R"))
			{
				result.put("code", 403);
				result.put("message", jobid+"任务未停止！");
			}
			else
			{
			String  subjoblist = jdetail[2];
			JSONArray jsubjoblist = JSONObject.parseArray(subjoblist);
			int success=0,failed=0;
			for(Object sjid : jsubjoblist.toArray())
			{
				String sjresult = deleteSubJob(Integer.parseInt(sjid.toString()));
				JSONObject jsjresult = JSONObject.parseObject(sjresult);
				if(jsjresult.getInteger("code")==200)
				{
					success++;
				}
				else
				{
					failed++;
				}
			}
			result.put("code",200);
			result.put("message", jobid+"任务删除成功，成功删除子任务"+success+"个，失败"+failed+"个");
			}
		}
		else
		{
			result.put("code", 404);
			result.put("message", jobid+"任务不存在");
		}
		
		return result.toJSONString();
	}
	
	public String modifyJob(int jobid,String config)
	{
		JSONObject result = new JSONObject();
		if(!jobs.containsKey(jobid))
		{
			result.put("code", 404);
			result.put("message","任务不存在");
		}
		else
		{
			JSONObject jconfig = new JSONObject();
			String[] jobdetail = jobs.get(jobid);
			if(jconfig.keySet().contains("plan")) 
			{
				
				if(!jobdetail[1].equals("P"))
				{
					result.put("code", 300);
					result.put("message", jobid+"任务未停止");
				}
				else
				{
					String subjoblist = jobdetail[2];
					JSONArray jsubjoblist = JSONArray.parseArray(subjoblist);
					int success =0, failed =0;
					for(Object sj:jsubjoblist.toArray())
					{
						String plan = jconfig.getString("plan");
						JSONObject sjconfig = new JSONObject();
						sjconfig.put("plan", plan);
						String sjresult = modifySubJob(Integer.parseInt(sj.toString()),sjconfig.toJSONString());
						JSONObject jsjresult = JSONObject.parseObject(sjresult);
						if(jsjresult.getInteger("code")==200)
						{
							success++;
						}
						else
						{
							failed++;
						}
					}
					String configs = jobdetail[0];
					JSONObject jconfigs = JSONObject.parseObject(configs); 
					if(jconfig.keySet().contains("name"))
					{
						jconfigs.put("name", jconfig.getString("name"));
					}
					if(jconfig.keySet().contains("category"))
					{
						jconfigs.put("category", jconfig.getInteger("category"));
					}
					if(jconfig.keySet().contains("description"))
					{
						jconfigs.put("description", jconfig.getInteger("description"));
					}
					jobdetail[0]=jconfigs.toJSONString();
					
					result.put("code", 200);
					result.put("message", jobid+"任务修改成功，成功修改子任务"+success+",失败"+failed+"个");
				}
			}
			else
			{
				String configs = jobdetail[0];
				JSONObject jconfigs = JSONObject.parseObject(configs); 
				if(jconfig.keySet().contains("name"))
				{
					jconfigs.put("name", jconfig.getString("name"));
				}
				if(jconfig.keySet().contains("category"))
				{
					jconfigs.put("category", jconfig.getInteger("category"));
				}
				if(jconfig.keySet().contains("description"))
				{
					jconfigs.put("description", jconfig.getInteger("description"));
				}
				jobdetail[0]=jconfigs.toJSONString();
				
				result.put("code", 200);
				result.put("message", jobid+"任务修改成功");
				
			}
		}

		return result.toJSONString();
	}
	
	public String startSubJob(int subjobid)
	{
		
		JSONObject result = new JSONObject();
		String[] subjobdetail = subjobs.get(subjobid);
		if(subjobdetail==null)
		{
			result.put("code", 404);
			result.put("message", subjobid+"未找到相关任务");
		}
		else
		{
			if(subjobdetail[1].equals("R"))
			{
				result.put("code", 300);
				result.put("message", subjobid+"子任务已启动");
			}
			else if(subjobdetail[1].equals("P"))
			{
				Parser parser = JSONObject.parseObject(subjobdetail[2], Parser.class);
				SubJob subjob = JSONObject.parseObject(subjobdetail[0], SubJob.class);
				if(!jobs.containsKey(subjob.getJobid()))
				{
					result.put("code", 403);
					result.put("message", subjobid+"附属任务不存在");
				}
				else
				{
					JobKey jk = new JobKey(subjob.getJobid() + "", subjob.getId() + "");
					
					try
					{
						if(scheduler.checkExists(jk))
						{
							scheduler.resumeJob(jk);
							result.put("code", 200);
							result.put("message", subjobid+"子任务启动成功！");
						}
						
						
						
						else
						{
						Job job = JSONObject.parseObject((jobs.get(subjob.getJobid()))[0],Job.class);
						
						System.err.println(subjob.getId());
						
						JobDetail jobdetail = newJob(ListParseJobs.class)
								.withIdentity(job.getId() + "", subjob.getId() + "")
								.usingJobData("jobid", job.getId())
								.usingJobData("parserid", parser.getId())
								.usingJobData("subjobid", subjob.getId()).build();
						TriggerKey tk = new TriggerKey(job.getId() + "",
								subjob.getId() + "");
							Trigger trigger = scheduler.getTrigger(tk);
							trigger = newTrigger()
									.withIdentity(
											triggerKey(job.getId() + "",
													subjob.getId() + ""))
									.withSchedule(PlanUtils.parse("intv:5s"))
									.startAt(futureDate(10, SECOND)).build();
							scheduler.scheduleJob(jobdetail, trigger);
						subjobdetail[1]="R";
						result.put("code", 200);
						result.put("message", subjobid+"子任务成功启动！");
					}
					} catch (SchedulerException e)
					{
						// TODO Auto-generated catch block
						
						result.put("code", 500);
						result.put("message", subjobid+"子任务添加时异常！");
						e.printStackTrace();
					}
				}
			}
		}
		return result.toJSONString();
	}
	
	public String stopSubJob(int subjobid)
	{
		JSONObject result = new JSONObject();
		String[] subjobdetail = subjobs.get(subjobid);
		
		if(subjobdetail==null)
		{
			result.put("code", 404);
			result.put("message", subjobid+"未找到相关任务");
		}
		else
		{
			System.err.println(subjobdetail[1]);
			if(subjobdetail[1].equals("P"))
			{
				result.put("code", 300);
				result.put("message", subjobid+"子任务已停止");
			}
			else if(subjobdetail[1].equals("R"))
			{
				SubJob subjob = JSONObject.parseObject(subjobdetail[0], SubJob.class);
				if(!jobs.containsKey(subjob.getJobid()))
				{
					result.put("code", 403);
					result.put("message", subjobid+"附属任务不存在");
				}
				else
				{
					JobKey jk = new JobKey(subjob.getJobid() + "",
							subjob.getId() + "");
					try
					{
						scheduler.pauseJob(jk);
						
					} catch (SchedulerException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					subjobdetail[1]="P";
					result.put("code", 200);
					result.put("message", subjobid+"子任务停止！");
				}
			}
		}
		return result.toJSONString();
	}
	
	public String restartSubJob(int subjobid)
	{
		JSONObject result = new JSONObject();
		String[] subjobdetail = subjobs.get(subjobid);
		if(subjobdetail==null)
		{
			result.put("code", 404);
			result.put("message", subjobid+"未找到相关任务");
		}
		else
		{
			if(subjobdetail[1].equals("P"))
			{
				result.put("code", 300);
				result.put("message", subjobid+"子任务已停止");
			}
			else if(subjobdetail[1].equals("R"))
			{
				SubJob subjob = JSONObject.parseObject(subjobdetail[0], SubJob.class);
				if(!jobs.containsKey(subjob.getJobid()))
				{
					result.put("code", 403);
					result.put("message", subjobid+"附属任务不存在");
				}
				else
				{
					JobKey jk = new JobKey(subjob.getJobid() + "",
							subjob.getId() + "");
					try
					{

						if(scheduler.checkExists(jk))
						{
							scheduler.deleteJob(jk);
						}
						
						Job job = JSONObject.parseObject((jobs.get(subjob.getJobid()))[0],Job.class);
						
						Parser parser = JSONObject.parseObject(subjobdetail[2],Parser.class);
						
						JobDetail jobdetail = newJob(ListParseJobs.class)
								.withIdentity(job.getId() + "", subjob.getId() + "")
								.usingJobData("jobid", job.getId())
								.usingJobData("parserid", parser.getId())
								.usingJobData("subjobid", subjob.getId()).build();
						TriggerKey tk = new TriggerKey(job.getId() + "",
								subjob.getId() + "");
							Trigger trigger = scheduler.getTrigger(tk);
							trigger = newTrigger()
									.withIdentity(
											triggerKey(job.getId() + "",
													subjob.getId() + ""))
									.withSchedule(PlanUtils.parse("intv:5s"))
									.startAt(futureDate(10, SECOND)).build();
							scheduler.scheduleJob(jobdetail, trigger);
						
						result.put("code", 200);
						result.put("message", subjobid+"子任务重启成功！");
									
						
					} catch (SchedulerException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					
						result.put("code", 500);
						result.put("message", subjobid+"子任务重启失败！");
					}
					
				}
			}
		}
		return result.toJSONString();
	}
	
	public String addSubJob(int subjobid)
	{
		JSONObject result = new JSONObject();
		return result.toJSONString();
	}
	
	public String addSubJob(SubJob subjob,Parser parser)
	{
		JSONObject result = new JSONObject();
		
	    if(subjobs.containsKey(subjob.getId()))
	    {
	    	result.put("code", 301);
	    	result.put("message", subjob.getId()+"子任务已存在！");
	    }
	    else
	    {
	    	String[] subjobdetail = new String[]{subjob.toJSONString(),"P",parser.toJSONString()};
	    	subjobs.put(subjob.getId(),subjobdetail);
	    	String[] configs =  jobs.get(subjob.getJobid());
			String subjoblist = configs[2];
			JSONArray array = JSONObject.parseArray(subjoblist);
			array.add(subjob.getId());
			configs[2] = array.toJSONString();
			result.put("code",200);
			result.put("message",subjob.getId()+"添加子任务成功！");
	    }
		
		return result.toJSONString();
	}
	
	public String deleteSubJob(int subjobid)
	{
		JSONObject result = new JSONObject();
		
		if(!subjobs.containsKey(subjobid))
		{
			result.put("code", 300);
			result.put("message", subjobid+"子任务不存在！");
		}
		else
		{
			String[] subjobdetail = subjobs.get(subjobid);
			if(subjobdetail[1].equals("R"))
			{
				result.put("code", 403);
				result.put("message", subjobid+"子任务正在运行！");
			}
			else
			{
				
				try
				{
					String subjob = subjobdetail[0];
					SubJob sj = JSONObject.parseObject(subjob,SubJob.class);
					JobKey jk = new JobKey(sj.getJobid() + "",
							sj.getId() + "");
					int jobid = sj.getJobid();
					subjobs.remove(subjobid);
					String[] configs = jobs.get(jobid);
					JSONArray subjoblist = JSONArray.parseArray(configs[2]);
					JSONArray newlist = new JSONArray();
					for(int i = 0; i<subjoblist.size();i++)
					{
						int id = subjoblist.getInteger(i);
						if(id!=subjobid)
						{
							newlist.add(id);
						}
					}
					configs[2] = newlist.toJSONString();
					scheduler.deleteJob(jk);
					result.put("code", 200);
					result.put("message", subjobid+"删除子任务成功！");
				} catch (SchedulerException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.put("code", 500);
					result.put("message", subjobid+"删除子任务失败！");
				}
				
				
				}
		}
		
		return result.toJSONString();
	}
	
	public String modifySubJob(int subjobid)
	{
		JSONObject result = new JSONObject();
		return result.toJSONString();
	}
	
	public String modifySubJob(int subjobid, String subjobconfig)
	{
		JSONObject result = new JSONObject();
		
		if(!subjobs.containsKey(subjobid))
		{
			result.put("code", 300);
			result.put("message", subjobid+"子任务不存在！");
		}
		else
		{
			String[] subjobdetail = subjobs.get(subjobid);
			if(subjobdetail[1].equals("R"))
			{
				result.put("code", 403);
				result.put("message", subjobid+"子任务正在运行！");
			}
			else
			{
				
				String subjob = subjobdetail[0];
				SubJob sj = JSONObject.parseObject(subjob,SubJob.class);		
				JSONObject subjobconf = JSONObject.parseObject(subjobconfig);
				
				if(subjobconf.containsKey("plan")||subjobconf.containsKey("subjob")||subjobconf.containsKey("parser"))
				{
					if(subjobconf.containsKey("subjob"))
					{
						subjobdetail[0]=subjobconf.getString("subjob");
					}
					if(subjobconf.containsKey("parser"))
					{
						subjobdetail[2]=subjobconf.getString("parser");
					}
					restartSubJob(sj.getId());
					result.put("code", 200);
					result.put("message", subjobid+"子任务修改成功！");
				}
				else
				{
					result.put("code",403 );
					result.put("message", subjobid+"无任何操作");
				}
				
				
				}
		}
		return result.toJSONString();
	}
	
	public boolean addConfig(Job job)
	{
		int jid = job.getId();
		Map<Integer, String[]> config = new HashMap<Integer, String[]>();
		logger.info(job.toJSONString());
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
		return addJob(subjob, parser, false);
	}
	
	public boolean addJob(SubJob subjob, Parser parser, boolean isinit)
	{
		Map<Integer, String[]> config = configs.get(subjob.getJobid());
		logger.info("subjob:"+subjob.toJSONString());
		logger.info("parser:"+parser.toJSONString());
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
					
					JSONObject docparser = new JSONObject();
					docparser.put("doccss", parser.isArtcss());
					docparser.put("docscript", parser.isArtcss());
					docparser.put("textmatch", parser.getTextparser());
					docparser.put("titlematch", parser.getTitleparser());
					docparser.put("timematch", parser.getTimeparser());
					docparser.put("sourceurlmatch", parser.getSourceparser());
					System.err.println(parser.getTimetransfer());
					docparser.put("timetransfer", parser.getTimetransfer());
					docparser.put("interval", subjob.getInterval());
					
					JobDetail jobdetail = newJob(ListParseJob.class)
							.withIdentity(job.getId() + "", subjob.getId() + "")
							.usingJobData("listcss", parser.isListcss())
							.usingJobData("listscript", parser.isListjs())
							.usingJobData("url", subjob.getUrl())
							.usingJobData("relative", parser.isUrlrelativer())
							.usingJobData("listmatch", parser.getListparser())
							.usingJobData("nextmatch", parser.getNextparser())
							.usingJobData("jobid", job.getId())
							.usingJobData("parser",docparser.toJSONString())
							.usingJobData("subjobid", subjob.getId()).build();
					
					TriggerKey tk = new TriggerKey(job.getId() + "",
							subjob.getId() + "");
					Trigger trigger = scheduler.getTrigger(tk);
					if (trigger == null)
					{
						if(isinit)
						{
							trigger = newTrigger()
									.withIdentity(
											triggerKey(job.getId() + "",
													subjob.getId() + ""))
									.startAt(futureDate(10, SECOND)).build();
						}
						else
						{
							trigger = newTrigger()
									.withIdentity(
											triggerKey(job.getId() + "",
													subjob.getId() + ""))
									.withSchedule(PlanUtils.parse(job.getPlan()))
									.startAt(futureDate(10, SECOND)).build();
						}
						
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
				GroupMatcher<TriggerKey> matcher = GroupMatcher.triggerGroupEndsWith(job.getId() + "");
				
				
				try
				{
					Set<TriggerKey> tks = scheduler.getTriggerKeys(matcher);
					for(TriggerKey tk : tks)
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
	
	@PostConstruct
	@Deprecated
	public boolean initScheduler()
	{
		try
		{
			Properties p = new Properties();
			p.setProperty("org.quartz.threadPool.threadCount", "30");
			p.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			
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
