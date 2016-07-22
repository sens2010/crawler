package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.StatTypeDao;
import cn.cnic.datapub.n.service.IStatTypeService;

@Service("statTypeServiceImpl")
public class StatTypeServiceImpl implements IStatTypeService
{
	@Resource
	StatTypeDao statTypeDao;
}
