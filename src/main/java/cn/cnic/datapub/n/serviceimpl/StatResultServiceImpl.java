package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.StatResultDao;
import cn.cnic.datapub.n.service.IStatResultService;

@Service("statResultServiceImpl")
public class StatResultServiceImpl implements IStatResultService
{
	@Resource
	StatResultDao statResultDao;
}
