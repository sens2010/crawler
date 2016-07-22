package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.StatCategoryDao;
import cn.cnic.datapub.n.service.IStatCategoryService;

@Service("statCategoryServiceImpl")
public class StatCategoryServiceImpl implements IStatCategoryService
{
	@Resource
	StatCategoryDao statCategoryDao;
}
