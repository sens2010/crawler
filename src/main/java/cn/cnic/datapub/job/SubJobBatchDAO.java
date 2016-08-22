package cn.cnic.datapub.job;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.general.AbstractDAO;
@SuppressWarnings("deprecation")
@Repository(value="subJobBatchDAO")
@Deprecated
public class SubJobBatchDAO extends AbstractDAO<SubJobBatchM>
{
	@Resource
	JdbcTemplate jdbcTemplate;
	
	public SubJobBatchDAO()
	{
		setTablename("op_batch_sub");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
