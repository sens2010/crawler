package cn.cnic.datapub.n.service;

import java.util.List;

import cn.cnic.datapub.n.model.Batch;


public interface IBatchService
{
	Batch addBatch(Batch batch);
	boolean deleteById(int id);
	Batch updateBatch(Batch batch);
	Batch getBatchById(int id);
	int countAll();
	List<Batch> findAll();
	List<Batch> list(int pid, int size);
	Batch findLastBatch(int subjobid, int batchnow);
	List<Batch> findLastBatches(int subjobid, int batchnow,int number);
}
