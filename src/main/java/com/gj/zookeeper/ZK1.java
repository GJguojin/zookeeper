package com.gj.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZK1 {
	private static String connectString = "127.0.0.1:2181";
	private static int sessionTimeout = 999999;

	public static void main(String[] args) throws Exception {
		Watcher watcher = new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("监听到的事件：" + event);
			}
		};
		final ZooKeeper zookeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
		System.out.println("获得连接：" + zookeeper);
		Stat exists = zookeeper.exists("/zk1", true);
		if(exists != null){
			final byte[] data = zookeeper.getData("/zk1", watcher, null);
			System.out.println("读取的值：" + new String(data));
		}
		zookeeper.create("/zk2", "test2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zookeeper.close();
	}
}