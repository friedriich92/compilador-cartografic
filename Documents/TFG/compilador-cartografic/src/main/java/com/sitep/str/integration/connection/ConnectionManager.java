package com.sitep.str.integration.connection;

import java.sql.Connection;

import javax.sql.DataSource;


public class ConnectionManager {
	
//	@Autowired
	private static DataSource datasource;	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal<Connection> theConnection = new ThreadLocal();
	
	public static void setDatasource(DataSource datasource) {
		ConnectionManager.datasource = datasource;
	}
	public static void createConnection() {
		try {
			theConnection.set(datasource.getConnection()); 
		} catch (Exception ign) {
			throw new RuntimeException(ign);
		}
	}
	public static void closeConnection() {
		try {
			getConnection().close();
		} catch (Exception ign) {
			throw new RuntimeException(ign);
		}
	}
	public static Connection getConnection() {
		return theConnection.get();
	}
	
}
