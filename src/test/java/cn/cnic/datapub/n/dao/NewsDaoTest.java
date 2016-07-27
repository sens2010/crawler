package cn.cnic.datapub.n.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.News;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsDaoTest
{
	
	
	@Resource
	NewsDao newsDao;
	
	@Test
	public void testFindNews()
	{
		News news = newsDao.findByNewId("newsid6");
		System.out.println(news.toJSONString());
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
