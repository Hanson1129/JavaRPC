package project.lanshan.JavaRPC.Consumer.Caller;

import project.lanshan.JavaRPC.Consumer.Netty.Consumer;
import project.lanshan.JavaRPC.model.Request;

public class DefaultCaller implements ConsumerCaller{

	private Consumer consumer;
	
	
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

	@Override
	public String getClassName() {
		return consumer.getClassName();
	}

}
