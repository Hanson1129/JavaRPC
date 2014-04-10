package project.lanshan.javarpc.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperUtil {

	private static final int SESSION_TIMEOUT = 10000;
	private static final String serverString = "127.0.0.1:2181";
	private ZooKeeper zookeeper;

	/**
	 * 创建ZK连接
	 * 
	 * @param connectString
	 *            ZK服务器地址列表
	 * @param sessionTimeout
	 *            Session超时时间
	 */
	public void createConnection() {
		this.releaseConnection();
		try {
			zookeeper = new ZooKeeper(serverString, SESSION_TIMEOUT, null);
		} catch (IOException e) {
			System.out.println("连接创建失败，发生 IOException");
			e.printStackTrace();
		}
	}

	/**
	 * 关闭ZK连接
	 */
	public void releaseConnection() {
		if (zookeeper != null) {
			try {
				this.zookeeper.close();
			} catch (InterruptedException e) {
				// ignore
				e.printStackTrace();
			}
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
	public boolean createPath(String path, String data) {
		try {
			System.out.println("节点创建成功, Path: "
					+ this.zookeeper.create(path, data.getBytes(),
							Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL)
					+ ", content: " + data);
		} catch (KeeperException e) {
			System.out.println("节点创建失败，发生KeeperException");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("节点创建失败，发生 InterruptedException");
			e.printStackTrace();
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
	public String readData(String path) {
		try {
			System.out.println("获取数据成功，path：" + path);
			return new String(this.zookeeper.getData(path, false, null));
		} catch (KeeperException e) {
			System.out.println("读取数据失败，发生KeeperException，path: " + path);
			e.printStackTrace();
			return "";
		} catch (InterruptedException e) {
			System.out.println("读取数据失败，发生 InterruptedException，path: " + path);
			e.printStackTrace();
			return "";
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
	public boolean writeData(String path, String data) {
		try {
			System.out.println("更新数据成功，path：" + path + ", stat: "
					+ this.zookeeper.setData(path, data.getBytes(), -1));
		} catch (KeeperException e) {
			System.out.println("更新数据失败，发生KeeperException，path: " + path);
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("更新数据失败，发生 InterruptedException，path: " + path);
			e.printStackTrace();
		}
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
			this.zookeeper.delete(path, -1);
			System.out.println("删除节点成功，path：" + path);
		} catch (KeeperException e) {
			System.out.println("删除节点失败，发生KeeperException，path: " + path);
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("删除节点失败，发生 InterruptedException，path: " + path);
			e.printStackTrace();
		}
	}

}
