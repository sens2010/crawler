package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="op_batch")
@PrimaryKeyJoinColumn( name = "id" ) 
public class Batch  extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="jobid")
	private int jobid;
	@Column(name="starttime")
	private Date starttime;
	@Column(name="endtime")
	private Date endtime;
	@Column(name="status")
	private int status;
	@Column(name="monitorinfo")
	private int monitorinfo;
	
	

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

	public int getMonitorinfo()
	{
		return monitorinfo;
	}

	public void setMonitorinfo(int monitorinfo)
	{
		this.monitorinfo = monitorinfo;
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
	