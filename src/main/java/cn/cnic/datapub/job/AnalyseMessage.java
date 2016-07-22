package cn.cnic.datapub.job;

import java.util.Date;

public class AnalyseMessage extends Message
{
	private String url;
	private int failnum;
	private Date lastfailedtime;
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getUrl()
	{
		return this.url;
	}
	
	public void setFailNum(int failnum)
	{
		this.failnum = failnum;
	}
	
	public int getFailNum()
	{
		return this.failnum;
	}
	
	public void setLastFailedTime(Date lastfailedtime)
	{
		this.lastfailedtime = lastfailedtime;
	}
	public Date getLastFailedTime()
	{
		return this.lastfailedtime;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
