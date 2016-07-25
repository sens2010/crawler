package cn.cnic.datapub.n.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Dictionary;

@Repository("dictionaryDao")
public interface DictionaryDao extends CrudRepository<Dictionary, Integer>
{
	Dictionary findById(int id);
	
	@Query("select count(*) from Dictionary")
	int countAll();
	
	@Query("from Dictionary")
	List<Dictionary> findAll();
}
