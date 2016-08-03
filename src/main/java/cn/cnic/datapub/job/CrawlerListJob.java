package cn.cnic.datapub.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
@Deprecated
public class CrawlerListJob implements Job
{
	private boolean listcss;
	private boolean listscript;
	private String url;
	private boolean relative;
	private String listmatch;
	private String nextmatch;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(this.isListcss());
			webClient.getOptions().setJavaScriptEnabled(this.isListscript());
			final HtmlPage page = webClient.getPage(this.getUrl());
			List<HtmlAnchor> list = (List<HtmlAnchor>) page.getByXPath(this.getListmatch());
			List<String> newslist = new ArrayList<String>();
			System.out
					.println("******************************list******************************");
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
			}
			System.out
					.println("****************************list-end****************************");
			List<HtmlAnchor> click = (List<HtmlAnchor>) page.getByXPath(this.getNextmatch());
			page.cleanUp();
			System.out.println("click_size:" + click.size());
			if (click.size() > 0)
			{
				final HtmlPage nextpage = click.get(0).click();
				list = (List<HtmlAnchor>) nextpage.getByXPath(this.getListmatch());
				System.out
						.println("****************************next-list****************************");
				System.out.println("list_size:" + list.size());
				for (HtmlAnchor ha : list)
				{
					System.out.println(ha.getHrefAttribute() + " "
							+ ha.getTextContent());
				}
				System.out
						.println("**************************next-list-end**************************");
				nextpage.cleanUp();
			}
			webClient.close();
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
