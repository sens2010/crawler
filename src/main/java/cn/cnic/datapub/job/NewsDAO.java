package cn.cnic.datapub.job;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.general.AbstractDAO;
//@Repository(value="newsDAO")
@Deprecated
public class NewsDAO extends AbstractDAO<NewsM>
{
	@Resource
	JdbcTemplate jdbcTemplate;
	
	public NewsDAO()
	{
		setTablename("result_news");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
