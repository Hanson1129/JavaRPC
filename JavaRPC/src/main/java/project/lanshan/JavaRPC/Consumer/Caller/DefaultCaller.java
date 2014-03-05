package project.lanshan.JavaRPC.Consumer.Caller;

import project.lanshan.JavaRPC.Consumer.Netty.Consumer;

public class DefaultCaller implements ConsumerCaller{

	private Consumer consumer;
	
	
	@Override
	public boolean call() {
		return false;
	}

	@Override
	public Object getObject() {
		return consumer.getObject();
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

}
