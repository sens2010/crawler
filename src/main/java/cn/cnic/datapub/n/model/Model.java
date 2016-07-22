package cn.cnic.datapub.n.model;

import com.alibaba.fastjson.JSONObject;

public abstract class Model
{
	
	
	public String toJSONString()
	{
		return JSONObject.toJSONString(this);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
