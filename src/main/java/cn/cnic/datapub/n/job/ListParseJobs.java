package cn.cnic.datapub.n.job;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jetty.util.security.Credential.MD5;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import cn.cnic.datapub.n.model.Batch;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.serviceimpl.BatchServiceImpl;
import cn.cnic.datapub.n.utils.SpringUtils;

import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Sets;

public class ListParseJobs extends ParseJob
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
	private Set<String> stops = new HashSet<String>();
	
	public int getBatchid()
	{
		return batchid;
	}

	public void setStops(Set<String> stops)
	{
		this.stops = stops;
	}

	public Set<String> getStops()
	{
		return this.stops;
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
				decoration.put("newsid", md5.substring(4));
				System.err.println(this.getParser());
				decoration.put("parser",JSONObject.parse(this.getParser()));
				sumacc++;
				this.amqpTemplate.convertAndSend("news", decoration.toJSONString());
			}
			if(this.batchServiceImpl==null)
			{
				setBatchServiceImpl((BatchServiceImpl)SpringUtils.getBean("batchServiceImpl"));
			}
			if(this.batchServiceImpl!=null)
			{
				System.out.println("batch:"+this.getBatchid());
				Batch batch = this.batchServiceImpl.getBatchById(this.getBatchid());
				System.err.println("batch(str)："+batch.toJSONString());
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
		
		Parser eparser = JSONObject.parseObject(this.parser, Parser.class);
		this.setListcss(eparser.isListcss());
		this.setListscript(eparser.isListjs());
		this.setRelative(eparser.isUrlrelativer());
		this.setListmatch(eparser.getListparser());
		this.setNextmatch(eparser.getNextparser());
		
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
			batch = this.batchServiceImpl.addBatch(batch);
			
			this.setBatchid(batch.getId());
			List<Batch> lastbatches = this.batchServiceImpl.findLastBatches(subjobid, batch.getId(), 3);
			for(Batch bth:lastbatches)
			{
				this.getStops().add(bth.getFlag());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException
	{
		initBatch();
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			Set<String> previousone = Sets.newHashSet();
			Set<String> foreone = Sets.newHashSet();
			int page_count=1;
			webClient.getOptions().setCssEnabled(this.isListcss());
			webClient.getOptions().setJavaScriptEnabled(this.isListscript());
			List<String> newslist = new ArrayList<String>();
			HtmlPage page = webClient.getPage(this.getUrl());
			String first_href =null;
			boolean first_href_flag=false;
			String last_page_href = null;
			//int flag_count=3;
			boolean isflag=false;
			Set<String> lastflag = new HashSet<String>();
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
				if(!isflag)
				{
					List<String> flagstr = new ArrayList<String>();
					if(list.size()>=3)
					{
						flagstr.add(list.get(0).getHrefAttribute());
						
					}
					else if(list.size()==1)
					{
						flagstr.add(list.get(0).getHrefAttribute());
						
					}
					else if(list.size()==2)
					{
						flagstr.add(list.get(0).getHrefAttribute());
						
					}
					if(this.batchServiceImpl==null)
					{
						setBatchServiceImpl((BatchServiceImpl)SpringUtils.getBean("batchServiceImpl"));
					}
					if(this.batchServiceImpl!=null)
					{
						Batch batch = this.batchServiceImpl.getBatchById(this.getBatchid());
						batch.setFlag(flagstr.get(0));
						this.batchServiceImpl.updateBatch(batch);
					}
					isflag=true;
				}
				
				System.out
						.println("******************************list("+page_count+")******************************");
				System.out.println("list_size:" + list.size());
				String now_href = list.get(0).getHrefAttribute();
				if(stops.contains(now_href))
				{
					break;
				}
				
				
				if(first_href!=null&&first_href.equals(now_href))
				{
					break;
				}
				if(last_page_href!=null&&last_page_href.equals(now_href))
				{
					break;
				}
				else
				{
					last_page_href = now_href;
				}
				for (HtmlAnchor ha : list)
				{
					String attrurl = ha.getHrefAttribute();
					if(attrurl.startsWith("javascript"))
					{
						HtmlPage jspage = ha.click();
						attrurl = jspage.getUrl().toString();
					}
					System.out.println(attrurl + " "
							+ ha.getTextContent());
					if(this.isRelative())
					{
						newslist.add(this.getUrl()+attrurl);
					}
					else
					{
						newslist.add(ha.getHrefAttribute());
					}
					
					now.add(attrurl);
					if(!first_href_flag)
					{
						first_href=ha.getHrefAttribute();
						first_href_flag=true;
					}
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
				if(page_count>1)
				{
					if(now.size()==0)
					{
						break;
					}
					else if(other.size()!=0)
					{
						other.removeAll(now);
						if(other.size()==0)
							break;
					}
					else
					{
						break;
					}
					System.err.println("now.size:"+now.size()+", other.size:"+other.size()+", page_count:"+page_count);
				}
				
				page_count++;
				
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
	
	
	public static void main(String[] args)
	{
		try
		{
			URL url = new URL("http://news.hexun.com:80/2016-06-28/184627838.html&page=1&abc=2");
			System.out.println(url.toURI());
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
