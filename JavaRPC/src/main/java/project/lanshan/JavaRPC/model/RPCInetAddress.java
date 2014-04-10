package project.lanshan.javarpc.model;

public class RPCInetAddress {
	private String host;
	private int port;
	public RPCInetAddress(){
		this.host = "127.0.0.1";
		this.port = 8879;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
