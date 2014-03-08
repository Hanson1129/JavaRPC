package project.lanshan.JavaRPC.Consumer;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import project.lanshan.JavaRPC.Consumer.Caller.ConsumerCaller;
import project.lanshan.JavaRPC.model.Request;


public class SpringConsumerBean implements InitializingBean{
	
	private static Logger log = Logger.getLogger(SpringConsumerBean.class.getName());
	private ConsumerCaller consumerCaller;
	private final AtomicBoolean inited = new AtomicBoolean(false);
	private ApplicationContext acxt;
	

	@Override
	public void afterPropertiesSet() throws Exception {
			if(!inited.compareAndSet(false, true)){
			log.error("Consumer has been inited!");
			return;
		}
		Config();
		
	}
	
	public void Config() throws ClassNotFoundException{
		acxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProxyFactoryBean proxy = (ProxyFactoryBean)acxt.getBean("proxy");
		Class<?>[] proxyInterfaces = new Class<?>[1];
		proxyInterfaces[0] = Class.forName(getClassName());
		
		proxy.setProxyInterfaces(proxyInterfaces);
	}



	public ConsumerCaller getConsumerCaller() {
		return consumerCaller;
	}

	public void setConsumerCaller(ConsumerCaller consumerCaller) {
		this.consumerCaller = consumerCaller;
	}

	public Object getObject(){
		return consumerCaller.getObject();
	}
	public void setRequest(Request request){
		consumerCaller.setRequest(request);
	}
	public String getClassName(){
		return consumerCaller.getClassName();
	} 
}
