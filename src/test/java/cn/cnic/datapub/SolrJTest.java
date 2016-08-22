package cn.cnic.datapub;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SolrJTest
{
	@Resource
	SolrTemplate solrTemplate;
	
	@Test
	public void searchOne()
	{
		System.out.println("*************");
		Query query = new SimpleQuery();
		SimpleStringCriteria ssc = new SimpleStringCriteria("select?q=\"B*\"");
		
		query.addCriteria(ssc);
		/*query.
		query.set("q", "中国科学院计算机网络信息中心");*/
		query.setRows(20);
		long start = System.currentTimeMillis();
		System.out.println(solrTemplate.count(query));
		System.out.println(System.currentTimeMillis()-start);
	}
	
	/*@Test
	public void isSolrTemplate()
	{
		Asserts.notNull(solrTemplate, "solrTemplate");
	}*/
	
	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String urlString = "http://localhost:8984/solr/mytest"; 
		SolrClient solr = new HttpSolrClient(urlString);
		SolrQuery query = new SolrQuery();
		query.set("q", "中国科学院计算机网络信息中心");
		query.setRows(20);
		try
		{
			QueryResponse response = solr.query(query);
			System.out.println(response.toString());
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", 68);
			sid.addField("FieldA", "中原大国");
			sid.addField("FieldB", "时间");
			sid.addField("FieldC", "BAC");
			sid.addField("FieldD", "ABC");
			
			System.out.println(solr.add(sid));
			System.out.println(solr.commit());
			
			response = solr.query(query);
			System.out.println(response.toString());
			
		} catch (SolrServerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
