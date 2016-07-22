package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.StatCategory;

@Repository("statCategoryDao")
public interface StatCategoryDao extends CrudRepository<StatCategory, Integer>
{
	StatCategory findById(int id);
}
