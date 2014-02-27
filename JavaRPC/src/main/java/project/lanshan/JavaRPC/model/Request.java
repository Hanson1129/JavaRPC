package project.lanshan.JavaRPC.model;

import java.io.Serializable;

public class Request implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5308860507944813424L;
	private String className;
	private String serviceName;
	private Object[] parameters;
	private String callWay;
	
	Request(){}
	
	Request(String className,String serviceName,Object[] parameters,String callWay){
		this.className = className;
		this.serviceName = serviceName;
		this.parameters = parameters;
		this.callWay = callWay;
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

	public String getCallWay() {
		return callWay;
	}

	public void setCallWay(String callWay) {
		this.callWay = callWay;
	}
	
}
