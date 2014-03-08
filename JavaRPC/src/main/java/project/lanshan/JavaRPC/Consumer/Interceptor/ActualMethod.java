package project.lanshan.JavaRPC.Consumer.Interceptor;

import java.util.concurrent.atomic.AtomicBoolean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import project.lanshan.JavaRPC.Consumer.SpringConsumerBean;
import project.lanshan.JavaRPC.model.Request;

public class ActualMethod implements MethodInterceptor{

	private ApplicationContext acxt;
	private Object object;
	private AtomicBoolean hasObject;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if(hasObject.compareAndSet(false, true))
			callForObject(invocation);
		return object;
	}
	
	public void callForObject(MethodInvocation invocation){
		acxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringConsumerBean consumerBean = (SpringConsumerBean)acxt.getBean("springConsumerBean");
		Request request = new Request();
		Object[] argsObject = invocation.getArguments();
		Class<?>[] argsClass = new Class<?>[argsObject.length];
		
		for(int i =0 ; i < argsObject.length ; i++){
			argsClass[i] = argsObject[i].getClass();
		}
		
		request.setParametersClass(argsClass);
		request.setParametersObject(argsObject);
		
		object = consumerBean.getObject();
	}
}
