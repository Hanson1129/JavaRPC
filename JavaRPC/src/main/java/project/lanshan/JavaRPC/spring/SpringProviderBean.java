package project.lanshan.javarpc.spring;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import project.lanshan.javarpc.provider.publisher.ProviderPublisher;
import project.lanshan.javarpc.zookeeper.ZookeeperService;



public class SpringProviderBean implements InitializingBean{
	
	private static Logger log = Logger.getLogger(SpringProviderBean.class.getName());
	
	@Autowired
	private ProviderPublisher providerPublisher;
	
	@Autowired
	private ZookeeperService zookeeperService;
	private static AtomicBoolean isProvided = new AtomicBoolean(false);
	private final AtomicBoolean inited = new AtomicBoolean(false);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(!inited.compareAndSet(false,true))
			return;
		if(!isProvided.compareAndSet(false,true))
			return;
		registerToZookeeper();
		publish();
	}
	
	private void registerToZookeeper() {
      if( !zookeeperService.register((providerPublisher.getMetadata()))){
        log.error("fail to regiter service!");
        System.exit(1);
      }
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
	
	public void setInterfaceName(String interfaceName){
	  providerPublisher.getMetadata().setInterfaceName(interfaceName);;
	}
	public void setImplClass(String implClass){
		providerPublisher.getMetadata().setImplClass(implClass);
	}
	public void setHost(String host){
	  providerPublisher.getMetadata().setHost(host);
	}
	public void setPort(int port){
	  providerPublisher.getMetadata().setPort(port);
	}
}
