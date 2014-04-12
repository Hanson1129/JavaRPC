package project.lanshan.javarpc.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import project.lanshan.javarpc.model.RPCInetAddress;
import project.lanshan.javarpc.model.ServiceMetadata;

public class ZookeeperUtil {

	private static Logger logger = Logger.getLogger(ZookeeperUtil.class);
	private static final int SESSION_TIMEOUT = 10000;
	private static final String serverString = "127.0.0.1:2181";
	private static ZooKeeper zookeeper;

	/**
	 * 创建ZK连接
	 * 
	 * @param connectString
	 *            ZK服务器地址列表
	 * @param sessionTimeout
	 *            Session超时时间
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void createConnection() throws IOException, InterruptedException {
		releaseConnection();
		zookeeper = new ZooKeeper(serverString, SESSION_TIMEOUT, null);
	}

	/**
	 * 关闭ZK连接
	 * 
	 * @throws InterruptedException
	 */
	public void releaseConnection() throws InterruptedException {
		if (zookeeper != null) {
			zookeeper.close();
		}
	}

	/**
	 * 创建节点
	 * 
	 * @param path
	 *            节点path
	 * @param data
	 *            初始数据内容
	 * @return
	 */
	public boolean registerProvider(ServiceMetadata metadata) {
		try {
			if (!exits(metadata.getServiceName()))
				zookeeper.create(metadata.getServiceName(),
						"provider".getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.EPHEMERAL);
			String path = zookeeper.create(metadata.getServiceName() + "/"
					+ metadata.getHost() + ":" + metadata.getPort(),
					"provider".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
			if (path.length() > 0)
				return true;
			else
				return false;
		} catch (KeeperException e) {
			logger.error("节点创建失败，发生KeeperException");
		} catch (InterruptedException e) {
			logger.error("节点创建失败，发生 InterruptedException");
		}
		return true;
	}

	/**
	 * 读取指定节点数据内容
	 * 
	 * @param path
	 *            节点path
	 * @return
	 */
	public List<RPCInetAddress> getProviders(String serviceName) {
		List<RPCInetAddress> addresses = new ArrayList<RPCInetAddress>();
		try {
			List<String> addressesString = zookeeper.getChildren(serviceName, false);
			for(String address : addressesString){
				String host = address.substring(0,address.indexOf(":"));
				int port = Integer.parseInt(address.substring(address.indexOf(":"),address.length()));
				if(host.length() > 0 && port != 0)
				addresses.add(new RPCInetAddress(host, port));
			}
		return addresses;
		} catch (KeeperException e) {
			logger.error("读取数据失败，发生KeeperException，path: " + serviceName);
			return null;
		} catch (InterruptedException e) {
			logger.error("读取数据失败，发生 InterruptedException，path: " + serviceName);
			return null;
		}
	}

	/**
	 * 更新指定节点数据内容
	 * 
	 * @param path
	 *            节点path
	 * @param data
	 *            数据内容
	 * @return
	 */
	public boolean updateAddress(ServiceMetadata metadata) {
		deleteNode(metadata.getServiceName() + "/" + metadata.getHost() + ":"
				+ metadata.getPort());
		if (registerProvider(metadata))
			return true;
		else
			return false;
	}

	/**
	 * 删除指定节点
	 * 
	 * @param path
	 *            节点path
	 */
	public void deleteNode(String path) {
		try {
			zookeeper.delete(path, -1);
		} catch (KeeperException e) {
			logger.error("删除节点失败，发生KeeperException，path: " + path);
		} catch (InterruptedException e) {
			logger.error("删除节点失败，发生 InterruptedException，path: " + path);
		}
	}

	/**
	 * 判断节点是否存在
	 * 
	 * @param path
	 * 
	 */
	public boolean exits(String path) {
		try {
			if (zookeeper.exists(path, false) != null)
				return true;
			else
				return false;
		} catch (KeeperException | InterruptedException e) {
			return false;
		}
	}

}
