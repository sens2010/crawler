package cn.cnic.datapub.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class JobBatchM
{
	private int id;
	private int jobid;
	private Date starttime;
	private Date endtime;
	private  int status;
	private  int monitorinfo;
	
	
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


	public Date getStarttime()
	{
		return starttime;
	}


	public void setStarttime(Date starttime)
	{
		this.starttime = starttime;
	}


	public Date getEndtime()
	{
		return endtime;
	}


	public void setEndtime(Date endtime)
	{
		this.endtime = endtime;
	}


	public int getStatus()
	{
		return status;
	}


	public void setStatus(int status)
	{
		this.status = status;
	}


	public int getMonitorinfo()
	{
		return monitorinfo;
	}


	public void setMonitorinfo(int monitorinfo)
	{
		this.monitorinfo = monitorinfo;
	}
	
	
	public String toString()
	{
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("id", this.getId());
		jsonobject.put("jobid", this.getJobid());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(this.getStarttime()!=null)
		jsonobject.put("starttime", sdf.format(this.getStarttime()));
		if(this.getEndtime()!=null)
		jsonobject.put("endtime", sdf.format(this.getEndtime()));
		jsonobject.put("status", this.getStatus());
		jsonobject.put("monitorinfo", this.getMonitorinfo());
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
