package project.lanshan.javarpc.provider.publisher;

import project.lanshan.javarpc.provider.netty.Provider;


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
