package cn.cnic.datapub.n.service;

import cn.cnic.datapub.n.model.User;

public interface IUserService
{
	
	User getUser(int id);
	
	boolean login(String name, String password);
	
	
	User addUser(User user);
	boolean deleteUserById(int id);
	User updateUser(User user);
}
