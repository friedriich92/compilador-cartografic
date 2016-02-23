package com.sitep.str.integration.in.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sitep.str.integration.in.UsuariService;

public class UsuariServiceImpl implements UsuariService {

	static Connection connectionUsuariService = null;
	
	public void DBConnection() {
		System.out.println("PostgreSQL " + "JDBC Connection Testing");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		connectionUsuariService = null;
		try {
			connectionUsuariService = DriverManager.getConnection(
					"jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (connectionUsuariService != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
	
	public void registerUser(String userInformation) throws IOException, SQLException {
		System.out.println("String field " + userInformation + " readed.");
		
		String[] dataArray =  userInformation.split(",");
		String username, password, email, role;
		username = password = email = role = "";
		int arrayIterator = 0;
		
		for (String s: dataArray) {
			if (arrayIterator == 1) username = s;
			else if (arrayIterator == 2) password = s;
			else if (arrayIterator == 3) email = s;
			else role = s;
			++arrayIterator;
		}
		
		PreparedStatement pstmt1 = null;
		try {
			DBConnection();
			String nomTaula = "ccusers";
			String columnsDest = "username, password, email, role";
			String sql1 = "INSERT INTO " + nomTaula + " (" + columnsDest + ") VALUES (?, ?, ?, ?)";
			System.out.println("SQL Statement: " + sql1);
			pstmt1 = connectionUsuariService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, password);
			pstmt1.setString(3, email);
			pstmt1.setString(4, role);
			pstmt1.executeUpdate();
		} catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		pstmt1.close();
	    		connectionUsuariService.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
	}

	public void registerUser2(String username, String password, String email, String role) throws IOException, SQLException {
		PreparedStatement pstmt1 = null;
		try {
			DBConnection();
			String nomTaula = "ccusers";
			String columnsDest = "username, password, email, role";
			String sql1 = "INSERT INTO " + nomTaula + " (" + columnsDest + ") VALUES (?, ?, ?, ?)";
			System.out.println("SQL Statement: " + sql1);
			pstmt1 = connectionUsuariService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, password);
			pstmt1.setString(3, email);
			pstmt1.setString(4, role);
			pstmt1.executeUpdate();
			pstmt1.close();
			connectionUsuariService.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt1.close();
				connectionUsuariService.close();
				} catch (SQLException e) {
					e.printStackTrace();
					}
		}
	}

	public int getUserConfirmation(String userInformation) throws SQLException {
		System.out.println("String field " + userInformation + " readed.");
		
		String[] dataArray =  userInformation.split(",");
		String username, password;
		username = password = "";
		int arrayIterator = 0;
		
		for (String s: dataArray) {
			if (arrayIterator == 1) username = s;
			else if (arrayIterator == 2) password = s;
			++arrayIterator;
		}

		ResultSet rs = null;
		PreparedStatement pstmt1 = null;
		try {
			DBConnection();
			String nomTaula = "ccusers";
			String sql1 = "SELECT COUNT(*) FROM " + nomTaula + " WHERE username = ? AND password = ?";
			System.out.println("SQL Statement: " + sql1);
			pstmt1 = connectionUsuariService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, password);
		    rs = pstmt1.executeQuery();
		    if (rs.next()) return rs.getInt(1);
		    else System.out.println("error: there's nobody named like this!");
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		    	try {
		    	  rs.close();
		    	  pstmt1.close();
		    	  connectionUsuariService.close();
		    	  } catch (SQLException e) {
		    		  e.printStackTrace();
		    		  }
		    	}
		return 0;
	}

}
