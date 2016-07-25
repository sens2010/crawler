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
@Table(name="util_dictionary")
@PrimaryKeyJoinColumn( name = "id" ) 
public class Dictionary
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name")
	private String name ;
	@Column(name="category")
	private int category ;
	@Column(name="description")
	private	String description;
	@Column(name="status")
	private int status;
	@Column(name="createtime")
	private Date createtime;
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

	public String getName()
	{
		return name;
	}

	public Dictionary setName(String name)
	{
		this.name = name;
		return this;
	}

	public int getCategory()
	{
		return category;
	}

	public Dictionary setCategory(int category)
	{
		this.category = category;
		return this;
	}

	public String getDescription()
	{
		return description;
	}

	public Dictionary setDescription(String description)
	{
		this.description = description;
		return this;
	}

	public int getStatus()
	{
		return status;
	}

	public Dictionary setStatus(int status)
	{
		this.status = status;
		return this;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public Dictionary setCreatetime(Date createtime)
	{
		this.createtime = createtime;
		return this;
	}

	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}

	public Dictionary setLastmodifytime(Date lastmodifytime)
	{
		this.lastmodifytime = lastmodifytime;
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
	