package com.sitep.str.integration.in.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.ImportarFitxerService;
import com.sitep.str.integration.in.classes.ExtensioDeFitxer;
import com.sitep.str.integration.in.classes.Fitxer;

public class ImportarFitxerServiceImpl implements ImportarFitxerService<Fitxer> {
	
	public void importFile(HttpServletRequest request, HttpServletResponse response, String userName) throws IOException, SQLException {
		int numeroDeFitxers = 0;
		try {
			// Create a new file upload handler 
			ServletFileUpload upload = new ServletFileUpload();
			// Parse the request 
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
	            String name = item.getFieldName();
	            InputStream stream = item.openStream();
	            
	            // Process the input stream
	            File f = new File("/files/"+item.getName());
	            System.out.println(f.getAbsolutePath());
	            FileOutputStream fout= new FileOutputStream(f);
	            BufferedOutputStream bout= new BufferedOutputStream (fout);
	            BufferedInputStream bin= new BufferedInputStream(stream);
	            int byte_;
	            while ((byte_=bin.read()) != -1) bout.write(byte_);
	            bout.close();
	            bin.close();
	            
	            // Atributs Fitxer
	            ExtensioDeFitxer extensioDeFitxer = new ExtensioDeFitxer(FilenameUtils.getExtension("/files/"+item.getName()));
				java.util.Date today = new java.util.Date();
	            System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
	            
				// Insert into DB
	            addFitxer(new Fitxer(item.getName(), userName, 1, new java.sql.Date(today.getTime()),
	            		FilenameUtils.removeExtension(item.getName()), extensioDeFitxer,
	            			false));
	            
	            // Number of files X User
	            numeroDeFitxers = fitxersPerUsuari(userName); // La nova fila => abans+1
	            System.out.println("numeroDeFitxers: " +  numeroDeFitxers);
				// Write number of files X User
	            response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.append(String.valueOf(numeroDeFitxers));
				out.close(); // to the response
	        }       
	    } 
	    catch (FileUploadException ex)
	    {
	        System.out.println("FileUploadException: " + ex);;
	    }
	}

	public int fitxersPerUsuari(String userName) {
        PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
        int numberOfRows = 0;
        ResultSet rs = null;
        try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
		    String sql1 = "SELECT COUNT(*) FROM fitxer WHERE idusuari = ? AND numerodeversio = ?";
		    System.out.println("fitxersPerUsuari: " + sql1 + " " + userName);
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, userName);
			pstmt1.setInt(2, 1);
			rs = pstmt1.executeQuery();
			if (rs.next()) numberOfRows = rs.getInt(1);
        } catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		pstmt1.close();
	    		connectionImportFileService.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
		return numberOfRows;
	}

	public void addFitxer(Fitxer fitxer) {
        PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
		try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			String columnsDest = "idfitxer, idusuari, numerodeversio, date, nomfitxer, extensiodefitxer, modificat";
			String sql1 = "INSERT INTO fitxer (" + columnsDest + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
			System.out.println("SQL Statement: " + sql1);
			
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, fitxer.getIdFitxer()); // Cal afegir l'usuari i la versió
			pstmt1.setString(2, fitxer.getIdUsuari());
			pstmt1.setInt(3, fitxer.getNumeroDeVersio());
			pstmt1.setDate(4, fitxer.getDate());
			pstmt1.setString(5, fitxer.getNomDeFitxer());
			pstmt1.setString(6, fitxer.getExtensioDeFitxer().toString());
			pstmt1.setBoolean(7, fitxer.isModificat());
			pstmt1.executeUpdate();
		} catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		pstmt1.close();
	    		connectionImportFileService.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
	}

	public void deleteFitxer(String fileName, String username) {
		PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
        String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
        try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			// Eliminar taules creades del fitxer
			// Versio 1
			String sql1 = "DROP TABLE IF EXISTS " + fileNameWithoutExtension;
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.execute();	
			// Versio 2
			sql1 = "DROP TABLE IF EXISTS " + fileNameWithoutExtension + "2";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.execute();	
			
			// Eliminar files del fitxer
			// Versio 1
			sql1 = "DELETE FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, fileNameWithoutExtension);
			pstmt1.setInt(3, 1);
			int rowsAffected1 = pstmt1.executeUpdate();
			// Versio 2
			sql1 = "DELETE FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, fileNameWithoutExtension+"2");
			pstmt1.setInt(3, 2);
			int rowsAffected2 = pstmt1.executeUpdate();
			System.out.println("rowsAffected 1:" + rowsAffected1 + ", 2:" + rowsAffected2);
		} catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		pstmt1.close();
	    		connectionImportFileService.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
		
	}
	
	public String editFitxer(String fileName, String username) {
		System.out.println("editFitxer");
		PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
        String fileNameWithoutExtension, almostFinalResponse, finalResponse;
        almostFinalResponse = finalResponse = "";
        fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
        ResultSet rs = null;
        int numberOfRows = 0;
        try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
        	String sql1 = "SELECT COUNT(*) FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			System.out.println("sql1: " + sql1 + " " + username + " " + fileNameWithoutExtension+"2");
        	pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, fileNameWithoutExtension+"2");
			pstmt1.setInt(3, 2);
			rs = pstmt1.executeQuery();
			if (rs.next()) numberOfRows = rs.getInt(1);
			if (numberOfRows == 1) {
				System.out.println("numberOfRows == 1");
    			sql1 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?"; // 2
    			System.out.println("sql2: " + sql1);
    			pstmt1 = connectionImportFileService.prepareStatement(sql1);
    			pstmt1.setString(1, fileNameWithoutExtension.toLowerCase());
    			rs = pstmt1.executeQuery();
    			while (rs.next())
    				almostFinalResponse += (rs.getString(1) + ", ");
    			System.out.println("almostFinalResponse: " + almostFinalResponse);
    			finalResponse += almostFinalResponse.substring(0, almostFinalResponse.length()-2);
    			// 3.2 FILES DE LA TAULA DE L'ARXIU
    		    sql1 = "SELECT COUNT(*) FROM " + fileNameWithoutExtension; // 2
    		    System.out.println("sql3: " + sql1);
    		    pstmt1 = connectionImportFileService.prepareStatement(sql1);
    			rs = pstmt1.executeQuery();
    			if (rs.next()) numberOfRows = rs.getInt(1);
    			finalResponse += " (" + numberOfRows + ");";
			}
			else { // No s'ha aplicat cap canvi
				System.out.println("numberOfRows == 0");
				finalResponse = "NO";
			}
        } catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		pstmt1.close();
	    		connectionImportFileService.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
        System.out.println("finalResponse: " + finalResponse);
        return finalResponse;
	}
}
