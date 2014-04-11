package project.lanshan.javarpc.zookeeper;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import project.lanshan.javarpc.model.ServiceMetadata;

@Service
public class ZookeeperServiceImpl implements ZookeeperService {

  private ZookeeperUtil zkUtil;
  private static Logger logger = Logger.getLogger(ZookeeperServiceImpl.class);

  @Override
  public boolean register(ServiceMetadata metadata) {
    AtomicBoolean result = new AtomicBoolean(false);
    try {
      zkUtil.createConnection();
      result.compareAndSet(
          false,
          zkUtil.createPath(metadata.getServiceName(),
              metadata.getHost() + ":" + metadata.getPort()));
      zkUtil.releaseConnection();
    } catch (IOException e) {
      logger.error("fail to connect zk server!");
    } catch (InterruptedException e) {
      logger.error("fail to release zk connection!");
    }
    return result.get();
  }

  @Override
  public boolean subscribe(ServiceMetadata metadata) {
    AtomicBoolean result = new AtomicBoolean(false);
    String serverAddress;

    try {
      zkUtil.createConnection();

      serverAddress = zkUtil.readData(metadata.getServiceName());

      if (validateAddress(serverAddress, metadata))
        result.compareAndSet(false, true);

      zkUtil.releaseConnection();
    } catch (IOException e) {
      logger.error("fail to connect zk server!");
    } catch (InterruptedException e) {
      logger.error("fail to release zk connection!");
    }
    return result.get();
  }

  public boolean validateAddress(String address, ServiceMetadata metadata) {
    String host = address.substring(0, address.indexOf(":"));
    int port = Integer.parseInt(address.substring(address.indexOf(":"), address.length()));
    if (host.length() > 0 && port != 0) {
      metadata.setHost(host);
      metadata.setPort(port);
      return true;
    }
    return false;
  }
}
