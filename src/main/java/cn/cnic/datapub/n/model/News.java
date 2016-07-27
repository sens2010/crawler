package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "result_news")
@PrimaryKeyJoinColumn(name = "id")
public class News extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name = "newsid")
	private String newsid;
	@Column(name = "relatebatch")
	private int relatebatch;
	@Column(name = "url")
	private String url;
	@Column(name = "pagecount")
	private int pagecount;
	@Column(name = "resource")
	private String resource;
	@Column(name = "pubtime")
	private Date pubtime;
	@Column(name = "title")
	private String title;
	@Column(name = "text")
	private String text;
	@Column(name = "createtime")
	private Date createtime;
	@Column(name = "lastmodifytime")
	private Date lastmodifytime;
	@Column(name = "status")
	private int status;
	
	public Date getCreatetime()
	{
		return createtime;
	}
	
	public News setCreatetime(Date createtime)
	{
		this.createtime = createtime;
		return this;
	}
	
	public int getId()
	{
		return id;
	}
	
	public News setId(int id)
	{
		this.id = id;
		return this;
	}
	
	public String getNewsid()
	{
		return newsid;
	}
	
	public News setNewsid(String newsid)
	{
		this.newsid = newsid;
		return this;
	}
	
	public int getRelatebatch()
	{
		return relatebatch;
	}
	
	public News setRelatebatch(int relatebatch)
	{
		this.relatebatch = relatebatch;
		return this;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public News setUrl(String url)
	{
		this.url = url;
		return this;
	}
	
	public int getPagecount()
	{
		return pagecount;
	}
	
	public News setPagecount(int pagecount)
	{
		this.pagecount = pagecount;
		return this;
	}
	
	public String getResource()
	{
		return resource;
	}
	
	public News setResource(String resource)
	{
		this.resource = resource;
		return this;
	}
	
	public Date getPubtime()
	{
		return pubtime;
	}
	
	public News setPubtime(Date pubtime)
	{
		this.pubtime = pubtime;
		return this;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public News setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	public String getText()
	{
		return text;
	}
	
	public News setText(String text)
	{
		this.text = text;
		return this;
	}
	
	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}
	
	public News setLastmodifytime(Date lastmodifytime)
	{
		this.lastmodifytime = lastmodifytime;
		return this;
	}
	
	public int getStatus()
	{
		return status;
	}

	public News setStatus(int status)
	{
		this.status = status;
		return this;
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
