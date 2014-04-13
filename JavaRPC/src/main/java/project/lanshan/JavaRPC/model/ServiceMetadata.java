package project.lanshan.javarpc.model;

public class ServiceMetadata {
	
	private String serviceClass;
	private String serviceName;
	private Class<?> clazz;
	private String serializableWay;
	private String host;
	private String callWay;
	private int port;
	
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


  public String getHost() {
    return host;
  }


  public void setHost(String host) {
    this.host = host;
  }


  public int getPort() {
    return port;
  }


  public void setPort(int port) {
    this.port = port;
  }


  public Class<?> getClazz() {
    return clazz;
  }


  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }


public String getCallWay() {
	return callWay;
}


public void setCallWay(String callWay) {
	this.callWay = callWay;
}
}
