package cn.cnic.datapub.n.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private int description;

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

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getDescription()
	{
		return description;
	}

	public void setDescription(int description)
	{
		this.description = description;
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
	