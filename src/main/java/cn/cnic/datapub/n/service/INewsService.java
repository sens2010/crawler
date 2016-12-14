package cn.cnic.datapub.n.service;

import java.util.List;

import cn.cnic.datapub.n.model.News;


public interface INewsService
{
	News addNews(News news);
	boolean deleteById(int id);
	News updateNews(News news);
	News getNewsById(int id);
	News getNewsByNewsId(String newsid);
	int countAll();
	List<News> findAll();
	List<News> list(int pid, int size);
	int countAll(String name, String value);
	List<News> list(String name, String value, int pid, int size);
}
