package cn.cnic.datapub.n.service;

import java.util.Collection;
import java.util.List;

import cn.cnic.datapub.n.model.StatCategory;


public interface IStatCategoryService
{

	List<StatCategory> findCategories(Collection<Integer> ids);

	StatCategory addStatCategory(StatCategory statCategory);

	boolean deleteStatCategory(int categoryid);

	StatCategory updateStatCategory(StatCategory statCategory);

	StatCategory findStatCategoryById(int id);

	List<StatCategory> findAll();

	StatCategory matchLastOne(String subcode);
	
}
