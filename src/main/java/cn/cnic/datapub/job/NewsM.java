package cn.cnic.datapub.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class NewsM
{
	private int id;
	private String newsid;
	private String url;
	private int pagecount;
	private String resource;
	private Date pubtime;
	private String title;
	private String text;
	private Date lastmodifytime;
	private int relatebatch;
	
	
	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public String getNewsid()
	{
		return newsid;
	}


	public void setNewsid(String newsid)
	{
		this.newsid = newsid;
	}


	public String getUrl()
	{
		return url;
	}


	public void setUrl(String url)
	{
		this.url = url;
	}


	public int getPagecount()
	{
		return pagecount;
	}


	public void setPagecount(int pagecount)
	{
		this.pagecount = pagecount;
	}


	public String getResource()
	{
		return resource;
	}


	public void setResource(String resource)
	{
		this.resource = resource;
	}


	public Date getPubtime()
	{
		return pubtime;
	}


	public void setPubtime(Date pubtime)
	{
		this.pubtime = pubtime;
	}


	public String getTitle()
	{
		return title;
	}


	public void setTitle(String title)
	{
		this.title = title;
	}


	public String getText()
	{
		return text;
	}


	public void setText(String text)
	{
		this.text = text;
	}


	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}


	public void setLastmodifytime(Date lastmodifytime)
	{
		this.lastmodifytime = lastmodifytime;
	}


	public int getRelatebatch()
	{
		return relatebatch;
	}


	public void setRelatebatch(int relatebatch)
	{
		this.relatebatch = relatebatch;
	}

	public String toString()
	{
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("id", this.getId());
		jsonobject.put("newsid", this.getNewsid());
		jsonobject.put("url", this.getUrl());
		jsonobject.put("pagecount", this.getPagecount());
		jsonobject.put("resource", this.getResource());
		jsonobject.put("pubtime", this.getPubtime());
		jsonobject.put("title", this.title);
		jsonobject.put("text", this.text);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(this.getPubtime()!=null)
		jsonobject.put("pubtime", sdf.format(this.getPubtime()));
		if(this.getLastmodifytime()!=null)
		jsonobject.put("lastmodifytime", sdf.format(this.getLastmodifytime()));
		jsonobject.put("relatesubbatch", this.getRelatebatch());
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
