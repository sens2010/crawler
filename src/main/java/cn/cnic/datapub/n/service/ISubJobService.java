package cn.cnic.datapub.n.service;

import java.util.Collection;
import java.util.List;

import cn.cnic.datapub.n.model.SubJob;


public interface ISubJobService
{
	SubJob addSubJob(SubJob subjob);
	boolean deleteById(int id);
	SubJob updateSubJob(SubJob subjob);
	SubJob getSubJobById(int id);
	int countAll();
	List<SubJob> findAll();
	List<SubJob> findByIds(Collection<Integer> ids);
	List<SubJob> findByJobId(int jid);
	String startSubJob(int subjobid);
	String stopSubJob(int subjobid);
	String deleteSubJob(int subjobid);
	String restartSubJob(int subjobid);
	SubJob findSubJobById(int subjobid);
}
