package cn.cnic.datapub.n.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.User;

@Repository("userDao")
public interface UserDao extends CrudRepository<User, Integer>
{
	User findById(int id);
	
	@Query("select count(*) from User u where name=?1 and password=?2")
	int login(String name, String password);
}
