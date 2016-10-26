package cn.cnic.datapub;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
@SuppressWarnings("unchecked")
public class CrawlerTest
{
	
	@Test
	public void listGet()throws Exception
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			String url = "https://itjuzi.com/investevents";
			//final HtmlPage page = webClient.getPage("https://itjuzi.com/investevents");
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			//webClient.getOptions().setSSLClientCertificate(certificateInputStream, certificatePassword, certificateType)
			//List<HtmlElement> list = (List<HtmlElement>) page.getByXPath("//*/div[@class='list-main-eventset']/li/i");
						
			//System.out.println(list.size());
			
			
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setRedirectEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setTimeout(60000);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			HtmlPage page = webClient.getPage(url);
			System.out.println(page.getTitleText());
			
			List<HtmlElement> list = (List<HtmlElement>) page.getByXPath("//*/ul[@class='list-main-eventset']/li");
			for(HtmlElement element:list)
			{
				System.out.println(element.asText());
			}
			System.out.println(list.size());
			
			
			
			
			
		}
		
		
	}
	
	
	
	public void homePage() throws Exception
	{
		// final WebClient webClient = new WebClient();
		try (final WebClient webClient = new WebClient())
		{
			final HtmlPage page = webClient
					.getPage("http://htmlunit.sourceforge.net");
			System.out.println(page.getTitleText());
			Assert.assertEquals("HtmlUnit – Welcome to HtmlUnit",
					page.getTitleText());
			
			final String pageAsXml = page.asXml();
			Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
			
			final String pageAsText = page.asText();
			Assert.assertTrue(pageAsText
					.contains("Support for the HTTP and HTTPS protocols"));
		}
	}
	
	// @Test
	public void homePage_Firefox() throws Exception
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage page = webClient
					.getPage("http://business.sohu.com/hgjj/index.shtml");
			System.out.println(page.getTitleText());
			// Assert.assertEquals("百度一下，你就知道", page.getTitleText());
			
			String result = page.asText();
			webClient.close();
			System.out.println(result);
		}
	}
	
	// @Test
	public void pageLogin() throws Exception
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			// webClient.getOptions().setCssEnabled(false);
			// webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage page = webClient
					.getPage("http://www.dataguru.cn/member.php?mod=logging&action=login");
			HtmlForm form = page.getFormByName("login");
			
			HtmlInput username = form.getInputByName("username");
			HtmlInput password = form.getInputByName("password");
			HtmlButton submit = form.getButtonByName("loginsubmit");
			
			username.click();
			username.type("sens2010");
			
			password.click();
			password.type("hanyeuqi315");
			
			Page result_page = submit.click();
			System.out.println("*****************");
			System.err.println(result_page.getUrl());
			System.err.println(result_page.hashCode());
			System.err.println(result_page.getWebResponse().getStatusMessage()
					+ ":" + result_page.getWebResponse().getStatusCode());
			
			Thread.sleep(10000);
			
			System.out.println(result_page.getWebResponse()
					.getContentAsString());
		}
	}
	
	// @Test
	
	public void testBussinessSohu()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			final HtmlPage page = webClient
					.getPage("http://business.sohu.com/hgjj/");
			List<HtmlAnchor> list = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
			System.out.println(list.size());
			System.out.println(list.get(0).getChildElementCount());
			for (HtmlAnchor ha : list)
			{
				// List<HtmlAnchor> ul =
				// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
				// for(HtmlAnchor ha:ul)
				System.out.println(ha.getHrefAttribute() + " "
						+ ha.getTextContent());
			}
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// @Test
	public void testBussinessSohuNews()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage page = webClient
					.getPage("http://business.sohu.com/20160621/n455483681.shtml");
			List<HtmlElement> title = (List<HtmlElement>) page
					.getByXPath("//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1");
			List<HtmlElement> time = (List<HtmlElement>) page
					.getByXPath("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']");
			List<HtmlElement> source = (List<HtmlElement>) page
					.getByXPath("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span");
			List<HtmlElement> sourceurl = (List<HtmlElement>) page
					.getByXPath("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span");
			
			List<HtmlElement> text = (List<HtmlElement>) page
					.getByXPath("//*/div[@itemprop='articleBody']");
			// HtmlElement time =
			// (HtmlElement)page.getByXPath("//*/div[@id='container']/*/div[@class='time-fun']/div[@class='time-source']/div[@class='time']").get(0);
			// HtmlElement source =
			// (HtmlElement)page.getByXPath("//*/div[@id='container']/*/div[@class='time-fun']/div[@class='time-source']/div[@class='sc']/*/a").get(0);
			
			System.out.println(title.get(0).getTextContent());
			System.out.println(time.get(0).getTextContent());
			System.out.println(source.get(0).getTextContent());
			System.out.println(sourceurl.get(0).asXml());
			
			// System.out.println(text.get(0).getTextContent());
			System.out.println(text.size());
			
			// System.out.println(source.asText());
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testBussinessClick()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			HtmlPage page = webClient.getPage("http://business.sohu.com/hgjj/");
			List<HtmlAnchor> list = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
			List<HtmlAnchor> click = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='pages']/p/table/tbody/tr/td/a[3]");
			for (HtmlAnchor ha : list)
			{
				// List<HtmlAnchor> ul =
				// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
				// for(HtmlAnchor ha:ul)
				System.out.println(ha.getHrefAttribute() + " "
						+ ha.getTextContent());
			}
			System.out.println(list.size());
			System.out.println(click.size());
			System.out.println(list.get(0).getChildElementCount());
			if (click.size() > 0)
			{
				page = click.get(0).click();
				System.out.println("________________________________________");
				System.out.println(click.get(0).asXml());
				list = (List<HtmlAnchor>) page
						.getByXPath("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a");
				for (HtmlAnchor ha : list)
				{
					// List<HtmlAnchor> ul =
					// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
					// for(HtmlAnchor ha:ul)
					System.out.println(ha.getHrefAttribute() + " "
							+ ha.getTextContent());
				}
			}
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testHexun()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			
			final HtmlPage page = webClient
					.getPage("http://crudeoil.hexun.com/sdpl/");
			List<HtmlAnchor> list = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@id='temp01']/ul/li/a");
			System.out.println(list.size());
			// System.out.println(list.get(0).getChildElementCount());
			for (HtmlAnchor ha : list)
			{
				// List<HtmlAnchor> ul =
				// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
				// for(HtmlAnchor ha:ul)
				System.out.println(ha.getHrefAttribute() + " "
						+ ha.getTextContent());
			}
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void testHexunClick()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getCurrentWindow().setInnerHeight(1024);
			//webClient.setCurrentWindow(ww);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			final HtmlPage page = webClient
					.getPage("http://news.hexun.com/economy/");
			List<HtmlAnchor> list = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@id='temp01']/ul/li/a");
			System.out.println(list.size());
			System.out.println(list.get(0).getChildElementCount());
			for (HtmlAnchor ha : list)
			{
				// List<HtmlAnchor> ul =
				// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
				// for(HtmlAnchor ha:ul)
				System.out.println(ha.getHrefAttribute() + " "
						+ ha.getTextContent());
			}
			List<HtmlAnchor> click = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='hx_paging']/ul/li[@class='next']/a");
			System.out.println(click.get(0).getHrefAttribute() + " "
					+ click.get(0).getTextContent());
			
			if (click.size() > 0)
			{
				
				final HtmlPage pagec = click.get(0).click();
				list = (List<HtmlAnchor>) pagec
						.getByXPath("//*/div[@id='temp01']/ul/li/a");
				System.out.println(list.size());
				System.out.println(list.get(0).getChildElementCount());
				for (HtmlAnchor ha : list)
				{
					// List<HtmlAnchor> ul =
					// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
					// for(HtmlAnchor ha:ul)
					System.out.println(ha.getHrefAttribute() + " "
							+ ha.getTextContent());
				}
			}
			System.out.println(click.size());
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// @Test
	public void testHexunNews()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage page = webClient
					.getPage("http://news.hexun.com/2016-06-22/184530621.html");
			List<HtmlElement> title = (List<HtmlElement>) page
					.getByXPath("//*/div[@class='layout mg articleName']/h1");
			List<HtmlElement> time = (List<HtmlElement>) page
					.getByXPath("//*/div[@class='tip fl']/span");
			List<HtmlAnchor> source = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='tip fl']/a");
			
			System.out.println(title.size());
			System.out.println(title.get(0).asText());
			System.out.println(time.size());
			System.out.println(time.get(0).asText());
			System.out.println(source.size());
			System.out.println(source.get(0).asText() + ":"
					+ source.get(0).getHrefAttribute());
			
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testSDPC()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage page = webClient
					.getPage("http://www.sdpc.gov.cn/jjxsfx/");
			List<HtmlAnchor> list = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='box1 ']/ul/li/a");
			System.out.println(list.size());
			// System.out.println(list.get(0).getChildElementCount());
			for (HtmlAnchor ha : list)
			{
				// List<HtmlAnchor> ul =
				// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
				// for(HtmlAnchor ha:ul)
				System.out.println(ha.getHrefAttribute() + " "
						+ ha.getTextContent());
			}
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// @Test
	public void testSDPCClick()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			final HtmlPage page = webClient
					.getPage("http://www.sdpc.gov.cn/jjxsfx/");
			List<HtmlAnchor> list = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='box1 ']/ul/li/a");
			System.out.println(list.size());
			System.out.println(list.get(0).getChildElementCount());
			for (HtmlAnchor ha : list)
			{
				// List<HtmlAnchor> ul =
				// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
				// for(HtmlAnchor ha:ul)
				System.out.println(ha.getHrefAttribute() + " "
						+ ha.getTextContent());
			}
			List<HtmlAnchor> click = (List<HtmlAnchor>) page
					.getByXPath("//*/ul[@class='pages clearfix']/li/a");
			System.out.println(click.size());
			for (HtmlAnchor has : click)
				if (has.getTextContent().contains("下一页"))
				{
					
					final HtmlPage pagec = has.click();
					list = (List<HtmlAnchor>) pagec
							.getByXPath("//*/div[@class='box1 ']/ul/li/a");
					System.out.println(list.size());
					System.out.println(list.get(0).getChildElementCount());
					for (HtmlAnchor ha : list)
					{
						// List<HtmlAnchor> ul =
						// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
						// for(HtmlAnchor ha:ul)
						System.out.println(ha.getHrefAttribute() + " "
								+ ha.getTextContent());
					}
				}
			System.out.println(click.size());
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testSDPCNews()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage page = webClient
					.getPage("http://www.sdpc.gov.cn/jjxsfx/201605/t20160531_805851.html");
			List<HtmlElement> title = (List<HtmlElement>) page
					.getByXPath("//*/div[@class='txt_title1 tleft']");
			List<HtmlElement> time = (List<HtmlElement>) page
					.getByXPath("//*/div[@class='txt_subtitle1 tleft']");
			List<HtmlAnchor> source = (List<HtmlAnchor>) page
					.getByXPath("//*/span[@id='dSourceText']/a");
			
			System.out.println(title.size());
			System.out.println(title.get(0).asText());
			System.out.println(time.size());
			System.out.println(time.get(0).getTextContent());
			System.out.println(source.size());
			System.out.println(source.get(0).asText() + ":"
					+ source.get(0).getHrefAttribute());
			
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void testFSina()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.setJavaScriptTimeout(35000);
			
			final HtmlPage page = webClient
					.getPage("http://finance.sina.com.cn/china/");
			/*try
			{
				Thread.sleep(10000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//System.out.println(page.asXml());
			List<HtmlElement> list = (List<HtmlElement>) page
					.getByXPath("//*/div[@class='feed-card-content']/div[1]/div/*/a");
			System.out.println(list.size());
			/*if(list.size()>0)
			{
				System.out.println(list.get(0).asXml());
			}*/
			// System.out.println(list.get(0).getChildElementCount());
			for (HtmlElement ha : list)
			{
				// List<HtmlAnchor> ul =
				// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
				// for(HtmlAnchor ha:ul)
				System.out.println(((HtmlAnchor)ha).getHrefAttribute() + " "
						+ ha.getTextContent());
			}
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void testFSinaClick()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			final HtmlPage page = webClient
					.getPage("http://finance.sina.com.cn/china/");
			List<HtmlAnchor> list = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='feed-card-content']/div[1]/div/*/a");
			System.out.println(list.size());
			System.out.println(list.get(0).getChildElementCount());
			for (HtmlAnchor ha : list)
			{
				// List<HtmlAnchor> ul =
				// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
				// for(HtmlAnchor ha:ul)
				System.out.println(ha.getHrefAttribute() + " "
						+ ha.getTextContent());
			}
			List<HtmlAnchor> click = (List<HtmlAnchor>) page
					.getByXPath("//*/span[@class='pagebox_next']/a");
			System.out.println(click.size());
			for (HtmlAnchor has : click)
				if (has.getTextContent().contains("下一页"))
				{
					
					final HtmlPage pagec = has.click();
					list = (List<HtmlAnchor>) pagec
							.getByXPath("//*/div[@class='feed-card-content']/div[1]/div/*/a");
					System.out.println(list.size());
					//System.out.println(list.get(0).getChildElementCount());
					for (HtmlAnchor ha : list)
					{
						// List<HtmlAnchor> ul =
						// (List<HtmlAnchor>)htmlAnchor.getByXPath("//*/li/a");
						// for(HtmlAnchor ha:ul)
						System.out.println(ha.getHrefAttribute() + " "
								+ ha.getTextContent());
					}
					pagec.cleanUp();
				}
			page.cleanUp();
			webClient.close();
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testFSinaNews()
	{
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage page = webClient
					.getPage("http://finance.sina.com.cn/china/gncj/2016-06-22/doc-ifxtfrrf0857661.shtml");
			List<HtmlElement> title = (List<HtmlElement>) page
					.getByXPath("//*/h1[@id='artibodyTitle']");
			List<HtmlElement> time = (List<HtmlElement>) page
					.getByXPath("//*/span[@class='time-source']");
			List<HtmlAnchor> source = (List<HtmlAnchor>) page
					.getByXPath("//*/div[@class='page-info']/span/*/a");
			
			System.out.println(title.size());
			System.out.println(title.get(0).asText());
			System.out.println(time.size());
			System.out.println(time.get(0).asText());
			System.out.println(source.size());
			System.out.println(source.get(0).asText() + ":"
					+ source.get(0).getHrefAttribute());
			
		} catch (FailingHttpStatusCodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//@Test
	public void httptest()
	{
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
	}
	
}
