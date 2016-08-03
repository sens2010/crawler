package cn.cnic.datapub.job;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.general.AbstractDAO;
//@Repository(value="jobBatchDAO")
@Deprecated
public class JobBatchDAO extends AbstractDAO<JobBatchM>
{
	@Resource
	JdbcTemplate jdbcTemplate;
	
	public JobBatchDAO()
	{
		setTablename("op_batch");
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
