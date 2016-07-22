package cn.cnic.datapub.n.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.serviceimpl.JobServiceImpl;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JobServiceImplTest
{
	
	
	@Resource
	JobServiceImpl jobServiceImpl;
	
	
	public void testAddJob()
	{
		
	}
	
	public void testDeleteJob()
	{
		
	}
	
	public void testUpdateJob()
	{
		
	}
	
	public void testFindJobById()
	{
		
	}
	
	public void testFindAllJob()
	{
		
	}
	
	@Test
	public void testModel()
	{
		Job job = new Job();
		job.setId(1);
		job.setName("name");
		job.setPlan("plan");
		job.setStatus(1);
		job.setDescription("description");
		System.out.println(job.toJSONString());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
