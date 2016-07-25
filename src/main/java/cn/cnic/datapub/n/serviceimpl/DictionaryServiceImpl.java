package cn.cnic.datapub.n.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.DictionaryDao;
import cn.cnic.datapub.n.model.Dictionary;
import cn.cnic.datapub.n.service.IDictionaryService;

@Service("dictionaryServiceImpl")
public class DictionaryServiceImpl implements IDictionaryService
{
	@Resource
	DictionaryDao dictionaryDao;

	@Override
	public Dictionary addDictionary(Dictionary dictionary)
	{
		return dictionaryDao.save(dictionary);
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			dictionaryDao.delete(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Dictionary updateDictionary(Dictionary dictionary)
	{
		return dictionaryDao.save(dictionary);
	}

	@Override
	public Dictionary getBatchById(int id)
	{
		return dictionaryDao.findById(id);
	}

	@Override
	public int countAll()
	{
		return dictionaryDao.countAll();
	}

	@Override
	public List<Dictionary> findAll()
	{
		return dictionaryDao.findAll();
	}
}
