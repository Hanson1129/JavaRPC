package project.lanshan.JavaRPC.model;

import java.io.Serializable;

public class Response implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1405236342156451097L;
	private Object ResponseObject;
	
	public Response(Object ResponseObject){
		this.ResponseObject = ResponseObject;
	}

	public Object getResponseObject() {
		return ResponseObject;
	}

	public void setResponseObject(Object responseObject) {
		ResponseObject = responseObject;
	}
}
