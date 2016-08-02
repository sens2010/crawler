package cn.cnic.datapub.n.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.News;
import cn.cnic.datapub.n.serviceimpl.NewsServiceImpl;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsServiceImplTest
{
	
	
	@Resource
	NewsServiceImpl newsServiceImpl;
	
	
	public void testAddNews()
	{
		
	}
	
	public void testDeleteNews()
	{
		
	}
	
	public void testUpdateNews()
	{
		
	}
	
	@Test
	public void testFindNewsById()
	{
		//System.out.println("all:"+newsServiceImpl.countAll());
		News news= newsServiceImpl.getNewsByNewsId("75696693b64dbc035163140fe2b5a48b");
		System.out.println(news.toJSONString());
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
