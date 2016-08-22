package cn.cnic.datapub.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.job.SubJobBatchDAO;
import cn.cnic.datapub.job.SubJobBatchM;

import com.alibaba.fastjson.JSONObject;
@SuppressWarnings("deprecation")
@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SubJobBatchTest
{
	
	@Resource
	SubJobBatchDAO subJobBatchDAO;
	
	@Test
	public void testModify()
	{
		SubJobBatchM jd = new SubJobBatchM();
		jd.setId(3);
		jd.setEndtime(new Date());
		jd.setStarttime(new Date());
		jd.setRelatebatch(1);
		jd.setSubjobid(2);
		jd.setStatus(2);

		System.out.println(subJobBatchDAO.modify(jd));
	}
	
	@Test
	public void testfindAll()
	{
		List<SubJobBatchM> list = subJobBatchDAO.findAll();
		for(SubJobBatchM jd:list)
		{
			System.out.println(jd);
		}
	}
	
	@Test
	public void testCount()
	{
		System.out.println(subJobBatchDAO.count());
	}
	@Test
	public void testGeneralFind()
	{
		JSONObject json = new JSONObject();
		json.put("status",2);
		//json.put("id",5);
		List<SubJobBatchM> list= subJobBatchDAO.findByParams(json);
		for(SubJobBatchM jd:list)
		{
			System.out.println(jd.toString());
		}
	}
	
	
	public void testFindById()
	{
		
		System.out.println(subJobBatchDAO.findByIdTest(3).toString());
	}
	
	public void testdelete()
	{
		System.out.println(subJobBatchDAO.deleteById(2));
	}
	
	
	public void testadd()
	{
		if(subJobBatchDAO!=null)
		{
			SubJobBatchM jd = new SubJobBatchM();
			jd.setEndtime(new Date());
			jd.setStarttime(new Date());
			jd.setRelatebatch(4);
			jd.setSubjobid(3);
			jd.setStatus(2);

			System.out.println(subJobBatchDAO.add(jd));
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
