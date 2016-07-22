package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.DictionaryItem;

@Repository("dictionaryItemDao")
public interface DictionaryItemDao extends CrudRepository<DictionaryItem, Integer>
{
	DictionaryItem findById(int id);
	
}
