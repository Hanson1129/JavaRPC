package project.lanshan.javarpc.zookeeper;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperUtil {

  private static Logger logger = Logger.getLogger(ZookeeperUtil.class);
  private static final int SESSION_TIMEOUT = 10000;
  private static final String serverString = "127.0.0.1:2181";
  private static ZooKeeper zookeeper;

  /**
   * 创建ZK连接
   * 
   * @param connectString ZK服务器地址列表
   * @param sessionTimeout Session超时时间
   * @throws IOException
   * @throws InterruptedException 
   */
  public void createConnection() throws IOException, InterruptedException {
    releaseConnection();
    zookeeper = new ZooKeeper(serverString, SESSION_TIMEOUT, null);
  }

  /**
   * 关闭ZK连接
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
   * @param path 节点path
   * @param data 初始数据内容
   * @return
   */
  public boolean createPath(String path, String data) {
    try {
      System.out.println("节点创建成功, Path: "
          + zookeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL)
          + ", content: " + data);
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
   * @param path 节点path
   * @return
   */
  public String readData(String path) {
    try {
      return new String(zookeeper.getData(path, false, null));
    } catch (KeeperException e) {
      logger.error("读取数据失败，发生KeeperException，path: " + path);
      return "";
    } catch (InterruptedException e) {
      logger.error("读取数据失败，发生 InterruptedException，path: " + path);
      return "";
    }
  }

  /**
   * 更新指定节点数据内容
   * 
   * @param path 节点path
   * @param data 数据内容
   * @return
   */
  public boolean updateData(String path, String data) {
    try {
      System.out.println("更新数据成功，path：" + path + ", stat: "
          + zookeeper.setData(path, data.getBytes(), -1));
    } catch (KeeperException e) {
      logger.error("更新数据失败，发生KeeperException，path: " + path);
    } catch (InterruptedException e) {
      logger.error("更新数据失败，发生 InterruptedException，path: " + path);
    }
    return false;
  }

  /**
   * 删除指定节点
   * 
   * @param path 节点path
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

}
