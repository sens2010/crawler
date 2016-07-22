package cn.cnic.datapub.job.manager;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.general.AbstractDAO;

@Repository(value="subJobDataDAO")
public class SubJobDataDAO extends AbstractDAO<SubJobDataM>
{
	@Resource
	JdbcTemplate jdbcTemplate;
	
	
	public SubJobDataDAO()
	{
		setTablename("job_sub_metadata");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
