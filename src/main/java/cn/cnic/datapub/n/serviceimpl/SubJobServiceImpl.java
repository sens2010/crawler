package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.SubJobDao;
import cn.cnic.datapub.n.service.ISubJobService;

@Service("subJobServiceImpl")
public class SubJobServiceImpl implements ISubJobService
{
	@Resource
	SubJobDao subJobDao;
}
