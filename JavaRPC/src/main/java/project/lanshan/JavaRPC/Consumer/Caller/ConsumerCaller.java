package project.lanshan.JavaRPC.Consumer.Caller;

import project.lanshan.JavaRPC.model.Request;

public interface ConsumerCaller {
	public boolean call();
	public Object getObject();
	public void setRequest(Request request);
}
