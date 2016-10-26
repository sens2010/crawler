package cn.cnic.datapub.n.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.SubJob;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SubJobDaoTest
{
	
	
	@Resource
	SubJobDao subJobDao;
	
	@Test
	public void testFindNews()
	{
		/*List<Integer> codes = new ArrayList<Integer>();
		codes.add(1);
		codes.add(2);
		Set<Integer> ids = new HashSet<Integer>();
		ids.add(18);
		ids.add(19);
		
		
		List<SubJob> subjobs = subJobDao.findNormal(codes,ids);

		for(SubJob job:subjobs)
		{
			System.out.println(job.toJSONString());
		}*/
		
		SubJob sj = new SubJob();
		sj.setCategory(1);
		sj.setCreatetime(new Date());
		sj.setDescription("description");
		sj.setInterval(1);
		sj.setJobid(18);
		sj.setName("name");
		sj.setParserid(18);
		sj.setStatus(1);
		sj.setUrl("url");
		
		sj = subJobDao.save(sj);
		
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
