package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.NewsImageDao;
import cn.cnic.datapub.n.service.INewsImageService;

@Service("newsImageServiceImpl")
public class NewsImageServiceImpl implements INewsImageService
{
	@Resource
	NewsImageDao newsImageDao;
}
