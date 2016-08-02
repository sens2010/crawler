package cn.cnic.datapub.n.serviceimpl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Transient;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.BatchDao;
import cn.cnic.datapub.n.model.Batch;
import cn.cnic.datapub.n.service.IBatchService;

@Service("batchServiceImpl")
public class BatchServiceImpl implements IBatchService
{
	@Resource
	BatchDao batchDao;
	@Transient
	@Override
	public Batch addBatch(Batch batch)
	{
		return batchDao.save(batch);
	}
	@Transient
	@Override
	public boolean deleteById(int id)
	{
		try
		{
			batchDao.delete(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Transient
	@Override
	public Batch updateBatch(Batch batch)
	{
		return batchDao.save(batch);
	}

	@Override
	public Batch getBatchById(int id)
	{
		return batchDao.findById(id);
	}

	@Override
	public int countAll()
	{
		return batchDao.countAll();
	}
	
	@Override
	public List<Batch> findAll()
	{
		return batchDao.findAll();
	}

}
