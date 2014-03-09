package project.lanshan.JavaRPC.model;

public class ServiceMetadata {
	
	private String serviceClass;
	private String serviceName;
	private String serializableWay;
	
	public ServiceMetadata(){
		this.serviceClass = "";
		this.serviceName = "";
		this.serializableWay = "Netty";
	}
	
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSerializableWay() {
		return serializableWay;
	}
	public void setSerializableWay(String serializableWay) {
		this.serializableWay = serializableWay;
	}


	public String getServiceClass() {
		return serviceClass;
	}


	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
}
