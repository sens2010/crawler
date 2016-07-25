package cn.cnic.datapub.n.dao;

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
}
