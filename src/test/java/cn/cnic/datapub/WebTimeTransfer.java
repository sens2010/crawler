package cn.cnic.datapub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebTimeTransfer
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String timestr = "2016年08月01日09:16 新浪财经";
		timestr = "2016-07-15 14:17:30";
		String format = "yyyy年MM月dd日hh:mm|0,16";
		format = "yyyy-MM-dd hh:mm:ss";
		String[] fa = format.split("\\|");
		String f = fa[0];
		System.out.println(fa.clone());
		for(String s:fa)
		{
			System.out.println(s);
		}
		System.out.println(f);
		if(fa.length>1&&fa[1]!=null)
		{
		String se = fa[1];
		System.out.println(se);
		String[]stend = se.split(",");
		int start = Integer.parseInt(stend[0]);
		int end = Integer.parseInt(stend[1]);
		timestr = timestr.substring(start,end);
		}
		//timestr = "2016-08-01 13:24:33";
		//timestr = timestr.substring(0,16);
		System.out.println(timestr);
		SimpleDateFormat sdf = new SimpleDateFormat(f);
		try
		{
			Date pubtime = sdf.parse(timestr);
			System.out.println(pubtime.toLocaleString());
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
