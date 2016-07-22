package cn.cnic.datapub.n.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="plan")
	private String plan;
	@Column(name="status")
	private int status;
	@Column(name="description")
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
	