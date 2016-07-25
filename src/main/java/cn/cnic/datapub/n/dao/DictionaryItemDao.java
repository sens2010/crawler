package cn.cnic.datapub.n.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.DictionaryItem;

@Repository("dictionaryItemDao")
public interface DictionaryItemDao extends CrudRepository<DictionaryItem, Integer>
{
	DictionaryItem findById(int id);
	
	@Query("select count(*) from DictionaryItem")
	int countAll();
	
	@Query("from DictionaryItem")
	List<DictionaryItem> findAll();
}
