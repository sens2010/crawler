package cn.cnic.datapub.n.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cnic.datapub.n.dao.ParserDao;
import cn.cnic.datapub.n.service.IParserService;

@Service("parserServiceImpl")
public class ParserServiceImpl implements IParserService
{
	@Resource
	ParserDao parserDao;
}
