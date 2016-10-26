package cn.cnic.datapub.n.service;

import java.util.Collection;
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
	List<Job> findByIds(Collection<Integer> ids);
	List<Job> list(int pid, int size);
	String startJob(int jobid);
	String deleteJob(int jobid);
	String stopJob(int jobid);
	String restartJob(int jobid);
	String getJobStatus();
}
