package cn.cnic.datapub.n.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.model.SubJob;
import cn.cnic.datapub.n.serviceimpl.ParserServiceImpl;
import cn.cnic.datapub.n.serviceimpl.SubJobServiceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@RestController
@RequestMapping("/admin/analysor")



public class AnalysorController
{
	@Resource
	ParserServiceImpl parserServiceImpl;
	
	@Resource
	SubJobServiceImpl subJobServiceImpl;
	
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getAllAnalysor()
	{
		List<Parser> parserlist= parserServiceImpl.findAll();
		JSONArray parserarr = new JSONArray();
		for(Parser parser:parserlist)
		{
			parserarr.add(parser);
		}
		return parserarr.toJSONString();
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getAnalysor(@PathVariable("id") int id)
	{
		Parser parser = parserServiceImpl.getParserById(id);
		if(parser!=null)
		{
			JSONObject  element = JSONObject.parseObject(parser.toJSONString());
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String createtime = df.format(parser.getCreatetime());
			element.put("createtime", createtime);
			if(parser.getLastmodifytime()!=null)
			{
				element.put("lastmodifytime", parser.getLastmodifytime());
			}
			if(parser.getLastchecktime()!=null)
			{
				element.put("lastchecktime", parser.getLastchecktime());
			}
			return parser.toJSONString();
		}
		else
		{
			return null;
		}
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getAnalysors()
	{
		return getAnalysors(0);
	}
	
	@RequestMapping(value = "/list/{pid}",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getAnalysors(@PathVariable("pid") int pid)
	{
		JSONObject result = new JSONObject();
		
		int pagesize = 10;
		
		int sum = parserServiceImpl.countAll();
		int page_count=sum/pagesize+(sum%pagesize==0?0:1);
		int courrent_index = pid;
		
		List<SubJob> subjobs = subJobServiceImpl.findAll();
		Map<Integer,Integer> parser_count = new HashMap<Integer,Integer>();
		for(SubJob sj:subjobs)
		{
			int parserid = sj.getParserid();
			if(parser_count.containsKey(parserid))
			{
				Integer pcount= parser_count.get(parserid);
				pcount +=1;
				parser_count.put(parserid, pcount);
			}
			else
			{
				parser_count.put(parserid,1);
			}
		}
		
		List<Parser> parsers = parserServiceImpl.list(pid, pagesize);
		
		
		JSONArray list = new JSONArray();
		for(Parser parser:parsers)
		{
			parser_count.get(parser.getId());
			JSONObject element = JSONObject.parseObject(parser.toJSONString());
			element.put("jcount", parser_count.containsKey(parser.getId())?parser_count.get(parser.getId()):0);
			Date createtime = parser.getCreatetime();
			Date lastmodifytime = parser.getLastmodifytime();
			Date lastchecktime = parser.getLastchecktime();
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			element.put("createtime",df.format(createtime));
			if(lastmodifytime!=null)
			{
				element.put("lastmoditytime", df.format(parser.getLastmodifytime()));
			}
			if(lastchecktime!=null)
			{
				element.put("lastchecktime", df.format(parser.getLastchecktime()));
			}
			
			list.add(element);
		}
		result.put("index", courrent_index);
		result.put("count", page_count);
		result.put("data", list);
		
		return result.toJSONString();
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String addAnalysor(HttpServletRequest request,@RequestBody Parser element)
	{
		
		///String analysordata = request.getParameter("data");
		if(element!=null)
		{
			int eid  = element.getId();
			if(parserServiceImpl.getParserById(eid)!=null)
			{
				element.setLastmodifytime(new Date());
				element.setStatus(1);
				element = parserServiceImpl.updateParser(element);
			}
			else
			{
				element.setCreatetime(new Date());
				element.setLastmodifytime(null);
				element.setStatus(1);
				element = parserServiceImpl.addParser(element);
			}
			return element.toJSONString();
		}
		
/*		if(analysordata!=null)
		{
			analysordata = analysordata.trim();
			JSONObject analysorj = JSONObject.parseObject(analysordata);
			Parser parser = new Parser();
			
			
			
			parser.setArtcss((analysorj.get("artcss")==null?false:analysorj.getBooleanValue("artcss")));
			parser.setArtjs((analysorj.get("artjs")==null?false:analysorj.getBooleanValue("artjs")));
			parser.setCreatetime(new Date());
			parser.setListcss((analysorj.get("listcss")==null?false:analysorj.getBooleanValue("listcss")));
			parser.setListjs(analysorj.get("listjs")==null?false:analysorj.getBooleanValue("listjs"));
			parser.setListparser(analysorj.get("listparser")==null?"":analysorj.getString("listparser"));
			parser.setNextparser(analysorj.get("nextparser")==null?"":analysorj.getString("nextparser"));
			parser.setSourceparser(analysorj.get("sourceparser")==null?"":analysorj.getString("sourceparser"));
			parser.setStatus(1);
			parser.setTextparser(analysorj.get("textparser")==null?"":analysorj.getString("textparser"));
			parser.setTimeparser(analysorj.get("timeparser")==null?"":analysorj.getString("timeparser"));
			parser.setTimetransfer(analysorj.get("timetransfer")==null?"":analysorj.getString("timetransfer"));
			parser.setTitleparser(analysorj.get("titleparser")==null?"":analysorj.getString("titleparser"));
			parser.setUrlrelativer((analysorj.get("urlrelative")==null?false:analysorj.getBooleanValue("urlrelative")));
			
			
			
			
			parser.setArtcss(false);
			parser.setArtjs(false);
			parser.setCreatetime(new Date());
			parser.setLastmodifytime(new Date());
			parser.setListcss(false);
			parser.setListjs(false);
			parser.setListparser("parser");
			parser.setNextparser("next");
			parser.setSourceparser("source");
			parser.setStatus(1);
			parser.setTextparser("text");
			parser.setTimeparser("time");
			parser.setTimetransfer("transfer");
			parser.setTitleparser("title");
			parser.setUrlrelativer(false);
			
			parser = parserServiceImpl.addParser(parser);
			return 	parser.toJSONString();
		}*/
		else
		{
			return null;
		}
		
		
		
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String changeAnalysor(@PathVariable("id") int id,@RequestBody String content)
	{
		Parser parser = parserServiceImpl.getParserById(id);
		if(content!=null&& parser!=null)
		{
			JSONObject element = JSONObject.parseObject(content);
			Set<String> keys = element.keySet();
			for(String key:keys)
			{
				if(key.equals("name"))
				{
					parser.setName(element.get("name")==null?"":element.getString("name"));
				}
				if(key.equals("artcss"))
				{
					parser.setArtcss((element.get("artcss")==null?false:element.getBooleanValue("artcss")));
				}
				if(key.equals("artjs"))
				{
					parser.setArtjs((element.get("artjs")==null?false:element.getBooleanValue("artjs")));
				}
				if(key.equals("listcss"))
				{	
					parser.setListcss((element.get("listcss")==null?false:element.getBooleanValue("listcss")));
				}
				if(key.equals("listjs"))
				{
					parser.setListjs(element.get("listjs")==null?false:element.getBooleanValue("listjs"));
				}
				if(key.equals("listparser"))
				{
					parser.setListparser(element.get("listparser")==null?"":element.getString("listparser"));
				}
				if(key.equals("nextparser"))
				{
					parser.setNextparser(element.get("nextparser")==null?"":element.getString("nextparser"));
				}
				if(key.equals("sourceparser"))
				{	
					parser.setSourceparser(element.get("sourceparser")==null?"":element.getString("sourceparser"));
				}
				if(key.equals("status"))
				{
					parser.setStatus(element.get("status")==null?1:element.getInteger("status"));
				}
				if(key.equals("textparser"))
				{
					parser.setTextparser(element.get("textparser")==null?"":element.getString("textparser"));
				}
				if(key.equals("timeparser"))
				{
					parser.setTimeparser(element.get("timeparser")==null?"":element.getString("timeparser"));
				}
				if(key.equals("timetransfer"))
				{
					parser.setTimetransfer(element.get("timetransfer")==null?"":element.getString("timetransfer"));
				}
				if(key.equals("titleparser"))
				{
					parser.setTitleparser(element.get("titleparser")==null?"":element.getString("titleparser"));
				}
				if(key.equals("urlrelativer"))
				{
					parser.setUrlrelativer((element.get("urlrelativer")==null?false:element.getBooleanValue("urlrelativer")));
				}
				if(key.equals("lastchecktime"))
				{
					parser.setLastchecktime(new Date());
				}
				else
				{
					parser.setLastmodifytime(new Date());
				}
			}
			parser = parserServiceImpl.updateParser(parser);
			//System.err.println(content);
			return parser.toJSONString();
		}
		else
		{
			return null;
		}
	}
	
	@RequestMapping(value = "/test/{id}",method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String testAnalysor(@PathVariable("id") int id,@RequestBody String content )
	{
		JSONObject jcontent = JSONObject.parseObject(content);
		String url = jcontent.getString("url");
		String result = parserServiceImpl.testParser(id, url);
		return result;
	}
	
	
	
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = "application/json;charset=UTF8")
	public String deleteAnalysor(@PathVariable("id") int id)
	{
		Parser parser = parserServiceImpl.getParserById(id);
		if(parser!=null)
		{
			boolean isdelete = parserServiceImpl.deleteById(id);
			if(isdelete)
			{
				return parser.toJSONString();
			}
			else 
			{
				JSONObject result = new JSONObject();
				result.put("code", 405);
				result.put("message", "删除失败！");
				return result.toJSONString();
			}
		}
		else
		{
			JSONObject result = new JSONObject();
			result.put("code", 404);
			result.put("message", "未找到解析器！");
			return result.toJSONString();
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
