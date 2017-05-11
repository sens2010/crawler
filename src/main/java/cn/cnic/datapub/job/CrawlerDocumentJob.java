package cn.cnic.datapub.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
@Deprecated
public class CrawlerDocumentJob implements Job
{
	private boolean doccss;
	private boolean docscript;
	private String url;
	private boolean isrelative;
	private String textmatch;
	private String titlematch;
	private String timematch;
	private String sourceurlmatch;

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

	public boolean isIsrelative()
	{
		return isrelative;
	}

	public void setIsrelative(boolean isrelative)
	{
		this.isrelative = isrelative;
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
			List<HtmlElement> title = newspage.getByXPath(this.getTitlematch());
			List<HtmlElement> time = newspage.getByXPath(this.getTimematch());
			List<HtmlElement> sourceurl = newspage.getByXPath(this.getSourceurlmatch());
			List<HtmlElement> text = newspage.getByXPath(this.getTextmatch());
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
		// TODO Auto-generated method stub
		
	}
	
}
