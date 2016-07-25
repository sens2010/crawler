package cn.cnic.datapub.n.service;

import java.util.List;



public interface IAbstractService<T>
{
	T add(T t);
	boolean deleteById(int id);
	T update(T t);
	T getById(int id);
	int countAll();
	List<T> findAll();
}
