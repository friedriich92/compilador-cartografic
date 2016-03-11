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
import com.sitep.str.integration.in.classes.FitxerVectorial;

public class CarregadorDeFitxersVectorialsServiceImpl implements CarregadorDeFitxersVectorialsService<FitxerVectorial> {

	public void vectoriseAndUploadFileToDatabase(String fileName, String fileNameWithoutExtension, HttpServletResponse response) {
		try {
			// 1. EXTREURE LA INFORMACIÓ NECESSÀRIA
			String extension = FilenameUtils.getExtension("/files/"+fileName);
			System.out.println("filename: " + fileName +
					" fileNameWithoutExtension: " + fileNameWithoutExtension + " extension: "
					+ extension);
			System.out.println("shp = " + extension.equalsIgnoreCase("shp") +
					" or kml = " + extension.equalsIgnoreCase("kml") + " or csv" +
					extension.equalsIgnoreCase("csv") + " or osm " + 
					extension.equalsIgnoreCase("pbf") + extension.equalsIgnoreCase("bz") +
					extension.equalsIgnoreCase("osm"));
			ProcessBuilder pb = null;
			
			// 2. IMPORTAR A LA BASE DE DADES
			if (extension.equalsIgnoreCase("shp")) {
				System.out.println("shp2pgsql -s 26986 /files/"+fileName+" public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
				pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+fileName+" public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
			} else if (extension.equalsIgnoreCase("kml")) {
				System.out.println("ogr2ogr -f \"PostgreSQL\" PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" /files/"+fileName+" -nln "+fileNameWithoutExtension);
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"PostgreSQL\" PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" /files/"+fileName+" -nln "+fileNameWithoutExtension);
			} else if (extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz") || extension.equalsIgnoreCase("osm")) {
				//pb = new ProcessBuilder("/bin/sh", "-c", "osm2pgsql -c -d osm -U postgres -H localhost -S /home/tecnic/default.style /files/"+fileName); */
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+".shp /files/"+fileName);
			} else if (extension.equalsIgnoreCase("csv")) {
				pb = new ProcessBuilder("/bin/sh", "-c", "/home/tecnic/csv2psql-master/src/csv2psql/./csv2psql.py --schema=public /files/"+fileName+" | psql -h localhost -d osm -U postgres");
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
		    if (extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz") || extension.equalsIgnoreCase("osm")) {
		    	pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+
		    			fileNameWithoutExtension+".shp/points.shp public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
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
		    Connection carregadorDeFitxersVectorialsConnection = null;
			PreparedStatement pstmt1 = null;
			String sql1, almostFinalResponse, finalResponse;
			sql1 = finalResponse = almostFinalResponse = "";
			int numberOfRows = 0;
			ResultSet rs = null;
			try {
				Class.forName("org.postgresql.Driver");
				carregadorDeFitxersVectorialsConnection = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
				// 3.1 COLUMNES
				sql1 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
				System.out.println(sql1 + " " + fileNameWithoutExtension.toLowerCase());
				pstmt1 = carregadorDeFitxersVectorialsConnection.prepareStatement(sql1);
				pstmt1.setString(1, fileNameWithoutExtension.toLowerCase());
				rs = pstmt1.executeQuery();
				while (rs.next())
					almostFinalResponse += (rs.getString(1) + ", ");
				System.out.println("almostFinalResponse: " + almostFinalResponse);
				finalResponse = almostFinalResponse.substring(0, almostFinalResponse.length()-2);
				// 3.2 FILES
			    sql1 = "SELECT COUNT(*) FROM " + fileNameWithoutExtension;
			    System.out.println(sql1 + " " + fileNameWithoutExtension);
				pstmt1 = carregadorDeFitxersVectorialsConnection.prepareStatement(sql1);
				rs = pstmt1.executeQuery();
				if (rs.next()) numberOfRows = rs.getInt(1);
				finalResponse += " (" + numberOfRows + ")";				
				System.out.println("finalResponse: " + finalResponse);
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

	public void printStream(InputStream stream) throws IOException {
	    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	        System.out.println(inputLine);
	    in.close();
	}

}
