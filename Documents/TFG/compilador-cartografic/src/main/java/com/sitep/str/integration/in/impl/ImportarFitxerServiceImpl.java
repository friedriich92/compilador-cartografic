package com.sitep.str.integration.in.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.ImportarFitxerService;

public class ImportarFitxerServiceImpl implements ImportarFitxerService {

	static Connection connectionImportFileService = null;
	
	public void importFile(HttpServletRequest request, String userName) throws IOException, SQLException {
		try {
			// Create a new file upload handler 
			ServletFileUpload upload = new ServletFileUpload();
			// Parse the request 
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
	            String name = item.getFieldName();
	            InputStream stream = item.openStream();
	            System.out.println("File field " + name + " with file name "
	            	+ item.getName() + " detected.");
	            
	            // Insert into DB
	            PreparedStatement pstmt1 = null;
	            try {
	            	DBConnection();
		    		String fileName, tableName;
		    		fileName = item.getName();
		    		tableName = FilenameUtils.removeExtension(fileName);
		    		java.util.Date today = new java.util.Date();
		    		
		    		
		    		String nomTaula = "loaded_objects";
		    		String columnsDest = "filename, date, username, tablename";
		    		String sql1 = "INSERT INTO " + nomTaula + " (" + columnsDest + ") VALUES (?, ?, ?, ?)";
		    		System.out.println("SQL Statement: " + sql1);
		    		
		    		pstmt1 = connectionImportFileService.prepareStatement(sql1);
		    		pstmt1.setString(1, fileName); // Cal afegir l'usuari i la versió
		    		pstmt1.setDate(2, new java.sql.Date(today.getTime()));
		    		pstmt1.setString(3, userName);
		    		pstmt1.setString(4, tableName);
		    		pstmt1.executeUpdate();
	            }
	            catch (Exception e) {
	  		      e.printStackTrace();
			    } finally {
			      try {
			        pstmt1.close();
			        connectionImportFileService.close();
			      } catch (SQLException e) {
			        e.printStackTrace();
			      }
			    }
	            
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
	        }       
	    } 
	    catch (FileUploadException ex)
	    {
	        System.out.println("FileUploadException: " + ex);;
	    }
	}

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
		connectionImportFileService = null;
		try {
			connectionImportFileService = DriverManager.getConnection(
					"jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (connectionImportFileService != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

}
