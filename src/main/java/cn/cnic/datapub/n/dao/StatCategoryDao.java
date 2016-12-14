package cn.cnic.datapub.n.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.StatCategory;
import cn.cnic.datapub.n.model.StatResult;

@Repository("statCategoryDao")
public interface StatCategoryDao extends CrudRepository<StatCategory, Integer>
{
	StatCategory findById(int id);
	
	@Query("from StatCategory l where l.status = 1 and l.id=?1")
	List<StatCategory> findByNewsID(int newsid);

	@Query("from StatCategory l where l.status = 1 and l.id in ?1")
	List<StatCategory> findByIDs(Collection<Integer> ids);
	
	
	@Query("from StatCategory l where l.status = 1 order by l.code")
	List<StatCategory> findAll();
	
	
	@Query(value="select * from stat_category where code like ?1 order by code desc limit 1", nativeQuery=true)
	StatCategory matchLastOne(String subcode);
	
	
}
