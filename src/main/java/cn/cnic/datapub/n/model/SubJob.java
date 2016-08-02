package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="job_sub_metadata")
@PrimaryKeyJoinColumn( name = "id" ) 
public class SubJob extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="jobid")
	private int jobid;
	@Column(name="name")
	private String name;
	@Column(name="url")
	private String url;
	@Column(name="category")
	private int category;
	@Column(name="status")
	private int status;
	@Column(name="description")
	private String description;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="lastmodifytime")
	private Date lastmodifytime;
	@Column(name="interval")
	private int interval;
	
	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public SubJob setCreatetime(Date createtime)
	{
		this.createtime = createtime;
		return this;
	}

	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}

	public SubJob setLastmodifytime(Date lastmodifytime)
	{
		this.lastmodifytime = lastmodifytime;
		return this;
	}

	public int getId()
	{
		return id;
	}

	public SubJob setId(int id)
	{
		this.id = id;
		return this;
	}

	public int getJobid()
	{
		return jobid;
	}

	public SubJob setJobid(int jobid)
	{
		this.jobid = jobid;
		return this;
	}

	public String getName()
	{
		return name;
	}

	public SubJob setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getUrl()
	{
		return url;
	}

	public SubJob setUrl(String url)
	{
		this.url = url;
		return this;
	}

	public int getCategory()
	{
		return category;
	}

	public SubJob setCategory(int category)
	{
		this.category = category;
		return this;
	}

	public int getStatus()
	{
		return status;
	}

	public SubJob setStatus(int status)
	{
		this.status = status;
		return this;
	}

	public String getDescription()
	{
		return description;
	}

	public SubJob setDescription(String description)
	{
		this.description = description;
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
	