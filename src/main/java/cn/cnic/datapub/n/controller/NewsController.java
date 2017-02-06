package cn.cnic.datapub.n.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.cnic.datapub.n.model.News;
import cn.cnic.datapub.n.model.StatCategory;
import cn.cnic.datapub.n.model.StatLabel;
import cn.cnic.datapub.n.serviceimpl.NewsServiceImpl;
import cn.cnic.datapub.n.serviceimpl.StatCategoryServiceImpl;
import cn.cnic.datapub.n.serviceimpl.StatLabelServiceImpl;

@RestController
@RequestMapping("/admin/news")
public class NewsController
{
	@Resource 
	NewsServiceImpl newsServiceImpl;
	
	@Resource
	StatLabelServiceImpl statLabelServiceImpl;
	
	@Resource
	StatCategoryServiceImpl statCategoryServiceImpl; 
	
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value="/labels",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getLabels()
	{
		JSONArray labels = new JSONArray();
		JSONObject result = new JSONObject();
		List<StatCategory> categorylist = statCategoryServiceImpl.findAll();
		for(StatCategory sc : categorylist)
		{
			JSONObject element = JSONObject.parseObject(sc.toJSONString());
			String code = element.getString("code");
			if(code.endsWith("00000000"))element.put("level", 1);
			else if(code.endsWith("000000"))element.put("level", 2);
			else if(code.endsWith("0000"))element.put("level", 3);
			else if(code.endsWith("00"))element.put("level", 4);
			else element.put("level", 5);
			labels.add(element);
		}
		result.put("data", labels);
		return result.toJSONString();
	}
	
	@RequestMapping(value="/labels",method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String addLabels(@RequestBody String element)
	{
		JSONObject result = JSONObject.parseObject(element);
		
		JSONArray news = result.getJSONArray("news");
		JSONArray labels = result.getJSONArray("labels");
		
		List<StatLabel> sllist = new ArrayList<StatLabel>();
		
		for(Object newsid : news)
		{
			for(Object labelid :labels)
			{
				int nid = Integer.parseInt(newsid.toString());
				int lid = Integer.parseInt(labelid.toString());
				
				StatLabel sl = new StatLabel();
				sl.setCategoryid(lid);
				sl.setNewsid(nid);
				sl.setStatus(1);
				sl.setCreatetime(new Date());
				
				sllist.add(sl);
			}
		}
		
		for(StatLabel sl : sllist)
		{
			if(!statLabelServiceImpl.findStatLabelByKeys(sl.getNewsid(), sl.getCategoryid()))
				statLabelServiceImpl.addStatLabel(sl);
		}
		return result.toJSONString();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE, produces ="application/json;charset=UTF8" )
	public String deleteNews(@PathVariable("id") int id)
	{
		JSONObject result = new JSONObject();
		
		newsServiceImpl.deleteById(id);
		statLabelServiceImpl.deleteStatLabelByNewsId(id);
		result.put("code", 200);
		result.put("msg", "删除成功！");
		return result.toJSONString();
		
	}
	
	@RequestMapping(value="/labels/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String updateNewsLabels(@PathVariable("id") int id,@RequestBody String element)
	{
		JSONObject result = new JSONObject();
		
		JSONObject jelement = JSONObject.parseObject(element);
		
		JSONArray labels = jelement.getJSONArray("labels");
		Set<Integer> deletelist = new HashSet<Integer>();
		Set<Integer> slabels = new HashSet<Integer>();
		for(Object index:labels)
		{
			slabels.add(Integer.parseInt(index.toString()));
		}
		
		
		List<StatLabel> nllist = statLabelServiceImpl.findStatLabelByNewsId(id);
		for(StatLabel sl:nllist)
		{
			if(slabels.contains(sl.getCategoryid()))
			{
				slabels.remove(sl.getCategoryid());
			}
			else
			{
				deletelist.add(sl.getId());
			}
		}
		
		for(Integer cid:slabels)
		{
			StatLabel sl = new StatLabel();
			sl.setCategoryid(cid);
			sl.setNewsid(id);
			sl.setStatus(1);
			sl.setCreatetime(new Date());
			statLabelServiceImpl.addStatLabel(sl);
		}
		if(deletelist.size()>0)
		statLabelServiceImpl.deleteStatLableByIds(deletelist);
		
		return result.toJSONString();
	}
	
	
	@RequestMapping(value="/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String updateNews(@PathVariable("id") int id,@RequestBody String element)
	{
		JSONObject result = new JSONObject();
		JSONObject jelement = JSONObject.parseObject(element);
		
		News news = newsServiceImpl.getNewsById(id);
		if(jelement.containsKey("url"))
		{
			news.setUrl(jelement.getString("url"));
		}
		if(jelement.containsKey("title"))
		{
			news.setTitle(jelement.getString("title"));
		}
		if(jelement.containsKey("resource"))
		{
			news.setResource(jelement.getString("resource"));
		}
		if(jelement.containsKey("text"))
		{
			news.setText(jelement.getString("text"));
		}
		if(jelement.containsKey("pubtime"))
		{
			try
			{
				SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
				String pubtime = jelement.getString("pubtime");
				news.setPubtime(df.parse(pubtime));
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
		news = newsServiceImpl.updateNews(news);
		result.put("data", news);
		return result.toJSONString();
	}
	
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getNews(@PathVariable("id") int id)
	{
		JSONObject result = new JSONObject();
		News news = newsServiceImpl.getNewsById(id);
		JSONObject element = JSONObject.parseObject(news.toJSONString());
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		if(element.containsKey("pubtime"))
		{
			element.put("pubtime",df.format(news.getPubtime()));
		}
		if(element.containsKey("createtime"))
		{
			element.put("createtime", df.format(news.getCreatetime()));
		}
		List<StatLabel> statLabels = statLabelServiceImpl.findStatLabelByNewsId(id);
		List<Integer> slids = new ArrayList<Integer>();
		for(StatLabel sl :statLabels)
		{
			slids.add(sl.getCategoryid());
		}
		
		JSONArray labels = new JSONArray();
		if(slids.size()>0)
		{
		List<StatCategory> sclist  = statCategoryServiceImpl.findCategories(slids);
		
		for(StatCategory sc : sclist)
		{
			labels.add(sc);
		}
		}
		element.put("labels", labels);
		result.put("data", element);
		
		return result.toJSONString();
	}
	
	
	
	@RequestMapping(value="/list",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getNewsList()
	{
		return getNewsList(0);
	}
	
	@RequestMapping(value="/list",method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String getNewsListBySearch(@RequestBody String element)
	{
		return getNewsListBySearch(0,element);
	}
	
	@RequestMapping(value="/list/{pid}",method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String getNewsListBySearch(@PathVariable("pid") int pid,@RequestBody String element)
	{
		JSONObject result = new JSONObject();
		JSONObject jelement = JSONObject.parseObject(element);
		String name = jelement.getString("name");
		String value = jelement.getString("value");
		
		long start = System.currentTimeMillis();
		
		if(value==null||value.equals(""))
		{
			return getNewsList();
		}
		
		int pagesize = 10;
		int sum = newsServiceImpl.countAll(name,value);
		int page_count=sum/pagesize+(sum%pagesize==0?0:1);
		int courrent_index = pid;
		List<News> newslist = newsServiceImpl.list(name,value,pid, pagesize);
		long end = System.currentTimeMillis();
		System.out.println("1:"+(start-end));
		
		
		JSONArray data = new JSONArray();
		
		Set<Integer> newsids = new HashSet<Integer>();
		List<News> newsmapper = new ArrayList<News>();
		
		for(News news :newslist)
		{
			newsids.add(news.getId());
			//newsmapper.put(news.getId(), news);
			newsmapper.add(news);
		}
		
		List<StatLabel> statlabels = new ArrayList<StatLabel>();
		if(newsids.size()>0)
		statlabels =statLabelServiceImpl.findStatLabelByNewsIds(newsids);
		end = System.currentTimeMillis();
		System.out.println("2:"+(start-end));
		
		Set<Integer> categoryids = new HashSet<Integer>();
		
		Map<Integer,List<Integer>> labels = new LinkedHashMap <Integer,List<Integer>>();
		for(StatLabel sl:statlabels)
		{
			categoryids.add(sl.getCategoryid());
			int newid = sl.getNewsid();
			if(labels.containsKey(newid))
			{
				labels.get(newid).add(sl.getCategoryid());
			}
			else
			{
				List<Integer> cids = new ArrayList<Integer>();
				cids.add(sl.getCategoryid());
				labels.put(newid,cids);
			}
		}

		
		List<StatCategory> categories = new ArrayList<StatCategory>();
		if(categoryids.size()>0)
			categories = statCategoryServiceImpl.findCategories(categoryids);
		end = System.currentTimeMillis();
		System.out.println("3:"+(start-end));
		Map<Integer,StatCategory> categorymapper = new HashMap<Integer,StatCategory>();
		for(StatCategory sc :categories)
		{
			categorymapper.put(sc.getId(), sc);
		}
	
		for(News nk :newsmapper)
		{
			JSONArray ls = new JSONArray();
			List<Integer> ll = labels.get(nk.getId());
			if(ll!=null)
			for(Integer k :ll)
			{
				ls.add(categorymapper.get(k));
			}
			JSONObject object = JSONObject.parseObject(nk.toJSONString());
			try{
				Object pubtime = object.get("pubtime");
				if(pubtime!=null)
				{
					long time = Long.parseLong(pubtime.toString());
					Date date = new Date(time);
					SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
					object.put("pubtime",df.format(date));
				}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
			object.put("labels", ls);
			data.add(object);
		}
		end = System.currentTimeMillis();
		System.out.println("4:"+(start-end));
		result.put("data", data);
		
		result.put("count", page_count);
		result.put("index", courrent_index);
		
		return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
	}
	
	
	@RequestMapping(value="/list/{pid}",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getNewsList(@PathVariable("pid") int pid)
	{
		int pagesize = 10;
		int sum = newsServiceImpl.countAll();
		int page_count=sum/pagesize+(sum%pagesize==0?0:1);
		int courrent_index = pid;
		long start = System.currentTimeMillis();
		List<News> newslist = newsServiceImpl.list(pid, pagesize);
		long end = System.currentTimeMillis();
		System.out.println("1:"+(start-end));
		start = end;
		JSONObject result = new JSONObject();
		
		JSONArray data = new JSONArray();
		
		Set<Integer> newsids = new HashSet<Integer>();
		List<News> newsmapper = new ArrayList<News>();
		
		for(News news :newslist)
		{
			newsids.add(news.getId());
			//newsmapper.put(news.getId(), news);
			newsmapper.add(news);
		}
		
		List<StatLabel> statlabels = statLabelServiceImpl.findStatLabelByNewsIds(newsids);
		end = System.currentTimeMillis();
		System.out.println("2:"+(start-end));
		start = end;
		Set<Integer> categoryids = new HashSet<Integer>();
		
		Map<Integer,List<Integer>> labels = new LinkedHashMap <Integer,List<Integer>>();
		for(StatLabel sl:statlabels)
		{
			categoryids.add(sl.getCategoryid());
			int newid = sl.getNewsid();
			if(labels.containsKey(newid))
			{
				labels.get(newid).add(sl.getCategoryid());
			}
			else
			{
				List<Integer> cids = new ArrayList<Integer>();
				cids.add(sl.getCategoryid());
				labels.put(newid,cids);
			}
		}

		
		List<StatCategory> categories = new ArrayList<StatCategory>();
		if(categoryids.size()>0)
			categories = statCategoryServiceImpl.findCategories(categoryids);
		end = System.currentTimeMillis();
		System.out.println("3:"+(start-end));
		start = end;
		Map<Integer,StatCategory> categorymapper = new HashMap<Integer,StatCategory>();
		for(StatCategory sc :categories)
		{
			categorymapper.put(sc.getId(), sc);
		}
	
		for(News nk :newsmapper)
		{
			JSONArray ls = new JSONArray();
			List<Integer> ll = labels.get(nk.getId());
			if(ll!=null)
			for(Integer k :ll)
			{
				ls.add(categorymapper.get(k));
			}
			JSONObject object = JSONObject.parseObject(nk.toJSONString());
			try{
				Object pubtime = object.get("pubtime");
				if(pubtime!=null)
				{
					long time = Long.parseLong(pubtime.toString());
					Date date = new Date(time);
					SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
					object.put("pubtime",df.format(date));
				}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
			object.put("labels", ls);
			data.add(object);
		}
		
		end = System.currentTimeMillis();
		System.out.println("4:"+(start-end));
		start = end;
		
		result.put("data", data);
		
		result.put("count", page_count);
		result.put("index", courrent_index);
		
	
		return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		json.put("a", "json");
		System.out.println(json);
		JSONArray arr = new JSONArray();
		json.put("arr",arr);
		System.out.println(json);
	}
	
}
