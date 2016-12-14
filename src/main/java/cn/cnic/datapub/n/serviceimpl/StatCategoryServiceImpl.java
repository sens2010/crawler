package cn.cnic.datapub.n.serviceimpl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.StatCategoryDao;
import cn.cnic.datapub.n.model.StatCategory;
import cn.cnic.datapub.n.service.IStatCategoryService;

@Service("statCategoryServiceImpl")
public class StatCategoryServiceImpl implements IStatCategoryService
{
	@Resource
	StatCategoryDao statCategoryDao;
	
	@Override
	public List<StatCategory> findCategories(Collection<Integer> ids)
	{
		return statCategoryDao.findByIDs(ids);
	}
	
	@Override
	public StatCategory addStatCategory(StatCategory statCategory)
	{
		statCategory = statCategoryDao.save(statCategory);
		return statCategory;
	}
	
	@Override
	public boolean deleteStatCategory(int categoryid)
	{
		try
		{
			statCategoryDao.delete(categoryid);
			return true;
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		return false;
	}
	
	@Override
	public StatCategory updateStatCategory(StatCategory statCategory)
	{
		return statCategoryDao.save(statCategory);
	}
	
	@Override
	public StatCategory findStatCategoryById(int id)
	{
		return statCategoryDao.findById(id);
	}
	
	@Override
	public List<StatCategory> findAll()
	{
		return statCategoryDao.findAll();
	}
	
	@Override 
	public StatCategory matchLastOne(String subcode)
	{
		return statCategoryDao.matchLastOne(subcode);
		
	}
	
}
