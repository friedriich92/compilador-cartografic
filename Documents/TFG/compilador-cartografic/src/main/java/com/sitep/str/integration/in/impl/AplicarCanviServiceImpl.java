package com.sitep.str.integration.in.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.sitep.str.integration.in.AplicarCanviService;
import com.sitep.str.integration.in.FitxerService;
import com.sitep.str.integration.in.classes.ExtensioDeFitxer;
import com.sitep.str.integration.in.classes.Fitxer;
import com.sitep.str.integration.in.classes.InformacioGeografica;
import com.sitep.str.integration.in.classes.VersioFitxer;

public class AplicarCanviServiceImpl implements AplicarCanviService {
	
	FitxerService importarFitxer = new FitxerServiceImpl();
	
	public void printSomething() {
		System.out.println("OK");
	}
	
	public VersioFitxer getFitxerVersio(String idFitxerVersioV, String idFitxerVersioF) {
		/* Recorre tots els FitxerVersio i seleccionar aquell que tingui idFitxerVersioV i
		 * idFitxerVersioF*/
		return null;
	}

	public void changeCS(String fileName, String fileNameWithoutExtension, String extension, String coordenades, String username, HttpServletResponse response) throws SQLException, IOException, InterruptedException {
		Connection aplicarCanviConnection = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		ProcessBuilder pb = null;
		int countNumber2;
		String sql1, finalResponse, exactNameWithoutExtension, exactName;
		// Inicialització
		sql1 = finalResponse = "";
		exactNameWithoutExtension = fileNameWithoutExtension + username;
		exactName = fileNameWithoutExtension + username+ "." + extension;
		countNumber2 = 0;
		try {
			Class.forName("org.postgresql.Driver");
			aplicarCanviConnection = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			// 1. CHECK IF THE 2ND VERSIONS ALREADY EXIST OR NOT & DROP TABLES
		    // -- Versió 2
			sql1 = "SELECT COUNT(*) FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, exactNameWithoutExtension.toLowerCase()+"2");
			pstmt1.setInt(3, 2);
			rs = pstmt1.executeQuery();
		    if (rs.next())
		    	countNumber2 = rs.getInt(1);
			
			// -- Versió 1
//			sql1 = "DROP TABLE IF EXISTS " + fileNameWithoutExtension;
//			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
//			pstmt1.execute();
			if (countNumber2 != 0) {
				// -- Versió 2
				sql1 = "DROP TABLE IF EXISTS " + exactNameWithoutExtension.toLowerCase() + "2";
				pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
				pstmt1.execute();				
			}
			
		    // 2. DELETE FILE #3
		    pb = new ProcessBuilder("/bin/sh", "-c", "rm /files/"+exactNameWithoutExtension.toLowerCase()+"3.*");
			System.out.println("Run (applyFilter) clean command " + pb.toString());
			Process process = pb.start();
	
		    System.out.println("Error (applyFilter) clean stream:" + pb.toString());
		    InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
		    
		    System.out.println("Output (applyFilter) clean stream:");
		    InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
			
			// 3. FILE => SHP => CHANGE C.S. (EPSG:4326) => SHP
			if (extension.equalsIgnoreCase("shp")) {
				System.out.println("changeCS SHP");
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension.toLowerCase()+"3.shp /files/"+exactName+" -t_srs "+coordenades);
			}
			else if (extension.equalsIgnoreCase("kml")) {
				System.out.println("changeCS KML");
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension.toLowerCase()+"3.shp /files/"+exactName+" -t_srs "+coordenades);
			}
			else if (extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz") || extension.equalsIgnoreCase("osm")) {
				// Geometry type of `Geometry Collection' not supported in shapefiles SHPT=POINT/ARC/POLYGON/MULTIPOINT/POINTZ/ARCZ/POLYGONZ/MULTIPOINTZ
				// pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+"3.shp /files/"+fileName+" -t_srs "+coordenades);
				System.out.println("changeCS OSM");
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension
						+".shp/"+exactNameWithoutExtension+"3.shp /files/"+exactNameWithoutExtension+
						".shp/points.shp -t_srs "+coordenades);			
			}
			else if (extension.equalsIgnoreCase("csv")) {
				System.out.println("changeCS CSV");
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension+"3.shp /files/"+fileName+" -s_srs "+coordenades);
			}
			System.out.println("Run (AplicarCanviServiceImpl) 1st command " + pb.toString());
			process = pb.start();
	
		    System.out.println("Error (AplicarCanviServiceImpl) 1st stream:" + pb.toString());
		    errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
	
		    System.out.println("Output (AplicarCanviServiceImpl) 1st stream:");
		    inputStream = process.getInputStream();
		    printStream(inputStream);
		    
		    // 4. INSERT INTO TABLE
			// 4.1 FILE 1 is now FILE2 before changing coordinates
		    // Aquesta no funciona perque falta guardar el fitxer 2, només es guarda el tercer
		    if (extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz") || extension.equalsIgnoreCase("osm"))
			    pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+exactNameWithoutExtension+".shp/"+exactNameWithoutExtension+"2.shp public."+exactNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
		    else if (extension.equalsIgnoreCase("csv"))
		    	pb = new ProcessBuilder("psql -h localhost -d osm -U postgres -f /files/"+exactNameWithoutExtension+".sql");			    	
		    else 
		    	pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+exactNameWithoutExtension+"2.shp public."+exactNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
			System.out.println("Run (AplicarCanviServiceImpl) 2nd command " + pb.toString());
			process = pb.start();
	
		    System.out.println("Error (AplicarCanviServiceImpl) 2nd stream:" + pb.toString());
		    errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
		    
		    System.out.println("Output (AplicarCanviServiceImpl) 2nd stream:");
		    inputStream = process.getInputStream();
		    printStream(inputStream);
		    
		    // 4.2 FILE 2 is now FILE3 created after changing coordinates
		    if (extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz") || extension.equalsIgnoreCase("osm"))
			    pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+exactNameWithoutExtension+".shp/"+exactNameWithoutExtension+"3.shp public."+exactNameWithoutExtension+"2 | psql -h localhost -d osm -U postgres");
		    else if (extension.equalsIgnoreCase("csv")) { }
		    else
		    	pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+exactNameWithoutExtension+"3.shp public."+exactNameWithoutExtension+"2 | psql -h localhost -d osm -U postgres");
			System.out.println("Run (AplicarCanviServiceImpl) 3rd command " + pb.toString());
			process = pb.start();
	
		    System.out.println("Error (AplicarCanviServiceImpl) 3rd stream:" + pb.toString());
		    errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
	
		    System.out.println("Output (AplicarCanviServiceImpl) 3rd stream:");
		    inputStream = process.getInputStream();
		    printStream(inputStream);
		    
		    // 5. INSERT INTO BD (fitxer)
		    java.util.Date today = new java.util.Date();
		    importarFitxer.addFitxer(new Fitxer(exactNameWithoutExtension+"2."+extension, username, 2, new java.sql.Date(today.getTime()),
		    		exactNameWithoutExtension+"2", new ExtensioDeFitxer(extension), true));
		    
		    // 6. SEND WHAT HAPPENED => data
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
		    	  aplicarCanviConnection.close();
		    	  } catch (SQLException e) {
		    		  e.printStackTrace();
		    		  }
		    	}
	}
	
	public void printStream(InputStream stream) throws IOException {
	    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	        System.out.println(inputLine);
	    in.close();
	}

	public void applyFilter(String fileName, String fileNameWithoutExtension, String extension, String info, String username, HttpServletResponse response) {
		Connection aplicarCanviConnection = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		ProcessBuilder pb = null;
		int countNumber2, numberOfRows;
		String sql1, finalResponse, limit, columns, columnfield, value, info2, almostFinalResponse, exactNameWithoutExtension, exactName;
		// Inicialització
		finalResponse = sql1 = limit = columns = columnfield = value = almostFinalResponse = "";
		exactNameWithoutExtension = fileNameWithoutExtension + username;
		exactName = fileNameWithoutExtension + username+ "." + extension;
		countNumber2 = numberOfRows = 0;
		try {
			// 1. CHECK IF THE 2ND VERSIONS ALREADY EXIST OR NOT
		    // -- Versió 2
			Class.forName("org.postgresql.Driver");
			aplicarCanviConnection = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			sql1 = "SELECT COUNT(*) FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, exactNameWithoutExtension+"2");
			pstmt1.setInt(3,  2);
			rs = pstmt1.executeQuery();
		    if (rs.next()) countNumber2 = rs.getInt(1);
			
		    // 2. DELETE FILE #3
		    pb = new ProcessBuilder("/bin/sh", "-c", "rm /files/"+exactNameWithoutExtension+"3.*");
			System.out.println("Run (applyFilter) clean command " + pb.toString());
			Process process = pb.start();
	
		    System.out.println("Error (applyFilter) clean stream:" + pb.toString());
		    InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
		    
		    System.out.println("Output (applyFilter) clean stream:");
		    InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
		    
			// 3. APPLY FILTER
			if (info.contains(";")) {
				if (info.contains("columns") && info.contains("rows")) {
					System.out.println("info.contains(columns) && info.contains(rows)");
					for (String retval: info.split(";", 2)) {
				          if (retval.contains("columns")) {
				        	  info2 = retval;
				        	  System.out.println("info2: " + info2);
				        	  for (String retval2: info2.split("=", 2))
				        		  if (!retval2.contains("columns")) columns = retval2;
				          }
				          else {
				        	  info2 = retval;
				        	  System.out.println("info2: " + info2);
				        	  for (String retval2: info2.split("=", 2))
				        		  if (!retval2.contains("rows")) limit = retval2;
				        	  }
				          }
					pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension+"3.shp PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" -sql \"SELECT "+
							columns+" from "+exactNameWithoutExtension.toLowerCase()+" limit " + limit +"\"");
					}
			}
			else {
				if (info.contains("=")) {
					if (info.contains("columns")) {
						System.out.println("info.contains(columns)" + info);
						for (String retval: info.split("=", 2))
					          if (!retval.contains("columns")) columns = retval;
						pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension+"3.shp PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" -sql \"SELECT "+
								columns+" FROM "+exactNameWithoutExtension.toLowerCase()+"\"");
					}
					else if (info.contains("rows")) {
						System.out.println("info.contains(rows)" + info);
						for (String retval: info.split("=", 2))
					          if (!retval.contains("rows")) limit = retval;
						pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension+"3.shp PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" -sql \"SELECT * FROM "+
								exactNameWithoutExtension.toLowerCase()+" limit " + limit +"\"");
					}
					else {
						System.out.println("info.contains(columnfield)" + info);
						int i = 0;
						for (String retval: info.split("=", 2)) {
					          if (i == 0) columnfield = retval;
					          else if (i == 1) value = retval;
					          ++i;
						}
						pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+exactNameWithoutExtension+"3.shp PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" -sql \"SELECT * FROM "+
								exactNameWithoutExtension.toLowerCase()+" where " + columnfield + " = " + value +"\"");
					}
				}
			}
			
			System.out.println("Run (applyFilter) 1st command " + pb.toString());
			process = pb.start();
	
		    System.out.println("Error (applyFilter) 1st stream:" + pb.toString());
		    errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
	
		    System.out.println("Output (applyFilter) 1st stream:");
		    inputStream = process.getInputStream();
		    printStream(inputStream);
			
		    // 4. DROP TABLES
			// -- Versió 1
//			sql1 = "DROP TABLE IF EXISTS " + fileNameWithoutExtension;
//			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
//			pstmt1.execute();
			
			if (countNumber2 != 0) {
				// -- Versió 2
				sql1 = "DROP TABLE IF EXISTS " + exactNameWithoutExtension + "2";
				pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
				pstmt1.execute();				
			}
		    
		    // 5. INSERT INTO TABLES (BD version1/2 and fitxer)
			// 5.1 FILE 1 is now FILE2 before changing coordinates
		    // Aquesta no funciona perque falta guardar el fitxer 2, només es guarda el tercer
		    pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+exactNameWithoutExtension+"2.shp public."+exactNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
			System.out.println("Run (applyFilter) 2nd command " + pb.toString());
			process = pb.start();
	
		    System.out.println("Error (applyFilter) 2nd stream:" + pb.toString());
		    errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
		    
		    System.out.println("Output (applyFilter) 2nd stream:");
		    inputStream = process.getInputStream();
		    printStream(inputStream);
		    
		    // 5.2 FILE 2 is now FILE3 created after changing coordinates
		    pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+exactNameWithoutExtension+"3.shp public."+exactNameWithoutExtension+"2 | psql -h localhost -d osm -U postgres");
			System.out.println("Run (applyFilter) 3rd command " + pb.toString());
			process = pb.start();
	
		    System.out.println("Error (applyFilter) 3rd stream:" + pb.toString());
		    errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
	
		    System.out.println("Output (applyFilter) 3rd stream:");
		    inputStream = process.getInputStream();
		    printStream(inputStream);
		    
		    // [OPCIONAL] 5.3 INSERT INTO BD (fitxer)
		    if (countNumber2 == 0) {
		    	File file = new File("/files/"+exactNameWithoutExtension+"3.shp");
		    	java.util.Date today = new java.util.Date();
		    	importarFitxer.addFitxer(new Fitxer(exactNameWithoutExtension+"2."+extension, username, 2, new java.sql.Date(today.getTime()),
		    			exactNameWithoutExtension+"2", new ExtensioDeFitxer(extension), true));
		    	importarFitxer.addVersioFitxer(new VersioFitxer(exactNameWithoutExtension+"2."+extension, 2, null, null, info));
		    	importarFitxer.addInformacioGeografica(new InformacioGeografica(exactNameWithoutExtension+"2."+extension, "aquesta es la informacio del fitxer", 2));
		    	// FileUtils.readFileToString(file) => (FORMAT) ERROR: invalid byte sequence for encoding "UTF8": 0x00
		    }
		    else {
		    	File file = new File("/files/"+exactNameWithoutExtension+"3.shp");
		    	importarFitxer.editVersioFitxer(exactNameWithoutExtension+"2."+extension, "filtre", info);
		    	importarFitxer.editInformacioGeografica(exactNameWithoutExtension+"2."+extension, "aquesta es la informacio del fitxer");
		    	// FileUtils.readFileToString(file) => (FORMAT) ERROR: invalid byte sequence for encoding "UTF8": 0x00
		    }
    		
		    // 6. SEND WHAT HAPPENED => data
			// 6.1 NOVES COLUMNES
			sql1 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
			pstmt1.setString(1, exactNameWithoutExtension.toLowerCase() + "2");
			rs = pstmt1.executeQuery();
			while (rs.next())
				almostFinalResponse += (rs.getString(1) + ", ");
			finalResponse = almostFinalResponse.substring(0, almostFinalResponse.length()-2);
			
			// 6.2 NOVES FILES
		    sql1 = "SELECT COUNT(*) FROM " + exactNameWithoutExtension + "2";
			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
			rs = pstmt1.executeQuery();
			if (rs.next()) numberOfRows = rs.getInt(1);
			finalResponse += " (" + numberOfRows + ")";	
		    
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
		    	  aplicarCanviConnection.close();
		    	  } catch (SQLException e) {
		    		  e.printStackTrace();
		    		  }
		    	}
	}

	public void deleteFile(String fileName, String username) {
		importarFitxer.deleteFitxer(fileName, username);
	}

	public String editFile(String fileName, String username) {
		return importarFitxer.editFitxer(fileName, username);
	}
}
