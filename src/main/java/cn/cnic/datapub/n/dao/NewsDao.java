package cn.cnic.datapub.n.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.News;

@Repository("newsDao")
public interface NewsDao extends CrudRepository<News, Integer>
{
	News findById(int id);
	
	@Query("select count(*) from News")
	int countAll();
	
	@Query("from News")
	List<News> findAll();
	
	@Query("from News n where n.newsid=?1")
	News findByNewId(String newsid);
}
