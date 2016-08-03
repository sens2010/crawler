package cn.cnic.datapub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Deprecated
public class WebCrawler
{
	private List<Map<String, Object>> weblist = new ArrayList<Map<String, Object>>();
	
	public List<Map<String, Object>> getWebList()
	{
		return this.weblist;
	}
	
	public void setWebList(List<Map<String, Object>> weblist)
	{
		this.weblist = weblist;
	}
	
	public WebCrawler()
	{
		//sohu
		Map<String, Object> web1 = new HashMap<String, Object>();
		web1.put("url", "http://business.sohu.com/hgjj/");
		web1.put("list",
				"//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
		web1.put("next", "//*/div[@class='pages']/p/table/tbody/tr/td/a[3]");
		web1.put("title",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1");
		web1.put(
				"time",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']");
		web1.put(
				"sourceurl",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span");
		web1.put("text", "//*/div[@itemprop='articleBody']");
		web1.put("listcss", false);
		web1.put("listscript", true);
		web1.put("newscss", false);
		web1.put("newsscript", false);
		weblist.add(web1);
		
		Map<String, Object> web2 = new HashMap<String, Object>();
		web2.put("url", "http://business.sohu.com/jrjg/");
		web2.put("list",
				"//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
		web2.put("next", "//*/div[@class='pages']/p/table/tbody/tr/td/a[3]");
		web2.put("title",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1");
		web2.put(
				"time",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']");
		web2.put(
				"sourceurl",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span");
		web2.put("text", "//*/div[@itemprop='articleBody']");
		web2.put("listcss", false);
		web2.put("listscript", true);
		web2.put("newscss", false);
		web2.put("newsscript", false);
		weblist.add(web2);
		
		Map<String, Object> web3 = new HashMap<String, Object>();
		web3.put("url", "http://business.sohu.com/jqgg/");
		web3.put("list",
				"//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
		web3.put("next", "//*/div[@class='pages']/p/table/tbody/tr/td/a[3]");
		web3.put("title",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1");
		web3.put(
				"time",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']");
		web3.put(
				"sourceurl",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span");
		web3.put("text", "//*/div[@itemprop='articleBody']");
		web3.put("listcss", false);
		web3.put("listscript", true);
		web3.put("newscss", false);
		web3.put("newsscript", false);
		weblist.add(web3);
		
		Map<String, Object> web4 = new HashMap<String, Object>();
		web4.put("url", "http://business.sohu.com/c241863626/");
		web4.put("list",
				"//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
		web4.put("next", "//*/div[@class='pages']/p/table/tbody/tr/td/a[3]");
		web4.put("title",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1");
		web4.put(
				"time",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']");
		web4.put(
				"sourceurl",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span");
		web4.put("text", "//*/div[@itemprop='articleBody']");
		web4.put("listcss", false);
		web4.put("listscript", true);
		web4.put("newscss", false);
		web4.put("newsscript", false);
		weblist.add(web4);
		
		Map<String, Object> web5 = new HashMap<String, Object>();
		web5.put("url", "http://business.sohu.com/jrqj/");
		web5.put("list",
				"//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
		web5.put("next", "//*/div[@class='pages']/p/table/tbody/tr/td/a[3]");
		web5.put("title",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1");
		web5.put(
				"time",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']");
		web5.put(
				"sourceurl",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span");
		web5.put("text", "//*/div[@itemprop='articleBody']");
		web5.put("listcss", false);
		web5.put("listscript", true);
		web5.put("newscss", false);
		web5.put("newsscript", false);
		weblist.add(web5);
		
		Map<String, Object> web6 = new HashMap<String, Object>();
		web6.put("url", "http://stock.sohu.com/news/");
		web6.put("list",
				"//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
		web6.put("next", "//*/div[@class='pages']/p/table/tbody/tr/td/a[3]");
		web6.put("title",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1");
		web6.put(
				"time",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']");
		web6.put(
				"sourceurl",
				"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span");
		web6.put("text", "//*/div[@itemprop='articleBody']");
		web6.put("listcss", false);
		web6.put("listscript", true);
		web6.put("newscss", false);
		web6.put("newsscript", false);
		weblist.add(web6);
		
		//hexun
		Map<String, Object> web7 = new HashMap<String, Object>();
		web7.put("url", "http://news.hexun.com/economy/");
		web7.put("list",
				"//*/div[@id='temp01']/ul/li/a");
		web7.put("next", "//*/div[@class='hx_paging']/ul/li[@class='next']/a");
		web7.put("title",
				"//*/div[@class='layout mg articleName']/h1");
		web7.put(
				"time",
				"//*/div[@class='tip fl']/span");
		web7.put(
				"sourceurl",
				"//*/div[@class='tip fl']/a");
		web7.put("text", "//*/div[@class='art_contextBox']");
		web7.put("listcss", false);
		web7.put("listscript", true);
		web7.put("newscss", false);
		web7.put("newsscript", false);
		weblist.add(web7);
		
		Map<String, Object> web8 = new HashMap<String, Object>();
		web8.put("url", "http://news.hexun.com/company/");
		web8.put("list",
				"//*/div[@id='temp01']/ul/li/a");
		web8.put("next", "//*/div[@class='hx_paging']/ul/li[@class='next']/a");
		web8.put("title",
				"//*/div[@class='layout mg articleName']/h1");
		web8.put(
				"time",
				"//*/div[@class='tip fl']/span");
		web8.put(
				"sourceurl",
				"//*/div[@class='tip fl']/a");
		web8.put("text", "//*/div[@class='art_contextBox']");
		web8.put("listcss", false);
		web8.put("listscript", true);
		web8.put("newscss", false);
		web8.put("newsscript", false);
		weblist.add(web8);
		
		Map<String, Object> web9 = new HashMap<String, Object>();
		web9.put("url", "http://news.hexun.com/international/");
		web9.put("list",
				"//*/div[@id='temp01']/ul/li/a");
		web9.put("next", "//*/div[@class='hx_paging']/ul/li[@class='next']/a");
		web9.put("title",
				"//*/div[@class='layout mg articleName']/h1");
		web9.put(
				"time",
				"//*/div[@class='tip fl']/span");
		web9.put(
				"sourceurl",
				"//*/div[@class='tip fl']/a");
		web9.put("text", "//*/div[@class='art_contextBox']");
		web9.put("listcss", false);
		web9.put("listscript", true);
		web9.put("newscss", false);
		web9.put("newsscript", false);
		weblist.add(web9);
		
		Map<String, Object> web10 = new HashMap<String, Object>();
		web10.put("url", "http://crudeoil.hexun.com/crudeoilhy/");
		web10.put("list",
				"//*/div[@class='dList pl14 pt6']/ul/li/a");
		web10.put("next", "//*/div[@class='hx_paging']/ul/li[@class='next']/a");
		web10.put("title",
				"//*/div[@id='artibodyTitle']/h1");
		web10.put(
				"time",
				"//*/span[@id='pubtime_baidu']");
		web10.put(
				"sourceurl",
				"//*/span[@id='source_baidu']/a");
		web10.put("text", "//*/div[@id='artibody']");
		web10.put("listcss", false);
		web10.put("listscript", false);
		web10.put("newscss", false);
		web10.put("newsscript", false);
		weblist.add(web10);
		
		Map<String, Object> web11 = new HashMap<String, Object>();
		web11.put("url", "http://futures.hexun.com/nyzx/");
		web11.put("list",
				"//*/div[@id='temp01']/ul/li/a");
		web11.put("next", "//*/div[@class='hx_paging']/ul/li[@class='next']/a");
		web11.put("title",
				"//*/div[@class='layout mg articleName']/h1");
		web11.put(
				"time",
				"//*/div[@class='tip fl']/span");
		web11.put(
				"sourceurl",
				"//*/div[@class='tip fl']/a");
		web11.put("text", "//*/div[@class='art_contextBox']");
		web11.put("listcss", false);
		web11.put("listscript", true);
		web11.put("newscss", false);
		web11.put("newsscript", false);
		weblist.add(web11);
		
		Map<String, Object> web12 = new HashMap<String, Object>();
		web12.put("url", "http://crudeoil.hexun.com/oiloverseas/");
		web12.put("list",
				"//*/div[@class='dList pl14 pt6']/ul/li/a");
		web12.put("next", "//*/div[@class='hx_paging']/ul/li[@class='next']/a");
		web12.put("title",
				"//*/div[@id='artibodyTitle']/h1");
		web12.put(
				"time",
				"//*/span[@id='pubtime_baidu']");
		web12.put(
				"sourceurl",
				"//*/span[@id='source_baidu']/a");
		web12.put("text", "//*/div[@id='artibody']");
		web12.put("listcss", false);
		web12.put("listscript", false);
		web12.put("newscss", false);
		web12.put("newsscript", false);
		weblist.add(web12);
		
		Map<String, Object> web13 = new HashMap<String, Object>();
		web13.put("url", "http://crudeoil.hexun.com/sdpl/");
		web13.put("list",
				"//*/div[@class='dList pl14 pt6']/ul/li/a");
		web13.put("next", "//*/div[@class='hx_paging']/ul/li[@class='next']/a");
		web13.put("title",
				"//*/div[@class='layout mg articleName']/h1");
		web13.put(
				"time",
				"//*/div[@class='tip fl']/span");
		web13.put(
				"sourceurl",
				"//*/div[@class='tip fl']/a");
		web13.put("text", "//*/div[@class='art_contextBox']");
		web13.put("listcss", false);
		web13.put("listscript", true);
		web13.put("newscss", false);
		web13.put("newsscript", false);
		weblist.add(web13);
		
		//發改委:在頁面跳轉選擇上有些問題
		Map<String, Object> web14 = new HashMap<String, Object>();
		web14.put("url", "http://www.sdpc.gov.cn/jjxsfx/");
		web14.put("list",
				"//*/ul[@class='list_02 clearfix']//li/a");
		web14.put("next", "//*/ul[@class='pages clearfix']/li[@class='L']/a[span[@class='f']][2]");
		web14.put("title",
				"//*/div[@class='txt_title1 tleft']");
		web14.put(
				"time",
				"//*/div[@class='txt_subtitle1 tleft']");
		web14.put(
				"sourceurl",
				"//*/span[@id='dSourceText']/a");
		web14.put("text", "//*/div[@class='TRS_Editor']");
		web14.put("isrelative", true);
		web14.put("listcss", false);
		web14.put("listscript", true);
		web14.put("newscss", false);
		web14.put("newsscript", false);
		weblist.add(web14);
		
		Map<String, Object> web15 = new HashMap<String, Object>();
		web15.put("url", "http://www.sdpc.gov.cn/fzgggz/hgjj/");
		web15.put("list",
				"//*/ul[@class='list_02 clearfix']//li/a");
		web15.put("next", "//*/ul[@class='pages clearfix']/li[@class='L']/a[span[@class='f']][2]");
		web15.put("title",
				"//*/div[@class='txt_title1 tleft']");
		web15.put(
				"time",
				"//*/div[@class='txt_subtitle1 tleft']");
		web15.put(
				"sourceurl",
				"//*/span[@id='dSourceText']/a");
		web15.put("text", "//*/div[@class='TRS_Editor']");
		web15.put("isrelative", true);
		web15.put("listcss", false);
		web15.put("listscript", true);
		web15.put("newscss", false);
		web15.put("newsscript", false);
		weblist.add(web15);
		
		//Sina
		Map<String, Object> web16 = new HashMap<String, Object>();
		web16.put("url", "http://finance.sina.com.cn/china/");
		web16.put("list",
				"//*/div[@class='feed-card-content']/div[1]/div/*/a");
		web16.put("next", "//*/span[@class='pagebox_next']/a");
		web16.put("title",
				"//*/h1[@id='artibodyTitle']");
		web16.put(
				"time",
				"//*/span[@class='time-source']");
		web16.put(
				"sourceurl",
				"//*/div[@class='page-info']/span/*/a");
		web16.put("text", "//*/div[@id='artibody']");
		web16.put("isrelative", false);
		web16.put("listcss", false);
		web16.put("listscript", true);
		web16.put("newscss", false);
		web16.put("newsscript", false);
		weblist.add(web16);
		
	}
	
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		WebCrawler wc = new WebCrawler();
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		 java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.IncorrectnessListenerImpl").setLevel(Level.OFF); 
		 java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		for(int i=14;i<15;i++)
		{
			
		Map<String, Object> config = wc.getWebList().get(i);
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(
					(boolean) config.get("listcss"));
			webClient.getOptions().setJavaScriptEnabled(
					(boolean) config.get("listscript"));
			final HtmlPage page = webClient.getPage(config.get("url")
					.toString());
			List<HtmlAnchor> list = (List<HtmlAnchor>) page.getByXPath(config
					.get("list").toString());
			List<String> newslist = new ArrayList<String>();
			System.out
					.println("******************************list******************************");
			System.out.println("list_size:" + list.size());
			for (HtmlAnchor ha : list)
			{
				System.out.println(ha.getHrefAttribute() + " "
						+ ha.getTextContent());
				if(config.get("isrelative")!=null&&(boolean)config.get("isrelative"))
				{
					newslist.add(config.get("url")+ha.getHrefAttribute());
				}
				else
				{
					newslist.add(ha.getHrefAttribute());
				}
			}
			System.out
					.println("****************************list-end****************************");
			List<HtmlAnchor> click = (List<HtmlAnchor>) page.getByXPath(config
					.get("next").toString());
			page.cleanUp();
			System.out.println("click_size:" + click.size());
			if (click.size() > 0)
			{
				final HtmlPage nextpage = click.get(0).click();
				list = (List<HtmlAnchor>) nextpage.getByXPath(config
						.get("list").toString());
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
			System.out
				.println("******************************news******************************");
			if(newslist.size()>0)
			{
				System.out.println(newslist.get(0));
				try (final WebClient newClient = new WebClient(BrowserVersion.CHROME))
				{
					newClient.getOptions().setCssEnabled(
							(boolean) config.get("newscss"));
					newClient.getOptions().setJavaScriptEnabled(
							(boolean) config.get("newsscript"));
					final HtmlPage newspage = newClient.getPage(newslist.get(0)
							.toString());
					List<HtmlElement> title = (List<HtmlElement>)newspage.getByXPath(config.get("title").toString());
					List<HtmlElement> time = (List<HtmlElement>)newspage.getByXPath(config.get("time").toString());
					List<HtmlElement> sourceurl = (List<HtmlElement>)newspage.getByXPath(config.get("sourceurl").toString());
					List<HtmlElement> text = (List<HtmlElement>)newspage.getByXPath(config.get("text").toString());
					//System.out.println("title:"+(title.size()>0?title.get(0).asText():""));
					//System.err.println("time:"+(time.size()>0?time.get(0).asText():""));
					//System.out.println("sourceurl:"+(sourceurl.size()>0?sourceurl.get(0).asText():""));
					//System.out.println("text:"+(text.size()>0?text.get(0).asText():""));
					newspage.cleanUp();
					newClient.close();
				}
			}
			System.out
			.println("****************************news-end****************************");
			webClient.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		}
	}
	
}
