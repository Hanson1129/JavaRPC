package project.lanshan.javarpc.consumer.caller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.lanshan.javarpc.consumer.netty.Consumer;
import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.RPCInetAddress;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.ServiceMetadata;

@Service
public class DefaultCaller implements ConsumerCaller {

	@Autowired
	private Consumer consumer;
	private RPCInetAddress targetAddress = null;
	
	public DefaultCaller() {
	}

	@Override
	public boolean call(AddressHolder addresses) {
		targetAddress = selectAddressFromHolder(addresses,consumer.getMetadata());
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

	private RPCInetAddress selectAddressFromHolder(AddressHolder addresses,ServiceMetadata metadata) {
		//TODO for test
		return addresses.getAddresses(metadata.getServiceName()).get(0);
	}

	@Override
	public Object getObject(AddressHolder addresses) {
		if(targetAddress != null)
		return consumer.getObject(targetAddress);
		else{
			targetAddress = selectAddressFromHolder(addresses,consumer.getMetadata());
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
