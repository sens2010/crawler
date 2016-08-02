package cn.cnic.datapub.n.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.dao.JobDao;
import cn.cnic.datapub.n.dao.ParserDao;
import cn.cnic.datapub.n.dao.SubJobDao;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.serviceimpl.NewsServiceImpl;
import cn.cnic.datapub.n.utils.SchedulerUtils;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SchedulerUtilsTest
{
	
	
	@Resource
	SchedulerUtils schedulerUtils;
	
	@Resource
	JobDao jobDao;
	
	@Resource
	SubJobDao subJobDao;
	
	@Resource
	ParserDao parserDao;
	
	@Test
	public void testAddJobs()
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
			
			schedulerUtils.addConfig(job);
		}
		
	}
	
	public void testDeleteNews()
	{
		
	}
	
	public void testUpdateNews()
	{
		
	}
	
	public void testFindNewsById()
	{
		
	}
	
	public void testFindAllNews()
	{
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
