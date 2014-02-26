package project.lanshan.JavaRPC.Provider;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.InitializingBean;

import project.lanshan.JavaRPC.Provider.Publisher.ProviderPublisher;

public class SpringProviderBean implements InitializingBean{
	
	private static Logger log = Logger.getLogger("SpringProviderBean.class");
	
	private String callWay;
	private ProviderPublisher publisher;
	
	private static AtomicBoolean isProvided = new AtomicBoolean(false);
	private final AtomicBoolean inited = new AtomicBoolean(false);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(!inited.compareAndSet(false,true))
			return;
		
		
		if(!isProvided.compareAndSet(false,true))
			return;
		
		if(!publisher.publish()){
			log.info("Your service has been provided!");
		}
	}

	
	
	public String getCallWay() {
		return callWay;
	}

	public void setCallWay(String callWay) {
		this.callWay = callWay;
	}

	
	public ProviderPublisher getPublisher() {
		return publisher;
	}

	public void setPublisher(ProviderPublisher publisher) {
		this.publisher = publisher;
	}

}
