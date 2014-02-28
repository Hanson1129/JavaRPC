package project.lanshan.JavaRPC.Provider.Publisher;

import project.lanshan.JavaRPC.Provider.Netty.NettyProvider;
import project.lanshan.JavaRPC.model.RPCInetAddress;
import project.lanshan.JavaRPC.model.ServiceMetadata;

public class DefaultPublisher implements ProviderPublisher{
	
	private NettyProvider provider;
	
	public DefaultPublisher(ServiceMetadata metadata,RPCInetAddress providerAddress){
		provider = new NettyProvider(metadata,providerAddress);
	}
	
	@Override
	public Boolean publish() {
		return provider.startPublish();
	}
	

	public NettyProvider getProvider() {
		return provider;
	}

	public void setProvider(NettyProvider provider) {
		this.provider = provider;
	} 

}
