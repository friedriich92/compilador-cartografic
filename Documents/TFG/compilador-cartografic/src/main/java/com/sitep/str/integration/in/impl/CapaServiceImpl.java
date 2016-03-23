package com.sitep.str.integration.in.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.CapaService;

public class CapaServiceImpl implements CapaService {

	public void getLayer(String capa, String filename, String atributGeometria, HttpServletResponse response) throws IOException, InterruptedException {
		System.out.println("getCapa");
		System.out.println("--------------");

		// 1. Capa(atributGeometria) -> GeoJSON -> .py
		String sql1 = "";
		Connection connectionCapa = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		FileWriter writer = null;
		try {
			Class.forName("org.postgresql.Driver");
			connectionCapa = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			sql1 = "SELECT ST_AsGeoJSON(" + atributGeometria + ") from " + capa;
			pstmt1 = connectionCapa.prepareStatement(sql1);
			rs = pstmt1.executeQuery();
			
//			File file = new File("C:\\TEMP\\testFile1.py");
			File file = new File("/home/tecnic/testFile1.py");
			if (file.createNewFile()) System.out.println("File is created!");
			else System.out.println("File already exists.");

			// Escriu a l'arxiu amb la informacio geografica
			writer = new FileWriter(file);
			while (rs.next()) writer.append(rs.getString(1));
		} catch (Exception e) {
		      e.printStackTrace();
	    } finally {
	    	try {
	    		writer.close();
	    		rs.close();
	    		pstmt1.close();
	    		connectionCapa.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    			}
	    	}
			
		// Redirect the file to the user's computer
		// 2. Execucio comanda Linux (de moment, windows)
//		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "echo", "This is ProcessBuilder Example from JCG");
		ProcessBuilder pb = new ProcessBuilder("echo", "This is ProcessBuilder Example from JCG");
		System.out.println("Run (doGet Capa) linux command");
		Process process = pb.start();

		System.out.println("Error (doGet Capa) stream:");
		InputStream errorStream = process.getErrorStream();
		printStream(errorStream);

		process.waitFor(); // (?)
		
		System.out.println("Output (doGet Capa) stream:");
		InputStream inputStream = process.getInputStream();
		printStream(inputStream);

		response.setHeader("Content-disposition","attachment; filename="+filename+"_export.py");
//		File my_file = new File("C:\\Users\\fprat\\Downloads\\testFile1.py");
        File my_file = new File("/home/tecnic/testFile1.py");
		
		// This should send the file to browser
		OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
           }
        in.close();
        out.flush();
        System.out.println();
	}

	public void printStream(InputStream stream) throws IOException {
	    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	        System.out.println(inputLine);
	    in.close();
	}
}
