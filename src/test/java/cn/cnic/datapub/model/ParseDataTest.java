package cn.cnic.datapub.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.job.manager.ParseDataDAO;
import cn.cnic.datapub.job.manager.ParseDataM;

import com.alibaba.fastjson.JSONObject;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParseDataTest
{
	
	@Resource
	ParseDataDAO parseDataDAO;
	
	
	public void testModify()
	{
		ParseDataM jd = new ParseDataM();
		jd.setId(3);
		jd.setStatus(10);
		System.out.println(parseDataDAO.modify(jd));
	}
	
	
	public void testfindAll()
	{
		List<ParseDataM> list = parseDataDAO.findAll();
		for(ParseDataM jd:list)
		{
			System.out.println(jd);
		}
	}
	
	
	public void testCount()
	{
		System.out.println(parseDataDAO.count());
	}
	
	@Test
	public void testGeneralFind()
	{
		JSONObject json = new JSONObject();
		json.put("status",2);
		json.put("listcss",0);
		//json.put("id",5);
		List<ParseDataM> list= parseDataDAO.findByParams(json);
		for(ParseDataM jd:list)
		{
			System.out.println(jd.toString());
		}
	}
	
	
	public void testFindById()
	{
		
		System.out.println(parseDataDAO.findByIdTest(3).toString());
	}
	
	
	public void testdelete()
	{
		System.out.println(parseDataDAO.deleteById(4));
	}
	
	
	public void testadd()
	{
		if(parseDataDAO!=null)
		{
			for(int i=1;i<10;i++)
			{
				ParseDataM jd = new ParseDataM();
				jd.setListparse("listparse"+i);
				jd.setListcss((i%2==0)?false:true);
				jd.setListjs((i%3==0)?false:true);
				jd.setNextparse("nextparse"+i);
				jd.setUrlrelative((i>5)?false:true);
				jd.setTitleparse("titleparse"+i);
				jd.setTimeparse("timeparse"+i);
				jd.setSourceparse("sourceparse"+i);
				jd.setTextparse("text"+i);
				jd.setArtcss((i%4==0)?false:true);
				jd.setArtjs((i%5==0)?false:true);
				jd.setStatus(i);
				jd.setStarttime(new Date());
				jd.setLastmodifytime(new Date());
				System.out.println(parseDataDAO.add(jd));
			}
			
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
