package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Noter;

@Repository("noterDao")
public interface NoterDao extends CrudRepository<Noter, Integer>
{
	Noter findById(int id);
}
