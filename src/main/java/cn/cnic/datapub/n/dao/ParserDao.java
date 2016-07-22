package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.Parser;

@Repository("parserDao")
public interface ParserDao extends CrudRepository<Parser, Integer>
{
	Parser findById(int id);
}
