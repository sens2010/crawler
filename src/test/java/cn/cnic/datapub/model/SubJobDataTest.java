package cn.cnic.datapub.model;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.job.manager.SubJobDataDAO;
import cn.cnic.datapub.job.manager.SubJobDataM;

import com.alibaba.fastjson.JSONObject;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SubJobDataTest
{
	
	@Resource
	SubJobDataDAO subJobDataDAO;
	
	@Test
	public void testModify()
	{
		SubJobDataM jd = new SubJobDataM();
		jd.setId(3);
		jd.setName("财经门户");
		jd.setUrl("2hour");
		jd.setCategory(1);
		System.out.println(subJobDataDAO.modify(jd));
	}
	
	@Test
	public void testfindAll()
	{
		List<SubJobDataM> list = subJobDataDAO.findAll();
		for(SubJobDataM jd:list)
		{
			System.out.println(jd);
		}
	}
	
	@Test
	public void testCount()
	{
		System.out.println(subJobDataDAO.count());
	}
	@Test
	public void testGeneralFind()
	{
		JSONObject json = new JSONObject();
		json.put("status",2);
		//json.put("id",5);
		List<SubJobDataM> list= subJobDataDAO.findByParams(json);
		for(SubJobDataM jd:list)
		{
			System.out.println(jd.toString());
		}
	}
	
	@Test
	public void testFindById()
	{
		
		System.out.println(subJobDataDAO.findByIdTest(2).toString());
	}
	
	@Test
	public void testdelete()
	{
		System.out.println(subJobDataDAO.deleteById(4));
	}
	
	
	public void testadd()
	{
		if(subJobDataDAO!=null)
		{
			for(int i=1;i<10;i++)
			{
				SubJobDataM jd = new SubJobDataM();
				jd.setName("财经门户");
				jd.setJobid(i/2);
				jd.setUrl("......");
				jd.setStatus(i);
				jd.setCategory(i-1);
				jd.setDescription("");
				System.out.println(subJobDataDAO.add(jd));
			}
			
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
