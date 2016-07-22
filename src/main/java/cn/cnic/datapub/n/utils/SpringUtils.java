package cn.cnic.datapub.n.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtils implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext()
	{
		return SpringUtils.applicationContext;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException
	{
		SpringUtils.applicationContext=applicationContext;
	}
	
	public static Object getBean(String className)
	{
		return applicationContext.getBean(className);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
