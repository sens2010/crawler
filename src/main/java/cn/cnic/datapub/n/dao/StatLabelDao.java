package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.StatLabel;

@Repository("statLabelDao")
public interface StatLabelDao extends CrudRepository<StatLabel, Integer>
{
	StatLabel findById(int id);
}
