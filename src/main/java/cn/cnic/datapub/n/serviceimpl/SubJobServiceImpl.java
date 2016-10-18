package cn.cnic.datapub.n.serviceimpl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnic.datapub.n.dao.ParserDao;
import cn.cnic.datapub.n.dao.SubJobDao;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.model.SubJob;
import cn.cnic.datapub.n.service.ISubJobService;
import cn.cnic.datapub.n.utils.SchedulerUtils;

import com.alibaba.fastjson.JSONObject;

@Service("subJobServiceImpl")
public class SubJobServiceImpl implements ISubJobService
{
	@Resource
	SubJobDao subJobDao;
	
	@Resource
	ParserDao parserDao;

	@Resource
	SchedulerUtils scheduleUtils;
	
	
	@Override
	public SubJob addSubJob(SubJob subjob)
	{
		int parserid = subjob.getParserid();
		Parser parser = parserDao.findById(parserid);
		String result = scheduleUtils.addSubJob(subjob, parser);
		JSONObject jresult= JSONObject.parseObject(result);
		if(jresult.getInteger("code")==200)
		{
			return subJobDao.save(subjob);
		}
		else
		{
			return new SubJob();
		}
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			String result = scheduleUtils.deleteSubJob(id);
			JSONObject jresult= JSONObject.parseObject(result);
			if(jresult.getInteger("code")==200)
			{
				subJobDao.delete(id);
			}
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
		JSONObject jsj = new JSONObject();
		jsj.put("subjob", subjob.toJSONString());
		String result = scheduleUtils.modifySubJob(subjob.getId(), jsj.toJSONString());
		JSONObject jresult= JSONObject.parseObject(result);
		if(jresult.getInteger("code")==200)
		{
			return subJobDao.save(subjob);
		}
		else
		{
			return new SubJob();
		}		
	}
	
	public SubJob updateSubJob(SubJob subjob, Parser parser)
	{
		JSONObject jsj = new JSONObject();
		jsj.put("subjob", subjob.toJSONString());
		jsj.put("parser", parser.toJSONString());
		String result = scheduleUtils.modifySubJob(subjob.getId(), jsj.toJSONString());
		JSONObject jresult= JSONObject.parseObject(result);
		if(jresult.getInteger("code")==200)
		{
			parserDao.save(parser);
			return subJobDao.save(subjob);
		}
		else
		{
			return new SubJob();
		}
	}

	@Transactional
	@Override
	public String startSubJob(int subjobid)
	{
		return scheduleUtils.startSubJob(subjobid);
	}
	
	@Transactional
	@Override
	public String stopSubJob(int subjobid)
	{
		return scheduleUtils.stopSubJob(subjobid);
	}
	
	@Transactional
	@Override
	public String restartSubJob(int subjobid)
	{
		return scheduleUtils.restartSubJob(subjobid);
	}
	
	@Transactional
	@Override
	public String deleteSubJob(int subjobid)
	{
		return scheduleUtils.deleteSubJob(subjobid);
	}
	
	@Override
	public SubJob getSubJobById(int id)
	{
		
		return subJobDao.findById(id);
	}

	@Override
	public int countAll()
	{
		
		return subJobDao.countAll();
	}

	@Override
	public List<SubJob> findAll()
	{
		// TODO Auto-generated method stub
		return subJobDao.findAll();
	}
	
	@Override
	public List<SubJob> findByIds(Collection<Integer> ids)
	{
		return subJobDao.findByIds(ids);
	}
	
	@Override
	public List<SubJob> findByJobId(int jid)
	{
		return subJobDao.findByJobId(jid);
	}
}
