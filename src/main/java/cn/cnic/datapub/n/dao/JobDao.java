package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Job;

@Repository("jobDao")
public interface JobDao extends CrudRepository<Job, Integer>
{
	Job findById(int id);
	
}
