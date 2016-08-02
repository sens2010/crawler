
package cn.cnic.datapub;

import org.eclipse.jetty.util.security.Credential.MD5;

public class CodeTest
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String md5 = MD5.digest("1");
		System.out.println(md5.substring(3));
		
	}
	
}
