package cn.cnic.datapub.n.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.ParserDao;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.service.IParserService;

import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Service("parserServiceImpl")
public class ParserServiceImpl implements IParserService
{
	@Resource
	ParserDao parserDao;
	
	@Override
	public Parser addParser(Parser parser)
	{
		return parserDao.save(parser);
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			parserDao.delete(id);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Parser updateParser(Parser parser)
	{
		return parserDao.save(parser);
	}

	@Override
	public Parser getParserById(int id)
	{
		return parserDao.findById(id);
	}

	@Override
	public int countAll()
	{
		return parserDao.countAll();
	}

	@Override
	public String testParser(int id, String url)
	{
		JSONObject result = new JSONObject();
		Parser parser = parserDao.findById(id);
	
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
		{
			webClient.getOptions().setCssEnabled(parser.isListcss());
			webClient.getOptions().setJavaScriptEnabled(parser.isListjs());
			final HtmlPage page = webClient.getPage(url);
			@SuppressWarnings("unchecked")
			List<HtmlAnchor> list = page.getByXPath(parser.getListparser());
			
			if(list.size()<=0)
			{
				result.put("code", 404);
				result.put("message","列表数据不存在！");
			}
			else
			{
				HtmlAnchor news = list.get(0);
				String news_url = "";
				if(parser.isUrlrelativer())
				{
					news_url = url+news.getHrefAttribute();
				}
				else
				{
					news_url = news.getHrefAttribute();
				}
				
				webClient.getOptions().setCssEnabled(parser.isArtcss());
				webClient.getOptions().setJavaScriptEnabled(parser.isArtjs());
				
				final HtmlPage newspage = webClient.getPage(news_url);
				
				//@SuppressWarnings("unchecked")
				List<HtmlElement> titles = newspage.getByXPath(parser.getTitleparser());
				//@SuppressWarnings("unchecked")
				List<HtmlElement> times = newspage.getByXPath(parser.getTimeparser());
				//@SuppressWarnings("unchecked")
				List<HtmlElement> sourceurls = newspage.getByXPath(parser.getSourceparser());
				//@SuppressWarnings("unchecked")
				List<HtmlElement> texts = newspage.getByXPath(parser.getTextparser());
			
				String title ="";
				String time ="";
				String sourceurl ="";
				String text ="";
				if(titles!=null&&titles.size()>=1)
				{
					title = titles.get(0).asText();
				}
				if(times!=null&&times.size()>=1)
				{
					time = times.get(0).asText();
				}
				if(sourceurls!=null&&sourceurls.size()>=1)
				{
					sourceurl = sourceurls.get(0).asText();
				}
				if(texts!=null&&texts.size()>=1)
				{
					text = texts.get(0).asText();
				}
			
				
				JSONObject data = new JSONObject();
				data.put("title",title);
				data.put("source",sourceurl);
				data.put("text",text);
				data.put("url", news_url);
				if(time!=null&&!time.equals(""))
				{
					String timetransfer = parser.getTimetransfer();
					String[] fa = timetransfer.split("\\|");
					String format = fa[0];
					
					if(fa.length>1&&fa[1]!=null)
					{
						String se = fa[1];
						System.out.println(se);
						String[]stend = se.split(",");
						int start = Integer.parseInt(stend[0]);
						int end = Integer.parseInt(stend[1]);
						time = time.substring(start,end);
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					try
					{
						Date pubtime = sdf.parse(time);
						SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
						data.put("time", df.format(pubtime));
					}
					catch(Exception ee)
					{
						data.put("time", "$请检查日期格式$");
						ee.printStackTrace();
					}
				}
				result.put("code", 200);
				result.put("message", "成功");
				result.put("data", data);
				newspage.cleanUp();
			}
			page.cleanUp();
			webClient.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result.toJSONString();
	}
	
	@Override
	public List<Parser> findAll()
	{
		return parserDao.findAll();
	}
	
	@Override
	public List<Parser> findParsers(Collection<Integer> ids)
	{
		return parserDao.findByIds(ids);
	}
	@Override
	public List<Parser> list(int pid, int size)
	{
		Pageable pagable = new PageRequest(pid, size);
		
		return parserDao.findAll(pagable).getContent();
	}
}
