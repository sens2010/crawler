package cn.cnic.datapub.n.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.n.model.Batch;
import cn.cnic.datapub.n.serviceimpl.BatchServiceImpl;

@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BatchServiceImplTest
{
	
	
	@Resource
	BatchServiceImpl batchServiceImpl;
	
	
	public void testAddBatch()
	{
		Batch batch = new Batch();
		batch.setJobid(1);
		batch.setMonitorinfo(2);
		batch.setStatus(1);
		batch.setStarttime(new Date());
		batch.setEndtime(new Date());
		batchServiceImpl.addBatch(batch);
	}
	
	public void testDeleteBatch()
	{
		System.out.println(batchServiceImpl.deleteById(4));
	}
	
	public void testUpdateBatch()
	{
		Batch batch = batchServiceImpl.getBatchById(5);
		batch.setStatus(10);
		System.out.println(batchServiceImpl.updateBatch(batch).toJSONString());
	}
	
	public void testFindBatchById()
	{
		Batch batch = batchServiceImpl.getBatchById(5);
		System.out.println(batch.toJSONString());
	}
	
	
	public void testFindAllBatch()
	{
		System.out.println(batchServiceImpl.countAll());
		List<Batch> list = batchServiceImpl.findAll();
		for(Batch b:list)
		{
			System.out.println(b.toJSONString());
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
