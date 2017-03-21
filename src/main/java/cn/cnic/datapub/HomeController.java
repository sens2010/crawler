package cn.cnic.datapub;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.cnic.datapub.schedule.SchedulePool;

/**
 * Handles requests for the application home page.
 */



@SuppressWarnings("deprecation")
@Controller
public class HomeController
{
	
	@Resource
	ElasticsearchTemplate elasticsearchTemplate;
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	
	/*
	 * @Resource JdbcTemplate jdbcTemplate;
	 * 
	 * @Resource SolrTemplate solrTemplate;
	 * 
	 * @Resource AmqpTemplate amqpTemplate;
	 */
	
	//@Resource
	@Deprecated
	SchedulePool schedulePool;
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin()
	{
		return "admin";
	}
	
	
	@RequestMapping(value="/testes",method = RequestMethod.GET, produces = "application/json;charset=UTF8")
	@ResponseBody
	public String testES()
	{
		JSONObject result = new JSONObject();
		result.put("test", "testes");
		
		result.put("test", elasticsearchTemplate);
		
		return result.toJSONString();
	}
	
	
	@RequestMapping(value="/status", method = RequestMethod.GET)
	public String status()
	{
		return "status";
	}
	
	@RequestMapping(value="/job", method = RequestMethod.GET)
	public String job()
	{
		return "job";
	}
	
	@RequestMapping(value="/system", method = RequestMethod.GET)
	public String system()
	{
		return "system";
	}
	
	@RequestMapping(value="/display", method = RequestMethod.GET)
	public String display()
	{
		return "display";
	}
	
	@RequestMapping(value="/analysor", method = RequestMethod.GET)
	public String analysor()
	{
		return "analysor";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model)
	{
		logger.info("access home!");
		
		// System.out.println(jdbcTemplate.queryForInt("select count(1) from STAT_TYPE"));
		
		// System.out.println(solrTemplate==null);
		
		// Query query = new SimpleQuery();
		
		// SimpleStringCriteria ssc = new SimpleStringCriteria("q=B*");
		
		// query.addCriteria(ssc);
		/*
		 * query. query.set("q", "中国科学院计算机网络信息中心");
			 */
		// query.setRows(20);
		long start = System.currentTimeMillis();
		
		// System.out.println(solrTemplate.count(query));
		
		/*
		 * start = System.currentTimeMillis(); SolrInputDocument sid = new
		 * SolrInputDocument(); sid.addField("id", 70); sid.addField("FieldA",
		 * "中华民国"); sid.addField("FieldB", "时间"); sid.addField("FieldC", "BAC");
		 * sid.addField("FieldD", "ABC");
		 * 
		 * solrTemplate.saveDocument(sid); solrTemplate.commit();
		 * System.out.println("solr:"+(System.currentTimeMillis()-start));
		 * 
		 * start = System.currentTimeMillis();
		 * 
		 * 
		 * for(int i=0;i<10;i++) amqpTemplate.convertAndSend("myqueue0",
		 * "foo"+i);
		 * 
		 * for(int i=0;i<10;i++) amqpTemplate.convertAndSend("myqueue1",
		 * "1foo"+i);
		 * 
		 * for(int i=0;i<10;i++) amqpTemplate.convertAndSend("myqueue2",
		 * "2foo"+i);
		 * 
		 * for(int i=0;i<20;i++) { String foo = (String)
		 * amqpTemplate.receiveAndConvert("myqueue0"); System.out.println(foo);
		 * foo = (String) amqpTemplate.receiveAndConvert("myqueue1");
		 * System.out.println(foo); foo = (String)
		 * amqpTemplate.receiveAndConvert("myqueue2"); System.out.println(foo);
		 * }
		 */
		System.out.println("rabbitmq:" + (System.currentTimeMillis() - start));
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate);
		
		return "home";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test()
	{
		return "test";
	}
	
	
//	
//	@RequestMapping(value = "/scheduler", method = RequestMethod.GET)
//	public String Schedule()
//	{
//		System.err.println(schedulePool == null);
//		Scheduler scheduler;
//		try
//		{
//			scheduler = StdSchedulerFactory.getDefaultScheduler();
//			scheduler.start();
//			
//			Random rand = new Random();
//			
//			String id = rand.nextInt() + "";
//			String name = rand.nextInt() + "";
//			
//			JobDetail job = newJob(HelloJob.class).withIdentity(id, name)
//					.usingJobData("name", rand.nextInt() + "").build();
//			System.out.println("id:" + job.getKey().getName() + ",name:"
//					+ job.getKey().getGroup());
//			
//			Trigger trigger = newTrigger()
//					.withIdentity(triggerKey(id, name))
//					.withSchedule(
//							simpleSchedule().withIntervalInSeconds(2)
//									.repeatForever())
//					.startAt(futureDate(10, SECOND)).build();
//			scheduler.scheduleJob(job, trigger);
//			String schedulereid = rand.nextInt() + "";
//			schedulePool.addScheduler(schedulereid, scheduler);
//			System.out.println("schedulePool:" + schedulereid);
//			
//		} catch (SchedulerException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "home";
//	}
//	
//	@RequestMapping(value = "/stopscheduler/{id}/{name}", method = RequestMethod.GET)
//	public String stopSchedule(@PathVariable("id") String id,
//			@PathVariable("name") String name)
//	{
//		System.out.println("sid:" + id + ",sname:" + name);
//		Map<String, Scheduler> schedulerstore = schedulePool.getSchedulestore();
//		System.out.println("schedulerstore.size:" + schedulerstore.size()
//				+ ",value:" + schedulerstore.values().size());
//		
//		Scheduler scheduler = schedulerstore.values().iterator().next();
//		try
//		{
//			JobKey jk = new JobKey(id, name);
//			scheduler.deleteJob(jk);
//		} catch (SchedulerException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "home";
//	}
//	
//	@RequestMapping(value = "/changescheduler/{id}/{name}", method = RequestMethod.GET)
//	public String changeSchedule(@PathVariable("id") String id,
//			@PathVariable("name") String name)
//	{
//		Map<String, Scheduler> schedulerstore = schedulePool.getSchedulestore();
//		Scheduler scheduler = schedulerstore.values().iterator().next();
//		TriggerKey tk = new TriggerKey(id, name);
//		try
//		{
//			
//			Trigger trigger = newTrigger()
//					.withIdentity(triggerKey(id, name))
//					.withSchedule(
//							simpleSchedule().withIntervalInSeconds(1)
//									.repeatForever())
//					.startAt(futureDate(10, SECOND)).build();
//			scheduler.rescheduleJob(tk, trigger);
//			
//		} catch (SchedulerException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "home";
//	}
	
}
