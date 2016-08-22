package cn.cnic.datapub.n.job;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DocParseJob extends ParseJob implements MessageListener 
{
	private INewsService newsService;
	private IBatchService batchService;

	public INewsService getNewsService()
	{
		return newsService;
	}

	public void setNewsService(INewsService newsService)
	{
		this.newsService = newsService;
	}

	public IBatchService getBatchService()
	{
		return batchService;
	}

	public void setBatchService(IBatchService batchService)
	{
		this.batchService = batchService;
	}

	private Logger logger = LoggerFactory.getLogger(DocParseJob.class);
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException
	{

		
	}
	
	@SuppressWarnings("unchecked")
	public void consumer(String webconfigs)
	{
		JSONObject rootconfig = JSONObject.parseObject(webconfigs);
		
		boolean doccss=rootconfig.getBooleanValue("doccss");
		boolean docscript=rootconfig.getBooleanValue("docscript");
		String textmatch=rootconfig.getString("textmatch");
		String titlematch=rootconfig.getString("titlematch");
		String timematch=rootconfig.getString("timematch");
		String sourceurlmatch=rootconfig.getString("sourceurlmatch");
		String timetransfer=rootconfig.getString("timetransfer");
		JSONArray newsconfigs = rootconfig.getJSONArray("newsconfigs");
		int interval = rootconfig.getIntValue("interval");
		int batchid = rootconfig.getIntValue("batchid");
		
		try (final WebClient newClient = new WebClient(BrowserVersion.CHROME))
		{
			
			
			newClient.getOptions().setCssEnabled(doccss);
			newClient.getOptions().setJavaScriptEnabled(docscript);
			for(int i=0; i<newsconfigs.size();i++)
			{
				
				JSONObject conf = newsconfigs.getJSONObject(0);
				String url = conf.getString("url");
				int newsid = conf.getIntValue("newsid");
				System.err.println("????????????????"+newsid+"???????????????????:");
				String code = conf.getString("code");
				String title=null,source = null,text=null;
				Date pubtime=null;
				try
				{
			final HtmlPage newspage = newClient.getPage(url);
			List<HtmlElement> titlelist = (List<HtmlElement>)newspage.getByXPath(titlematch);
			List<HtmlElement> timelist = (List<HtmlElement>)newspage.getByXPath(timematch);
			List<HtmlElement> sourceurllist = (List<HtmlElement>)newspage.getByXPath(sourceurlmatch);
			List<HtmlElement> textlist = (List<HtmlElement>)newspage.getByXPath(textmatch);
			
			if(titlelist.size()>0)
			{
				title = titlelist.get(0).asText();
			}
			if(timelist.size()>0)
			{
				String timestr = timelist.get(0).asText();
				String[] fa = timetransfer.split("\\|");
				String format = fa[0];
				
				if(fa.length>1&&fa[1]!=null)
				{
					String se = fa[1];
					System.out.println(se);
					String[]stend = se.split(",");
					int start = Integer.parseInt(stend[0]);
					int end = Integer.parseInt(stend[1]);
					timestr = timestr.substring(start,end);
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				try
				{
					pubtime = sdf.parse(timestr);
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
			}
			if(sourceurllist.size()>0)
			{
				source = sourceurllist.get(0).asText();
			}
			if(textlist.size()>0)
			{
				text = textlist.get(0).asText();
			}
			newspage.cleanUp();
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
			if(this.newsService==null)
			{
				setNewsService((NewsServiceImpl)SpringUtils.getBean("newsServiceImpl"));
			}
			if(this.batchService==null)
			{
				setBatchService((BatchServiceImpl)SpringUtils.getBean("batchServiceImpl"));
			}
			
			Batch batch = this.getBatchService().getBatchById(batchid);
			
			News news = this.getNewsService().getNewsById(newsid);
			//判断是否有值未取到
			
			System.err.println("this.code:"+code);
			news.setText(text).setResource(source).setPubtime(pubtime).setTitle(title).setLastmodifytime(new Date());
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
				
			
				Thread.sleep(interval*1000);
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
		JSONObject rootconfig = new JSONObject();
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
		String parserstr = decoration.getString("parser");
		
		JSONObject parser = JSONObject.parseObject(parserstr);
		rootconfig.put("doccss",parser.getBooleanValue("doccss"));
		rootconfig.put("doccss",parser.getBooleanValue("docscript"));
		rootconfig.put("textmatch",parser.getString("textmatch"));
		rootconfig.put("titlematch",parser.getString("titlematch"));
		rootconfig.put("timematch",parser.getString("timematch"));
		rootconfig.put("sourceurlmatch",parser.getString("sourceurlmatch"));
		rootconfig.put("timetransfer",parser.getString("timetransfer"));
		rootconfig.put("interval",parser.getString("interval"));
		JSONArray newsconfigs = new JSONArray();
		
		
		
		System.err.println(parser.getString("timetransfer")+":"+parser.toJSONString());
		
		JSONObject config = new JSONObject();
		config.put("url", url);
		config.put("code", nid);
		if(this.newsService==null)
		{
			setNewsService((NewsServiceImpl)SpringUtils.getBean("newsServiceImpl"));
		}
		if(this.batchService==null)
		{
			setBatchService((BatchServiceImpl)SpringUtils.getBean("batchServiceImpl"));
		}
		News news = new News();
		System.err.println("bid"+bid);
		news.setNewsid(nid).setCreatetime(new Date()).setPagecount(0)
			.setUrl(url).setRelatebatch(bid).setStatus(1);
		this.getNewsService().addNews(news);
		if(news.getId()==0){throw new NullPointerException();}
		config.put("newsid", news.getId());
		System.err.println("************newsid:"+news.getId()+"******************");
		rootconfig.put("batchid",bid);
		newsconfigs.add(config);
		rootconfig.put("newsconfigs",newsconfigs);
		this.consumer(rootconfig.toJSONString());
		System.err.println("url:"+url+",jobid:"+jid+",subjobid:"+sjid+",batchid:"+bid+",sbid:"+sbid+",nid:"+nid);
	}


	
}
