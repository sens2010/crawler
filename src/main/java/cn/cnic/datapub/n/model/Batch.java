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
@Table(name="op_batch")
@PrimaryKeyJoinColumn( name = "id" ) 
public class Batch  extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="jobid")
	private int jobid;
	@Column(name="subjobid")
	private int subjobid;
	@Column(name="starttime")
	private Date starttime;
	@Column(name="endtime")
	private Date endtime;
	@Column(name="status")
	private int status;
	@Column(name="monitorinfo")
	private int monitorinfo;
	@Column(name="psum")
	private int psum;
	@Column(name="csum")
	private int csum;
	@Column(name="fsum")
	private int fsum;
	@Column(name="ssum")
	private int ssum;
	@Column(name="asum")
	private int asum;
	@Column(name="flag")
	private String flag;
	
	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public int getAsum()
	{
		return asum;
	}

	public void setAsum(int asum)
	{
		this.asum = asum;
	}

	public int getSubjobid()
	{
		return subjobid;
	}

	public void setSubjobid(int subjobid)
	{
		this.subjobid = subjobid;
	}

	public int getPsum()
	{
		return psum;
	}

	public void setPsum(int psum)
	{
		this.psum = psum;
	}

	public int getCsum()
	{
		return csum;
	}

	public void setCsum(int csum)
	{
		this.csum = csum;
	}

	public int getFsum()
	{
		return fsum;
	}

	public void setFsum(int fsum)
	{
		this.fsum = fsum;
	}

	public int getSsum()
	{
		return ssum;
	}

	public void setSsum(int ssum)
	{
		this.ssum = ssum;
	}

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
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
}
	