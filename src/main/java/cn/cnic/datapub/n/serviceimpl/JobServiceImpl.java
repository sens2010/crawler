package cn.cnic.datapub.n.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	public void initJob()
	{
		 initJob(false);
	}
	public void initJob(boolean once)
	{
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
			ids.add(id);
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
			int id = parser.getSubjobid();
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
			scheduleUtils.addJob(subjob, parser_mapping.get(subjob.getId()),once);
		}
	}
	
	
	@Transactional
	@Override
	public Job addJob(Job job)
	{
		return jobDao.save(job);
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			jobDao.delete(id);
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
		return jobDao.save(job);
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
	
	
}
