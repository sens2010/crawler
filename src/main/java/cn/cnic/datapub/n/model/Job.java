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
@Table(name="job_metadata")
@PrimaryKeyJoinColumn( name = "id" ) 
public class Job extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="plan")
	private String plan;
	@Column(name="status")
	private int status;
	@Column(name="description")
	private String description;
	@Column(name="category")
	private int category ;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="lastmodifytime")
	private Date lastmodifytime;
	
	
	public Date getCreatetime()
	{
		return createtime;
	}

	public Job setCreatetime(Date createtime)
	{
		this.createtime = createtime;
		return this;
	}

	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}

	public Job setLastmodifytime(Date lastmodifytime)
	{
		this.lastmodifytime = lastmodifytime;
		return this;
	}

	public int getCategory()
	{
		return category;
	}

	public Job setCategory(int category)
	{
		this.category = category;
		return this;
	}

	public int getId()
	{
		return id;
	}

	public Job setId(int id)
	{
		this.id = id;
		return this;
	}

	public String getName()
	{
		return name;
	}

	public Job setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getPlan()
	{
		return plan;
	}

	public Job setPlan(String plan)
	{
		this.plan = plan;
		return this;
	}

	public int getStatus()
	{
		return status;
	}

	public Job setStatus(int status)
	{
		this.status = status;
		return this;
	}

	public String getDescription()
	{
		return description;
	}

	public Job setDescription(String description)
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
	