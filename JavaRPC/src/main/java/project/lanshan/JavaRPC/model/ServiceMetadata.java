package project.lanshan.JavaRPC.model;

public class ServiceMetadata {
	
	private String interfaceName;
	private String serviceName;
	private Object[] parameters;
	private String callWay;
	private String serializableWay;
	
	public ServiceMetadata(){
		this.interfaceName = "";
		this.serviceName = "";
		this.parameters = null;
		this.callWay = "serialization";
		this.serializableWay = "java";
	}
	
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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


	public String getCallWay() {
		return callWay;
	}


	public void setCallWay(String callWay) {
		this.callWay = callWay;
	}
	
}
