package project.lanshan.javarpc.provider.publisher;

import project.lanshan.javarpc.model.ServiceMetadata;
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


  @Override
  public void setHost(String host) {
    provider.setHost(host);
  }


  @Override
  public void setPost(int port) {
    provider.setPort(port);
  }


  @Override
  public ServiceMetadata getMetadata() {
    return provider.getMeatadata();
  } 

}
