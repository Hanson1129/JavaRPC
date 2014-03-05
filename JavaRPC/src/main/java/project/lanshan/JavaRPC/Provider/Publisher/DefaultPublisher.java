package project.lanshan.JavaRPC.Provider.Publisher;

import project.lanshan.JavaRPC.Provider.Netty.Provider;

public class DefaultPublisher implements ProviderPublisher{
	
	private Provider provider;
	
	@Override
	public Boolean publish() {
		return provider.startPublish();
	}
	

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	} 

}
