package cn.cnic.datapub.n.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnic.datapub.n.model.News;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/system")
public class SystemController
{
	
	@RequestMapping(method = RequestMethod.GET)
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
		
	}
	
}
