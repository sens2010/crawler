package cn.cnic.datapub.n.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnic.datapub.n.model.StatCategory;
import cn.cnic.datapub.n.serviceimpl.StatCategoryServiceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/labels")
public class LabelController
{
	@Resource
	StatCategoryServiceImpl statCategoryServiceImpl;
	
	@RequestMapping(value="/list",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
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
	
	@RequestMapping(value="/",method = RequestMethod.POST, produces= "application/json;charset=UTF8")
	public String addLabel(@RequestBody StatCategory statcategory )
	{
		JSONObject result = new JSONObject();
		
		statcategory.setCreatetime(new Date());
		statcategory.setStatus(1);
		statcategory = statCategoryServiceImpl.addStatCategory(statcategory);
		
		result.put("data", statcategory);
		result.put("code", 200);
		result.put("msg", "添加成功！");
		
		return result.toJSONString();
		
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String updateLabel(@PathVariable("id") int id, @RequestBody String element)
	{
		JSONObject result = new JSONObject();
		
		JSONObject jelement = JSONObject.parseObject(element);
		StatCategory statcategory = statCategoryServiceImpl.findStatCategoryById(id);
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
		
		statcategory.setLastmodifytime(new Date());
		
		result.put("code", 200);
		result.put("msg", "修改成功！");
		
		result.put("data", statcategory);
		return result.toJSONString();
		
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE, produces = "application/json;charset=UTF8")
	public String deleteLabel(@PathVariable("id") int id)
	{
		JSONObject result = new JSONObject();
		
		boolean issuccess = statCategoryServiceImpl.deleteStatCategory(id);
		if(issuccess)
		{
			result.put("code", 200);
			result.put("msg", "删除成功！");
		}
		else
		{
			result.put("code", 401);
			result.put("msg", "删除失败！");
		}
		
		return result.toJSONString();
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
