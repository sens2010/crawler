package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="stat_label")
@PrimaryKeyJoinColumn( name = "id" ) 
public class StatLabel
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="relation")
	private	int relation;
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

	public void setName(String name)
	{
		this.name = name;
	}

	public int getRelation()
	{
		return relation;
	}

	public void setRelation(int relation)
	{
		this.relation = relation;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
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
	