package cn.cnic.datapub.job.manager;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.cnic.datapub.general.AbstractDAO;

//@Repository(value="parseDataDAO")
@Deprecated
public class ParseDataDAO extends AbstractDAO<ParseDataM> 
{
	@Resource
	JdbcTemplate jdbcTemplate;
	
	
	public ParseDataDAO()
	{
		setTablename("job_sub_parse");
	}
}
