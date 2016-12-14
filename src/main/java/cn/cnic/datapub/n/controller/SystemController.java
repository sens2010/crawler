package cn.cnic.datapub.n.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnic.datapub.n.model.News;
import cn.cnic.datapub.n.model.StatCategory;
import cn.cnic.datapub.n.serviceimpl.StatCategoryServiceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/system")
public class SystemController
{
	@Resource
	StatCategoryServiceImpl statCategoryServiceImpl;
	
	@RequestMapping(value = "/statcategory",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getCategory()
	{
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		List<StatCategory> lists = statCategoryServiceImpl.findAll();
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		for(StatCategory sc : lists)
		{
			Date createtime = sc.getCreatetime();
			Date lastmodifytime = sc.getLastmodifytime();
			
			JSONObject element = JSONObject.parseObject(sc.toJSONString());
			if(element.containsKey("lastmodifytime"))
			{
				element.put("lastmodifytime", df.format(lastmodifytime));
			}
			element.put("createtime", df.format(createtime));
			data.add(element);
		}
		
		result.put("code", 200);
		result.put("data",data);
		return result.toJSONString();
	}
	
	@RequestMapping(value = "/statcategory",method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String addCategory(@RequestBody String params)
	{
		JSONObject element = JSONObject.parseObject(params);
		String name = element.getString("name");
		String description = element.getString("description");
		int parent = element.getInteger("parent");
		String code ="";
		if(parent!=-1)
		{
			StatCategory sc = statCategoryServiceImpl.findStatCategoryById(parent);
			code = sc.getCode();
			while(code.endsWith("00"))code = code.substring(0,code.length()-2);
		}
		else
		{
			code = "";
		}
		
		StatCategory last = statCategoryServiceImpl.matchLastOne(code+"%");
		String flag = last.getCode().substring(code.length(), code.length()+2);
		
		flag = String.format("%02d",(Integer.parseInt(flag)+1));
		code = code+flag;
		int appendex= 10-code.length();
		for(int i=appendex;i>0;i--)
		{
			code+="0";
		}
		
		StatCategory newsc = new StatCategory();
		newsc.setCode(code);
		newsc.setName(name);
		newsc.setDescription(description);
		newsc.setCreatetime(new Date());
		newsc.setStatus(1);
		newsc = statCategoryServiceImpl.addStatCategory(newsc);
		return newsc.toJSONString();
	}
	
	@RequestMapping(value = "/statcategory/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String updateCategory(@PathVariable("id") int id,@RequestBody String element)
	{
		StatCategory statcategory = statCategoryServiceImpl.findStatCategoryById(id);
		statcategory.setLastmodifytime(new Date());
		
		JSONObject jelement = JSONObject.parseObject(element);
		if(jelement.containsKey("code"))
		{
			statcategory.setCode(jelement.getString("code"));
		}
		if(jelement.containsKey("name"))
		{
			statcategory.setName(jelement.getString("name"));
		}
		if(jelement.containsKey("description"))
		{
			statcategory.setDescription(jelement.getString("description"));
		}
		
		statcategory = statCategoryServiceImpl.addStatCategory(statcategory);
		return statcategory.toJSONString();
	}
	
	@RequestMapping(value = "/statcategory/{id}",method = RequestMethod.DELETE, produces = "application/json;charset=UTF8")
	public String deleteCategory(@PathVariable("id") int id)
	{
		JSONObject result = new JSONObject();
		
		statCategoryServiceImpl.deleteStatCategory(id);
		result.put("code", 200);
		result.put("msg", "删除成功！");
		
		return result.toJSONString();
	}
	
	@RequestMapping(value = "/statcategory/{id}",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getCategoryById(@PathVariable("id") int id)
	{
		JSONObject result = new JSONObject();
		
		StatCategory statcategory = statCategoryServiceImpl.findStatCategoryById(id);
		if(statcategory!=null)
		{
			result.put("code", 200);
			result.put("msg", "获取成功！");
			
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Date createtime = statcategory.getCreatetime();
			Date lastmodifytime = statcategory.getLastmodifytime();
			
			JSONObject element = JSONObject.parseObject(statcategory.toJSONString());
			if(element.containsKey("lastmodifytime"))
			{
				element.put("lastmodifytime", df.format(lastmodifytime));
			}
			element.put("createtime", df.format(createtime));
			result.put("data",element);
			
		}
		else
		{
			result.put("code", 404);
			result.put("msg", "未找到标签！");
		}
			
		return result.toJSONString();
	}
	
	
	@RequestMapping(value = "/statcategory/tree",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getCategoryTree()
	{
		JSONArray tree = new JSONArray();
		
		JSONArray array = JSONObject.parseObject(getCategory()).getJSONArray("data");
		
		Map<String,String> ancestors = new HashMap<String,String>();
		for(int i=0;i<array.size();i++)
		{
			JSONObject element = array.getJSONObject(i);
			JSONObject e = new JSONObject();
			String code = element.getString("code");
			while(code.endsWith("00"))
			{
				code=code.substring(0,code.length()-2);
			}
			if(ancestors.containsKey(code.subSequence(0, code.length()-2)))
			{
				e.put("parent", ancestors.get(code.subSequence(0, code.length()-2)));
				e.put("id", element.get("id")+"");
				e.put("text", element.get("name"));
			}
			else
			{
				e.put("parent", "#");
				e.put("id", element.get("id")+"");
				e.put("text", element.get("name"));
				ancestors.put(code, element.get("id")+"");
			}
			tree.add(e);
		}
		return tree.toJSONString();
	}
	
	@RequestMapping(value ="/", method=RequestMethod.GET)
	public String getSysteminfo()
	{
		JSONObject result = new JSONObject();
		
		News news = new News();
		news.setId(1);
		news.setLastmodifytime(new Date());
		news.setRelatebatch(1);
		news.setTitle("1234");
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(news);
		news = new News();
		news.setId(2);
		news.setLastmodifytime(new Date());
		news.setRelatebatch(1);
		news.setTitle("12345");
		jsonArray.add(news);
		result.put("list", jsonArray);
		
		return result.toJSONString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String code = "02010000";
		System.out.println(code.substring(0, code.length()-2));
	}
	
}
