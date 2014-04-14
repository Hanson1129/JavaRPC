package project.lanshan.javarpc.model;

import java.io.Serializable;

public class Request implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5308860507944813424L;
	private String interfaceName;
	private String serviceName;
	private Class<?>[] parametersClass;
	private Object[] parametersObject;
	private String callWay;
	
	
	public Request(){
		this.interfaceName = null;
		this.parametersClass = null;
		this.parametersObject = null;
		this.callWay = "result";
	}
	
	Request(String interfaceName,Class<?>[] parametersClass,Object[] parametersObject,String callWay){
		this.interfaceName = interfaceName;
		this.parametersClass = parametersClass;
		this.parametersObject = parametersObject;
		this.callWay = callWay;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
}
