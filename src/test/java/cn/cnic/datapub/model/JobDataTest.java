package cn.cnic.datapub.model;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.job.manager.JobDataDAO;
import cn.cnic.datapub.job.manager.JobDataM;

import com.alibaba.fastjson.JSONObject;
@SuppressWarnings("deprecation")
@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JobDataTest
{
	
	@Resource
	JobDataDAO jobDataDAO;
	
	@Test
	public void testModify()
	{
		JobDataM jd = new JobDataM();
		jd.setId(2);
		jd.setName("财经门户");
		jd.setPlan("2hour");
		jd.setStatus(4);
		jd.setDescription("解释");
		System.out.println(jobDataDAO.modify(jd));
	}
	
	
	public void testfindAll()
	{
		List<JobDataM> list = jobDataDAO.findAll();
		for(JobDataM jd:list)
		{
			System.out.println(jd);
		}
	}
	
	
	public void testCount()
	{
		System.out.println(jobDataDAO.count());
	}
	
	public void testGeneralFind()
	{
		JSONObject json = new JSONObject();
		json.put("status",2);
		//json.put("id",5);
		List<JobDataM> list= jobDataDAO.findByParams(json);
		for(JobDataM jd:list)
		{
			System.out.println(jd.toString());
		}
	}
	
	
	public void testFindById()
	{
		
		System.out.println(jobDataDAO.findByIdTest(2).toString());
	}
	
	public void testdelete()
	{
		System.out.println(jobDataDAO.deleteById(4));
	}
	
	
	public void testadd()
	{
		if(jobDataDAO!=null)
		{
			JobDataM jd = new JobDataM();
			jd.setName("财经门户");
			jd.setPlan("2hour");
			jd.setStatus(2);
			jd.setDescription("");
			System.out.println(jobDataDAO.add(jd));
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
