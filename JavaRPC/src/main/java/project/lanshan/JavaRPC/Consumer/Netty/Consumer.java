package project.lanshan.javarpc.consumer.netty;

import project.lanshan.javarpc.model.Request;


public interface Consumer {
	public void startCall() throws InterruptedException ;
	public Object getObject();
	public void setRequest(Request request);
}
