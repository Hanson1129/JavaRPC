package project.lanshan.javarpc.consumer.netty;

import project.lanshan.javarpc.model.RPCInetAddress;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.ServiceMetadata;

public interface Consumer {
	public void startCall(RPCInetAddress address) throws InterruptedException;

	public Object getObject(RPCInetAddress address);

	public void setRequest(Request request);

	public Class<?> getObjectType();

	public ServiceMetadata getMetadata();

	public void setMetadata(ServiceMetadata metadata);
}
