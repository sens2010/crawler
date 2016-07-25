package cn.cnic.datapub.n.service;

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
}
