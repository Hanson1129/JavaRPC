package project.lanshan.javarpc.spring;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import project.lanshan.javarpc.consumer.caller.DefaultCaller;
import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.zookeeper.ZookeeperService;

public class SpringConsumerBean implements InitializingBean,
		FactoryBean<Object> {

	private static Logger log = Logger.getLogger(SpringConsumerBean.class
			.getName());
	
	private static DefaultCaller consumerCaller = new DefaultCaller();
	
	@Autowired
	private ZookeeperService zookeeperService;

	private AddressHolder addresses = new AddressHolder();

	private final AtomicBoolean inited = new AtomicBoolean(false);

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!inited.compareAndSet(false, true)) {
			log.error("Consumer has been inited!");
			return;
		}
		subscribeFromZookeeper();
		init();
	}

	private void init() {
		if (!consumerCaller.call(addresses)){
			log.error("fail to consumer the service!");
			System.exit(1);
		}
	}

	private void subscribeFromZookeeper() {
		addresses = zookeeperService.subscribe(consumerCaller.getMetadata());
	}

	public Object getObject() {
		if (addresses.size() > 0)
			return consumerCaller.getObject();
		else {
			subscribeFromZookeeper();
			return consumerCaller.getObject();
		}
	}

	public void setRequest(Request request) {
		consumerCaller.setRequest(request);
	}

	public void setHost(String host) {
		consumerCaller.getMetadata().setHost(host);
	}

	public void setPort(int port) {
		consumerCaller.getMetadata().setPort(port);
	}
	public void setServiceName(String serviceName){
		consumerCaller.getMetadata().setServiceName(serviceName);
	}
	public void setInterfaceName(String interfaceName) {
		consumerCaller.getMetadata().setInterfaceName(interfaceName);;
	}
	public void setCallWay(String callWay){
		consumerCaller.getMetadata().setCallWay(callWay);
	}
	@Override
	public Class<?> getObjectType() {
		return consumerCaller.getObjectType();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
