package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="op_batch_sub")
@PrimaryKeyJoinColumn( name = "id" ) 
public class SubBatch
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="subjobid")
	private int subjobid;
	@Column(name="relatebatch")
	private	int relatebatch;
	@Column(name="starttime")
	private Date starttime;
	@Column(name="endtime")
	private Date endtime;
	@Column(name="status")
	private int status;
	@Column(name="count")
	private int count;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getSubjobid()
	{
		return subjobid;
	}

	public void setSubjobid(int subjobid)
	{
		this.subjobid = subjobid;
	}

	public int getRelatebatch()
	{
		return relatebatch;
	}

	public void setRelatebatch(int relatebatch)
	{
		this.relatebatch = relatebatch;
	}

	public Date getStarttime()
	{
		return starttime;
	}

	public void setStarttime(Date starttime)
	{
		this.starttime = starttime;
	}

	public Date getEndtime()
	{
		return endtime;
	}

	public void setEndtime(Date endtime)
	{
		this.endtime = endtime;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
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
	