package com.sitep.str.integration.in.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sitep.str.integration.in.UsuariService;
import com.sitep.str.integration.in.classes.Usuari;

public class UsuariServiceImpl implements UsuariService<Usuari> {

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

	public String getFiles(String userName) {
		PreparedStatement pstmt1, pstmt2;
		pstmt1 = pstmt2 = null;
		String sql1, sql2, url, id, pass, finalResponse, almostFinalResponse, theFinalResponse;
		Connection conn = null;
		int countNumber2, numberOfRows, loopNumber;
		ResultSet rs, rs2;
 		url = "jdbc:postgresql://192.122.214.77:5432/osm";
 		id = "postgres";
 		pass = "SiteP0305";
		finalResponse = almostFinalResponse = theFinalResponse = "";
		rs = rs2 = null;
		numberOfRows = loopNumber = 0;
		countNumber2 = 10;
		String nomTaula = "loaded_objects";
    	try {
     		Class.forName("org.postgresql.Driver");
     		conn = java.sql.DriverManager.getConnection(url, id, pass);
     		// 1. #ARXIUS
    		sql1 = "SELECT COUNT(*) FROM " + nomTaula + " WHERE username = ?";
    		pstmt1 = conn.prepareStatement(sql1);
    		pstmt1.setString(1, userName);
    		rs = pstmt1.executeQuery();
    	    if (rs.next()) countNumber2 = rs.getInt(1);
    	} catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    	  rs.close();
	    	  pstmt1.close();
	    	  } catch (java.sql.SQLException e) {
	    		  e.printStackTrace();
	    		  }
	    	}
    	    try {
    	    	if (countNumber2 > 0) {
        		// 2. DADES DELS ARXIUS
        		sql1 = "SELECT filename, date, tablename FROM " + nomTaula + " WHERE username = ?";
        		pstmt1 = conn.prepareStatement(sql1);
        		pstmt1.setString(1, userName);
        		rs = pstmt1.executeQuery();
        	    while (rs.next()) { // 3. PER A CADA ARXIU
        	    	++loopNumber;
    				try {
    					almostFinalResponse += (rs.getString(1) + "'" + rs.getString(2) + "'");
            			String fileNameWithoutExtension = rs.getString(3);
        				// 3.1 COLUMNES DE LA TAULA DE L'ARXIU
            			sql2 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?"; // 2
            			pstmt2 = conn.prepareStatement(sql2);
            			pstmt2.setString(1, fileNameWithoutExtension.toLowerCase());
            			rs2 = pstmt2.executeQuery();
            			while (rs2.next())
            				almostFinalResponse += (rs2.getString(1) + ", ");
            			finalResponse += almostFinalResponse.substring(0, almostFinalResponse.length()-2);
            			// 3.2 FILES DE LA TAULA DE L'ARXIU
            		    sql2 = "SELECT COUNT(*) FROM " + fileNameWithoutExtension; // 2
            			pstmt2 = conn.prepareStatement(sql2);
            			rs2 = pstmt2.executeQuery();
            			if (rs2.next()) numberOfRows = rs2.getInt(1);
            			finalResponse += " (" + numberOfRows + ");";
            			almostFinalResponse = "";
    				} catch (Exception e) {
    				      e.printStackTrace();
    			    } finally {
    			    	try {
    			    	  rs2.close();
    			    	  pstmt2.close();
    			    	  } catch (java.sql.SQLException e) {
    			    		  e.printStackTrace();
    			    		  }
    			    	}
        	    }
        	    theFinalResponse = finalResponse.substring(0, finalResponse.length()-1);
    	    }
    	} catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		    	try {
		    	  rs.close();
		    	  pstmt1.close();
		    	  conn.close();
		    	  } catch (java.sql.SQLException e) {
		    		  e.printStackTrace();
		    		  }
		    	}
    	System.out.println("9. theFinalResponse: " + theFinalResponse);
    	return theFinalResponse;
	}

}
