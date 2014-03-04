package project.lanshan.JavaRPC.Consumer;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import project.lanshan.JavaRPC.Consumer.Caller.ConsumerCaller;


public class SpringConsumerBean {
	
	private static Logger log = Logger.getLogger(SpringConsumerBean.class.getName());
	private ConsumerCaller consumerCaller;
	private final AtomicBoolean inited = new AtomicBoolean(false);
	
	public SpringConsumerBean(){
		if(!inited.compareAndSet(false, true)){
			log.error("Consumer has been inited!");
			return;
		}
		Config();
	}



	public void Config(){
	}



	public ConsumerCaller getConsumerCaller() {
		return consumerCaller;
	}

	public void setConsumerCaller(ConsumerCaller consumerCaller) {
		this.consumerCaller = consumerCaller;
	}




}
