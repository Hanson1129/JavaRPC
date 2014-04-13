package project.lanshan.javarpc.model;

import java.io.Serializable;

public class Response implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1405236342156451097L;
	private Object ResponseObject;
	Class<?> clazz;
	public Response(Object ResponseObject,Class<?> clazz){
		this.ResponseObject = ResponseObject;
		this.clazz = clazz;
	}

	public Object getResponseObject() {
		return ResponseObject;
	}

	public void setResponseObject(Object responseObject) {
		ResponseObject = responseObject;
	}

	public Class<?> getObjectClzss() {
		return clazz;
	}

}
