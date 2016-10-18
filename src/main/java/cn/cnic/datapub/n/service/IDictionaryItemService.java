package cn.cnic.datapub.n.service;

import java.util.List;

import cn.cnic.datapub.n.model.DictionaryItem;


public interface IDictionaryItemService
{
	DictionaryItem addDictionaryItem(DictionaryItem dictionaryitem);
	boolean deleteById(int id);
	DictionaryItem updateDictionaryItem(DictionaryItem dictionaryitem);
	DictionaryItem getBatchById(int id);
	int countAll();
	List<DictionaryItem> findAll();
	List<DictionaryItem> findCodes(int did);
}
