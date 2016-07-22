package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.SubBatchDao;
import cn.cnic.datapub.n.service.ISubBatchService;

@Service("subBatchServiceImpl")
public class SubBatchServiceImpl implements ISubBatchService
{
	@Resource
	SubBatchDao subBatchDao;
}
