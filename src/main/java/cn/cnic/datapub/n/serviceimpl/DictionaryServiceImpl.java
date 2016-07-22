package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.DictionaryDao;
import cn.cnic.datapub.n.service.IDictionaryService;

@Service("dictionaryServiceImpl")
public class DictionaryServiceImpl implements IDictionaryService
{
	@Resource
	DictionaryDao dictionaryDao;
}
