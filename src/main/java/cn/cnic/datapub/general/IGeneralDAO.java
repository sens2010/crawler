package cn.cnic.datapub.general;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
@Deprecated
public interface IGeneralDAO<T>
{
	public T findById(Object id);
	public List<T> findByParams(JSONObject json);
	public List<T> findAll();
	public boolean add(T model);
	public boolean delete(T model);
	public boolean deleteById(Object id);
	public boolean modify(T model);
	public boolean modifyById(Object id,T model);
	public int count();
}
