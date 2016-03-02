package com.sitep.str.integration.in.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.CarregadorDeFitxersVectorialsService;

public class CarregadorDeFitxersVectorialsServiceImpl implements CarregadorDeFitxersVectorialsService {

	Connection carregadorDeFitxersVectorialsConnection = null;
	
	public void vectoriseAndUploadFileToDatabase(String fileName, String fileNameWithoutExtension, HttpServletResponse response) {
		try {
			// 1. EXTREURE LA INFORMACIÓ NECESSÀRIA
			String extension = FilenameUtils.getExtension("/files/"+fileName);
			System.out.println("filename: " + fileName +
					" fileNameWithoutExtension: " + fileNameWithoutExtension + " extension: "
					+ extension);
			System.out.println("shp = " + extension.equalsIgnoreCase("shp") +
					"or kml = " + extension.equalsIgnoreCase("kml"));
			ProcessBuilder pb = null;
			
			// 2. IMPORTAR A LA BASE DE DADES
			if (extension.equalsIgnoreCase("shp")) {
				System.out.println("shp2pgsql -s 26986 /files/"+fileName+" public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
				pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+fileName+" public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
			} else if (extension.equalsIgnoreCase("kml")) {
				System.out.println("ogr2ogr -f \"PostgreSQL\" PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" /files/"+fileName+" -nln "+fileNameWithoutExtension);
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"PostgreSQL\" PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" /files/"+fileName+" -nln "+fileNameWithoutExtension);
			} else if (extension.equalsIgnoreCase("osm.pbf")) {
				//pb = new ProcessBuilder("/bin/sh", "-c", "osm2pgsql -c -d osm -U postgres -H localhost -S /home/tecnic/default.style /files/"+fileName); */
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+".shp /files/"+fileName);
			}
			System.out.println("Run (carregarFitxerVectorial) command " + pb.toString());
			Process process = pb.start();

		    System.out.println("Error (carregarFitxerVectorial) stream:" + pb.toString());
		    InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);

		    process.waitFor();

		    System.out.println("Output (carregarFitxerVectorial) stream:");
		    InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
		    
		    // [OPCIONAL] POSAR EL FITXER OSM CONVERTIT A SHP A LA BASE DE DADES
		    if (extension.equalsIgnoreCase("osm.pbf")) {
		    	pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+
		    			fileNameWithoutExtension+"/"+fileNameWithoutExtension+".shp public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
				System.out.println("Run (carregarFitxerVectorial) OSM " + pb.toString());
				process = pb.start();

			    System.out.println("Error (carregarFitxerVectorial) OSM stream:" + pb.toString());
			    errorStream = process.getErrorStream();
			    printStream(errorStream);

			    process.waitFor();

			    System.out.println("Output (carregarFitxerVectorial) OSM stream:");
			    inputStream = process.getInputStream();
			    printStream(inputStream);
		    }
		    
		    // 3. AGAFAR LES COLUMNES QUE ES PODRAN FILTRAR & EL # DE FILES
			DBConnection();
			PreparedStatement pstmt1 = null;
			String sql1, almostFinalResponse, finalResponse;
			sql1 = finalResponse = almostFinalResponse = "";
			int numberOfRows = 0;
			ResultSet rs = null;
			try {
				// 3.1 COLUMNES
				sql1 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
				pstmt1 = carregadorDeFitxersVectorialsConnection.prepareStatement(sql1);
				pstmt1.setString(1, fileNameWithoutExtension);
				rs = pstmt1.executeQuery();
				while (rs.next()) almostFinalResponse += (rs.getString(1) + ", ");
				finalResponse = almostFinalResponse.substring(0, almostFinalResponse.length()-2);
				
				// 3.2 FILES
			    sql1 = "SELECT COUNT(*) FROM " + fileNameWithoutExtension;
				System.out.println(sql1);
				pstmt1 = carregadorDeFitxersVectorialsConnection.prepareStatement(sql1);
				rs = pstmt1.executeQuery();
				if (rs.next()) numberOfRows = rs.getInt(1);
				finalResponse += " (" + numberOfRows + ")";
				
				
			    // 4. SEND WHAT HAPPENED => data
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.append(finalResponse);
				out.close();
			} catch (Exception e) {
			      e.printStackTrace();
			    } finally {
			    	try {
			    	  rs.close();
			    	  pstmt1.close();
			    	  carregadorDeFitxersVectorialsConnection.close();
			    	  } catch (SQLException e) {
			    		  e.printStackTrace();
			    		  }
			    	}
		    
		}
		catch (Exception evt) {
			System.out.println("Error CarregadorFitxersVectorials: " + evt);
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
		carregadorDeFitxersVectorialsConnection = null;
		try {
			carregadorDeFitxersVectorialsConnection = DriverManager.getConnection(
					"jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (carregadorDeFitxersVectorialsConnection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	public void printStream(InputStream stream) throws IOException {
	    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	        System.out.println(inputLine);
	    in.close();
	}

}
