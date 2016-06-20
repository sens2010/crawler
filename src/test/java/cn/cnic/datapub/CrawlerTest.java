package cn.cnic.datapub;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CrawlerTest
{
	
	public void homePage() throws Exception {
	    //final WebClient webClient = new WebClient();
	    try (final WebClient webClient = new WebClient()) {
	        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
	        System.out.println(page.getTitleText());
	        Assert.assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());

	        final String pageAsXml = page.asXml();
	        Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

	        final String pageAsText = page.asText();
	        Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
	    }
	}
	
	//@Test
	public void homePage_Firefox() throws Exception {
	    try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
	        webClient.getOptions().setCssEnabled(false);
	        webClient.getOptions().setJavaScriptEnabled(false);
	    	final HtmlPage page = webClient.getPage("http://business.sohu.com/hgjj/index.shtml");
	        System.out.println(page.getTitleText());
	        //Assert.assertEquals("百度一下，你就知道", page.getTitleText());
	                             
	        String result = page.asText();
	        webClient.close();
	        System.out.println(result);  
	    }
	}
	//@Test
	public void pageLogin() throws Exception {
	    try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
	        //webClient.getOptions().setCssEnabled(false);
	        //webClient.getOptions().setJavaScriptEnabled(false);
	    	final HtmlPage page = webClient.getPage("http://www.dataguru.cn/member.php?mod=logging&action=login");
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
	    	System.err.println(result_page.getWebResponse().getStatusMessage()+":"+result_page.getWebResponse().getStatusCode());
	    
	    	Thread.sleep(10000);
	    	
	    	System.out.println(result_page.getWebResponse().getContentAsString());
	    }
	}
	
	@Test
	public void testBussinessSohu()
	{
		 try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
			 final HtmlPage page = webClient.getPage("http://business.sohu.com/hgjj/index_2372.shtml");
		     List<HtmlAnchor> list = (List<HtmlAnchor>)page.getAnchors();
		     for(HtmlAnchor htmlAnchor:list)
		     {
		    	 System.out.println(htmlAnchor.getHrefAttribute()+":"+htmlAnchor.getTextContent());
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
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
