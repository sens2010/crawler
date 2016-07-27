package cn.cnic.datapub.n.job;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ListParserTest
{
	static Map<String, Object> web1 = new HashMap<String, Object>();
	static
	{
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
	}
	
	
	
	@Test
	public void testExecute()
	{
		ListParseJob lpj = new ListParseJob();
		lpj.setListcss((boolean)web1.get("listcss"));
		lpj.setListmatch((String)web1.get("list"));
		lpj.setListscript((boolean)web1.get("listscript"));
		lpj.setNextmatch((String)web1.get("next"));
		//lpj.setRelative((boolean)web1.get("isrelative"));
		lpj.setUrl((String)web1.get("url"));
		try
		{
			lpj.execute(null);
			/*AmqpTemplate template = lpj.getAmqpTemplate();
			String result = null;
			result=(String) template.receiveAndConvert("news");
			while(result!=null)
			{	
				String foo = (String) template.receiveAndConvert("news");
				System.err.println(foo);
				result=(String) template.receiveAndConvert("news");
			}*/
		} 
		catch (JobExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
