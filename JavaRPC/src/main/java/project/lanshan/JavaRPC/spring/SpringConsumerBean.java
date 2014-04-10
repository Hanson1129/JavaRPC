package project.lanshan.javarpc.spring;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import project.lanshan.javarpc.consumer.caller.ConsumerCaller;
import project.lanshan.javarpc.model.Request;



public class SpringConsumerBean implements InitializingBean{
	
	private static Logger log = Logger.getLogger(SpringConsumerBean.class.getName());
	private ConsumerCaller consumerCaller;
	private final AtomicBoolean inited = new AtomicBoolean(false);
	

	public SpringConsumerBean(){}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(!inited.compareAndSet(false, true)){
			log.error("Consumer has been inited!");
			return;
		}
		
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
	
}
