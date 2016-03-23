package com.sitep.str.integration.in.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.VectorialService;

public class VectorialServiceImpl implements VectorialService {

	public void vectoriseAndUploadFileToDatabase(String fileName, String fileNameWithoutExtension, HttpServletResponse response, HttpServletRequest request) {
		try {
			// 1. EXTREURE LA INFORMACIÓ NECESSÀRIA
			String extension = FilenameUtils.getExtension("/files/"+fileName);
			System.out.println("filename: " + fileName +
					" fileNameWithoutExtension: " + fileNameWithoutExtension + " extension: "
					+ extension + "i shp = " + extension.equalsIgnoreCase("shp") +
					" or kml = " + extension.equalsIgnoreCase("kml") + " or csv = " +
					extension.equalsIgnoreCase("csv") + " or osm.pbf||osm.bz2 = " + 
					(extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz2")));
			String username = request.getParameter("user");
			if (extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz2")) {
				System.out.println("vectorise bz2 extension");
            	fileNameWithoutExtension = FilenameUtils.removeExtension(fileNameWithoutExtension);
            	extension = "osm." + extension;
            	System.out.println("vectorise fileNameWithoutExtension: " + fileNameWithoutExtension +
            			", extension: " + extension);
			}
			String exactNameWithoutExtension = fileNameWithoutExtension + username;
			String exactName = fileNameWithoutExtension + username+ "." + extension;
			System.out.println("exactNameWithoutExtension: " + exactNameWithoutExtension + ", "
					+ "exactName: " + exactName);
			ProcessBuilder pb = null;
			
			// 2. IMPORTAR A LA BASE DE DADES
			if (extension.equalsIgnoreCase("shp")) {
				System.out.println("shp2pgsql -s 26986 /files/"+exactName+" public."+exactNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
				pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+exactName+" public."+exactNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
			} else if (extension.equalsIgnoreCase("kml")) {
				System.out.println("ogr2ogr -f \"PostgreSQL\" PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" /files/"+exactName+" -nln "+exactNameWithoutExtension);
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"PostgreSQL\" PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" /files/"+exactName+" -nln "+exactNameWithoutExtension);
			} else if (extension.equalsIgnoreCase("osm.pbf") || extension.equalsIgnoreCase("osm.bz2")) {
				pb = new ProcessBuilder("/bin/sh", "-c", "osm2pgsql -c -d osm -U postgres -H localhost -S /home/tecnic/default.style /files/"+exactName);
				//pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension+".shp /files/"+exactName);
			} else if (extension.equalsIgnoreCase("csv")) {
				pb = new ProcessBuilder("/bin/sh", "-c", "/home/tecnic/csv2psql-master/src/csv2psql/./csv2psql.py --schema=public /files/"+exactName+" | psql -h localhost -d osm -U postgres");
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
		    
		    String finalResponse = "";
		    
		    // [OPCIONAL] POSAR EL FITXER OSM PGSQL CONVERTIT A SHP /files
		    if (extension.equalsIgnoreCase("osm.pbf") || extension.equalsIgnoreCase("osm.bz2")) {
		    	/*pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+
		    			exactNameWithoutExtension+".shp/points.shp public."+exactNameWithoutExtension+" | psql -h localhost -d osm -U postgres");*/
		        String type = "";
		    	for (int i = 0; i < 4; ++i) {
		    		if (i == 0) type = "line";
		    		else if (i == 1) type = "point";
		    		else if (i == 2) type = "polygon";
		    		else type = "roads";
		    		
			    	// 3. AGAFAR LES COLUMNES QUE ES PODRAN FILTRAR & EL # DE FILES (x4)
			    	if (i <= 0 && i >= 2) {
			    		finalResponse += (getFileInformation("planet_osm_" + type) + ";");
			    		System.out.println("!!!if finalResponse += " + finalResponse);
			    	}
			    	else {
			    		finalResponse += getFileInformation("planet_osm_" + type);
			    		System.out.println("!!!else finalResponse += " + finalResponse);
			    	}
		    		
		    		// pgsql2shp
			    	pb = new ProcessBuilder("/bin/sh", "-c", "pgsql2shp -f /files/" +
			    			fileNameWithoutExtension + type + username + " -h localhost -u postgres"
			    					+ " -P SiteP0305 osm public.planet_osm_" + type);
			    	System.out.println("Run (carregarFitxerVectorial) OSM 1" + pb.toString());
					process = pb.start();
	
				    System.out.println("Error (carregarFitxerVectorial) OSM 1 stream:" + pb.toString());
				    errorStream = process.getErrorStream();
				    printStream(errorStream);

			    	process.waitFor();

			    	System.out.println("Output (carregarFitxerVectorial) OSM 1 stream:");
			    	inputStream = process.getInputStream();
			    	printStream(inputStream);
		    	}
		    }
		    else // 3. AGAFAR LES COLUMNES QUE ES PODRAN FILTRAR & EL # DE FILES 
		    	finalResponse = getFileInformation(exactNameWithoutExtension);
		    // 4. SEND WHAT HAPPENED => data
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.append(finalResponse);
			out.close();
		    
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
	
	public String getFileInformation(String exactNameWithoutExtension) {
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
			System.out.println(sql1 + " " + exactNameWithoutExtension.toLowerCase());
			pstmt1 = carregadorDeFitxersVectorialsConnection.prepareStatement(sql1);
			pstmt1.setString(1, exactNameWithoutExtension.toLowerCase());
			rs = pstmt1.executeQuery();
			while (rs.next())
				almostFinalResponse += (rs.getString(1) + ", ");
			System.out.println("almostFinalResponse: " + almostFinalResponse);
			finalResponse = almostFinalResponse.substring(0, almostFinalResponse.length()-2);
			// 3.2 FILES
		    sql1 = "SELECT COUNT(*) FROM " + exactNameWithoutExtension;
		    System.out.println(sql1 + " " + exactNameWithoutExtension);
			pstmt1 = carregadorDeFitxersVectorialsConnection.prepareStatement(sql1);
			rs = pstmt1.executeQuery();
			if (rs.next()) numberOfRows = rs.getInt(1);
			finalResponse += " (" + numberOfRows + ")";				
			System.out.println("finalResponse: " + finalResponse);
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
		return finalResponse;
	}

	public void vectoriseAndUploadOsmFileToDatabase(String fileName, String fileNameWithoutExtension, HttpServletResponse response, HttpServletRequest request) throws IOException {
		String username = request.getParameter("user");
		String type = "";
		for (int j = 0; j < 4; ++j) {
			if (j == 0) type = "line";
			else if (j == 1) type = "point";
			else if (j == 2) type = "polygon";
			else type = "roads";
	    	
	    	// shp2pgsql
			System.out.println("/bin/sh -c shp2pgsql -s 26986 /files/"+
	    			fileNameWithoutExtension + type + username + ".shp public." + 
	    			fileNameWithoutExtension + type + username + " | psql -h localhost -d "
	    					+ "osm -U postgres");
	    	ProcessBuilder pb2 = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+
	    			fileNameWithoutExtension + type + username + ".shp public." + 
	    			fileNameWithoutExtension + type + username + " | psql -h localhost -d "
	    					+ "osm -U postgres");
	    	System.out.println("Run (carregarFitxerVectorial) OSM 2" + pb2.toString());
			Process process2 = pb2.start();
	
		    System.out.println("Error (carregarFitxerVectorial) OSM 2 stream:" + pb2.toString());
		    InputStream errorStream = process2.getErrorStream();
		    printStream(errorStream);
	
		    try {
				process2.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
	    	System.out.println("Output (carregarFitxerVectorial) OSM 2 stream:");
	    	InputStream inputStream = process2.getInputStream();
	    	printStream(inputStream);
		}
	}
		

}
