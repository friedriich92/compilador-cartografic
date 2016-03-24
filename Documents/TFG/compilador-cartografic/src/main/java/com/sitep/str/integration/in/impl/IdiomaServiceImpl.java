package com.sitep.str.integration.in.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sitep.str.integration.in.IdiomaService;
import com.sitep.str.integration.in.classes.Idioma;

public class IdiomaServiceImpl implements IdiomaService<Idioma> {

	public void editIdioma(String idioma) throws IOException {
        PreparedStatement pstmt1 = null;
        Connection connectionIdiomaService = null;
        String idioma2;
        if (idioma.equalsIgnoreCase("encatala")) idioma2 = "encastella";
        else idioma2 = "encatala";
		try {
			Class.forName("org.postgresql.Driver");
			connectionIdiomaService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			String sql1 = "UPDATE idioma SET " + idioma + " = ?, " + idioma2 + " = ? WHERE identificador = ?";
			System.out.println("SQL Statement editIdioma: " + sql1);
			
			pstmt1 = connectionIdiomaService.prepareStatement(sql1);
			pstmt1.setString(1, "X");
			pstmt1.setString(2,  "O");
			pstmt1.setInt(3, 1);
			pstmt1.executeUpdate();
		} catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		pstmt1.close();
	    		connectionIdiomaService.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
	}

	public void addIdioma(Idioma idioma) throws IOException {
        Connection connectionIdiomaService = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        String sql1;
        int count = 0;
        sql1 = "";
		try {
			Class.forName("org.postgresql.Driver");
			connectionIdiomaService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			sql1 = "SELECT COUNT(*) FROM idioma WHERE identificador = ?";
			pstmt1 = connectionIdiomaService.prepareStatement(sql1);
			pstmt1.setInt(1, 1);
			rs = pstmt1.executeQuery();
		    if (rs.next()) count = rs.getInt(1);
		    
		    // Existeix la fila d'idioma?
		    if (count == 0) { // No existeix
				String columnsDest = "identificador, encatala, encastella";
				sql1 = "INSERT INTO idioma (" + columnsDest + ") VALUES (?, ?, ?)";
				System.out.println("SQL Statement addIdioma: " + sql1);
				
				pstmt1 = connectionIdiomaService.prepareStatement(sql1);
				pstmt1.setInt(1, 1); // Cal afegir l'usuari i la versió
				pstmt1.setString(2, "X");
				pstmt1.setString(3, "O");
				pstmt1.executeUpdate();
		    }
		} catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		pstmt1.close();
	    		connectionIdiomaService.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
	}
}
