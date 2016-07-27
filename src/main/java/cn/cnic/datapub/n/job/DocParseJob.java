package cn.cnic.datapub.n.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import cn.cnic.datapub.n.model.Batch;
import cn.cnic.datapub.n.model.News;
import cn.cnic.datapub.n.service.IBatchService;
import cn.cnic.datapub.n.service.INewsService;
import cn.cnic.datapub.n.serviceimpl.BatchServiceImpl;
import cn.cnic.datapub.n.serviceimpl.NewsServiceImpl;
import cn.cnic.datapub.n.utils.SpringUtils;

import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DocParseJob extends ParseJob implements MessageListener 
{
	private boolean doccss;
	private boolean docscript;
	private String textmatch;
	private String titlematch;
	private String timematch;
	private String sourceurlmatch;
	private String timetransfer;
	private List<String> newsconfigs = new ArrayList<String>();;
	private INewsService newsService;
	private IBatchService batchService;
	private int batchid;
	
	
	
	public int getBatchid()
	{
		return batchid;
	}

	public void setBatchid(int batchid)
	{
		this.batchid = batchid;
	}

	public IBatchService getBatchService()
	{
		return batchService;
	}

	public void setBatchService(IBatchService batchService)
	{
		this.batchService = batchService;
	}

	public List<String> getNewsconfigs()
	{
		return newsconfigs;
	}

	public void setNewsconfigs(List<String> newsconfigs)
	{
		this.newsconfigs = newsconfigs;
	}

	public INewsService getNewsService()
	{
		return newsService;
	}

	public void setNewsService(INewsService newsService)
	{
		this.newsService = newsService;
	}

	private Logger logger = LoggerFactory.getLogger(DocParseJob.class);
	
	
	public boolean isDoccss()
	{
		return doccss;
	}

	public void setDoccss(boolean doccss)
	{
		this.doccss = doccss;
	}

	public boolean isDocscript()
	{
		return docscript;
	}

	public void setDocscript(boolean docscript)
	{
		this.docscript = docscript;
	}

	public String getTextmatch()
	{
		return textmatch;
	}

	public void setTextmatch(String textmatch)
	{
		this.textmatch = textmatch;
	}

	public String getTitlematch()
	{
		return titlematch;
	}

	public void setTitlematch(String titlematch)
	{
		this.titlematch = titlematch;
	}

	public String getTimematch()
	{
		return timematch;
	}

	public void setTimematch(String timematch)
	{
		this.timematch = timematch;
	}

	public String getSourceurlmatch()
	{
		return sourceurlmatch;
	}

	public void setSourceurlmatch(String sourceurlmatch)
	{
		this.sourceurlmatch = sourceurlmatch;
	}
	
	public String getTimetransfer()
	{
		return timetransfer;
	}

	public void setTimetransfer(String timetransfer)
	{
		this.timetransfer = timetransfer;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException
	{

		try (final WebClient newClient = new WebClient(BrowserVersion.CHROME))
		{
			List<String> configs = this.getNewsconfigs();
			
			
			newClient.getOptions().setCssEnabled(this.isDoccss());
			newClient.getOptions().setJavaScriptEnabled(this.isDocscript());
			for(String config:configs)
			{
				JSONObject conf = JSONObject.parseObject(config);
				String url = conf.getString("url");
				String newsid = conf.getString("newsid");
			final HtmlPage newspage = newClient.getPage(url);
			List<HtmlElement> titlelist = (List<HtmlElement>)newspage.getByXPath(this.getTitlematch());
			List<HtmlElement> timelist = (List<HtmlElement>)newspage.getByXPath(this.getTimematch());
			List<HtmlElement> sourceurllist = (List<HtmlElement>)newspage.getByXPath(this.getSourceurlmatch());
			List<HtmlElement> textlist = (List<HtmlElement>)newspage.getByXPath(this.getTextmatch());
			String title=null,source = null,text=null;
			Date pubtime=null;
			if(titlelist.size()>0)
			{
				title = titlelist.get(0).asText();
			}
			if(timelist.size()>0)
			{
				String timestr = timelist.get(0).asText();
				SimpleDateFormat sdf = new SimpleDateFormat(this.getTimetransfer());
				pubtime = sdf.parse(timestr);
			}
			if(sourceurllist.size()>0)
			{
				source = sourceurllist.get(0).asText();
			}
			if(textlist.size()>0)
			{
				text = textlist.get(0).asText();
			}
			
			if(this.newsService==null)
			{
				setNewsService((NewsServiceImpl)SpringUtils.getBean("newsServiceImpl"));
			}
			if(this.batchService==null)
			{
				setBatchService((BatchServiceImpl)SpringUtils.getBean("batchServiceImpl"));
			}
			
			Batch batch = this.getBatchService().getBatchById(this.getBatchid());
			
			News news = this.getNewsService().getNewsByNewsId(newsid);
			//判断是否有值未取到
			boolean partly = ((text==null)||(source==null)||(pubtime==null)||(title==null));
			boolean fail = ((text==null)&&(source==null)&&(pubtime==null)&&(title==null));
			if(news.getStatus()==1)
			{
				if(partly)
				{
					news.setStatus(4);
					batch.setSsum(batch.getSsum()+1);
					
				}
				else if(fail)
				{
					news.setStatus(3);
					batch.setFsum(batch.getFsum()+1);
				}
				else
				{
					news.setStatus(2);
					batch.setSsum(batch.getSsum()+1);
				}
				batch.setCsum(batch.getCsum()+1);
				if(batch.getCsum()==batch.getPsum())
				{
					batch.setEndtime(new Date());
					
					if(batch.getFsum()>0||batch.getAsum()>0)
					{
						batch.setMonitorinfo(3);
						batch.setStatus(5);
					}
					else
					{
						batch.setMonitorinfo(2);
						batch.setStatus(2);
					}
				}
				else if(batch.getCsum()>batch.getPsum())
				{
					batch.setMonitorinfo(3);
					batch.setStatus(5);
				}
				this.getBatchService().updateBatch(batch);
				news.setLastmodifytime(new Date());
				this.getNewsService().updateNews(news);
			}	
				newspage.cleanUp();
			
			
			}
			
			
			newClient.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

	}

	@Override
	public void onMessage(Message message)
	{
		System.out.println("this:"+this);
		logger.error("receive msg:{}",message);
		System.out.println(message.getBody());
		JSONObject decoration = JSONObject.parseObject(new String(message.getBody()));
		String url = decoration.getString("url");
		int jid = decoration.getIntValue("jobid");
		int sjid = decoration.getIntValue("subjobid");
		int bid = decoration.getIntValue("batchid");
		int sbid = decoration.getIntValue("subbatchid");
		String nid = decoration.getString("newsid");
		JSONObject parser = decoration.getJSONObject("parser");
		this.setDoccss(parser.getBooleanValue("doccss"));
		this.setDocscript(parser.getBooleanValue("docscript"));
		
		List<String> configs = this.getNewsconfigs();
		configs.clear();
		JSONObject config = new JSONObject();
		config.put("url", url);
		config.put("newsid", nid);
		configs.add(config.toJSONString());
		this.setTextmatch(parser.getString("textmatch"));
		this.setTitlematch(parser.getString("titlematch"));
		this.setTimematch(parser.getString("timematch"));
		this.setSourceurlmatch(parser.getString("sourceurlmatch"));
		this.setTimetransfer(parser.getString("timetransfer"));
		try
		{
			if(this.newsService==null)
			{
				setNewsService((NewsServiceImpl)SpringUtils.getBean("newsServiceImpl"));
			}
			if(this.batchService==null)
			{
				setBatchService((BatchServiceImpl)SpringUtils.getBean("batchServiceImpl"));
			}
			News news = new News();
			news.setNewsid(nid).setCreatetime(new Date()).setPagecount(0)
				.setUrl(url).setRelatebatch(bid).setStatus(1);
			this.getNewsService().addNews(news);
			this.setBatchid(bid);
			this.execute(null);
		} catch (JobExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("url:"+url+",jobid:"+jid+",subjobid:"+sjid+",batchid:"+bid+",sbid:"+sbid+",nid:"+nid);
	}


	
}
