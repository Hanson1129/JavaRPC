package project.lanshan.javarpc.consumer.caller;

import project.lanshan.javarpc.consumer.netty.Consumer;
import project.lanshan.javarpc.model.Request;



public class DefaultCaller implements ConsumerCaller{

	private Consumer consumer;
	
	public DefaultCaller(){}
	
	@Override
	public boolean call() {
		return false;
	}

	@Override
	public Object getObject(){
		return consumer.getObject();
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	@Override
	public void setRequest(Request request) {
		consumer.setRequest(request);
	}


}
