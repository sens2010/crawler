package cn.cnic.datapub.job.manager;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.cnic.datapub.general.AbstractDAO;

@Repository(value="jobDataDAO")
public class JobDataDAO extends AbstractDAO<JobDataM>
{
	@Resource
	JdbcTemplate jdbcTemplate;
	
	
	public JobDataDAO()
	{
		setTablename("job_metadata");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/*@Override
	public JobDataM findById(Object id)
	{
		JobDataM jobDataM = null;
		if(id!=null)
		{
			String sql = "select * from job_metadata where id=?";
			jobDataM = this.jdbcTemplate.queryForObject(sql,new Object[]{id}, new BeanPropertyRowMapper<JobDataM>(JobDataM.class));
		}
		return jobDataM;
	}

	@Override
	public List<JobDataM> findByParams(JSONObject json)
	{	
		Object[] params = ParamTransfer.paramsAnalyseSQL(json);
		if(params[0]!=null&&params[1]!=null)
		{
			String sql = " select * from job_metadata where "+params[0];
			return this.jdbcTemplate.query(sql,(Object[])params[1],new BeanPropertyRowMapper<JobDataM>(JobDataM.class));
		}
		else
		{
			return findAll();
		}
	}

	@Override
	public boolean add(JobDataM model)
	{
		if(model!=null)
		{
			String sql = "insert into job_metadata(NAME,PLAN,STATUS,DESCRIPTION) values(?,?,?,?)";
			Object[] paras = new Object[]{model.getName(),model.getPlan(),model.getStatus(),model.getDescription()};
			int result = this.jdbcTemplate.update(sql,paras);
			if(result>=1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean delete(JobDataM model)
	{
		if(model!=null)
		{
			return deleteById(model.getId());
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean deleteById(Object id)
	{
		if(id!=null)
		{
			String sql = "delete from job_metadata where id=?";
			int num = this.jdbcTemplate.update(sql,new Object[]{id});
			if(num>0)return true;
			else return false;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean modify(JobDataM model)
	{
		if(model!=null)
		{
			String sql = "update job_metadata set NAME=?, PLAN=?, STATUS=?, DESCRIPTION=? where id=?";
			int num = this.jdbcTemplate.update(sql,new Object[]{model.getName(),model.getPlan(),model.getStatus(),model.getDescription(),model.getId()});
			if(num>0)return true;
			else return false;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean modifyById(Object id, JobDataM model)
	{
		if(id!=null)
		{
			String sql = "update job_metadata set id=?,NAME=?, PLAN=?, STATUS=?, DESCRIPTION=? where id=?";
			int num = this.jdbcTemplate.update(sql,new Object[]{model.getId(),model.getName(),model.getPlan(),model.getStatus(),model.getDescription(),model.getId(),id});
			if(num>0)return true;
			else return false;
		}
		else
		{
			return false;
		}
	}


	@Override
	public List<JobDataM> findAll()
	{
		String sql = " select * from job_metadata ";
		return this.jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<JobDataM>(JobDataM.class));

	}


	@Override
	public int count()
	{
		String sql = " select count(*) from job_metadata ";
		return this.jdbcTemplate.queryForObject(sql,new Object[]{},Integer.class);
	}*/
	
}
