package project.lanshan.javarpc.consumer.caller;

import project.lanshan.javarpc.model.Request;


public interface ConsumerCaller {
	public boolean call();
	public Object getObject();
	public void setRequest(Request request);
}
