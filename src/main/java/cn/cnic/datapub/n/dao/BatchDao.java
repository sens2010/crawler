package cn.cnic.datapub.n.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Batch;

@Repository("batchDao")
@RepositoryRestResource(collectionResourceRel = "batch", path = "batch")
public interface BatchDao extends PagingAndSortingRepository<Batch, Integer>
{
	Batch findById(int id);
	
	@Query("select count(*) from Batch")
	int countAll();
	
	@Query("from Batch")
	List<Batch> findAll();

	@Query(value="select * from op_batch ob where subjobid =?1 and id<>?2 order by ob.STARTTIME desc limit 1", nativeQuery=true)
	Batch findLastBatch(int subjobid, int batchnow);
	
	@Query(value="select * from op_batch ob where subjobid =?1 and id<>?2 order by ob.STARTTIME desc limit ?3", nativeQuery=true)
	List<Batch> findLastBatches(int subjobid, int batchnow, int number);
}
