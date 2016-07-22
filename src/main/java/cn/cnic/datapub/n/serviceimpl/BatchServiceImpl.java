package cn.cnic.datapub.n.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.BatchDao;
import cn.cnic.datapub.n.model.Batch;
import cn.cnic.datapub.n.service.IBatchService;

@Service("batchServiceImpl")
public class BatchServiceImpl implements IBatchService
{
	@Resource
	BatchDao batchDao;

	@Override
	public Batch addBatch(Batch batch)
	{
		return batchDao.save(batch);
	}

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
