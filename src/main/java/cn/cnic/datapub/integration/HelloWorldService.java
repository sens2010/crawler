package cn.cnic.datapub.integration;

public class HelloWorldService implements HelloService{

	public String sayHello(String name) {
		String result = ("Hello " + name);
		return result;
	}

}