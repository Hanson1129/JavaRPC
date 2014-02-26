package project.lanshan.JavaRPC.model;

public class Request {
	
	private String className;
	private String serviceName;
	private Object[] parameters;
	
	Request(){}
	
	Request(String className,String serviceName,Object[] parameters){
		this.className = className;
		this.serviceName = serviceName;
		this.parameters = parameters;
	}
	

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	
}
