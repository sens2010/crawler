package cn.cnic.datapub.n.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Parser;

@Repository("parserDao")
public interface ParserDao extends CrudRepository<Parser, Integer>
{
	Parser findById(int id);
	
	@Query("select count(*) from Parser")
	int countAll();
	
	@Query("from Parser")
	List<Parser> findAll();
}
