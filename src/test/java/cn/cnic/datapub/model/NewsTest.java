package cn.cnic.datapub.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.job.NewsDAO;
import cn.cnic.datapub.job.NewsM;

import com.alibaba.fastjson.JSONObject;
@SuppressWarnings("deprecation")
@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsTest
{
	
	@Resource
	NewsDAO newsDAO;
	
	@Test
	public void testModify()
	{
		NewsM jd = new NewsM();
		jd.setId(3);
		jd.setNewsid("上山打老虎！");
		System.out.println(newsDAO.modify(jd));
	}
	@Test
	public void testfindAll()
	{
		List<NewsM> list = newsDAO.findAll();
		for(NewsM jd:list)
		{
			System.out.println(jd);
		}
	}
	
	@Test
	public void testCount()
	{
		System.out.println(newsDAO.count());
	}
	
	@Test
	public void testGeneralFind()
	{
		JSONObject json = new JSONObject();
		json.put("id",1);
		//json.put("id",5);
		List<NewsM> list= newsDAO.findByParams(json);
		for(NewsM jd:list)
		{
			System.out.println(jd.toString());
		}
	}
	
	
	public void testFindById()
	{
		
		System.out.println(newsDAO.findByIdTest(3).toString());
	}
	
	
	public void testdelete()
	{
		System.out.println(newsDAO.deleteById(4));
	}
	
	
	public void testadd()
	{
		if(newsDAO!=null)
		{
			for(int i=1;i<10;i++)
			{
				NewsM jd = new NewsM();
				jd.setNewsid("newsid"+i);
				jd.setUrl("url"+i);
				jd.setPagecount(i);
				jd.setResource("resource"+i);
				jd.setPubtime(new Date());
				jd.setTitle("title"+i);
				jd.setText("text"+i);
				jd.setLastmodifytime(new Date());
				jd.setRelatebatch(i+100);
				System.out.println(newsDAO.add(jd));
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
