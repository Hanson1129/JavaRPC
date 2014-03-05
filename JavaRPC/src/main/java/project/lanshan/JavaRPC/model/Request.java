package project.lanshan.JavaRPC.model;

import java.io.Serializable;

public class Request implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5308860507944813424L;
	private String className;
	private String serviceName;
	private Class<?>[] parametersClass;
	private Object[] parametersObject;
	private String callWay;
	
	
	public Request(){
		this.className = "";
		this.serviceName = "";
		this.parametersClass = null;
		this.parametersObject = null;
		this.callWay = "result";
	}
	
	Request(String className,String serviceName,Class<?>[] parametersClass,Object[] parametersObject,String callWay){
		this.className = className;
		this.serviceName = serviceName;
		this.parametersClass = parametersClass;
		this.parametersObject = parametersObject;
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
	public String getCallWay() {
		return callWay;
	}

	public void setCallWay(String callWay) {
		this.callWay = callWay;
	}

	public Class<?>[] getParametersClass() {
		return parametersClass;
	}

	public void setParametersClass(Class<?>[] parametersClass) {
		this.parametersClass = parametersClass;
	}

	public Object[] getParametersObject() {
		return parametersObject;
	}

	public void setParametersObject(Object[] parametersObject) {
		this.parametersObject = parametersObject;
	}
	
}
