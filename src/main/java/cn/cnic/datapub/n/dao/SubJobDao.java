package cn.cnic.datapub.n.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.SubJob;

@Repository("subJobDao")
public interface SubJobDao extends CrudRepository<SubJob, Integer>
{
	SubJob findById(int id);
	
	@Query("select count(*) from SubJob")
	int countAll();
	
	@Query("from SubJob")
	List<SubJob> findAll();
	
	List<SubJob> findByStatusIn(Collection<Integer> codes);
	
	@Query("from SubJob j where j.status in ?1 and j.jobid in ?2")
	List<SubJob> findNormal(Collection<Integer> codes,Collection<Integer> jobs);
	
}
