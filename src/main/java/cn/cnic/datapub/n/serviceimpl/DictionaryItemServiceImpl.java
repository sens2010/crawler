package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.DictionaryItemDao;
import cn.cnic.datapub.n.service.IDictionaryItemService;

@Service("dictionaryItemServiceImpl")
public class DictionaryItemServiceImpl implements IDictionaryItemService
{
	@Resource
	DictionaryItemDao dictionaryItemDao;
}
