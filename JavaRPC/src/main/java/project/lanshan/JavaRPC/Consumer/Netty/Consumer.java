package project.lanshan.JavaRPC.Consumer.Netty;

import project.lanshan.JavaRPC.model.Request;

public interface Consumer {
	public void startCall() throws InterruptedException ;
	public Object getObject();
	public void setRequest(Request request);
}
