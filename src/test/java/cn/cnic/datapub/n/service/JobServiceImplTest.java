package cn.cnic.datapub.n.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	@Test
	public void testAddJob()
	{
		/*List<Job> list = new ArrayList<Job>();
		Job job1 = new Job();
		job1.setName("搜狐财经");
		job1.setPlan("p1");
		job1.setStatus(1);
		job1.setDescription("");
		
		Job job2 = new Job();
		job2.setName("和讯网");
		job2.setPlan("");
		job2.setStatus(1);
		job2.setDescription("");
		Job job3 = new Job();
		job3.setName("发改委");
		job3.setPlan("");
		job3.setStatus(1);
		job3.setDescription("");
		Job job4 = new Job();
		job4.setName("人民银行官网");
		job4.setPlan("");
		job4.setStatus(1);
		job4.setDescription("");
		Job job5 = new Job();
		job5.setName("");
		job5.setPlan("");
		job5.setStatus(1);
		job5.setDescription("");
		
		list.add(job1);
		list.add(job2);
		list.add(job3);
		list.add(job4);
		list.add(job5);
		
		for(Job job:list)
		{
			System.out.println(jobServiceImpl.addJob(job).toJSONString());
		}*/
		Job job;
		job= new Job();
		job= job.setName("搜狐财经").setPlan("intr:2h").setCategory(1).setDescription("").setStatus(1).setCreatetime(new Date());
		jobServiceImpl.addJob(job);
		System.out.println(job.toJSONString());
		//job=jobServiceImpl.addJob(job);
		System.err.println(job.toJSONString());
		System.err.println(job.getId());
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
