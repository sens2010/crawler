package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.service.IAbstractService;

@Service("batchServiceImpl")
public abstract class  AbstractServiceImpl<T,D extends CrudRepository<T,Integer>> implements IAbstractService<T>
{
	@Resource
	D dao;

	@Override
	public T add(T t)
	{
		return dao.save(t);
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			dao.delete(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public T update(T t)
	{
		return dao.save(t);
	}

}
