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

import cn.cnic.datapub.n.model.DictionaryItem;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.model.SubJob;
import cn.cnic.datapub.n.serviceimpl.DictionaryItemServiceImpl;
import cn.cnic.datapub.n.serviceimpl.JobServiceImpl;
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
		result.put("category", category);
		result.put("status", status);
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
		List<Job> jobs = jobServiceImpl.list(pid, pagesize);
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
			element.put("name", job.getName());
			element.put("plan", job.getPlan());
			System.err.println("job_category:"+job.getCategory()+",mapping:"+category_mapping.get(job.getCategory()).getName());
			element.put("category", category_mapping.get(job.getCategory()).getName());
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
			String createtime = df.format(job.getCreatetime());
			element.put("createtime", createtime);
			Date lastmodifytime = job.getLastmodifytime();
			if(lastmodifytime!=null)
			{
				element.put("lastmodifytime", df.format(lastmodifytime));
			}
			element.put("status", job.getStatus());
			array.add(element);
		}
		return array.toJSONString();
		
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
		result.put("job", job.toJSONString());
		JSONArray jsubjobs = new JSONArray();
		for(SubJob sj:subjobs)
		{
			jsubjobs.add(sj);
		}
		result.put("subjobs", jsubjobs);
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
