package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.NewsDao;
import cn.cnic.datapub.n.service.INewsService;

@Service("newsServiceImpl")
public class NewsServiceImpl implements INewsService
{
	@Resource
	NewsDao newsDao;
}
