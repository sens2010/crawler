package cn.cnic.datapub.n.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="result_news_images")
@PrimaryKeyJoinColumn( name = "id" ) 
public class NewsImage
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="image")
	private String image;
	@Column(name="order")
	private	int order;
	@Column(name="status")
	private	int status;
	@Column(name="url")
	private String url;
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

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order)
	{
		this.order = order;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
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
	