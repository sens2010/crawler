package cn.cnic.datapub.n.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.Job;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JobDaoTest
{
	
	
	@Resource
	JobDao jobDao;
	
	@Test
	public void testFindNews()
	{
		List<Integer> codes = new ArrayList<Integer>();
		codes.add(1);
		codes.add(2);
		List<Job> jobs = jobDao.findNormal(codes);

		for(Job job:jobs)
		{
			System.out.println(job.toJSONString());
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
