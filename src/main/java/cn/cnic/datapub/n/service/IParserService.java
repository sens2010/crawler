package cn.cnic.datapub.n.service;

import java.util.List;

import cn.cnic.datapub.n.model.Parser;


public interface IParserService
{
	Parser addParser(Parser parser);
	boolean deleteById(int id);
	Parser updateParser(Parser parser);
	Parser getParserById(int id);
	int countAll();
	List<Parser> findAll();
	List<Parser> list(int pid, int size);
}
