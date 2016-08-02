package cn.cnic.datapub.n.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Job;

@Repository("jobDao")
public interface JobDao extends CrudRepository<Job, Integer>
{
	Job findById(int id);
	
	@Query("select count(*) from Job")
	int countAll();
	
	@Query("from Job")
	List<Job> findAll();
	
	//@Query("from Job j where j.status in ?1")
	List<Job> findByStatusIn(Collection<Integer> codes);
	@Query("from Job j where j.status in ?1")
	List<Job> findNormal(Collection<Integer> codes);
}
