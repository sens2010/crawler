package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.NoterDao;
import cn.cnic.datapub.n.service.INoterService;

@Service("noterServiceImpl")
public class NoterServiceImpl implements INoterService
{
	@Resource
	NoterDao noterDao;
}
