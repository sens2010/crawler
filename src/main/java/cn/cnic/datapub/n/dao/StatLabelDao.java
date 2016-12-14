package cn.cnic.datapub.n.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.cnic.datapub.n.model.StatLabel;

@Repository("statLabelDao")
public interface StatLabelDao extends CrudRepository<StatLabel, Integer>
{
	StatLabel findById(int id);

	@Query("from StatLabel p where p.status in ?1")
	List<StatLabel> findNormal(Collection<Integer> statuses);

	@Query("from StatLabel p where p.status = 1 and p.newsid =?1")
	List<StatLabel> findByNewsId(int newsid);
	
	@Query("from StatLabel p where p.status = 1 and p.newsid in ?1")
	List<StatLabel> findByNewsIds(Collection<Integer> newsids);
	
	@Query("from StatLabel p where p.newsid =?1 and p.categoryid=?2")
	List<StatLabel> findByKeys(int newsid, int categoryid);
	
	@Modifying
	@Transactional
	@Query(value="delete from stat_label where newsid=?1", nativeQuery=true)
	int deleteByNewsId(int newsid);
	
	@Modifying
	@Transactional
	@Query(value="delete from stat_label where newsid=?1 and categoryid in ?2", nativeQuery=true)
	int deleteByNewsIds(int newsid, Collection<Integer> categoryids);
	
	@Modifying
	@Transactional
	@Query(value="delete from stat_label where id in ?1", nativeQuery=true)
	int deleteByIds(Collection<Integer> ids);
	
}
