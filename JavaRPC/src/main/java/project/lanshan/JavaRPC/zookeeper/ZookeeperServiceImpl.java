package project.lanshan.javarpc.zookeeper;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.ServiceMetadata;

@Service
public class ZookeeperServiceImpl implements ZookeeperService {

	private ZookeeperUtil zkUtil = new ZookeeperUtil();
	private static Logger logger = Logger.getLogger(ZookeeperServiceImpl.class);

	@Override
	public boolean register(ServiceMetadata metadata) {
		AtomicBoolean result = new AtomicBoolean(false);
		try {
			zkUtil.createConnection();
			result.compareAndSet(false, zkUtil.registerProvider(metadata));
			zkUtil.releaseConnection();
		} catch (IOException e) {
			logger.error("fail to connect zk server!");
		} catch (InterruptedException e) {
			logger.error("fail to release zk connection!");
		}
		return result.get();
	}

	@Override
	public AddressHolder subscribe(ServiceMetadata metadata) {
		AddressHolder addressHolder = new AddressHolder();
		try {
			zkUtil.createConnection();

			addressHolder.putAddresses(metadata.getInterfaceName(),zkUtil.getProviders(metadata.getInterfaceName()));

			zkUtil.releaseConnection();
			if(addressHolder.size() > 0)
				return addressHolder;
		} catch (IOException e) {
			logger.error("fail to connect zk server!");
		} catch (InterruptedException e) {
			logger.error("fail to release zk connection!");
		}
		return addressHolder;
	}

	
}
