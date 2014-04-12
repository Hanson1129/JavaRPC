package project.lanshan.javarpc.spring;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import project.lanshan.javarpc.consumer.caller.ConsumerCaller;
import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.zookeeper.ZookeeperService;

public class SpringConsumerBean implements InitializingBean,
		FactoryBean<Object> {

	private static Logger log = Logger.getLogger(SpringConsumerBean.class
			.getName());
	@Autowired
	private ConsumerCaller consumerCaller;
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
		if (!consumerCaller.call(addresses))
			log.error("fail to consumer the service!");
		System.exit(1);
	}

	private void subscribeFromZookeeper() {
		addresses = zookeeperService.subscribe(consumerCaller.getMetadata());
	}

	public Object getObject() {
		if (addresses.size() > 0)
			return consumerCaller.getObject(addresses);
		else {
			subscribeFromZookeeper();
			return consumerCaller.getObject(addresses);
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

	public void setServiceName(String serviceName) {
		consumerCaller.getMetadata().setServiceName(serviceName);
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
