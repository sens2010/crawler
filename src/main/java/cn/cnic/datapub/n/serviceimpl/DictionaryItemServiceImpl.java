package cn.cnic.datapub.n.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.DictionaryItemDao;
import cn.cnic.datapub.n.model.DictionaryItem;
import cn.cnic.datapub.n.service.IDictionaryItemService;

@Service("dictionaryItemServiceImpl")
public class DictionaryItemServiceImpl implements IDictionaryItemService
{
	@Resource
	DictionaryItemDao dictionaryItemDao;

	@Override
	public DictionaryItem addDictionaryItem(DictionaryItem dictionaryitem)
	{
		return dictionaryItemDao.save(dictionaryitem);
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			dictionaryItemDao.delete(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public DictionaryItem updateDictionaryItem(DictionaryItem dictionaryitem)
	{
		return dictionaryItemDao.save(dictionaryitem);
	}

	@Override
	public DictionaryItem getBatchById(int id)
	{
		return dictionaryItemDao.findById(id);
	}

	@Override
	public int countAll()
	{
		return dictionaryItemDao.countAll();
	}

	@Override
	public List<DictionaryItem> findAll()
	{
		return dictionaryItemDao.findAll();
	}
	
	@Override
	public List<DictionaryItem> findCodes(int did)
	{
		return dictionaryItemDao.findCodes(did);
	}
}
