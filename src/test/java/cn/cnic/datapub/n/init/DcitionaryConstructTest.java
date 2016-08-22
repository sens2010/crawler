package cn.cnic.datapub.n.init;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.Dictionary;
import cn.cnic.datapub.n.model.DictionaryItem;
import cn.cnic.datapub.n.model.Job;
import cn.cnic.datapub.n.model.Parser;
import cn.cnic.datapub.n.model.SubJob;
import cn.cnic.datapub.n.serviceimpl.DictionaryItemServiceImpl;
import cn.cnic.datapub.n.serviceimpl.DictionaryServiceImpl;
import cn.cnic.datapub.n.serviceimpl.JobServiceImpl;
import cn.cnic.datapub.n.serviceimpl.ParserServiceImpl;
import cn.cnic.datapub.n.serviceimpl.SubJobServiceImpl;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DcitionaryConstructTest
{
	
	Logger logger = LoggerFactory.getLogger(DcitionaryConstructTest.class);
	
	@Resource
	DictionaryItemServiceImpl dictionaryItemServiceImpl;
	@Resource
	DictionaryServiceImpl dictionaryServiceImpl;
	@Resource
	JobServiceImpl jobServiceImpl;
	@Resource
	SubJobServiceImpl subJobServiceImpl;
	@Resource
	ParserServiceImpl parserServiceImpl;
	
	@Test
	public void partInit()
	{
		
	}
	
	//@Test
	public void dictInit()
	{
		List<Dictionary> dicts = new ArrayList<Dictionary>();
		dicts.add((new Dictionary()).setCategory(2).setName("网站类别").setStatus(1).setCreatetime(new Date()));
		dicts.add((new Dictionary()).setCategory(2).setName("任务状态").setStatus(1).setCreatetime(new Date()));
		dicts.add((new Dictionary()).setCategory(2).setName("子任务状态").setStatus(1).setCreatetime(new Date()));
		dicts.add((new Dictionary()).setCategory(2).setName("子任务类别").setStatus(1).setCreatetime(new Date()));
		dicts.add((new Dictionary()).setCategory(2).setName("转换器状态").setStatus(1).setCreatetime(new Date()));
		dicts.add((new Dictionary()).setCategory(3).setName("监控结果").setStatus(1).setCreatetime(new Date()));
		dicts.add((new Dictionary()).setCategory(3).setName("采集状态").setStatus(1).setCreatetime(new Date()));
		dicts.add((new Dictionary()).setCategory(3).setName("采集结果").setStatus(1).setCreatetime(new Date()));
		List<DictionaryItem> dict_items = new ArrayList<DictionaryItem>();
		//表类型
		dict_items.add((new DictionaryItem()).setName("基础表").setInnerid(1).setStatus(1).setDictionaryid(1).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("任务").setInnerid(2).setStatus(1).setDictionaryid(1).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("采集结果").setInnerid(3).setStatus(1).setDictionaryid(1).setDescription("").setCreatetime(new Date()));
		//网站类别
		dict_items.add((new DictionaryItem()).setName("财经门户").setInnerid(1).setStatus(1).setDictionaryid(2).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("国家财经网站").setInnerid(2).setStatus(1).setDictionaryid(2).setDescription("").setCreatetime(new Date()));
		//任务状态
		dict_items.add((new DictionaryItem()).setName("正常").setInnerid(1).setStatus(1).setDictionaryid(3).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("运行").setInnerid(2).setStatus(1).setDictionaryid(3).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("停用").setInnerid(3).setStatus(1).setDictionaryid(3).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("删除").setInnerid(4).setStatus(1).setDictionaryid(3).setDescription("").setCreatetime(new Date()));
		//子任务状态
		dict_items.add((new DictionaryItem()).setName("正常").setInnerid(1).setStatus(1).setDictionaryid(4).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("运行").setInnerid(2).setStatus(1).setDictionaryid(4).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("停用").setInnerid(3).setStatus(1).setDictionaryid(4).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("删除").setInnerid(4).setStatus(1).setDictionaryid(4).setDescription("").setCreatetime(new Date()));
		//子任务类别
		dict_items.add((new DictionaryItem()).setName("暂无分类").setInnerid(1).setStatus(1).setDictionaryid(5).setDescription("").setCreatetime(new Date()));
		//转换器状态
		dict_items.add((new DictionaryItem()).setName("启用").setInnerid(1).setStatus(1).setDictionaryid(6).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("停用").setInnerid(2).setStatus(1).setDictionaryid(6).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("删除").setInnerid(3).setStatus(1).setDictionaryid(6).setDescription("").setCreatetime(new Date()));
		//监控结果
		dict_items.add((new DictionaryItem()).setName("正常结束").setInnerid(1).setStatus(1).setDictionaryid(7).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("采集失败").setInnerid(2).setStatus(1).setDictionaryid(7).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("解析失败").setInnerid(3).setStatus(1).setDictionaryid(7).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("部分采集失败").setInnerid(4).setStatus(1).setDictionaryid(7).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("部分解析失败").setInnerid(5).setStatus(1).setDictionaryid(7).setDescription("").setCreatetime(new Date()));
		//采集状态
		dict_items.add((new DictionaryItem()).setName("运行").setInnerid(1).setStatus(1).setDictionaryid(8).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("结束").setInnerid(2).setStatus(1).setDictionaryid(8).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("异常").setInnerid(3).setStatus(1).setDictionaryid(8).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("被删除").setInnerid(4).setStatus(1).setDictionaryid(8).setDescription("").setCreatetime(new Date()));
				
		//采集结果
		dict_items.add((new DictionaryItem()).setName("采集中").setInnerid(1).setStatus(1).setDictionaryid(9).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("成功").setInnerid(2).setStatus(1).setDictionaryid(9).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("失败").setInnerid(3).setStatus(1).setDictionaryid(9).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("存在问题").setInnerid(4).setStatus(1).setDictionaryid(9).setDescription("").setCreatetime(new Date()));
		dict_items.add((new DictionaryItem()).setName("被删除").setInnerid(5).setStatus(1).setDictionaryid(9).setDescription("").setCreatetime(new Date()));
		
		
		for(Dictionary dict:dicts)
		{
			logger.info("add dictionary:"+dictionaryServiceImpl.addDictionary(dict));
		}
		for(DictionaryItem dict_item:dict_items)
		{
			logger.info("add dictionary item:"+dictionaryItemServiceImpl.addDictionaryItem(dict_item));
		}
	}
	
	//@Test
	public void jobInit()
	{
		Job job;
		SubJob subjob;
		Parser parser;
		//Sohu
		job= new Job();
		job= job.setName("搜狐财经").setPlan("intr:2h").setCategory(1).setDescription("").setStatus(1).setCreatetime(new Date());
		jobServiceImpl.addJob(job);
		System.out.println(job.toJSONString());
		jobServiceImpl.addJob(job);
		System.out.println(job.toJSONString());
		//sohu宏观经济
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a")
				.setNextparser("//*/div[@class='pages']/p/table/tbody/tr/td/a[3]")
				.setTitleparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1")
				.setTimeparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']")
				.setSourceparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span")
				.setTextparser("//*/div[@itemprop='articleBody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("宏观经济")
				.setUrl("http://business.sohu.com/hgjj/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//sohu金融政策/监管
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a")
				.setNextparser("//*/div[@class='pages']/p/table/tbody/tr/td/a[3]")
				.setTitleparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1")
				.setTimeparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']")
				.setSourceparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span")
				.setTextparser("//*/div[@itemprop='articleBody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("金融监管")
				.setUrl("http://business.sohu.com/hgjj/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//sohu金融改革/创新

		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a")
				.setNextparser("//*/div[@class='pages']/p/table/tbody/tr/td/a[3]")
				.setTitleparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1")
				.setTimeparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']")
				.setSourceparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span")
				.setTextparser("//*/div[@itemprop='articleBody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("金融改革")
				.setUrl("http://business.sohu.com/jqgg/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//sohu金融机构
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a")
				.setNextparser("//*/div[@class='pages']/p/table/tbody/tr/td/a[3]")
				.setTitleparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1")
				.setTimeparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']")
				.setSourceparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span")
				.setTextparser("//*/div[@itemprop='articleBody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("金融机构")
				.setUrl("http://business.sohu.com/c241863626/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//sohu金融动态
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a")
				.setNextparser("//*/div[@class='pages']/p/table/tbody/tr/td/a[3]")
				.setTitleparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1")
				.setTimeparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']")
				.setSourceparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span")
				.setTextparser("//*/div[@itemprop='articleBody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("金融动态")
				.setUrl("http://business.sohu.com/jrqj/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//sohu证券要闻
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='main area']/*/div[@class='f14list']/ul/li/a")
				.setNextparser("//*/div[@class='pages']/p/table/tbody/tr/td/a[3]")
				.setTitleparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1")
				.setTimeparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']")
				.setSourceparser("//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span")
				.setTextparser("//*/div[@itemprop='articleBody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("证券要闻")
				.setUrl("http://stock.sohu.com/news/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		
		//hexun
		job= new Job();
		job= job.setName("和讯网").setPlan("intr:2h").setCategory(1).setDescription("").setStatus(1).setCreatetime(new Date());
		jobServiceImpl.addJob(job);
		//hexun宏观经济
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@id='temp01']/ul/li/a")
				.setNextparser("//*/div[@class='hx_paging']/ul/li[@class='next']/a")
				.setTitleparser("//*/div[@class='layout mg articleName']/h1")
				.setTimeparser("//*/div[@class='tip fl']/span")
				.setSourceparser("//*/div[@class='tip fl']/a")
				.setTextparser("//*/div[@class='art_contextBox']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("宏观经济")
				.setUrl("http://news.hexun.com/economy/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//hexun产业报道
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@id='temp01']/ul/li/a")
				.setNextparser("//*/div[@class='hx_paging']/ul/li[@class='next']/a")
				.setTitleparser("//*/div[@class='layout mg articleName']/h1")
				.setTimeparser("//*/div[@class='tip fl']/span")
				.setSourceparser("//*/div[@class='tip fl']/a")
				.setTextparser("//*/div[@class='art_contextBox']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("产业报道")
				.setUrl("http://news.hexun.com/company/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//hexun国际经济
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@id='temp01']/ul/li/a")
				.setNextparser("//*/div[@class='hx_paging']/ul/li[@class='next']/a")
				.setTitleparser("//*/div[@class='layout mg articleName']/h1")
				.setTimeparser("//*/div[@class='tip fl']/span")
				.setSourceparser("//*/div[@class='tip fl']/a")
				.setTextparser("//*/div[@class='art_contextBox']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("国际经济")
				.setUrl("http://news.hexun.com/international/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//hexun原油行业资讯
	
		parser = (new Parser()).setListcss(false).setListjs(false)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='dList pl14 pt6']/ul/li/a")
				.setNextparser("//*/div[@class='hx_paging']/ul/li[@class='next']/a")
				.setTitleparser("//*/div[@id='artibodyTitle']/h1")
				.setTimeparser("//*/span[@id='pubtime_baidu']")
				.setSourceparser("//*/span[@id='source_baidu']/a")
				.setTextparser("//*/div[@id='artibody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("原油行业资讯")
				.setUrl("http://crudeoil.hexun.com/crudeoilhy/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//hexun能源资讯
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@id='temp01']/ul/li/a")
				.setNextparser("//*/div[@class='hx_paging']/ul/li[@class='next']/a")
				.setTitleparser("//*/div[@class='layout mg articleName']/h1")
				.setTimeparser("//*/div[@class='tip fl']/span")
				.setSourceparser("//*/div[@class='tip fl']/a")
				.setTextparser("//*/div[@class='art_contextBox']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("能源资讯")
				.setUrl("http://futures.hexun.com/nyzx/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//hexun国际油市要闻
		
		parser = (new Parser()).setListcss(false).setListjs(false)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='dList pl14 pt6']/ul/li/a")
				.setNextparser("//*/div[@class='hx_paging']/ul/li[@class='next']/a")
				.setTitleparser("//*/div[@id='artibodyTitle']/h1")
				.setTimeparser("//*/span[@id='pubtime_baidu']")
				.setSourceparser("//*/span[@id='source_baidu']/a")
				.setTextparser("//*/div[@id='artibody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("国际油市要闻")
				.setUrl("http://crudeoil.hexun.com/oiloverseas/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		
		//hexun原油深度评论
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='dList pl14 pt6']/ul/li/a")
				.setNextparser("//*/div[@class='hx_paging']/ul/li[@class='next']/a")
				.setTitleparser("//*/div[@class='layout mg articleName']/h1")
				.setTimeparser("//*/div[@class='tip fl']/span")
				.setSourceparser("//*/div[@class='tip fl']/a")
				.setTextparser("//*/div[@class='art_contextBox']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("原油深度评论")
				.setUrl("http://crudeoil.hexun.com/sdpl/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//发改委
		job= new Job();
		job= job.setName("发改委官网").setPlan("intr:2h").setCategory(2).setDescription("").setStatus(1).setCreatetime(new Date());
		jobServiceImpl.addJob(job);
		//发改委-经济形势
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/ul[@class='list_02 clearfix']//li/a")
				.setNextparser("//*/ul[@class='pages clearfix']/li[@class='L']/a[5]")
				.setTitleparser("//*/div[@class='txt_title1 tleft']")
				.setTimeparser("//*/div[@class='txt_subtitle1 tleft']")
				.setSourceparser("//*/span[@id='dSourceText']/a")
				.setTextparser("//*/div[@class='TRS_Editor']")
				.setUrlrelativer(true).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("经济形势")
				.setUrl("http://www.sdpc.gov.cn/jjxsfx/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		//发改委-宏观经济
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/ul[@class='list_02 clearfix']//li/a")
				.setNextparser("//*/ul[@class='pages clearfix']/li[@class='L']/a[5]")
				.setTitleparser("//*/div[@class='txt_title1 tleft']")
				.setTimeparser("//*/div[@class='txt_subtitle1 tleft']")
				.setSourceparser("//*/span[@id='dSourceText']/a")
				.setTextparser("//*/div[@class='TRS_Editor']")
				.setUrlrelativer(true).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("宏观经济")
				.setUrl("http://www.sdpc.gov.cn/fzgggz/hgjj/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
		
		//中国人民银行
		//中国人民银行-新闻发布
		//中国人民银行-货币政策
		//中国人民银行-信贷政策
		//中国人民银行-金融市场
		//中国人民银行-金融稳定
		//中国人民银行-利率政策
		
		//新浪财经
		job= new Job();
		job= job.setName("新浪财经").setPlan("intr:2h").setCategory(1).setDescription("").setStatus(1).setCreatetime(new Date());
		jobServiceImpl.addJob(job);
		//新浪财经-国内财经
		
		parser = (new Parser()).setListcss(false).setListjs(true)
				.setArtcss(false).setArtjs(false).setCreatetime(new Date())
				.setListparser("//*/div[@class='feed-card-content']/div[1]/div/*/a")
				.setNextparser("//*/span[@class='pagebox_next']/a")
				.setTitleparser("//*/h1[@id='artibodyTitle']")
				.setTimeparser("//*/span[@class='time-source']")
				.setSourceparser("//*/div[@class='page-info']/span/*/a")
				.setTextparser("//*/div[@id='artibody']")
				.setUrlrelativer(false).setStatus(1);
		parser = parserServiceImpl.addParser(parser);
		subjob = (new SubJob()).setJobid(job.getId()).setCategory(0).setName("国内财经")
				.setUrl("http://finance.sina.com.cn/china/").setStatus(1)
				.setCreatetime(new Date()).setDescription("").setParserid(parser.getId());
		subJobServiceImpl.addSubJob(subjob);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
