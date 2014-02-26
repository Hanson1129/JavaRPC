package project.lanshan.JavaRPC.Provider;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.InitializingBean;

import project.lanshan.JavaRPC.model.ServiceMetadata;

public class SpringProviderBean implements InitializingBean{
	
	private static Logger log = Logger.getLogger("SpringProviderBean.class");
	
	private String callWay;
	private ProviderPublisher publisher;
	private static ServiceMetadata metadata;  
	private static AtomicBoolean isProvided = new AtomicBoolean(false);
	private final AtomicBoolean inited = new AtomicBoolean(false);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(!inited.compareAndSet(false,true))
			return;
		
		initLog4j();
		
		if(!isProvided.compareAndSet(false,true))
			return;
		
		if(!publisher.publish()){
			log.info("Your service has been provided!");
		}
	}

	
	
	public void initLog4j(){
		PropertyConfigurator.configure("log4j.properties");
	}
	
	public String getCallWay() {
		return callWay;
	}

	public void setCallWay(String callWay) {
		this.callWay = callWay;
	}

	public static ServiceMetadata getMetadata() {
		return metadata;
	}

	public static void setMetadata(ServiceMetadata metadata) {
		SpringProviderBean.metadata = metadata;
	}

	public ProviderPublisher getPublisher() {
		return publisher;
	}

	public void setPublisher(ProviderPublisher publisher) {
		this.publisher = publisher;
	}


}
