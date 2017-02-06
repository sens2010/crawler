package cn.cnic.datapub.n.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Transient;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.NewsDao;
import cn.cnic.datapub.n.dao.StatLabelDao;
import cn.cnic.datapub.n.model.News;
import cn.cnic.datapub.n.service.INewsService;

@Service("newsServiceImpl")
public class NewsServiceImpl implements INewsService
{
	@Resource
	NewsDao newsDao;

	@Resource
	StatLabelDao statLabelDao;
	
	@Transient
	@Override
	public News addNews(News news)
	{
		return newsDao.save(news);
	}
	@Transient
	@Override
	public boolean deleteById(int id)
	{
		try
		{
			newsDao.delete(id);
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
	public News updateNews(News news)
	{
		return newsDao.save(news);
	}

	@Override
	public News getNewsById(int id)
	{
		return newsDao.findById(id);
	}

	@Override
	public News getNewsByNewsId(String newsid)
	{
		return newsDao.findByNewId(newsid);
	}

	@Override
	public int countAll()
	{
		return newsDao.countAll();
	}
	
	@Override
	public int countAll(String name,String value)
	{
		System.err.println(value);
		if(name.equals("id"))
			return (newsDao.findById(Integer.parseInt(value))==null?0:1);
		else if(name.equals("name"))
			return newsDao.countAllByName("%"+value+"%");
		else if(name.equals("resource"))
			return newsDao.countAllByResource("%"+value+"%");
		else if(name.equals("text"))
			return newsDao.countAllByText("%"+value+"%");
		else
			return 0;
	}
	
	@Override
	public List<News> list(String name,String value,int pid, int size)
	{
		int offset = pid*size;
		if(name.equals("id"))
		{
			List<News> newslist = new ArrayList<News>();
			newslist.add(newsDao.findById(Integer.parseInt(value)));
			return newslist;
		}
		else if(name.equals("name"))
			return newsDao.findAllByName("%"+value+"%",size,offset);
		else if(name.equals("resource"))
			return newsDao.findAllByResource("%"+value+"%",size,offset);
		else if(name.equals("text"))
			return newsDao.findAllByText("%"+value+"%",size,offset);
		else
			return null;
	}

	@Override
	public List<News> findAll()
	{
		return newsDao.findAll();
	}
	
	@Override
	public List<News> list(int pid, int size)
	{
		int offset = pid*size;
		return newsDao.findAll(size, offset);
	}
}
