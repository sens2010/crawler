package cn.cnic.datapub.n.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.StatResult;

@Repository("statResultDao")
public interface StatResultDao extends CrudRepository<StatResult, Integer>
{
	StatResult findById(int id);
	
	

	
}
