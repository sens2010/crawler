package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Dictionary;

@Repository("dictionaryDao")
public interface DictionaryDao extends CrudRepository<Dictionary, Integer>
{
	Dictionary findById(int id);
	
}
