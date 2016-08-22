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
@Table(name="job_sub_parse")
@PrimaryKeyJoinColumn( name = "id" ) 
public class Parser extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
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
	@Column(name="timetransfer")
	private String timetransfer;
	@Column(name="artcss")
	private boolean artcss;
	@Column(name="artjs")
	private boolean artjs;
	@Column(name="status")
	private int status;
	@Column(name="createtime")
	private Date createtime;
	@Column(name="lastmodifytime")
	private Date lastmodifytime;
	
	
	

	public String getTimetransfer()
	{
		return timetransfer;
	}

	public void setTimetransfer(String timetransfer)
	{
		this.timetransfer = timetransfer;
	}

	public int getId()
	{
		return id;
	}

	public Parser setId(int id)
	{
		this.id = id;
		return this;
	}

	public String getListparser()
	{
		return listparser;
	}

	public Parser setListparser(String listparser)
	{
		this.listparser = listparser;
		return this;
	}

	public boolean isListcss()
	{
		return listcss;
	}

	public Parser setListcss(boolean listcss)
	{
		this.listcss = listcss;
		return this;
	}

	public boolean isListjs()
	{
		return listjs;
	}

	public Parser setListjs(boolean listjs)
	{
		this.listjs = listjs;
		return this;
	}

	public String getNextparser()
	{
		return nextparser;
	}

	public Parser setNextparser(String nextparser)
	{
		this.nextparser = nextparser;
		return this;
	}

	public boolean isUrlrelativer()
	{
		return urlrelativer;
	}

	public Parser setUrlrelativer(boolean urlrelativer)
	{
		this.urlrelativer = urlrelativer;
		return this;
	}

	public String getTitleparser()
	{
		return titleparser;
	}

	public Parser setTitleparser(String titleparser)
	{
		this.titleparser = titleparser;
		return this;
	}

	public String getTimeparser()
	{
		return timeparser;
	}

	public Parser setTimeparser(String timeparser)
	{
		this.timeparser = timeparser;
		return this;
	}

	public String getSourceparser()
	{
		return sourceparser;
	}

	public Parser setSourceparser(String sourceparser)
	{
		this.sourceparser = sourceparser;
		return this;
	}

	public String getTextparser()
	{
		return textparser;
	}

	public Parser setTextparser(String textparser)
	{
		this.textparser = textparser;
		return this;
	}

	public boolean isArtcss()
	{
		return artcss;
	}

	public Parser setArtcss(boolean artcss)
	{
		this.artcss = artcss;
		return this;
	}

	public boolean isArtjs()
	{
		return artjs;
	}

	public Parser setArtjs(boolean artjs)
	{
		this.artjs = artjs;
		return this;
	}

	public int getStatus()
	{
		return status;
	}

	public Parser setStatus(int status)
	{
		this.status = status;
		return this;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public Parser setCreatetime(Date createtime)
	{
		this.createtime = createtime;
		return this;
	}

	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}

	public Parser setLastmodifytime(Date lastmodifytime)
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
	