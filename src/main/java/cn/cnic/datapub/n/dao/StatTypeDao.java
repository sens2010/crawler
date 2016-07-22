package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.StatType;

@Repository("statTypeDao")
public interface StatTypeDao extends CrudRepository<StatType, Integer>
{
	StatType findById(int id);
}
