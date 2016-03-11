package com.sitep.str.integration.in.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sitep.str.integration.in.ClientService;
import com.sitep.str.integration.in.classes.Client;

public class ClientServiceImpl implements ClientService<Client> {

	public void registerClients(HttpServletRequest request) throws IOException, FileUploadException {
		// Create a new file upload handler 
		System.out.println("ClientService");
		ServletFileUpload upload = new ServletFileUpload();
		// Parse the request 
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
			FileItemStream item = iter.next();
	        String name = item.getFieldName();
	        InputStream stream = item.openStream();
	        System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
	        String temp = "";
	        System.out.println("/clients/"+item.getName());
	        
            // SAVE FILE /clients/filename
            File f = new File("/clients/"+item.getName());
            System.out.println(f.getAbsolutePath());
            FileOutputStream fout= new FileOutputStream(f);
            BufferedOutputStream bout= new BufferedOutputStream (fout);
            BufferedInputStream bin= new BufferedInputStream(stream);
            int byte_;
            while ((byte_=bin.read()) != -1) bout.write(byte_);
            bout.close();
            bin.close();
	        
            // READ FILE /clients/filename
	        FileReader fileReader = new FileReader(f);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        while ((temp = bufferedReader.readLine()) != null) {
	    		String[] dataArray =  temp.split(",");
	    		String identificadordeclient, empresa, telefon, adresa;
	    		identificadordeclient = empresa = telefon = adresa = "";
	    		int arrayIterator, responseValue;
	    		arrayIterator = responseValue = 0;
	    		
	    		for (String s: dataArray) {
	    			if (arrayIterator == 1) identificadordeclient = s;
	    			else if (arrayIterator == 2) empresa = s;
	    			else if (arrayIterator == 3) telefon = s;
	    			else adresa = s;
	    			++arrayIterator;
	    		}
	    		Client client = new Client(identificadordeclient, empresa, telefon, adresa);
	    		addClient(client); // SAVE CLIENT
	        }
		}
	}

	public void addClient(Client client) {
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		int count = 0;
		try {
			Class.forName("org.postgresql.Driver");
     		conn = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			// Existeix el client?
     		String sql1 = "SELECT COUNT(*) FROM client WHERE identificadordeclient = ?";
    		pstmt1 = conn.prepareStatement(sql1);
    		pstmt1.setString(1, client.getIdentificadorDeClient());
    		rs = pstmt1.executeQuery();
    	    if (rs.next()) count = rs.getInt(1);
			if (count == 0) { // Existeix el client
    	    	System.out.println("No existeix el client");
    			String columnsDest = "identificadordeclient, empresa, telefon, adresa";
    			sql1 = "INSERT INTO client (" + columnsDest + ") VALUES (?, ?, ?, ?)";
    			System.out.println("SQL Statement: " + sql1);
    			pstmt1 = conn.prepareStatement(sql1);
    			pstmt1.setString(1, client.getIdentificadorDeClient());
    			pstmt1.setString(2, client.getEmpresa());
    			pstmt1.setString(3, client.getTelefon());
    			pstmt1.setString(4, client.getAdreça());
    			pstmt1.executeUpdate();
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
		
	}
}
