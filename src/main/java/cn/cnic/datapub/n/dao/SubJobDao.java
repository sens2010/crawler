package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.SubJob;

@Repository("subJobDao")
public interface SubJobDao extends CrudRepository<SubJob, Integer>
{
	SubJob findById(int id);
}
