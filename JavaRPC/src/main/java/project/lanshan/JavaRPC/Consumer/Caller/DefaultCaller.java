package project.lanshan.javarpc.consumer.caller;

import project.lanshan.javarpc.consumer.netty.Consumer;
import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.RPCInetAddress;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.ServiceMetadata;

public class DefaultCaller implements ConsumerCaller {

	private Consumer consumer;
	private RPCInetAddress targetAddress = null;
	
	public DefaultCaller() {
	}

	@Override
	public boolean call(AddressHolder addresses) {
		targetAddress = selectAddressFromHolder(addresses);
		try {
			consumer.startCall(targetAddress);
		} catch (InterruptedException e) {
			return false;
		}
		if (consumer.getObject(targetAddress) != null)
			return true;
		else
			return false;
	}

	private RPCInetAddress selectAddressFromHolder(AddressHolder addresses) {
		// TODO 这里缺少选址算法 
		return null;
	}

	@Override
	public Object getObject(AddressHolder addresses) {
		if(targetAddress != null)
		return consumer.getObject(targetAddress);
		else{
			targetAddress = selectAddressFromHolder(addresses);
			return consumer.getObject(targetAddress);
		}
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
	public Class<?> getObjectType() {
		return consumer.getObjectType();
	}

	@Override
	public ServiceMetadata getMetadata() {
		return consumer.getMetadata();
	}

	@Override
	public void setMetadata(ServiceMetadata metadata) {
		consumer.setMetadata(metadata);
	}

}
