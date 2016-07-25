package cn.cnic.datapub.n.service;

import java.util.List;

import cn.cnic.datapub.n.model.Job;


public interface IJobService
{
	Job addJob(Job job);
	boolean deleteById(int id);
	Job updateJob(Job job);
	Job getJobById(int id);
	int countAll();
	List<Job> findAll();
}
