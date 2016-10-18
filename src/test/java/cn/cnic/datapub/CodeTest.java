
package cn.cnic.datapub;

import cn.cnic.datapub.n.model.SubJob;

import com.alibaba.fastjson.JSONArray;

public class CodeTest
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
/*		Map<Integer,String[]> codes = new HashMap<Integer,String[]>();
		String[] list= new String[]{"string1","string2","string3"};
		codes.put(0, list);
		String[] test = codes.get(0);
		test[2]="string4";
		System.out.println(codes.get(0)[2]);*/
/*		JSONArray ja = new JSONArray();
		ja.add("1");
		ja.add("2");
		ja.add("3");
		System.out.println(ja.toJSONString());
		ja.remove("2");
		System.out.println(ja.toJSONString());*/
		SubJob sj =  new SubJob();
		System.out.println(sj.getId());
	}
	
}
