package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.News;

@Repository("newsDao")
public interface NewsDao extends CrudRepository<News, Integer>
{
	News findById(int id);
}
