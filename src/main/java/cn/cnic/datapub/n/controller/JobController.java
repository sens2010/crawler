package cn.cnic.datapub.n.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnic.datapub.n.model.DictionaryItem;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.model.SubJob;
import cn.cnic.datapub.n.serviceimpl.DictionaryItemServiceImpl;
import cn.cnic.datapub.n.serviceimpl.JobServiceImpl;
import cn.cnic.datapub.n.serviceimpl.ParserServiceImpl;
import cn.cnic.datapub.n.serviceimpl.SubJobServiceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/job")
public class JobController
{
	@Resource
	JobServiceImpl jobServiceImpl;
	@Resource
	SubJobServiceImpl subJobServiceImpl;
	@Resource
	DictionaryItemServiceImpl dictionaryItemServiceImpl;
	@Resource
	ParserServiceImpl parserServiceImpl;
	
	@RequestMapping(value = "/metadata",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getJobMetaData()
	{	
		JSONObject result = new JSONObject();
		List<DictionaryItem> category_types = dictionaryItemServiceImpl.findCodes(2);
		JSONArray category = new JSONArray();
		for(DictionaryItem di:category_types)
		{
			category.add(di);
		}
		List<DictionaryItem> job_status = dictionaryItemServiceImpl.findCodes(3);
		JSONArray status = new JSONArray();
		for(DictionaryItem di:job_status)
		{
			status.add(di);
		}
		
		List<DictionaryItem> subcategory_types = dictionaryItemServiceImpl.findCodes(5);
		JSONArray subcategory = new JSONArray();
		for(DictionaryItem di:subcategory_types)
		{
			subcategory.add(di);
		}
		
		List<DictionaryItem> subjob_status = dictionaryItemServiceImpl.findCodes(4);
		JSONArray substatus = new JSONArray();
		for(DictionaryItem di:subjob_status)
		{
			substatus.add(di);
		}
		
		result.put("category", category);
		result.put("status", status);
		result.put("subcategory", subcategory);
		result.put("substatus", substatus);
		
		return result.toJSONString();
	}
	
	@RequestMapping(value = "/submetadata",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getSubJobMetaData()
	{	
		JSONObject result = JSONObject.parseObject(getJobMetaData());
		List<Parser> parsers = parserServiceImpl.findAll();
		JSONArray jparsers = new JSONArray();
		for(Parser parser:parsers)
		{
			jparsers.add(parser);
		}
		result.put("parser", jparsers);
		return result.toJSONString();
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getJobs()
	{	
		return getJobs(0);
	}
	
	@RequestMapping(value = "/list/{pid}",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getJobs(@PathVariable("pid") int pid)
	{
		int pagesize = 10;
		int sum = jobServiceImpl.countAll();
		int page_count=sum/pagesize+(sum%pagesize==0?0:1);
		int courrent_index = pid;
		List<Job> jobs = jobServiceImpl.list(pid, pagesize);
		
		JSONObject status = JSONObject.parseObject(jobServiceImpl.getJobStatus());
		System.err.println(status.toJSONString());
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		List<DictionaryItem> category_types = dictionaryItemServiceImpl.findCodes(2);
		System.err.println(category_types.size());
		Map<Integer,DictionaryItem> category_mapping = new HashMap<Integer,DictionaryItem>();
		for(DictionaryItem dict_item :category_types)
		{
			category_mapping.put(dict_item.getInnerid(), dict_item);
		}
		List<DictionaryItem> job_status = dictionaryItemServiceImpl.findCodes(3);
		Map<Integer,DictionaryItem> status_mapping = new HashMap<Integer,DictionaryItem>();
		for(DictionaryItem dict_item :job_status)
		{
			status_mapping.put(dict_item.getId(), dict_item);
		}
		
		
		for(Job job:jobs)
		{
			JSONObject element = new JSONObject();
			element.put("id", job.getId());
			element.put("name", job.getName());
			element.put("plan", job.getPlan());
			element.put("status", status.getString(job.getId()+""));
			System.err.println("job_category:"+job.getCategory()+",mapping:"+category_mapping.get(job.getCategory()).getName());
			element.put("category", category_mapping.get(job.getCategory()).getName());
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String createtime = df.format(job.getCreatetime());
			element.put("createtime", createtime);
			Date lastmodifytime = job.getLastmodifytime();
			if(lastmodifytime!=null)
			{
				element.put("lastmodifytime", df.format(lastmodifytime));
			}
			//element.put("status", job.getStatus());
			array.add(element);
		}
		
		result.put("data", array);
		result.put("index", courrent_index);
		result.put("count", page_count);
		
		return result.toJSONString();
		
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String addJob(@RequestBody Job element)
	{
		
		if(jobServiceImpl.getJobById(element.getId()) != null)
		{
			element.setLastmodifytime(new Date());
			element.setStatus(1);
			element = jobServiceImpl.updateJob(element);
		}
		else
		{
			element.setCreatetime(new Date());
			element.setStatus(1);
			element.setLastmodifytime(null);
 			element = jobServiceImpl.addJob(element);
		}
		return element.toJSONString();
	}
	
	@RequestMapping(value="/subjob/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getSubJob(@PathVariable("id") int id)
	{
		JSONObject result = new JSONObject();
		SubJob subjob = subJobServiceImpl.getSubJobById(id);
		String metadata = getSubJobMetaData();
		
		result.put("subjob", subjob);
		result.put("metadata", JSONObject.parseObject(metadata));
		
		return result.toJSONString();
	}
	
	
	@RequestMapping(value="/subjob", method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String addSubJob(@RequestBody SubJob element)
	{
		if(subJobServiceImpl.getSubJobById(element.getId()) != null)
		{
			element.setLastmodifytime(new Date());
			element.setStatus(1);
			element = subJobServiceImpl.updateSubJob(element);
		}
		else
		{
			element.setCreatetime(new Date());
			element.setStatus(1);
			element.setLastmodifytime(null);
			element.setInterval(1);
			System.out.println(element.toJSONString());
 			element = subJobServiceImpl.addSubJob(element);
		}
		return element.toJSONString();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getJob(@PathVariable("id") int id)
	{
		
		Job job = jobServiceImpl.getJobById(id);
		if(job!=null)
		{
			return job.toJSONString();
		}
		else
		{
			return null;
		}
	}
	
	@RequestMapping(value="/f/{id}",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getJobWithSubJob(@PathVariable("id") int id)
	{
		
		JSONObject result = new JSONObject();
		Job job = jobServiceImpl.getJobById(id);
		List<SubJob> subjobs = subJobServiceImpl.findByJobId(id);
		JSONObject subjobstatus = JSONObject.parseObject(subJobServiceImpl.getSubJobStatus());
		result.put("job", job);
		JSONArray jsubjobs = new JSONArray();
		
		Set<Integer> parserids = new HashSet<Integer>();
		
		for(SubJob sj:subjobs)
		{
			JSONObject jsj = JSONObject.parseObject(sj.toJSONString());
			
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String createtime = df.format(sj.getCreatetime());
			jsj.put("sstatus",subjobstatus.get(sj.getId()+""));
			jsj.put("createtime", createtime);
			jsubjobs.add(jsj);
			parserids.add(sj.getParserid());
		}
		
		List<Parser> parsers = parserServiceImpl.findAll();
		JSONObject jparsers = new JSONObject();
		for(Parser parser:parsers)
		{
			jparsers.put(parser.getId()+"", parser);
		}
		
		String metadata = getJobMetaData();
		
		result.put("subjobs", jsubjobs);
		result.put("metadata", JSONObject.parseObject(metadata));
		result.put("parsers", jparsers);
		
		return result.toJSONString();
	}
	
	@RequestMapping(value="/jstat/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String changeJobStatus(@PathVariable("id") int id, @RequestBody String element)
	{
		String result = null;
		JSONObject jelement = JSONObject.parseObject(element);
		
		String operation = jelement.getString("operation");
		if(operation.equals("start"))
		{
			return jobServiceImpl.startJob(id);
		}
		else if(operation.equals("stop"))
		{
			return jobServiceImpl.stopJob(id);	
		}
		else if(operation.equals("restart"))
		{
			return jobServiceImpl.restartJob(id);	
		}
		else
		{
			
		}
		
		return result;
	}
	
	@RequestMapping(value="/sjstat/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String changeSubJobStatus(@PathVariable("id") int id, @RequestBody String element)
	{
		String result = null;
		JSONObject jelement = JSONObject.parseObject(element);
		
		String operation = jelement.getString("operation");
		if(operation.equals("start"))
		{
			return subJobServiceImpl.startSubJob(id);
		}
		else if(operation.equals("stop"))
		{
			return subJobServiceImpl.stopSubJob(id);	
		}
		else if(operation.equals("restart"))
		{
			return subJobServiceImpl.restartSubJob(id);	
		}
		else
		{
			
		}
		
		return result;
	}
	
	@RequestMapping(value="/subjob/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String changeSubJob(@PathVariable("id") int id,@RequestBody String element)
	{
		SubJob subjob = subJobServiceImpl.getSubJobById(id);
		JSONObject jelement = JSONObject.parseObject(element);
		for(String key:jelement.keySet())
		{
			if(key.equals("name"))
			{
				subjob.setName(jelement.getString(key));
			}
			else if(key.equals("url"))
			{
				subjob.setUrl(jelement.getString(key));
			}
			else if(key.equals("parserid"))
			{
				subjob.setParserid(jelement.getInteger(key));
			}
			else if(key.equals("category"))
			{
				subjob.setCategory(jelement.getInteger(key));
			}
			else if(key.equals("description"))
			{
				subjob.setDescription(jelement.getString(key));
			}
			subjob.setLastmodifytime(new Date());
		}
		subjob = subJobServiceImpl.updateSubJob(subjob);
		return subjob.toJSONString();
	}
	
	
	
	@RequestMapping(value="/{id}",method = RequestMethod.PUT, produces = "application/json;charset=UTF8")
	public String changeJob(@PathVariable("id") int id,@RequestBody String element)
	{
		
		Job job = jobServiceImpl.getJobById(id);
		JSONObject jelement = JSONObject.parseObject(element);
		for(String key:jelement.keySet())
		{
			if(key.equals("name"))
			{
				job.setName(jelement.getString("name"));
			}
			else if(key.equals("plan"))
			{
				job.setPlan(jelement.getString("plan"));
			}
			else if(key.equals("category"))
			{
				job.setCategory(jelement.getIntValue("category"));
			}
			else if(key.equals("status"))
			{
				job.setStatus(jelement.getIntValue("status"));
			}
			else if(key.equals("description"))
			{
				job.setDescription(jelement.getString("description"));
			}
			job.setLastmodifytime(new Date());
		}
		job = jobServiceImpl.updateJob(job);
		return job.toJSONString();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE, produces = "application/json;charset=UTF8")
	public String deleteJob(@PathVariable("id") int id)
	{
		if(jobServiceImpl.getJobById(id)!=null)
		{
			Job job = jobServiceImpl.getJobById(id);
			jobServiceImpl.deleteById(id);
			return job.toJSONString();
		}
		else
		{
			return null;
		}
	}
	
	@RequestMapping(value="/subjob/{id}",method = RequestMethod.DELETE, produces = "application/json;charset=UTF8")
	public String deleteSubJob(@PathVariable("id") int id)
	{
		if(subJobServiceImpl.getSubJobById(id)!=null)
		{
			SubJob subjob = subJobServiceImpl.getSubJobById(id);
			subJobServiceImpl.deleteById(id);
			return subjob.toJSONString();
		}
		else
		{
			return null;
		}
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.POST, produces = "application/json;charset=UTF8")
	public String changeJob(@PathVariable("id") int id)
	{
	
		//包含start，pause，restart
		JSONObject result = new JSONObject();
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
