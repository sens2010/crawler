package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="result_news")
@PrimaryKeyJoinColumn( name = "id" ) 
public class News
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="newsid")
	private String newsid;
	@Column(name="relatesubbatch")
	private	int relatesubbatch;
	@Column(name="url")
	private String url;
	@Column(name="pagecount")
	private int pagecount;
	@Column(name="resource")
	private String resource;
	@Column(name="pubtime")
	private Date pubtime;
	@Column(name="title")
	private String title;
	@Column(name="text")
	private String text;
	@Column(name="lastmodifytime")
	private Date lastmodifytime;
	


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

	public int getRelatesubbatch()
	{
		return relatesubbatch;
	}

	public void setRelatesubbatch(int relatesubbatch)
	{
		this.relatesubbatch = relatesubbatch;
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

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
}
	