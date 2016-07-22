package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.JobDao;
import cn.cnic.datapub.n.service.IJobService;

@Service("jobServiceImpl")
public class JobServiceImpl implements IJobService
{
	@Resource
	JobDao jobDao;
}
