package cn.cnic.datapub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnic.datapub.n.model.News;

@RestController
@RequestMapping("/comments.json")
public class RestExampleController
{
	
	@RequestMapping(method = RequestMethod.GET)
	public List<News> getNews()
	{
		News news = new News();
		news.setId(1);
		news.setLastmodifytime(new Date());
		news.setRelatebatch(1);
		news.setTitle("1234");
		List<News> news_list = new ArrayList<News>();
		news_list.add(news);
		news_list.add(news);
		return news_list;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
