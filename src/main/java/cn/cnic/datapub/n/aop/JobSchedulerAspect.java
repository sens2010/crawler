package cn.cnic.datapub.n.aop;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class JobSchedulerAspect
{
	private final Logger logger = Logger.getLogger(JobSchedulerAspect.class);
	
	@Pointcut("execution(* cn.cnic.datapub.n.utils.SchedulerUtils.*(..))")
	public void aspect(){	}
	
	@Before("aspect()")
	public void before(JoinPoint joinPoint)
	{
		if(logger.isInfoEnabled())
		{
			logger.info("before " + joinPoint);
		}
	}
	
	@After("aspect()")
	public void after(JoinPoint joinPoint)
	{
		if(logger.isInfoEnabled())
		{
			logger.info("before " + joinPoint);
		}
	}
	
	@Around("aspect()")
	public Object around(JoinPoint joinPoint){
		long start = System.currentTimeMillis();
		Object object = null ;
		try {
			object = ((ProceedingJoinPoint) joinPoint).proceed();
			long end = System.currentTimeMillis();
			if(logger.isInfoEnabled()){
				logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
			}
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			if(logger.isInfoEnabled()){
				logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
			}
		}
		return object;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
