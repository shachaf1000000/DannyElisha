package com.group6.couponSystem.dbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ConnectionPool {
	private String dbName = "couponSystem";
	private String userName = "root";
	private String password = "1234";
	private String connectionString = "jdbc:mysql://localhost/" + dbName + "?user=" + userName + "&password="
			+ password;
	private static Set<Connection> connections = new HashSet<>();
	private static ConnectionPool instance = null;
	private final int num_of_connections = 10;

	private ConnectionPool() {
		try {
			for (int i = 0; i < num_of_connections; i++) {
				connections.add(DriverManager.getConnection(connectionString));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionPool getInstance() {
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
		// log
		System.out.println("checking if theres an available connection...");
		while (connections.size() == 0) {
			try {
				// log
				System.out.println("there is no available connection, waiting...");
				connections.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized (connections) {
			// log
			System.out.println("connection available.");
			Connection connection = connections.iterator().next();
			connections.remove(connection);
			// log
			System.out.println("connection returned.");
			return connection;
		}
	}

	public void restoreConnection(Connection connection) {
		synchronized (connections) {
			// log
			System.out.println("connection returned added to set.");
			connections.add(connection);
			// log
			System.out.println("notified all.");
			connections.notifyAll();
		}
	}

	public void closeAllConnections() {
		// log
		System.out.println("checking size of the set");
		while (connections.size() != 5) {
			try {
				// log
				System.out.println("not all connections here, waiting...");
				connections.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// log
		System.out.println("all connections closed.");
		connections.forEach(connection -> connections.remove(connection));
	}

}
