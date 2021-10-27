package cuponproject.alltheconnectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class ConnectionPool
{	
	private final int  NUM_OF_CONNECTIONS=10;
	private final String URL = "jdbc:mysql://localhost:3306/java project - coupon system?createDatabaseIfNotExist=TRUE";
	private final String USER = "root";
	private final String PASSWORD = "1234";
	private Stack<Connection> connections= new Stack<>();
	private static ConnectionPool singeltonInstance=null;
	public ConnectionPool() {
		for (int i = 0; i < NUM_OF_CONNECTIONS; i++) {
			try {
				Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				connections.push(con);
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
		
	}
	public static ConnectionPool getSingeltonInstance() {
		if (singeltonInstance==null)
			synchronized (ConnectionPool.class) {
			if (singeltonInstance==null) {
				singeltonInstance= new ConnectionPool();
			}	
			}
		return singeltonInstance;
	}
	public Connection getConnection()throws InterruptedException {
		synchronized (connections) {
			if (connections.isEmpty()) {
				connections.wait();
			}
		return connections.pop();
		}
	}
	public void restoreConnection(Connection con) {
		synchronized (connections) {
			connections.push(con);
			connections.notify();
		}
	}
	public void closeAllConnections() throws InterruptedException{
		synchronized (connections) {
			while (connections.size()<NUM_OF_CONNECTIONS) {
				connections.wait();
			}
			for (Connection connection : connections) {
				try {
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			
		}
	}
	@Override
	public String toString() {
		return "ConnectionPool [URL=" + URL + ", USER=" + USER + ", PASSWORD=" + PASSWORD + ", connections="
				+ connections + "]";
	}
	
}
