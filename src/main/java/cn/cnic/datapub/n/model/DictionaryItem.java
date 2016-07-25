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
@Table(name="util_dictionary_item")
@PrimaryKeyJoinColumn( name = "id" ) 
public class DictionaryItem
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name")
	private String name ;
	@Column(name="innerid")
	private int innerid ;
	@Column(name="dictionaryid")
	private int dictionaryid ;
	@Column(name="description")
	private	String description;
	@Column(name="status")
	private int status;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="lastmodifytime")
	private Date lastmodifytime;

	
	
	public int getInnerid()
	{
		return innerid;
	}

	public DictionaryItem setInnerid(int innerid)
	{
		this.innerid = innerid;
		return this;
	}

	public int getId()
	{
		return id;
	}

	public DictionaryItem setId(int id)
	{
		this.id = id;
		return this;
	}

	public String getName()
	{
		return name;
	}

	public DictionaryItem setName(String name)
	{
		this.name = name;
		return this;
	}

	public int getDictionaryid()
	{
		return dictionaryid;
	}

	public DictionaryItem setDictionaryid(int dictionaryid)
	{
		this.dictionaryid = dictionaryid;
		return this;
	}

	public String getDescription()
	{
		return description;
	}

	public DictionaryItem setDescription(String description)
	{
		this.description = description;
		return this;
	}

	public int getStatus()
	{
		return status;
	}

	public DictionaryItem setStatus(int status)
	{
		this.status = status;
		return this;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public DictionaryItem setCreatetime(Date createtime)
	{
		this.createtime = createtime;
		return this;
	}

	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}

	public DictionaryItem setLastmodifytime(Date lastmodifytime)
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
	