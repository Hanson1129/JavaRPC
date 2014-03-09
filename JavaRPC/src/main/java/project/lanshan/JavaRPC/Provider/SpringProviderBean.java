package project.lanshan.JavaRPC.Provider;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import project.lanshan.JavaRPC.Provider.Publisher.ProviderPublisher;

public class SpringProviderBean implements InitializingBean{
	
	private static Logger log = Logger.getLogger(SpringProviderBean.class.getName());
	
	private ProviderPublisher providerPublisher;
	
	private static AtomicBoolean isProvided = new AtomicBoolean(false);
	private final AtomicBoolean inited = new AtomicBoolean(false);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(!inited.compareAndSet(false,true))
			return;
		if(!isProvided.compareAndSet(false,true))
			return;
		Config();
	}
	
	private void Config() {
	}
	
	public void publish(){
		providerPublisher.publish();
	}
	public ProviderPublisher getProviderPublisher() {
		return providerPublisher;
	}

	public void setProviderPublisher(ProviderPublisher providerPublisher) {
		this.providerPublisher = providerPublisher;
	}


	

}
