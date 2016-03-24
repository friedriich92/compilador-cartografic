package com.sitep.str.integration.in.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sitep.str.integration.in.FitxerService;
import com.sitep.str.integration.in.IdiomaService;
import com.sitep.str.integration.in.UsuariService;
import com.sitep.str.integration.in.classes.Idioma;
import com.sitep.str.integration.in.classes.RolDeUsuari;
import com.sitep.str.integration.in.classes.Usuari;

public class UsuariServiceImpl implements UsuariService {

	static Connection connectionUsuariService = null;
	FitxerService importarFitxer = new FitxerServiceImpl();
	
	public int registerUser(String userInformation) throws IOException, SQLException {
		System.out.println("String field " + userInformation + " readed.");
		Connection conn = null;
		ResultSet rs = null;
		
		String[] dataArray =  userInformation.split(",");
		String username, password, email, role, client;
		username = password = email = role = client = "";
		int arrayIterator, count, responseValue;
		arrayIterator = count = responseValue = 0;
		
		for (String s: dataArray) {
			if (arrayIterator == 1) username = s;
			else if (arrayIterator == 2) password = s;
			else if (arrayIterator == 3) email = s;
			else if (arrayIterator == 4) role = s;
			else client = s;
			++arrayIterator;
		}
		System.out.println(username + password + email + role);
		PreparedStatement pstmt1 = null;
		try {
			Class.forName("org.postgresql.Driver");
     		conn = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			// Existeix el client?
     		String sql1 = "SELECT COUNT(*) FROM client WHERE identificadordeclient = ?";
    		pstmt1 = conn.prepareStatement(sql1);
    		pstmt1.setString(1, client);
    		rs = pstmt1.executeQuery();
    	    if (rs.next()) count = rs.getInt(1);
			if (count == 1) { // Existeix el client
				System.out.println("Existeix el client");
	     		sql1 = "SELECT COUNT(*) FROM usuari WHERE identificadordeusuari = ?";
	    		pstmt1 = conn.prepareStatement(sql1);
	    		pstmt1.setString(1, username);
	    		rs = pstmt1.executeQuery();
	    	    if (rs.next()) count = rs.getInt(1);
	    	    if (count == 0) { // No existeix l'usuari => Afegir Usuari
	    	    	System.out.println("No existeix l'usuari");
	    	    	addUsuari(new Usuari(username, password, email, false, new RolDeUsuari(role), client));
	    			responseValue = 1; // Usuari creat
	    	    }
	    	    else {
	    	    	responseValue = 2; // Ja existeix l'usuari
	    	    	System.out.println("Ja existeix l'usuari");
	    	    }
			}
			else { // No existeix el client
				System.out.println("No existeix el client");
				responseValue = 3;
			}
		} catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		pstmt1.close();
	    		conn.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
		return responseValue;
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
		Connection connectionUsuariService = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			connectionUsuariService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			String sql1 = "SELECT COUNT(*) FROM usuari WHERE identificadordeusuari = ? AND contrasenya = ?";
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
		boolean does2Exist;
		String name2Use, fileNameEdit, fileNameEdit2, sql1, sql2, finalResponse, almostFinalResponse, 
		theFinalResponse, exactName, extensioDeFitxer, information;
		PreparedStatement pstmt1, pstmt2;
		int countNumber2, numberOfRows, loopNumber;
		ResultSet rs, rs2;
		// Inicialització
		does2Exist = false;
		finalResponse = fileNameEdit = fileNameEdit2 = almostFinalResponse = theFinalResponse = sql2
				= name2Use = exactName = extensioDeFitxer = information = "";
		rs = rs2 = null;
		pstmt1 = pstmt2 = null;
		Connection conn = null;
		numberOfRows = loopNumber = 0;
		countNumber2 = importarFitxer.fitxersPerUsuari(userName); // 1. #ARXIUS
     	System.out.println("countNumber2: " + countNumber2);
    	try {
    		if (countNumber2 > 0) {
    			Class.forName("org.postgresql.Driver");
    			conn = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");

    			// 2. DADES DELS ARXIUS
	        	sql1 = "SELECT idfitxer, date, nomfitxer, extensiodefitxer, info FROM fitxer WHERE idusuari = ? AND numerodeversio = ?";
	        	pstmt1 = conn.prepareStatement(sql1);
	        	pstmt1.setString(1, userName);
	        	pstmt1.setInt(2, 1);
	        	rs = pstmt1.executeQuery();
	        	while (rs.next()) { // 3. PER A CADA ARXIU
	        		++loopNumber;
	    			try {
	    				fileNameEdit = rs.getString(1);
	    				System.out.println("fileNameEdit: " + fileNameEdit + " contains: " +
	    						fileNameEdit.contains(userName));
	    				if (fileNameEdit.contains(userName)) fileNameEdit2 = fileNameEdit.replace(userName, "");
	    				System.out.println("New fileNameEdit: " + fileNameEdit2);
	    				almostFinalResponse += (fileNameEdit2 + "'" + rs.getString(2) + "'");
	            		exactName = rs.getString(3);
	            		extensioDeFitxer = rs.getString(4);
	            		information = rs.getString(5);
	            		if (extensioDeFitxer.equalsIgnoreCase("osm.pbf") || extensioDeFitxer.equalsIgnoreCase("osm.bz2")) {
	            			System.out.println("information: " + information + ";");
	            			almostFinalResponse += (information + ";");
	            			System.out.println("almostFinalResponse: " + fileNameEdit + " " + almostFinalResponse);
	            			if (loopNumber == countNumber2) finalResponse = almostFinalResponse;
	            			System.out.println("theFinalResponse: " + fileNameEdit + " " + theFinalResponse);
	            		}
	            		else {
		            		// 3.0 EXISTEIX EL FITXER 2?
		              		sql2 = "SELECT EXISTS(SELECT * FROM information_schema.tables WHERE table_name = ?)"; // 2
		            		pstmt2 = conn.prepareStatement(sql2);
		            		pstmt2.setString(1, exactName.toLowerCase() + "2");
		            		rs2 = pstmt2.executeQuery();
		            		if (rs2.next()) does2Exist = rs2.getBoolean(1);
		            		
		            		if (does2Exist) name2Use = exactName.toLowerCase() + "2";
		            		else name2Use = exactName.toLowerCase();
		            		
		            		// 3.1 COLUMNES DE LA TAULA DE L'ARXIU
		            		sql2 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?"; // 2
		            		pstmt2 = conn.prepareStatement(sql2);
		            		pstmt2.setString(1, name2Use);
		            		rs2 = pstmt2.executeQuery();
		            		while (rs2.next())
		            			almostFinalResponse += (rs2.getString(1) + ", ");
		            		finalResponse += almostFinalResponse.substring(0, almostFinalResponse.length()-2);
		            		
		            		// 3.2 FILES DE LA TAULA DE L'ARXIU
		            		sql2 = "SELECT COUNT(*) FROM " + name2Use; // 2
		            		pstmt2 = conn.prepareStatement(sql2);
		            		rs2 = pstmt2.executeQuery();
		            		if (rs2.next()) numberOfRows = rs2.getInt(1);
		            		finalResponse += " (" + numberOfRows + ");";
		            		almostFinalResponse = "";
	            		}} catch (Exception e) {
	    				      e.printStackTrace();
	    			    } finally {
	    			    	try {
	    			    	  if (!extensioDeFitxer.equalsIgnoreCase("osm.pbf") && !extensioDeFitxer.equalsIgnoreCase("osm.bz2")) {
		    			    	  rs2.close();
		    			    	  pstmt2.close();
	    			    	  }} catch (java.sql.SQLException e) {
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
    					if (countNumber2 > 0) {
    						rs.close();
        					pstmt1.close();
        					conn.close();
    					}
    					} catch (java.sql.SQLException e) {
    						e.printStackTrace();
    						}
    				}
    	System.out.println("9. theFinalResponse: " + theFinalResponse);
		return theFinalResponse;
	}
	
	public void addUsuari(Usuari usuari) {
		Connection usuari_connection = null;
		String sql_line = "";
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("org.postgresql.Driver");
			usuari_connection = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			String columnsDest = "identificadordeusuari, contrasenya, email, connectat, rol, client";
			sql_line = "INSERT INTO usuari (" + columnsDest + ") VALUES (?, ?, ?, ?, ?, ?)";
			System.out.println("addUsuari SQL Statement: " + sql_line);
			preparedStatement = usuari_connection.prepareStatement(sql_line);
			preparedStatement.setString(1, usuari.getIdentificadorDeUsuari());
			preparedStatement.setString(2, usuari.getContrasenya());
			preparedStatement.setString(3, usuari.getEmail());
			preparedStatement.setBoolean(4, usuari.isConnectat());
			preparedStatement.setString(5, usuari.getRol().toString());
			preparedStatement.setString(6, usuari.getClient());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		    	try {
		    	  preparedStatement.close();
		    	  usuari_connection.close();
		    	  } catch (java.sql.SQLException e) {
		    		  e.printStackTrace();
		    		  }
		    	}
	}

}
