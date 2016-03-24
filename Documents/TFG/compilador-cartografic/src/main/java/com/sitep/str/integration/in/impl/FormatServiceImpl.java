package com.sitep.str.integration.in.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.FormatService;
import com.sitep.str.integration.in.classes.InformacioGeografica;

public class FormatServiceImpl implements FormatService<InformacioGeografica> {

	public void getBackground(HttpServletResponse response) {
		response.setHeader("Content-disposition","attachment; filename=test.map");
		File my_file = new File("/home/tecnic/test.map");
		try {
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
			} catch (Exception e) {
				
			}
	}

	public void getLayer(String capa, String filename, String atributGeometria, HttpServletResponse response, String userName) throws IOException, InterruptedException {
		System.out.println("getCapa");
		System.out.println("--------------");

		// 1. Capa(atributGeometria) -> GeoJSON -> .py
		String sql1 = "";
		Connection connectionCapa = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		boolean does2Exist = false;
		FileWriter writer = null;
		String extensioDeFitxer, information;
		extensioDeFitxer = information = "";
		try {
			Class.forName("org.postgresql.Driver");
			connectionCapa = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
    		// 2. EXISTEIX EL FITXER 2?
      		sql1 = "SELECT EXISTS(SELECT * FROM information_schema.tables WHERE table_name = ?)"; // 2
    		pstmt1 = connectionCapa.prepareStatement(sql1);
    		pstmt1.setString(1, capa.toLowerCase() + "2");
    		rs = pstmt1.executeQuery();
    		if (rs.next()) does2Exist = rs.getBoolean(1);
			
    		// 3. FORMAT FITXER?
    		sql1 = "SELECT extensiodefitxer FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			System.out.println(sql1 + " " + userName + " " + capa.toLowerCase());
			pstmt1 = connectionCapa.prepareStatement(sql1);
			pstmt1.setString(1, userName);
			pstmt1.setString(2, capa.toLowerCase());
			pstmt1.setInt(3,  1);
			rs = pstmt1.executeQuery();
		    if (rs.next()) extensioDeFitxer = rs.getString(1);
		    
		    if (extensioDeFitxer.equalsIgnoreCase("osm.bz2") || extensioDeFitxer.equalsIgnoreCase("osm.pbf")) {
    	    	sql1 = "SELECT info FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
    			System.out.println(sql1 + " " + userName + " " + capa.toLowerCase());
    			pstmt1 = connectionCapa.prepareStatement(sql1);
    			pstmt1.setString(1, userName);
    			pstmt1.setString(2, capa.toLowerCase()+"2");
    			pstmt1.setInt(3,  2);
    			rs = pstmt1.executeQuery();
    			if (rs.next()) information = rs.getString(1);
    			
    			atributGeometria = "way";
    			if (capa.contains("line")) capa = "planet_osm_line";
    			else if (capa.contains("point")) capa = "planet_osm_point";
    			else if (capa.contains("polygon")) capa = "planet_osm_polygon";
    			else capa = "planet_osm_roads";
    			
    			information = information.substring(information.indexOf("(") + 1);
    			information = information.substring(0, information.indexOf(")"));
    			
    			sql1 = "SELECT ST_AsGeoJSON(" + atributGeometria + ") from " + capa + " limit " + information;
    		}
		    else {
		    	if (does2Exist) {
		    		capa = capa + "2";
		    		sql1 = "SELECT ST_AsGeoJSON(" + atributGeometria + ") from " + capa;
		    	}
		    	else
		    		sql1 = "SELECT ST_AsGeoJSON(" + atributGeometria + ") from " + capa;
		    }
    		
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

	public void getBackgroundPost(String data) {
		try {
			System.out.println("String field " + data + " readed.");
			
			System.out.println();
			System.out.println("doPost FonsPost");
			System.out.println("-----------------");
			
			ProcessBuilder pb = new ProcessBuilder
					("osmosis", "--read-pbf", "file=/home/tecnic/spain.osm.pbf", "--mapfile-writer", 
							"file=/home/tecnic/test.map", "bbox="+data+"");
			
			System.out.println("Run (doPost) osmosis command");
			Process process = pb.start();

		    System.out.println("Error (doPost) stream:" + pb.command());
		    InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);

		    process.waitFor();

		    System.out.println("Output (doPost) stream:" + pb.command());
		    InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
		}
		catch (Exception evt) {
			System.out.println("Error (doPost) FonsPost: " + evt);
		}
	}

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
		    
		    String finalResponse, preview;
		    finalResponse = preview = "";
		    
		    // [OPCIONAL] POSAR EL FITXER OSM PGSQL CONVERTIT A SHP /files
		    if (extension.equalsIgnoreCase("osm.pbf") || extension.equalsIgnoreCase("osm.bz2")) {
		    	/*pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+
		    			exactNameWithoutExtension+".shp/points.shp public."+exactNameWithoutExtension+" | psql -h localhost -d osm -U postgres");*/
		        String type = "";
		    	PreparedStatement pstmt1 = null;
		        Connection connectionImportFileService = null;
		    	for (int i = 0; i < 4; ++i) {
		    		if (i == 0) type = "line";
		    		else if (i == 1) type = "point";
		    		else if (i == 2) type = "polygon";
		    		else type = "roads";
		    		
			    	// 3. AGAFAR LES COLUMNES QUE ES PODRAN FILTRAR & EL # DE FILES (x4)
			    	if (i <= 0 && i >= 2) {
			    		preview = getFileInformation("planet_osm_" + type);
			    		finalResponse += (preview + ";");
			    		System.out.println("!!!if finalResponse += " + finalResponse);
			    	}
			    	else {
			    		preview = getFileInformation("planet_osm_" + type);
			    		finalResponse += preview;
			    		System.out.println("!!!else finalResponse += " + finalResponse);
			    	}
			    	
			    	// Update fitxer (x4)
			    	try {
			    		Class.forName("org.postgresql.Driver");
			    		connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			    		// 1
			    		String updateTableSQL = "UPDATE fitxer SET info = ? "
			    				+ " WHERE idfitxer = ? AND numerodeversio = ?";			    			
			    		System.out.println("updateTableSQL1: " + updateTableSQL);
			    		pstmt1 = connectionImportFileService.prepareStatement(updateTableSQL);
			    		pstmt1.setString(1, preview);
			    		pstmt1.setString(2, fileNameWithoutExtension + type + username + ".shp");
			    		pstmt1.setInt(3, 1);
			    		pstmt1.executeUpdate();
			    		// 2
			    		updateTableSQL = "UPDATE fitxer SET info = ? "
			    				+ " WHERE idfitxer = ? AND numerodeversio = ?";			    			
			    		System.out.println("updateTableSQL2: " + updateTableSQL);
			    		pstmt1 = connectionImportFileService.prepareStatement(updateTableSQL);
			    		pstmt1.setString(1, preview);
			    		pstmt1.setString(2, fileNameWithoutExtension + type + username + "2.shp");
			    		pstmt1.setInt(3, 2);
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
		    else  
		    	finalResponse = getFileInformation(exactNameWithoutExtension); // 3. AGAFAR LES COLUMNES QUE ES PODRAN FILTRAR & EL # DE FILES
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
	
	public void editInformacioGeografica(String fileName, String readFileToString) {
        PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
		try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			String sql1 = "UPDATE informaciogeografica SET informacio = ? WHERE idinformaciogeografica = ? AND numerodeversio = ?";
			System.out.println("SQL Statement editInformacioGeografica: " + sql1);
			
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, readFileToString);
			pstmt1.setString(2, fileName);
			pstmt1.setInt(3, 2);
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
	
	public void addInformacioGeografica(InformacioGeografica informacioGeografica) {
        PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
		try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			String columnsDest = "idinformaciogeografica, informacio, numerodeversio";
			String sql1 = "INSERT INTO informaciogeografica (" + columnsDest + ") VALUES (?, ?, ?)";
			System.out.println("SQL Statement: " + sql1);
			
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, informacioGeografica.getIdInformacioGeografica()); // Cal afegir l'usuari i la versió
			pstmt1.setString(2, informacioGeografica.getInformacio());
			pstmt1.setInt(3, informacioGeografica.getNumerodeversio());
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

}
