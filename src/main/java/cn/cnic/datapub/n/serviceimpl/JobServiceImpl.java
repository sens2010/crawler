package cn.cnic.datapub.n.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnic.datapub.n.dao.JobDao;
import cn.cnic.datapub.n.dao.ParserDao;
import cn.cnic.datapub.n.dao.SubJobDao;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.model.SubJob;
import cn.cnic.datapub.n.service.IJobService;
import cn.cnic.datapub.n.utils.SchedulerUtils;

import com.alibaba.fastjson.JSONObject;

@Service("jobServiceImpl")
public class JobServiceImpl implements IJobService
{
	
	private Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
	
	@Resource
	JobDao jobDao;
	
	@Resource
	SubJobDao subJobDao;
	
	@Resource
	ParserDao parserDao;
	
	@Resource
	SchedulerUtils scheduleUtils;

	
	boolean isinit = false;
	@PostConstruct 
	public boolean initJob()
	{
		if(!isinit)
		{
			isinit = true;
			initJob(false);
		}
		return true;
	}
	public void initJob(boolean once)
	{
		/*List<Integer> statuses = new ArrayList<Integer>();
		statuses.add(1);
		statuses.add(2);
		List<Job> jobs = jobDao.findByStatusIn(statuses);
		Set<Integer> ids = new HashSet<Integer>();
		for(Job job:jobs)
		{
			int id = job.getId();
			ids.add(id);
		}
		
		List<SubJob> subjobs = subJobDao.findNormal(statuses, ids);
		Map<Integer,SubJob> subjob_mapping = new HashMap<Integer,SubJob>();
		ids.clear();
		for(SubJob subjob:subjobs)
		{
			int id = subjob.getId();
			int pid = subjob.getParserid();
			ids.add(pid);
			if(subjob_mapping.containsKey(id))
			{
				logger.warn("subjob_mapping id:"+id+" is multiple!");
			}
			else
			{
				subjob_mapping.put(id, subjob);
			}
		}
		statuses.clear();
		statuses.add(1);
		List<Parser> parsers = parserDao.findNormal(statuses,ids);
		Map<Integer,Parser> parser_mapping = new HashMap<Integer,Parser>();
		for(Parser parser:parsers)
		{
			int id = parser.getId();
			if(parser_mapping.get(id)!=null)
			{
				logger.warn("parser_mapping id:"+id+" is multiple!");
			}
			else
			{
				parser_mapping.put(id, parser);
			}
			
		}
		
		for(Job job:jobs)
		{
			System.err.println("****"+job.toJSONString()+"*****");
		}
		for(SubJob subjob:subjobs)
		{
			System.err.println("****"+subjob.toJSONString()+"*****");
		}
		
		
		
		for(Job job:jobs)
		{
			scheduleUtils.addConfig(job);
		}
		
		for(SubJob subjob:subjobs)
		{
			scheduleUtils.addJob(subjob, parser_mapping.get(subjob.getParserid()),once);
		}*/
		
		List<Integer> statuses = new ArrayList<Integer>();
		statuses.add(1);
		statuses.add(2);
		List<Job> jobs = jobDao.findByStatusIn(statuses);
		Set<Integer> ids = new HashSet<Integer>();
		for(Job job:jobs)
		{
			int id = job.getId();
			ids.add(id);
		}
		
		List<SubJob> subjobs = subJobDao.findNormal(statuses, ids);
		Map<Integer,SubJob> subjob_mapping = new HashMap<Integer,SubJob>();
		ids.clear();
		
		for(SubJob subjob:subjobs)
		{
			int id = subjob.getId();
			int pid = subjob.getParserid();
			ids.add(pid);
			if(subjob_mapping.containsKey(id))
			{
				logger.warn("subjob_mapping id:"+id+" is multiple!");
			}
			else
			{
				subjob_mapping.put(id, subjob);
			}
		}
		statuses.clear();
		statuses.add(1);
		List<Parser> parsers = parserDao.findNormal(statuses,ids);
		Map<Integer,Parser> parser_mapping = new HashMap<Integer,Parser>();
		for(Parser parser:parsers)
		{
			int id = parser.getId();
			if(parser_mapping.get(id)!=null)
			{
				logger.warn("parser_mapping id:"+id+" is multiple!");
			}
			else
			{
				parser_mapping.put(id, parser);
			}
			
		}
		
		for(Job job:jobs)
		{
			System.err.println(scheduleUtils.addJob(job));
		}
		
		for(SubJob subjob:subjobs)
		{
			System.err.println(scheduleUtils.addSubJob(subjob,parser_mapping.get(subjob.getParserid())));
		}
	}
	
	@Transactional
	@Override
	public String getJobStatus()
	{
		Map<Integer,String[]>  jobs = scheduleUtils.getJobs();
		JSONObject result = new JSONObject();
		for(Integer key :jobs.keySet())
		{
			result.put(key+"", jobs.get(key)[1]);
		}
		return result.toJSONString();
	}
	
	
	
	@Transactional
	@Override
	public String startJob(int jobid)
	{
		return scheduleUtils.startJob(jobid);
	}
	
	@Transactional
	@Override
	public String stopJob(int jobid)
	{
		return scheduleUtils.stopJob(jobid);
	}
	
	@Transactional
	@Override
	public String deleteJob(int jobid)
	{
		return scheduleUtils.deleteJob(jobid);
	}
	
	@Transactional
	@Override
	public String restartJob(int jobid)
	{
		return scheduleUtils.restartJob(jobid);
	}
	
	@Transactional
	@Override
	public Job addJob(Job job)
	{
		job = jobDao.save(job);
		String result = scheduleUtils.addJob(job);
		JSONObject jresult= JSONObject.parseObject(result);
		if(jresult.getInteger("code")==200)
		{
			return job;
		}
		else
		{
			return new Job();
		}
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			String result = scheduleUtils.deleteJob(id);
			JSONObject jresult= JSONObject.parseObject(result);
			if(jresult.getInteger("code")==200)
			{
				jobDao.delete(id);
			}
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Job updateJob(Job job)
	{
		String result = scheduleUtils.modifyJob(job.getId(), job.toJSONString());
		JSONObject jresult= JSONObject.parseObject(result);
		if(jresult.getInteger("code")==200)
		{
			return jobDao.save(job);
		}
		else
		{
			return new Job();
		}
		
	}

	@Override
	public Job getJobById(int id)
	{
		return jobDao.findById(id);
	}

	@Override
	public int countAll()
	{
		return jobDao.countAll();
	}

	@Override
	public List<Job> findAll()
	{
		// TODO Auto-generated method stub
		return jobDao.findAll();
	}
	
	@Override
	public List<Job> findByIds(Collection<Integer> ids)
	{
		return jobDao.findByIds(ids);
	}
	
	@Override
	public List<Job> list(int pid, int size)
	{
		/*Pageable pagable = new PageRequest(pid, size,new Sort(Direction.DESC,"createtime"));
		return jobDao.findAll(pagable).getContent();*/
		int offset = pid*size;
		return jobDao.findAll(size, offset);
	}
	
}
