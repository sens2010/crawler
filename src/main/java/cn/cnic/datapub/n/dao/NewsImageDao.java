package cn.cnic.datapub.n.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.n.model.NewsImage;

@Repository("newsImageDao")
public interface NewsImageDao extends CrudRepository<NewsImage, Integer>
{
	NewsImage findById(int id);
}
