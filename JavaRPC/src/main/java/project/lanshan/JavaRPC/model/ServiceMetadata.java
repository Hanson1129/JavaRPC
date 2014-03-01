package project.lanshan.JavaRPC.model;

public class ServiceMetadata {
	
	private String serviceClass;
	private String serviceName;
	private String serializableWay;
	
	public ServiceMetadata(){
		this.setServiceClass("");
		this.serviceName = "";
		this.serializableWay = "java";
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
