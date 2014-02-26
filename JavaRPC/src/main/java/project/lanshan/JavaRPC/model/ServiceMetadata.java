package project.lanshan.JavaRPC.model;

public class ServiceMetadata {
	
	private String interfaceName = "";
	private String serviceName = "";
	private Class<?> serviceClass = null;
	private Object[] parameters;
	private String serializableWay = "Java";
	
	public ServiceMetadata(){}
	
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Class<?> getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(Class<?> serviceClass) {
		this.serviceClass = serviceClass;
	}
	public Object[] getParameters() {
		return parameters;
	}
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	public String getSerializableWay() {
		return serializableWay;
	}
	public void setSerializableWay(String serializableWay) {
		this.serializableWay = serializableWay;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
}
