package cn.cnic.datapub.job.manager;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
@Deprecated
public class ParseDataM
{
	private int id;
	private String listparse;
	private boolean listcss;
	private boolean listjs;
	private String nextparse;
	private boolean urlrelative;
	private String titleparse;
	private String timeparse;
	private String sourceparse;
	private String textparse;
	private boolean artcss;
	private boolean artjs;
	private int status;
	private Date starttime;
	private Date lastmodifytime;
	
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}



	public String getListparse()
	{
		return listparse;
	}



	public void setListparse(String listparse)
	{
		this.listparse = listparse;
	}



	public boolean isListcss()
	{
		return listcss;
	}



	public void setListcss(boolean listcss)
	{
		this.listcss = listcss;
	}



	public boolean isListjs()
	{
		return listjs;
	}



	public void setListjs(boolean listjs)
	{
		this.listjs = listjs;
	}



	public String getNextparse()
	{
		return nextparse;
	}



	public void setNextparse(String nextparse)
	{
		this.nextparse = nextparse;
	}



	public boolean isUrlrelative()
	{
		return urlrelative;
	}



	public void setUrlrelative(boolean urlrelative)
	{
		this.urlrelative = urlrelative;
	}



	public String getTitleparse()
	{
		return titleparse;
	}



	public void setTitleparse(String titleparse)
	{
		this.titleparse = titleparse;
	}



	public String getTimeparse()
	{
		return timeparse;
	}



	public void setTimeparse(String timeparse)
	{
		this.timeparse = timeparse;
	}



	public String getSourceparse()
	{
		return sourceparse;
	}



	public void setSourceparse(String sourceparse)
	{
		this.sourceparse = sourceparse;
	}



	public String getTextparse()
	{
		return textparse;
	}



	public void setTextparse(String textparse)
	{
		this.textparse = textparse;
	}



	public boolean isArtcss()
	{
		return artcss;
	}



	public void setArtcss(boolean artcss)
	{
		this.artcss = artcss;
	}



	public boolean isArtjs()
	{
		return artjs;
	}



	public void setArtjs(boolean artjs)
	{
		this.artjs = artjs;
	}



	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Date getStarttime()
	{
		return starttime;
	}

	public void setStarttime(Date starttime)
	{
		this.starttime = starttime;
	}

	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime)
	{
		this.lastmodifytime = lastmodifytime;
	}

	public String toString()
	{
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("id", this.getId());
		jsonobject.put("listparser", this.getListparse());
		jsonobject.put("listcss", this.isListcss());
		jsonobject.put("listjs", this.isListjs());
		jsonobject.put("nextparse", this.getNextparse());
		jsonobject.put("urlrelative", this.isUrlrelative());
		jsonobject.put("titleparse", this.getTitleparse());
		jsonobject.put("timeparse", this.getTimeparse());
		jsonobject.put("sourceparse", this.getSourceparse());
		jsonobject.put("textparse", this.getTextparse());
		jsonobject.put("artcss", this.isArtcss());
		jsonobject.put("artjs", this.isArtjs());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jsonobject.put("status", this.getStatus());
		if(this.getStarttime()!=null)
		jsonobject.put("starttime", sdf.format(this.getStarttime()));
		if(this.getLastmodifytime()!=null)
		jsonobject.put("lastmodifytime", sdf.format(this.getLastmodifytime()));
		return jsonobject.toJSONString();
	}
	
}
