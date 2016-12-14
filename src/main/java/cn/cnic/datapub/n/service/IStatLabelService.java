package cn.cnic.datapub.n.service;

import java.util.Collection;
import java.util.List;

import cn.cnic.datapub.n.model.StatLabel;


public interface IStatLabelService
{

	StatLabel addStatLabel(StatLabel statlabel);

	boolean deleteStatLabel(StatLabel statlabel);

	StatLabel updateStatLabel(StatLabel statlabel);

	List<StatLabel> findStatLabelByNewsId(int newsid);

	StatLabel findStatLabelById(int id);

	List<StatLabel> findStatLabelByNewsIds(Collection<Integer> newsids);

	boolean findStatLabelByKeys(int newsid, int categoryid);

	void deleteStatLabelByNewsId(int newsid);

	void deleteStatLableByNewsIds(int newsid, Collection<Integer> categoryids);

	void deleteStatLableByIds(Collection<Integer> ids);
	
}
