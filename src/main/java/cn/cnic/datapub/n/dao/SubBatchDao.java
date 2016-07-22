package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.SubBatch;

@Repository("subBatchDao")
public interface SubBatchDao extends CrudRepository<SubBatch, Integer>
{
	SubBatch findById(int id);
}
