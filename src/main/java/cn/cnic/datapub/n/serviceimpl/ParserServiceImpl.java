package cn.cnic.datapub.n.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.ParserDao;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.service.IParserService;

@Service("parserServiceImpl")
public class ParserServiceImpl implements IParserService
{
	@Resource
	ParserDao parserDao;

	@Override
	public Parser addParser(Parser parser)
	{
		return parserDao.save(parser);
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			parserDao.delete(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Parser updateParser(Parser parser)
	{
		return parserDao.save(parser);
	}

	@Override
	public Parser getParserById(int id)
	{
		return parserDao.findById(id);
	}

	@Override
	public int countAll()
	{
		return parserDao.countAll();
	}

	@Override
	public List<Parser> findAll()
	{
		return parserDao.findAll();
	}
}
