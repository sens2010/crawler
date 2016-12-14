package cn.cnic.datapub.n.serviceimpl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.StatLabelDao;
import cn.cnic.datapub.n.model.StatLabel;
import cn.cnic.datapub.n.service.IStatLabelService;

@Service("statLabelServiceImpl")
public class StatLabelServiceImpl implements IStatLabelService
{
	@Resource
	StatLabelDao statLabelDao;
	 
	@Override
	public StatLabel addStatLabel(StatLabel statlabel)
	{
		return statLabelDao.save(statlabel);
	}
	
	@Override
	public boolean deleteStatLabel(StatLabel statlabel)
	{
		try
		{
			statLabelDao.delete(statlabel);
			return true;
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		return false;
	}
	
	@Override
	public StatLabel updateStatLabel(StatLabel statlabel)
	{
		return statLabelDao.save(statlabel);
	}
	
	@Override
	public StatLabel findStatLabelById(int id)
	{
		return statLabelDao.findById(id);
	}
	
	@Override
	public boolean findStatLabelByKeys(int newsid, int categoryid)
	{
		List<StatLabel> list =  statLabelDao.findByKeys(newsid, categoryid);
		if(list.size()>0)return true;
		else return false;
	}
	
	
	@Override
	public List<StatLabel> findStatLabelByNewsId(int newsid)
	{
		return statLabelDao.findByNewsId(newsid);
	}
	
	@Override
	public List<StatLabel> findStatLabelByNewsIds(Collection<Integer> newsids )
	{
		return statLabelDao.findByNewsIds(newsids);
	}
	
	@Override
	public void deleteStatLabelByNewsId(int newsid)
	{
		statLabelDao.deleteByNewsId(newsid);
	}
	
	@Override
	public void deleteStatLableByNewsIds(int newsid,Collection<Integer> categoryids)
	{
		statLabelDao.deleteByNewsIds(newsid,categoryids);
	}
	
	@Override
	public void deleteStatLableByIds(Collection<Integer> ids)
	{
		statLabelDao.deleteByIds(ids);
	}
}
