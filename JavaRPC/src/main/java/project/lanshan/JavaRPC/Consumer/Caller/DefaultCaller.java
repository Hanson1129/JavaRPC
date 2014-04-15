package project.lanshan.javarpc.consumer.caller;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;




import project.lanshan.javarpc.consumer.netty.Consumer;
import project.lanshan.javarpc.consumer.netty.NettyConsumer;
import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.RPCInetAddress;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.ServiceMetadata;

public class DefaultCaller implements InvocationHandler {

	private Consumer consumer = new NettyConsumer();
	private Class<?> clazz; 
	private RPCInetAddress targetAddress = null;

	public boolean call(AddressHolder addresses) {
		targetAddress = selectAddressFromHolder(addresses,
				consumer.getMetadata());
		try {
			consumer.startCall(targetAddress);
		} catch (InterruptedException e) {
			return false;
		}
		if (consumer.getObject(targetAddress) != null) {
			try {
				clazz = Class.forName(consumer.getMetadata().getInterfaceName());
			}catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			return true;
		} else
			return false;
	}

	private RPCInetAddress selectAddressFromHolder(AddressHolder addresses,
			ServiceMetadata metadata) {
		// TODO for test
		return addresses.getAddresses(metadata.getInterfaceName()).get(0);
	}

	public Object getObject() {
	  return Proxy.newProxyInstance(clazz.getClassLoader(),
          new Class<?>[]{ clazz }, this);
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public void setRequest(Request request) {
		consumer.setRequest(request);
	}

	public Class<?> getObjectType() {
		return consumer.getObjectType();
	}

	public ServiceMetadata getMetadata() {
		return consumer.getMetadata();
	}

	public void setMetadata(ServiceMetadata metadata) {
		consumer.setMetadata(metadata);
	}

	@Override
	public Object invoke(Object target, Method method, Object[] args)
			throws Throwable {
		return consumer.getObject(targetAddress);
	}

}
