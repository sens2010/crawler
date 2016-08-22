package cn.cnic.datapub;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cnic.datapub.integration.HelloService;
@ContextConfiguration(locations = "classpath:/spring/spring-integration-gateway.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringIntegrationTest
{
	private static Logger logger = Logger.getLogger(SpringIntegrationTest.class);
	
	/*//in-output test
	@Resource
	MessageChannel inputChannel;
	
	@Resource
	PollableChannel outputChannel;
	
	@Test
	public void testHelloService()
	{
		System.out.println(inputChannel==null);
		System.out.println(outputChannel==null);
		inputChannel.send(new GenericMessage<String>("World"));
		logger.info("==> HelloWorldDemo: " + outputChannel.receive(0).getPayload());
	}
	*/
	
	//gateway test
	
	@Resource
	HelloService helloGateway;
	
	@Test
	public void testHelloGateWay()
	{
		logger.info(helloGateway.sayHello("sens"));
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
