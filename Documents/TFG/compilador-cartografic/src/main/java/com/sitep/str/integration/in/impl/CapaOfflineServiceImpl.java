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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.CapaOfflineService;
import com.sitep.str.integration.in.classes.FitxerCapa;

public class CapaOfflineServiceImpl implements CapaOfflineService<FitxerCapa> {

	static Connection connectionCapaOffline = null;
	
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
		connectionCapaOffline = null;
		try {
			connectionCapaOffline = DriverManager.getConnection(
					"jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (connectionCapaOffline != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	public void getLayerOffline(String capa, String atributGeometria, HttpServletResponse response) throws IOException {
		try {
			System.out.println("getCapaOffline");
			System.out.println("--------------");
			
			DBConnection();
			// 1. Capa(atributGeometria) -> GeoJSON -> .py
			PreparedStatement pstmt1 = null;
			String sql1 = "";
			
			sql1 = "SELECT ST_AsGeoJSON(" + atributGeometria + ") from " + capa;
			pstmt1 = connectionCapaOffline.prepareStatement(sql1);
			ResultSet rs = pstmt1.executeQuery();
			
//			File file = new File("C:\\TEMP\\testFile1.py");
			File file = new File("/home/tecnic/testFile1.py");
			if (file.createNewFile()) System.out.println("File is created!");
			else System.out.println("File already exists.");

			// Escriu a l'arxiu amb la informacio geografica
			FileWriter writer = new FileWriter(file);
			while (rs.next()) writer.append(rs.getString(1));
			
			rs.close();
			writer.close();
			
			// Redirect the file to the user's computer
			// 2. Execucio comanda Linux (de moment, windows)
//			ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "echo", "This is ProcessBuilder Example from JCG");
			ProcessBuilder pb = new ProcessBuilder("echo", "This is ProcessBuilder Example from JCG");
			System.out.println("Run (doGet CapaOffline) linux command");
			Process process = pb.start();

			System.out.println("Error (doGet CapaOffline) stream:");
			InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);

			process.waitFor(); // (?)

			System.out.println("Output (doGet CapaOffline) stream:");
			InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
		}
		catch (Exception evt) {
			System.out.println("Error doGet CapaOffline: " + evt);
		}

		response.setHeader("Content-disposition","attachment; filename=export.py");
//		File my_file = new File("C:\\Users\\fprat\\Downloads\\testFile1.py");
        File my_file = new File("/home/tecnic/testFile1.py");
		
		// This should send the file to browser
		OutputStream out = response.getOutputStream();
		System.out.println("OutputStream");
        FileInputStream in = new FileInputStream(my_file);
        System.out.println("FileInputStream");
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
           }
        System.out.println("While");
        in.close();
        System.out.println("in.close();");
        out.flush();
        System.out.println("out.flush();");
	}

	public void printStream(InputStream stream) throws IOException {
	    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	        System.out.println(inputLine);
	    in.close();
	}
}
