package project.lanshan.JavaRPC.Provider.Publisher;

import project.lanshan.JavaRPC.model.ServiceMetadata;

public abstract class ResultPublisher implements ProviderPublisher{
	
	private ServiceMetadata metadata;
	public Boolean publish() {
		return null;
	}
	
	public ServiceMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(ServiceMetadata metadata) {
		this.metadata = metadata;
	}

}
