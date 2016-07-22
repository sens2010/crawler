package cn.cnic.datapub.n.service;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.annotation.MyTest;
import cn.cnic.datapub.n.serviceimpl.DictionaryServiceImpl;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryServiceImplTest
{
	
	@MyTest
	String name;
	@MyTest(name="name1")
	String name1;
	@MyTest(name="name2")
	String name2;
	
	
	@Resource
	DictionaryServiceImpl dictionaryServiceImpl;
	
	@Test
	public void testAddBatch()
	{
		
		Field[] fileds = DictionaryServiceImplTest.class.getDeclaredFields();
		System.out.println(fileds.length);
		for(Field field:fileds)
		{
			System.out.println(field.getName());
			MyTest myTest;
			if((myTest = field.getAnnotation(MyTest.class))!=null)
			{
				System.out.println(field.getName()+":"+myTest.name());
				
			}
		}
		
		System.err.println(name);
	}
	
	
	public void testDeleteBatch()
	{
		
	}
	
	public void testUpdateBatch()
	{
	}
	
	public void testFindBatchById()
	{
	}
	
	
	public void testFindAllBatch()
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
