package cn.cnic.datapub.n.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.jetty.util.security.Credential.MD5;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import cn.cnic.datapub.n.model.Batch;
import cn.cnic.datapub.n.serviceimpl.BatchServiceImpl;
import cn.cnic.datapub.n.utils.SpringUtils;

import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Sets;

public class ListParseJob extends ParseJob
{
	private Logger logger = LoggerFactory.getLogger(ListParseJob.class);
	
	private boolean listcss;
	private boolean listscript;
	private String url;
	private boolean relative;
	private String listmatch;
	private String nextmatch;
	private int jobid;
	private int subjobid;
	private int batchid;
	private String parser;
	public int getBatchid()
	{
		return batchid;
	}

	public void setBatchid(int batchid)
	{
		this.batchid = batchid;
	}

	public int getJobid()
	{
		return jobid;
	}

	public void setJobid(int jobid)
	{
		this.jobid = jobid;
	}

	public int getSubjobid()
	{
		return subjobid;
	}

	public void setSubjobid(int subjobid)
	{
		this.subjobid = subjobid;
	}

	private AmqpTemplate amqpTemplate;
	private BatchServiceImpl batchServiceImpl;

	
	public boolean isListcss()
	{
		return listcss;
	}

	public void setListcss(boolean listcss)
	{
		this.listcss = listcss;
	}

	public boolean isListscript()
	{
		return listscript;
	}

	public void setListscript(boolean listscript)
	{
		this.listscript = listscript;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public boolean isRelative()
	{
		return relative;
	}

	public void setRelative(boolean relative)
	{
		this.relative = relative;
	}

	public String getListmatch()
	{
		return listmatch;
	}

	public void setListmatch(String listmatch)
	{
		this.listmatch = listmatch;
	}

	public String getNextmatch()
	{
		return nextmatch;
	}

	public void setNextmatch(String nextmatch)
	{
		this.nextmatch = nextmatch;
	}
	
	
	public void setAmqpTemplate(AmqpTemplate amqpTemplate)
	{
		this.amqpTemplate = amqpTemplate;
	}
	
	public AmqpTemplate getAmqpTemplate()
	{
		return this.amqpTemplate;
	}
	
	public void setBatchServiceImpl(BatchServiceImpl batchServiceImpl)
	{
		this.batchServiceImpl=batchServiceImpl;
	}
	public BatchServiceImpl getBatchServiceImpl()
	{
		return this.batchServiceImpl;
	}
	
	public void sendMQ(List<String> newslist)
	{
		System.out.println("newlist's size is "+newslist.size());
		if(this.amqpTemplate==null)
		{
			setAmqpTemplate((AmqpTemplate)SpringUtils.getBean("amqpTemplate"));
		}
		if(this.amqpTemplate!=null)
		{
			int sumacc=0;
			for(String news:newslist)
			{
				String md5 = MD5.digest(news);
				JSONObject decoration = new JSONObject();
				decoration.put("url", news);
				decoration.put("jobid", this.getJobid());
				decoration.put("subjobid", this.getSubjobid());
				decoration.put("batchid", this.getBatchid());
				decoration.put("newsid", md5);
				decoration.put("parser",this.getParser());
				sumacc++;
				this.amqpTemplate.convertAndSend("news", decoration.toJSONString());
			}
			if(this.batchServiceImpl==null)
			{
				setBatchServiceImpl((BatchServiceImpl)SpringUtils.getBean("batchServiceImpl"));
			}
			if(this.batchServiceImpl!=null)
			{
				Batch batch = this.batchServiceImpl.getBatchById(this.getBatchid());
				int psum = batch.getPsum();
				psum+=sumacc;
				batch.setPsum(psum);
				this.batchServiceImpl.updateBatch(batch);
			}
		}
		else
		{
			System.err.println("amqpTemplate is failed to be initialized!");
		}
	}
	
	public void initBatch()
	{
		logger.info("init batch:"+this.getJobid()+"/"+this.getSubjobid());
		if(this.batchServiceImpl==null)
		{
			setBatchServiceImpl((BatchServiceImpl)SpringUtils.getBean("batchServiceImpl"));
		}
		if(this.batchServiceImpl!=null)
		{
			Batch batch = new Batch();
			batch.setJobid(this.getJobid());
			batch.setSubjobid(this.getSubjobid());
			batch.setStarttime(new Date());
			batch.setStatus(1);
			batch.setPsum(0);
			batch.setCsum(0);
			batch.setFsum(0);
			batch.setSsum(0);
			batch.setAsum(0);
			this.batchServiceImpl.addBatch(batch);
			this.setBatchid(batch.getId());
			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			
			Set<String> previousone = Sets.newHashSet();
			Set<String> foreone = Sets.newHashSet();
			int page_count=1;
			webClient.getOptions().setCssEnabled(this.isListcss());
			webClient.getOptions().setJavaScriptEnabled(this.isListscript());
			List<String> newslist = new ArrayList<String>();
			HtmlPage page = webClient.getPage(this.getUrl());
			do
			{
				Set<String> now,other;
				if(page_count%2==0)
				{
					now = previousone;
					other = foreone;
				}
				else
				{
					now = foreone;
					other = previousone;
				}
				now.clear();
				List<HtmlAnchor> list = (List<HtmlAnchor>) page.getByXPath(this.getListmatch());
				System.out
						.println("******************************list("+page_count+")******************************");
				System.out.println("list_size:" + list.size());
				for (HtmlAnchor ha : list)
				{
					System.out.println(ha.getHrefAttribute() + " "
							+ ha.getTextContent());
					if(this.isRelative())
					{
						newslist.add(this.getUrl()+ha.getHrefAttribute());
					}
					else
					{
						newslist.add(ha.getHrefAttribute());
					}
					now.add(ha.getHrefAttribute());
				}
			System.out
					.println("****************************list-end("+page_count+")****************************");
			List<HtmlAnchor> click = (List<HtmlAnchor>) page.getByXPath(this.getNextmatch());
			page.cleanUp();
			System.out.println("click_size:" + click.size());
			if (click.size() > 0)
			{
				page = click.get(0).click();
				Thread.sleep(2000);
				page_count++;
				if(other!=null&&other.size()!=0)
				{
					other.removeAll(now);
					if(other.size()==0)
						break;
				}
			}
			else
			{
				break;
			}
			if(newslist.size()>100)
			{
				List<String> sublist = newslist.subList(0, 100);
				sendMQ(sublist);
				sublist.clear();
			}
			logger.warn("newslist.size:"+newslist.size());
			}while(true);
			webClient.close();
			sendMQ(newslist);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getParser()
	{
		return parser;
	}

	public void setParser(String parser)
	{
		this.parser = parser;
	}
	
}
