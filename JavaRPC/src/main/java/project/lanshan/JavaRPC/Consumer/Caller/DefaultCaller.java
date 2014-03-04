package project.lanshan.JavaRPC.Consumer.Caller;

import project.lanshan.JavaRPC.Consumer.Netty.NettyConsumer;

public class DefaultCaller implements ConsumerCaller{

	private NettyConsumer consumer;
	@Override
	public boolean call() {
		return false;
	}

	@Override
	public Object getObject() {
		return consumer.getObject();
	}

	public NettyConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(NettyConsumer consumer) {
		this.consumer = consumer;
	}

}
