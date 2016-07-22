package cn.cnic.datapub.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class ParamTransfer
{
	public static Object[] paramsAnalyseSQL(JSONObject params)
	{
		if(params!=null)
		{
			List<String> names = new ArrayList<String>();
			List<Object> values = new ArrayList<Object>();
			for(String key:params.keySet())
			{
				names.add(key);
				values.add(params.get(key));
			}
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<names.size()-1;i++)
			{
				sb.append(names.get(i)+"=? and ");
			}
			sb.append(names.get(names.size()-1)+"=? ");
			return new Object[]{sb.toString(),values.toArray()};
		}
		else
		{
			return new Object[]{null,null};
		}
	}
	
	public static Object[] paramsAnalyseAddSQL(JSONObject params)
	{
		if(params!=null)
		{
			List<String> names = new ArrayList<String>();
			List<Object> values = new ArrayList<Object>();
			for(String key:params.keySet())
			{
				if(!key.equals("id"))
				{
					names.add(key);
					values.add(params.get(key));
				}
			}
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<names.size()-1;i++)
			{
				sb.append(names.get(i)+", ");
			}
			sb.append(names.get(names.size()-1)+" ");
			return new Object[]{sb.toString(),values.toArray()};
		}
		else
		{
			return new Object[]{null,null};
		}
	}
	
	public static Object[] paramsAnalyseAddSQLWithoutNull(JSONObject params)
	{
		if(params!=null)
		{
			List<String> names = new ArrayList<String>();
			List<Object> values = new ArrayList<Object>();
			for(String key:params.keySet())
			{
				Object value = params.get(key);
				if(value!=null&&!value.toString().equals("")&&!key.equals("id"))
				{
					names.add(key);
					values.add(params.get(key));
				}
			}
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<names.size()-1;i++)
			{
				sb.append(names.get(i)+", ");
			}
			sb.append(names.get(names.size()-1)+" ");
			System.out.println(params);
			System.out.println(names);
			System.out.println(values);
			return new Object[]{sb.toString(),values.toArray()};
		}
		else
		{
			return new Object[]{null,null};
		}
	}
	
	
	
	public static Object[] paramsAnalyseUpdateSQL(JSONObject params)
	{
		if(params!=null)
		{
			List<String> names = new ArrayList<String>();
			List<Object> values = new ArrayList<Object>();
			Object id = null;
			for(String key:params.keySet())
			{
				if(!key.equals("id"))
				{
					names.add(key);
					values.add(params.get(key));
				}	
				else
				{
					id=params.get("id");
				}
			}
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<names.size()-1;i++)
			{
				sb.append(names.get(i)+"=?, ");
			}
			sb.append(names.get(names.size()-1)+"=? ");
			values.add(id);
			return new Object[]{sb.toString(),values.toArray()};
		}
		else
		{
			return new Object[]{null,null};
		}
	}
	
	public static Object[] paramsAnalyseUpdateSQLWithId(JSONObject params,Object id)
	{
		if(params!=null)
		{
			List<String> names = new ArrayList<String>();
			List<Object> values = new ArrayList<Object>();
			for(String key:params.keySet())
			{
				names.add(key);
				values.add(params.get(key));
				
			}
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<names.size()-1;i++)
			{
				sb.append(names.get(i)+"=?, ");
			}
			sb.append(names.get(names.size()-1)+"=? ");
			values.add(id);
			return new Object[]{sb.toString(),values.toArray()};
		}
		else
		{
			return new Object[]{null,null};
		}
	}
	
}
