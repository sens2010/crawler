package cn.cnic.datapub.general;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.cnic.datapub.util.ParamTransfer;
import com.alibaba.fastjson.JSONObject;
@Deprecated
public abstract class AbstractDAO<T> implements IGeneralDAO<T>
{
	@Resource
	JdbcTemplate jdbcTemplate;
	
	private String tablename;
	
	public T findByIdTest(Object id)
	{
		T t = null;
		if(id!=null)
		{
			String sql = "select * from "+getTablename()+" where id=?";
			t = this.jdbcTemplate.queryForObject(sql,new Object[]{id}, new BeanPropertyRowMapper<T>(getTClass()));
		}
		return t;
	}
	
	@Override
	public T findById(Object id)
	{
		T t = null;
		if(id!=null)
		{
			String sql = "select * from "+getTablename()+" where id=?";
			t = this.jdbcTemplate.queryForObject(sql,new Object[]{id}, new BeanPropertyRowMapper<T>(getTClass()));
		}
		return t;
	}

	@Override
	public List<T> findByParams(JSONObject json)
	{	
		Object[] params = ParamTransfer.paramsAnalyseSQL(json);
		if(params[0]!=null&&params[1]!=null)
		{
			String sql = " select * from "+getTablename()+" where "+params[0];
			return this.jdbcTemplate.query(sql,(Object[])params[1],new BeanPropertyRowMapper<T>(getTClass()));
		}
		else
		{
			return findAll();
		}
	}

	@Override
	public boolean add(T model)
	{
		if(model!=null)
		{
			JSONObject jmodel = JSONObject.parseObject(model.toString());
			Object[] params = ParamTransfer.paramsAnalyseAddSQLWithoutNull(jmodel);
			
			if(params[0]!=null&&params[1]!=null)
			{
				int count = ((Object[])params[1]).length;
				StringBuilder paramsb = new StringBuilder();
				for(int i=0;i<count-1;i++)
				{
					paramsb.append("?,");
				}
				paramsb.append("?");
				String sql = "insert into "+getTablename()+"("+params[0]+") values("+paramsb+")";
				int result = this.jdbcTemplate.update(sql,(Object[])params[1]);
				if(result>=1)
				{
					return true;
				}
			}	
		}
		return false;
		
	}

	@Override
	public boolean delete(T model)
	{
		if(model!=null)
		{
			JSONObject jmodel = JSONObject.parseObject(model.toString());
			return deleteById(jmodel.get("id"));
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
			String sql = "delete from "+getTablename()+" where id=?";
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
	public boolean modify(T model)
	{
		if(model!=null)
		{
			JSONObject jmodel = JSONObject.parseObject(model.toString());
			Object[] params = ParamTransfer.paramsAnalyseUpdateSQL(jmodel);
			
			if(params[0]!=null&&params[1]!=null)
			{
				String sql = "update "+getTablename()+" set "+params[0]+" where id=?";
				int num = this.jdbcTemplate.update(sql,(Object[])params[1]);
				if(num>0)return true;
			}
		}
		return false;
	}

	@Override
	public boolean modifyById(Object id, T model)
	{
		if(id!=null)
		{
			JSONObject jmodel = JSONObject.parseObject(model.toString());
			Object[] params = ParamTransfer.paramsAnalyseUpdateSQLWithId(jmodel,id);
			String sql = "update "+getTablename()+" set "+params[0]+" where id=?";
			int num = this.jdbcTemplate.update(sql,(Object[])params[1]);
			if(num>0)return true;
			else return false;
		}
		else
		{
			return false;
		}
	}

	@Override
	public List<T> findAll()
	{
		String sql = " select * from "+getTablename()+" ";
		return this.jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<T>(getTClass()));

	}

	@Override
	public int count()
	{
		String sql = " select count(*) from "+getTablename()+" ";
		return this.jdbcTemplate.queryForObject(sql,new Object[]{},Integer.class);
	}
	
	@SuppressWarnings("unchecked")
	public Class<T> getTClass()
	{
	        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	        return tClass;
	}

	public String getTablename()
	{
		return tablename;
	}

	public void setTablename(String tablename)
	{
		this.tablename = tablename;
	}
}
