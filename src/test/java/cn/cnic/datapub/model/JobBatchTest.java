package cn.cnic.datapub.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.job.JobBatchDAO;
import cn.cnic.datapub.job.JobBatchM;

import com.alibaba.fastjson.JSONObject;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JobBatchTest
{
	
	@Resource
	JobBatchDAO jobBatchDAO;
	
	@Test
	public void testModify()
	{
		JobBatchM jd = new JobBatchM();
		jd.setId(3);
		jd.setEndtime(new Date());
		jd.setStarttime(new Date());
		jd.setJobid(1);
		jd.setMonitorinfo(1);
		jd.setStatus(2);

		System.out.println(jobBatchDAO.modify(jd));
	}
	
	
	public void testfindAll()
	{
		List<JobBatchM> list = jobBatchDAO.findAll();
		for(JobBatchM jd:list)
		{
			System.out.println(jd);
		}
	}
	
	
	public void testCount()
	{
		System.out.println(jobBatchDAO.count());
	}
	
	public void testGeneralFind()
	{
		JSONObject json = new JSONObject();
		json.put("status",2);
		//json.put("id",5);
		List<JobBatchM> list= jobBatchDAO.findByParams(json);
		for(JobBatchM jd:list)
		{
			System.out.println(jd.toString());
		}
	}
	
	
	public void testFindById()
	{
		
		System.out.println(jobBatchDAO.findByIdTest(3).toString());
	}
	
	public void testdelete()
	{
		System.out.println(jobBatchDAO.deleteById(2));
	}
	
	
	public void testadd()
	{
		if(jobBatchDAO!=null)
		{
			JobBatchM jd = new JobBatchM();
			jd.setEndtime(new Date());
			jd.setStarttime(new Date());
			jd.setJobid(2);
			jd.setMonitorinfo(2);
			jd.setStatus(3);

			System.out.println(jobBatchDAO.add(jd));
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
