package cn.cnic.datapub.n.job;

import java.util.List;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DocParseJob extends ParseJob implements MessageListener 
{
	private boolean doccss;
	private boolean docscript;
	private String url;
	private String textmatch;
	private String titlematch;
	private String timematch;
	private String sourceurlmatch;

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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException
	{

		try (final WebClient newClient = new WebClient(BrowserVersion.CHROME))
		{
			newClient.getOptions().setCssEnabled(this.isDoccss());
			newClient.getOptions().setJavaScriptEnabled(this.isDocscript());
			final HtmlPage newspage = newClient.getPage(this.getUrl());
			List<HtmlElement> title = (List<HtmlElement>)newspage.getByXPath(this.getTitlematch());
			List<HtmlElement> time = (List<HtmlElement>)newspage.getByXPath(this.getTimematch());
			List<HtmlElement> sourceurl = (List<HtmlElement>)newspage.getByXPath(this.getSourceurlmatch());
			List<HtmlElement> text = (List<HtmlElement>)newspage.getByXPath(this.getTextmatch());
			System.out.println("title:"+(title.size()>0?title.get(0).asText():""));
			System.out.println("time:"+(time.size()>0?time.get(0).asText():""));
			System.out.println("sourceurl:"+(sourceurl.size()>0?sourceurl.get(0).asText():""));
			System.out.println("text:"+(text.size()>0?text.get(0).asText():""));
			newspage.cleanUp();
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
		this.setUrl(url);
		this.setTextmatch(parser.getString("textmatch"));
		this.setTitlematch(parser.getString("titlematch"));
		this.setTimematch(parser.getString("timematch"));
		this.setSourceurlmatch(parser.getString("sourceurlmatch"));
	
		System.err.println("url:"+url+",jobid:"+jid+",subjobid:"+sjid+",batchid:"+bid+",sbid:"+sbid+",nid:"+nid);
	}
	
}
