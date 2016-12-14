package cn.cnic.datapub.n.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.model.News;

@Repository("newsDao")
public interface NewsDao extends CrudRepository<News, Integer>
{
	News findById(int id);
	
	@Query("select count(*) from News")
	int countAll();
	
	@Query(value="select count(*) from result_news n where n.title like ?1" , nativeQuery=true)
	int countAllByName(String value);
	
	@Query(value="select count(*) from result_news n where n.resource like ?1" , nativeQuery=true)
	int countAllByResource(String value);
	
	@Query(value="select count(*) from result_news n where n.text like ?1", nativeQuery=true)
	int countAllByText(String text);
	
	@Query("from News")
	List<News> findAll();
	
	@Query("from News n where n.newsid=?1")
	News findByNewId(String newsid);
	
	@Query(value="select * from result_news n order by n.pubtime desc limit ?1 offset ?2", nativeQuery=true)
	List<News> findAll(int limit, int offset);
	
	@Query(value="select * from result_news n where n.title like ?1 order by n.pubtime desc limit ?2 offset ?3", nativeQuery=true)
	List<News> findAllByName(String value, int limit, int offset);
	
	@Query(value="select * from result_news n where n.resource like ?1 order by n.pubtime desc limit ?2 offset ?3", nativeQuery=true)
	List<News> findAllByResource(String value, int limit, int offset);
	
	@Query(value="select * from result_news n where n.text like ?1 order by n.pubtime desc limit ?2 offset ?3", nativeQuery=true)
	List<News> findAllByText(String value, int limit, int offset);
	
}
