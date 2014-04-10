package project.lanshan.javarpc.consumer.interceptor;

import java.util.concurrent.atomic.AtomicBoolean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.spring.SpringConsumerBean;


public class ActualMethod implements MethodInterceptor{

	private ApplicationContext acxt;
	private Object object;
	private AtomicBoolean hasObject = new AtomicBoolean(false);

	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if(hasObject.compareAndSet(false, true))
			callForObject(invocation);
		return object;
	}
	
	public void callForObject(MethodInvocation invocation) throws ClassNotFoundException{
//		Config();
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
		consumerBean.setRequest(request);
		object = consumerBean.getObject();
	}
	public void Config() throws ClassNotFoundException{
		
		ProxyFactoryBean proxy = (ProxyFactoryBean)acxt.getBean("proxy");
		Class<?>[] proxyInterfaces = new Class<?>[1];
		proxyInterfaces[0] = acxt.getBean("targetService").getClass();
		proxy.setProxyInterfaces(proxyInterfaces);
	}
}
