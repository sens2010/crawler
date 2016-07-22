package cn.cnic.datapub.job.manager;

import com.alibaba.fastjson.JSONObject;

public class SubJobDataM
{
	private int id;
	private int jobid;
	private String name;
	private String url;
	private int category;
	private int status;
	private String description;
	public int getId()
	{
		return id;
	}




	public void setId(int id)
	{
		this.id = id;
	}




	public int getJobid()
	{
		return jobid;
	}




	public void setJobid(int jobid)
	{
		this.jobid = jobid;
	}




	public String getName()
	{
		return name;
	}




	public void setName(String name)
	{
		this.name = name;
	}




	public String getUrl()
	{
		return url;
	}




	public void setUrl(String url)
	{
		this.url = url;
	}




	public int getCategory()
	{
		return category;
	}




	public void setCategory(int category)
	{
		this.category = category;
	}

	
	
	
	
	public String getDescription()
	{
		return description;
	}




	public void setDescription(String description)
	{
		this.description = description;
	}




	public int getStatus()
	{
		return status;
	}




	public void setStatus(int status)
	{
		this.status = status;
	}




	public String toString()
	{
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("id", this.getId());
		jsonobject.put("jobid", this.getJobid());
		jsonobject.put("name", this.getName());
		jsonobject.put("url", this.getUrl());
		jsonobject.put("category", this.getCategory());
		jsonobject.put("status", this.getStatus());
		jsonobject.put("description", this.getDescription());
		return jsonobject.toJSONString();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
