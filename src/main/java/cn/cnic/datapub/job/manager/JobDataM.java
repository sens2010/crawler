package cn.cnic.datapub.job.manager;

import com.alibaba.fastjson.JSONObject;

public class JobDataM
{
	private int id ;
	private String name;
	private String plan;
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



	public String getName()
	{
		return name;
	}



	public void setName(String name)
	{
		this.name = name;
	}



	public String getPlan()
	{
		return plan;
	}



	public void setPlan(String plan)
	{
		this.plan = plan;
	}



	public int getStatus()
	{
		return status;
	}



	public void setStatus(int status)
	{
		this.status = status;
	}



	public String getDescription()
	{
		return description;
	}



	public void setDescription(String description)
	{
		this.description = description;
	}

	public String toString()
	{
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("id", this.getId());
		jsonobject.put("name", this.getName());
		jsonobject.put("plan", this.getPlan());
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
