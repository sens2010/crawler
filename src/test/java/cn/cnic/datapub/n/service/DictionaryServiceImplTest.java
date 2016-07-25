package cn.cnic.datapub.n.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.Dictionary;
import cn.cnic.datapub.n.serviceimpl.DictionaryServiceImpl;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryServiceImplTest
{
	
	
	@Resource
	DictionaryServiceImpl dictionaryServiceImpl;
	
	@Test
	public void testAddDictionary()
	{
		
		Dictionary dic = new Dictionary();
		dic.setName("字典分类");
		dic.setCategory(-1);
		dic.setDescription("字典的不同类型");
		dic.setStatus(1);
		dic.setCreatetime(new Date());
		
		dictionaryServiceImpl.addDictionary(dic);
		
	}
	
	
	public void testDeleteDictionary()
	{
		
	}
	
	public void testUpdateDictionary()
	{
	}
	
	public void testFindDictionaryById()
	{
	}
	
	
	public void testFindAllDictionary()
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
