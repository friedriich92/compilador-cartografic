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

import com.sitep.str.integration.in.AplicarCanviService;
import com.sitep.str.integration.in.classes.FitxerVersio;

public class AplicarCanviServiceImpl implements AplicarCanviService<FitxerVersio> {
	
	static Connection aplicarCanviConnection = null;
	
	public void printSomething() {
		System.out.println("OK");
	}
	
	public void addFitxerVersio() {
		
	}
	
	public void deleteFitxerVersio() {
		
	}
	
	public void listFitxerVersio() {
		
	}
	
	public FitxerVersio getFitxerVersio(String idFitxerVersioV, String idFitxerVersioF) {
		/* Recorre tots els FitxerVersio i seleccionar aquell que tingui idFitxerVersioV i
		 * idFitxerVersioF*/
		return null;
	}

	public void detailFitxerVersio() {
		
	}

	public void saveFitxerVersio() {
		
	}

	public void changeCS(String fileName, String fileNameWithoutExtension, String extension, String coordenades, String username, HttpServletResponse response) throws SQLException, IOException, InterruptedException {
		DBConnection();
		PreparedStatement pstmt1 = null;
		String sql1, finalResponse;
		sql1 = finalResponse = "";
		ResultSet rs = null;
		int countNumber2 = 0;
		ProcessBuilder pb = null;
		try {
			// 1. CHECK IF THE 2ND VERSIONS ALREADY EXIST OR NOT & DROP TABLES
		    // -- Versió 2
			String nomTaula = "loaded_objects";
			sql1 = "SELECT COUNT(*) FROM " + nomTaula + " WHERE username = ? AND filename = ?";
			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, fileName+"2");
			rs = pstmt1.executeQuery();
		    if (rs.next())
		    	countNumber2 = rs.getInt(1);
			
			// -- Versió 1
//			sql1 = "DROP TABLE IF EXISTS " + fileNameWithoutExtension;
//			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
//			pstmt1.execute();
			if (countNumber2 != 0) {
				// -- Versió 2
				sql1 = "DROP TABLE IF EXISTS " + fileNameWithoutExtension + "2";
				pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
				pstmt1.execute();				
			}
			
		    // 2. DELETE FILE #3
		    pb = new ProcessBuilder("/bin/sh", "-c", "rm /files/"+fileNameWithoutExtension+"3.*");
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
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+"3.shp /files/"+fileName+" -t_srs "+coordenades);
			}
			else if (extension.equalsIgnoreCase("kml")) {
				System.out.println("changeCS KML");
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+"3.shp /files/"+fileName+" -t_srs "+coordenades);
			}
			else if (extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz") || extension.equalsIgnoreCase("osm")) {
				// Geometry type of `Geometry Collection' not supported in shapefiles SHPT=POINT/ARC/POLYGON/MULTIPOINT/POINTZ/ARCZ/POLYGONZ/MULTIPOINTZ
				// pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+"3.shp /files/"+fileName+" -t_srs "+coordenades);
				System.out.println("changeCS OSM");
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension
						+".shp/"+fileNameWithoutExtension+"3.shp /files/"+fileNameWithoutExtension+
						".shp/points.shp -t_srs "+coordenades);			
			}
			else if (extension.equalsIgnoreCase("csv")) {
				System.out.println("changeCS CSV");
				pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+"3.shp /files/"+fileName+" -s_srs "+coordenades);
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
			    pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+fileNameWithoutExtension+".shp/"+fileNameWithoutExtension+"2.shp public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
		    else if (extension.equalsIgnoreCase("csv"))
		    	pb = new ProcessBuilder("psql -h localhost -d osm -U postgres -f /files/"+fileNameWithoutExtension+".sql");			    	
		    else 
		    	pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+fileNameWithoutExtension+"2.shp public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
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
			    pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+fileNameWithoutExtension+".shp/"+fileNameWithoutExtension+"3.shp public."+fileNameWithoutExtension+"2 | psql -h localhost -d osm -U postgres");
		    else if (extension.equalsIgnoreCase("csv")) { }
		    else
		    	pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+fileNameWithoutExtension+"3.shp public."+fileNameWithoutExtension+"2 | psql -h localhost -d osm -U postgres");
			System.out.println("Run (AplicarCanviServiceImpl) 3rd command " + pb.toString());
			process = pb.start();
	
		    System.out.println("Error (AplicarCanviServiceImpl) 3rd stream:" + pb.toString());
		    errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
	
		    System.out.println("Output (AplicarCanviServiceImpl) 3rd stream:");
		    inputStream = process.getInputStream();
		    printStream(inputStream);
		    
		    // 5. INSERT INTO BD (loaded_objects)
		    java.util.Date today = new java.util.Date();
    		nomTaula = "loaded_objects";
    		String columnsDest = "filename, date, username, tablename";
    		sql1 = "INSERT INTO " + nomTaula + " (" + columnsDest + ") VALUES (?, ?, ?, ?)";
    		System.out.println("SQL Statement: " + sql1);
			
    		pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
    		pstmt1.setString(1, fileName+"2"); // Cal afegir l'usuari i la versió
    		pstmt1.setDate(2, new java.sql.Date(today.getTime()));
    		pstmt1.setString(3, username);
    		pstmt1.setString(4, fileNameWithoutExtension+"2");
    		pstmt1.executeUpdate();
		    
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
		aplicarCanviConnection = null;
		try {
			aplicarCanviConnection = DriverManager.getConnection(
					"jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (aplicarCanviConnection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	public void applyFilter(String fileName, String fileNameWithoutExtension, String extension, String info, String username, HttpServletResponse response) {
		DBConnection();
		PreparedStatement pstmt1 = null;
		String sql1;
		String finalResponse, limit, tables, info2, almostFinalResponse;
		finalResponse = limit = tables = almostFinalResponse = "";
		ResultSet rs = null;
		int countNumber2, numberOfRows;
		countNumber2 = numberOfRows = 0;
		ProcessBuilder pb = null;
		try {
			// 1. CHECK IF THE 2ND VERSIONS ALREADY EXIST OR NOT
		    // -- Versió 2
			String nomTaula = "loaded_objects";
			sql1 = "SELECT COUNT(*) FROM " + nomTaula + " WHERE username = ? AND filename = ?";
			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, fileName+"2");
			rs = pstmt1.executeQuery();
		    if (rs.next()) countNumber2 = rs.getInt(1);
			
		    // 2. DELETE FILE #3
		    pb = new ProcessBuilder("/bin/sh", "-c", "rm /files/"+fileNameWithoutExtension+"3.*");
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
				if (info.contains("tables") && info.contains("rows")) {
					System.out.println("info.contains(tables) && info.contains(rows)");
					for (String retval: info.split(";", 2)) {
				          if (retval.contains("tables")) {
				        	  info2 = retval;
				        	  System.out.println("info2: " + info2);
				        	  for (String retval2: info2.split("=", 2))
				        		  if (!retval2.contains("tables")) tables = retval2;
				          }
				          else {
				        	  info2 = retval;
				        	  System.out.println("info2: " + info2);
				        	  for (String retval2: info2.split("=", 2))
				        		  if (!retval2.contains("rows")) limit = retval2;
				        	  }
				          }
					pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+"3.shp PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" -sql \"SELECT "+
							tables+" from "+fileNameWithoutExtension.toLowerCase()+" limit " + limit +"\"");
					}
			}
			else {
				if (info.contains("tables")) {
					System.out.println("info.contains(tables)" + info);
					for (String retval: info.split("=", 2))
				          if (!retval.contains("tables")) tables = retval;
					pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+"3.shp PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" -sql \"SELECT "+
							tables+" FROM "+fileNameWithoutExtension.toLowerCase()+"\"");
				}
				else if (info.contains("rows")) {
					System.out.println("info.contains(rows)" + info);
					for (String retval: info.split("=", 2))
				          if (!retval.contains("rows")) limit = retval;
					pb = new ProcessBuilder("/bin/sh", "-c", "ogr2ogr -f \"ESRI Shapefile\" /files/"+fileNameWithoutExtension+"3.shp PG:\"host=192.122.214.77 user=postgres dbname=osm password=SiteP0305\" -sql \"SELECT * FROM "+
							fileNameWithoutExtension.toLowerCase()+" limit " + limit +"\"");
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
				sql1 = "DROP TABLE IF EXISTS " + fileNameWithoutExtension + "2";
				pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
				pstmt1.execute();				
			}
		    
		    // 5. INSERT INTO TABLES (BD version1/2 and loaded_objects)
			// 5.1 FILE 1 is now FILE2 before changing coordinates
		    // Aquesta no funciona perque falta guardar el fitxer 2, només es guarda el tercer
		    pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+fileNameWithoutExtension+"2.shp public."+fileNameWithoutExtension+" | psql -h localhost -d osm -U postgres");
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
		    pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+fileNameWithoutExtension+"3.shp public."+fileNameWithoutExtension+"2 | psql -h localhost -d osm -U postgres");
			System.out.println("Run (applyFilter) 3rd command " + pb.toString());
			process = pb.start();
	
		    System.out.println("Error (applyFilter) 3rd stream:" + pb.toString());
		    errorStream = process.getErrorStream();
		    printStream(errorStream);
	
		    process.waitFor();
	
		    System.out.println("Output (applyFilter) 3rd stream:");
		    inputStream = process.getInputStream();
		    printStream(inputStream);
		    
		    // [OPCIONAL] 5.3 INSERT INTO BD (loaded_objects)
		    if (countNumber2 == 0) {
		    	java.util.Date today = new java.util.Date();
	    		nomTaula = "loaded_objects";
	    		String columnsDest = "filename, date, username, tablename";
	    		sql1 = "INSERT INTO " + nomTaula + " (" + columnsDest + ") VALUES (?, ?, ?, ?)";
	    		System.out.println("SQL Statement: " + sql1);
				
	    		pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
	    		pstmt1.setString(1, fileName+"2"); // Cal afegir l'usuari i la versió
	    		pstmt1.setDate(2, new java.sql.Date(today.getTime()));
	    		pstmt1.setString(3, username);
	    		pstmt1.setString(4, fileNameWithoutExtension+"2");
	    		pstmt1.executeUpdate();
		    }
    		
		    // 6. SEND WHAT HAPPENED => data
			// 6.1 NOVES COLUMNES
			sql1 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
			pstmt1 = aplicarCanviConnection.prepareStatement(sql1);
			pstmt1.setString(1, fileNameWithoutExtension.toLowerCase() + "2");
			rs = pstmt1.executeQuery();
			while (rs.next())
				almostFinalResponse += (rs.getString(1) + ", ");
			finalResponse = almostFinalResponse.substring(0, almostFinalResponse.length()-2);
			
			// 6.2 NOVES FILES
		    sql1 = "SELECT COUNT(*) FROM " + fileNameWithoutExtension + "2";
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
}
