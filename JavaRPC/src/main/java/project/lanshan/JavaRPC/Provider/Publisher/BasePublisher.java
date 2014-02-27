package project.lanshan.JavaRPC.Provider.Publisher;

import project.lanshan.JavaRPC.model.ServiceMetadata;

public abstract class BasePublisher implements ProviderPublisher{
	
	private ServiceMetadata metadata;
	
	@Override
	public Boolean publish() {
		if(metadata.getCallWay().equals("object"))
			return publishInSerialization();
		else if(metadata.getCallWay().equals("result"))
			return publishInReflection();
		else
			return false;
	}
	
	abstract Boolean publishInReflection();

	abstract Boolean publishInSerialization(); 

	public ServiceMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(ServiceMetadata metadata) {
		this.metadata = metadata;
	}

}
