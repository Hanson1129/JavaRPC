package project.lanshan.JavaRPC.Consumer;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import project.lanshan.JavaRPC.Consumer.Proxy.RPCProxy;


public class SpringConsumerBean implements InitializingBean{
	
	private static Logger log = Logger.getLogger(SpringConsumerBean.class.getName());
	private static RPCProxy proxy;
	private final AtomicBoolean inited = new AtomicBoolean(false);
	
	
	
	
	public void Config(){}




	@Override
	public void afterPropertiesSet() throws Exception {
		if(!inited.compareAndSet(false, true)){
			log.error("Consumer has been inited!");
			return;
		}
		Config();
		if(proxy.getCaller().call()){
			log.info("Call service successful!");
		}else{
			log.error("Fail to call the remote service");
		}
	}
	
	public Object getObject(){
		return proxy.getCaller().getObject();
	}


}
