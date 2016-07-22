package cn.cnic.datapub;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class RabbitMQTest
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ApplicationContext context =
			    new GenericXmlApplicationContext("classpath:spring/spring-rabbitmq.xml");
			AmqpTemplate template = context.getBean(AmqpTemplate.class);
			template.convertAndSend("myqueue", "foo1");
			String foo = (String) template.receiveAndConvert("myqueue");
			System.out.println(foo);
			
	}
	
}
