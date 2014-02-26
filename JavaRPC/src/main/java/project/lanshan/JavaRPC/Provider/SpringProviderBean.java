package project.lanshan.JavaRPC.Provider;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import project.lanshan.JavaRPC.Provider.Publisher.ProviderPublisher;

public class SpringProviderBean implements InitializingBean{
	
	private static Logger log = Logger.getLogger("SpringProviderBean.class");
	
	private ProviderPublisher publisher;
	
	private static AtomicBoolean isProvided = new AtomicBoolean(false);
	private final AtomicBoolean inited = new AtomicBoolean(false);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(!inited.compareAndSet(false,true))
			return;
		if(!isProvided.compareAndSet(false,true))
			return;
		Config();
		if(!publisher.publish()){
			log.info("Your service has been provided!");
		}
	}
	
	private void Config() {
		
	}


	public ProviderPublisher getPublisher() {
		return publisher;
	}

	public void setPublisher(ProviderPublisher publisher) {
		this.publisher = publisher;
	}

}
