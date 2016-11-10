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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnic.datapub.n.model.Batch;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.model.SubJob;
import cn.cnic.datapub.n.serviceimpl.BatchServiceImpl;
import cn.cnic.datapub.n.serviceimpl.JobServiceImpl;
import cn.cnic.datapub.n.serviceimpl.SubJobServiceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/status")
public class StatusController
{
	
	@Resource
	BatchServiceImpl batchServiceImpl;
	@Resource
	JobServiceImpl jobServiceImpl;
	@Resource
	SubJobServiceImpl subJobServiceImpl;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getStatus()
	{
		return getStatus(0);
	}
	
	@RequestMapping(value = "/list/{pid}",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	public String getStatus(@PathVariable("pid") int pid)
	{
		int pagesize = 10;
		
		int csum = batchServiceImpl.countAll();
		int page_count=csum/pagesize+(csum%pagesize==0?0:1);
		
		
		List<Batch> batches = batchServiceImpl.list(pid, pagesize);
		Set<Integer> jids = new HashSet<Integer>();
		Set<Integer> sids = new HashSet<Integer>();
		
		for(Batch batch : batches)
		{
			jids.add(batch.getJobid());
			sids.add(batch.getSubjobid());
		}
		
		
		List<Job> jobs = jobServiceImpl.findByIds(jids);
		for(Job job:jobs)
		{
			System.out.println(job.toJSONString());
		}
		List<SubJob> subjobs = subJobServiceImpl.findByIds(sids);
		System.err.println(jobs.size()+"："+subjobs.size());
		
		Map<Integer,Job> job_mapping = new HashMap<Integer,Job>();
		Map<Integer,SubJob> subjob_mapping = new HashMap<Integer,SubJob>();
		
		for(Job j: jobs)
		{
			job_mapping.put(j.getId(), j);
		}
		
		for(SubJob s: subjobs)
		{
			subjob_mapping.put(s.getId(), s);
		}
		
		
		JSONArray resultlist = new JSONArray();
		for(Batch batch : batches)
		{
			Job j = job_mapping.get(batch.getJobid());
			SubJob s = subjob_mapping.get(batch.getSubjobid());
			
			String name = j.getName()+"-"+s.getName();
			String type;
			if(j.getPlan().startsWith("intr"))
			{
				type="周期采集";
			}	
			else if(j.getPlan().startsWith("cron"))
			{
				type="定时采集";
			}
			else if(j.getPlan().startsWith("time"))
			{
				type="定点采集";
			}
			else
			{
				type="其他采集";
			}
			
			Date dendtime = batch.getEndtime();
			String endtime = null;
			if(dendtime!=null)
			{
				SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
				endtime = df.format(dendtime);
			}
			Date dstarttime = batch.getStarttime();
			String starttime = null;
			if(dstarttime!=null)
			{
				SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
				starttime = df.format(dstarttime);
			}
			int status = batch.getStatus();
			String result_text = null;
			switch(status)
			{
				case 1: result_text = "正常结束";break;
				case 2: result_text = "采集失败";break;
				case 3: result_text = "解析失败";break;
				case 4: result_text = "部分采集失败";break;
				case 5: result_text = "部分解析失败";break;
				default :break;
			}
			int sum = batch.getPsum();
			int success = batch.getSsum();
			int fail = batch.getFsum();
			int same = batch.getAsum();
			
			JSONObject result = new JSONObject();
			result.put("name", name);
			result.put("type", type);
			result.put("endtime", endtime);
			result.put("starttime", starttime);
			result.put("result", result_text);
			result.put("sum", sum);
			result.put("success", success);
			result.put("fail", fail);
			result.put("same", same);
			
			resultlist.add(result);
		}
		
		JSONObject result = new JSONObject();
		result.put("count", page_count);
		result.put("index",pid );
		result.put("data", resultlist);
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
