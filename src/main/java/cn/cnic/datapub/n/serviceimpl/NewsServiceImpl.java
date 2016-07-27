package cn.cnic.datapub.n.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.NewsDao;
import cn.cnic.datapub.n.model.News;
import cn.cnic.datapub.n.service.INewsService;

@Service("newsServiceImpl")
public class NewsServiceImpl implements INewsService
{
	@Resource
	NewsDao newsDao;

	@Override
	public News addNews(News news)
	{
		return newsDao.save(news);
	}

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
	public List<News> findAll()
	{
		return newsDao.findAll();
	}
}
