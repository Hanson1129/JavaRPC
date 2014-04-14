package project.lanshan.javarpc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AddressHolder {
	private ConcurrentHashMap<String,List<RPCInetAddress>> ServicesAddresses = new ConcurrentHashMap<String,List<RPCInetAddress>>();
	
	public void putAddress(String interfaceName,RPCInetAddress address){
		List<RPCInetAddress> serviceAddresses = ServicesAddresses.get(interfaceName);
		if(serviceAddresses == null){
			serviceAddresses = new ArrayList<RPCInetAddress>();
		}
		serviceAddresses.add(address);
		ServicesAddresses.putIfAbsent(interfaceName, serviceAddresses);
	}
	
	public void putAddresses(String interfaceName,List<RPCInetAddress> addresses){
		ServicesAddresses.putIfAbsent(interfaceName, addresses);
	}
	public List<RPCInetAddress> getAddresses(String interfaceName){
		return ServicesAddresses.get(interfaceName);
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

