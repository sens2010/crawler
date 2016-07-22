package cn.cnic.datapub.n.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.User;
import cn.cnic.datapub.n.serviceimpl.UserServiceImpl;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest
{
	
	
	@Resource
	UserServiceImpl userServiceImpl;
	
	
	public void testFindById()
	{
		System.out.println(userServiceImpl.getUser(1).getName());
	}
	
	
	public void testLogin()
	{
		System.out.println(userServiceImpl.login("sens2010", "hanyueqi123"));
	}
	
	@Test
	public void testAddDeleteModify()
	{
		User user = new User();
		user.setName("h1");
		user.setName("p1");
		
		System.err.println(userServiceImpl.addUser(user).getId());
		user.setName("h2");
		user.setName("p2");
		System.err.println(userServiceImpl.addUser(user).getId());
		
		user.setName("h3");
		user.setName("p3");
		System.err.println(userServiceImpl.addUser(user).getId());
		
		System.out.println(userServiceImpl.deleteUserById(1));
		
		user = userServiceImpl.getUser(2);
		if(user!=null)
		{
			user.setPassword("ppp");
			System.err.println(userServiceImpl.updateUser(user).getId());
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
