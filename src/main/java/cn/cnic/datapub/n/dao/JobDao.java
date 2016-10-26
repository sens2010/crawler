package cn.cnic.datapub.n.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Job;

@Repository("jobDao")
public interface JobDao extends PagingAndSortingRepository<Job, Integer>
{
	Job findById(int id);
	
	@Query("select count(*) from Job")
	int countAll();
	
	@Query("from Job j order by j.createtime desc")
	List<Job> findAll();
	
	@Query(value="select * from job_metadata j order by j.CREATETIME desc limit ?1 offset ?2", nativeQuery=true)
	List<Job> findAll(int limit, int offset);
	
	
	//@Query("from Job j where j.status in ?1")
	List<Job> findByStatusIn(Collection<Integer> codes);
	@Query("from Job j where j.status in ?1 order by j.createtime desc")
	List<Job> findNormal(Collection<Integer> codes);
	
	@Query("from Job j where j.id in ?1 order by j.createtime desc")
	List<Job> findByIds(Collection<Integer> ids);
	
}
