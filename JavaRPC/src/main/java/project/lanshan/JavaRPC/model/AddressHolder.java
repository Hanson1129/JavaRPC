package project.lanshan.javarpc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AddressHolder {
	private ConcurrentHashMap<String,List<RPCInetAddress>> ServicesAddresses = new ConcurrentHashMap<String,List<RPCInetAddress>>();
	
	public void putAddress(String serviceName,RPCInetAddress address){
		List<RPCInetAddress> serviceAddresses = ServicesAddresses.get(serviceName);
		if(serviceAddresses == null){
			serviceAddresses = new ArrayList<RPCInetAddress>();
		}
		serviceAddresses.add(address);
		ServicesAddresses.putIfAbsent(serviceName, serviceAddresses);
	}
	
	public void putAddresses(String serviceName,List<RPCInetAddress> addresses){
		ServicesAddresses.putIfAbsent(serviceName, addresses);
	}
	public List<RPCInetAddress> getAddresses(String serviceName){
		return ServicesAddresses.get(serviceName);
	}
	public static boolean validateAddress(String address) {
		String host = address.substring(0, address.indexOf(":"));
		int port = Integer.parseInt(address.substring(address.indexOf(":"),
				address.length()));
		if (host.length() > 0 && port != 0) {
			return true;
		}
		return false;
	}
	public int size(){
		return ServicesAddresses.size();
	}
}

