package cn.cnic.datapub.n.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnic.datapub.n.dao.JobDao;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.service.IJobService;

@Service("jobServiceImpl")
public class JobServiceImpl implements IJobService
{
	@Resource
	JobDao jobDao;

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
