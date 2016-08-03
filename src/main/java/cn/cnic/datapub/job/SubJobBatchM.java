package cn.cnic.datapub.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
@Deprecated
public class SubJobBatchM
{
	private int id;
	private int subjobid;
	private int relatebatch;
	private Date starttime;
	private Date endtime;
	private int status;
	
	public int getId()
	{
		return id;
	}



	public void setId(int id)
	{
		this.id = id;
	}



	public int getSubjobid()
	{
		return subjobid;
	}



	public void setSubjobid(int subjobid)
	{
		this.subjobid = subjobid;
	}



	public int getRelatebatch()
	{
		return relatebatch;
	}



	public void setRelatebatch(int relatebatch)
	{
		this.relatebatch = relatebatch;
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


	public String toString()
	{
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("id", this.getId());
		jsonobject.put("subjobid", this.getSubjobid());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(this.getStarttime()!=null)
		jsonobject.put("starttime", sdf.format(this.getStarttime()));
		if(this.getEndtime()!=null)
		jsonobject.put("endtime", sdf.format(this.getEndtime()));
		jsonobject.put("status", this.getStatus());
		jsonobject.put("relatebatch", this.getRelatebatch());
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
