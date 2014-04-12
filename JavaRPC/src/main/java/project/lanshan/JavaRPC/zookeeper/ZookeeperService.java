package project.lanshan.javarpc.zookeeper;

import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.ServiceMetadata;

public interface ZookeeperService {
	public boolean register(ServiceMetadata metadata);

	public AddressHolder subscribe(ServiceMetadata metadata);
}
