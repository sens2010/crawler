package cn.cnic.datapub.n.service;

import java.util.List;

import cn.cnic.datapub.n.model.Dictionary;


public interface IDictionaryService
{
	Dictionary addDictionary(Dictionary dictionary);
	boolean deleteById(int id);
	Dictionary updateDictionary(Dictionary dictionary);
	Dictionary getBatchById(int id);
	int countAll();
	List<Dictionary> findAll();
}
