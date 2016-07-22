package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="job_sub_parse")
@PrimaryKeyJoinColumn( name = "id" ) 
public class Parser extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="subjobid")
	private int subjobid;
	@Column(name="listparser")
	private String listparser;
	@Column(name="listcss")
	private boolean listcss;
	@Column(name="listjs")
	private boolean listjs;
	@Column(name="nextparser")
	private String nextparser;
	@Column(name="urlrelativer")
	private boolean urlrelativer;
	@Column(name="titleparser")
	private String titleparser;
	@Column(name="timeparser")
	private String timeparser;
	@Column(name="sourceparser")
	private String sourceparser;
	@Column(name="textparser")
	private String textparser;
	@Column(name="artcss")
	private boolean artcss;
	@Column(name="artjs")
	private boolean artjs;
	@Column(name="status")
	private int status;
	@Column(name="starttime")
	private Date starttime;
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

	public int getSubjobid()
	{
		return subjobid;
	}

	public void setSubjobid(int subjobid)
	{
		this.subjobid = subjobid;
	}

	public String getListparser()
	{
		return listparser;
	}

	public void setListparser(String listparser)
	{
		this.listparser = listparser;
	}

	public boolean isListcss()
	{
		return listcss;
	}

	public void setListcss(boolean listcss)
	{
		this.listcss = listcss;
	}

	public boolean isListjs()
	{
		return listjs;
	}

	public void setListjs(boolean listjs)
	{
		this.listjs = listjs;
	}

	public String getNextparser()
	{
		return nextparser;
	}

	public void setNextparser(String nextparser)
	{
		this.nextparser = nextparser;
	}

	public boolean isUrlrelativer()
	{
		return urlrelativer;
	}

	public void setUrlrelativer(boolean urlrelativer)
	{
		this.urlrelativer = urlrelativer;
	}

	public String getTitleparser()
	{
		return titleparser;
	}

	public void setTitleparser(String titleparser)
	{
		this.titleparser = titleparser;
	}

	public String getTimeparser()
	{
		return timeparser;
	}

	public void setTimeparser(String timeparser)
	{
		this.timeparser = timeparser;
	}

	public String getSourceparser()
	{
		return sourceparser;
	}

	public void setSourceparser(String sourceparser)
	{
		this.sourceparser = sourceparser;
	}

	public String getTextparser()
	{
		return textparser;
	}

	public void setTextparser(String textparser)
	{
		this.textparser = textparser;
	}

	public boolean isArtcss()
	{
		return artcss;
	}

	public void setArtcss(boolean artcss)
	{
		this.artcss = artcss;
	}

	public boolean isArtjs()
	{
		return artjs;
	}

	public void setArtjs(boolean artjs)
	{
		this.artjs = artjs;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Date getStarttime()
	{
		return starttime;
	}

	public void setStarttime(Date starttime)
	{
		this.starttime = starttime;
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
	