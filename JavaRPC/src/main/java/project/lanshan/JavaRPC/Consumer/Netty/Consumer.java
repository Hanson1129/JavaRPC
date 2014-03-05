package project.lanshan.JavaRPC.Consumer.Netty;

public interface Consumer {
	public void startCall() throws InterruptedException ;
	public Object getObject();
}
