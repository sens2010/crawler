package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnic.datapub.n.dao.UserDao;
import cn.cnic.datapub.n.model.User;
import cn.cnic.datapub.n.service.IUserService;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService
{
	@Resource
	UserDao userDao;
	
	@Override
	public User getUser(int id)
	{
		return userDao.findById(id);
	}
	
	@Override
	public boolean login(String username, String password)
	{
		int result = userDao.login(username,password);
		boolean isSuccess = (result>0?true:false);
		return isSuccess;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
	}

	@Override
	@Transactional
	public User addUser(User user)
	{ 
		return userDao.save(user);
	}

	@Override
	@Transactional
	public boolean deleteUserById(int id)
	{
		try
		{
			userDao.delete(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public User updateUser(User user)
	{
		
		return userDao.save(user);
	}
	
}
