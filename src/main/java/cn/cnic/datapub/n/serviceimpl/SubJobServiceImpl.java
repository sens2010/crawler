package cn.cnic.datapub.n.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.SubJobDao;
import cn.cnic.datapub.n.model.SubJob;
import cn.cnic.datapub.n.service.ISubJobService;

@Service("subJobServiceImpl")
public class SubJobServiceImpl implements ISubJobService
{
	@Resource
	SubJobDao subJobDao;

	@Override
	public SubJob addSubJob(SubJob subjob)
	{
		return subJobDao.save(subjob);
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			subJobDao.delete(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public SubJob updateSubJob(SubJob subjob)
	{
		// TODO Auto-generated method stub
		return subJobDao.save(subjob);
	}

	@Override
	public SubJob getSubJobById(int id)
	{
		// TODO Auto-generated method stub
		return subJobDao.findById(id);
	}

	@Override
	public int countAll()
	{
		// TODO Auto-generated method stub
		return subJobDao.countAll();
	}

	@Override
	public List<SubJob> findAll()
	{
		// TODO Auto-generated method stub
		return subJobDao.findAll();
	}
}
