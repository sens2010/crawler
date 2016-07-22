package cn.cnic.datapub.n.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Batch;

@Repository("batchDao")
@RepositoryRestResource(collectionResourceRel = "batch", path = "batch")
public interface BatchDao extends CrudRepository<Batch, Integer>
{
	Batch findById(int id);
	
	@Query("select count(*) from Batch")
	int countAll();
	
	@Query("from Batch")
	List<Batch> findAll();
}
