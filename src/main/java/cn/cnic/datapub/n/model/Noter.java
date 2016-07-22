package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="op_note")
@PrimaryKeyJoinColumn( name = "id" ) 
public class Noter
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="newsid")
	private int newsid;
	@Column(name="label")
	private	int label;
	@Column(name="type")
	private int type;
	@Column(name="note")
	private String note;
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

	public int getNewsid()
	{
		return newsid;
	}

	public void setNewsid(int newsid)
	{
		this.newsid = newsid;
	}

	public int getLabel()
	{
		return label;
	}

	public void setLabel(int label)
	{
		this.label = label;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
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
	