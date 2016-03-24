package com.sitep.str.integration.in.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.FitxerService;
import com.sitep.str.integration.in.FormatService;
import com.sitep.str.integration.in.classes.ExtensioDeFitxer;
import com.sitep.str.integration.in.classes.Fitxer;
import com.sitep.str.integration.in.classes.InformacioGeografica;
import com.sitep.str.integration.in.classes.VersioFitxer;

public class FitxerServiceImpl implements FitxerService<Fitxer> {
	
	FormatService<InformacioGeografica> formatService = new FormatServiceImpl();
	
	public void importFile(HttpServletRequest request, HttpServletResponse response, String userName) throws IOException, SQLException, InterruptedException {
		int numeroDeFitxers, counter;
		numeroDeFitxers = counter = 0;
		String fileNameWithoutExtension, itemGetName, extension, exactName, exactNameWithoutExtension, zipfiles;
		fileNameWithoutExtension = itemGetName = extension = exactName = exactNameWithoutExtension = zipfiles = "";
		try {
			// Create a new file upload handler 
			ServletFileUpload upload = new ServletFileUpload();
			// Parse the request 
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
	            String name = item.getFieldName();
	            InputStream stream = item.openStream();
	            
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
	            
	            // If (zip)
	            boolean iszip = isZipFile(f);
	            System.out.println("Is zip? " + iszip);
	            if (iszip) {
	            	// Obtenir nom arxius
	            	zipfiles = getList("/files/"+item.getName());
	            	for (int i = 0; i < zipfiles.length(); i++) {
	            	    if (zipfiles.charAt(i) == ';')
	            	        counter++;
	            	}
	            	if (counter >= 2) {
		            	// Descomprimir arxiu
		            	ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "unzip /files/" + item.getName() + 
								" -d /files/");
						System.out.println("Run (importFile) unzip command " + pb.toString());
						Process process = pb.start();

						System.out.println("Error (importFile) unzip stream:" + pb.toString());
						InputStream errorStream = process.getErrorStream();
						printStream(errorStream);

						process.waitFor();

						System.out.println("Output (importFile) unzip stream:");
						InputStream inputStream = process.getInputStream();
						printStream(inputStream);
	    	    		
						String[] dataArray =  zipfiles.split(";");
	    	    		for (String s: dataArray) {
	    	    			// Canviar el nom del fitxer
	    	    			if (s.contains("shp") && !s.contains("xml")) {
	    	    				System.out.println("Contains shp");
	    	    				itemGetName = s;
	    			            fileNameWithoutExtension = FilenameUtils.removeExtension(s);
	    			            extension = FilenameUtils.getExtension("/files/" + s);
	    			            exactName = fileNameWithoutExtension + userName + "." + extension;
	    			            exactNameWithoutExtension = fileNameWithoutExtension + userName;
	    			            System.out.println("itemGetName: " + itemGetName + ", fileNameWithoutExtension: "
	    			            		+ fileNameWithoutExtension + ", extension: " + extension + ", exactName: "
	    			            		+ exactName);
	    	    			}
	    	    			// Canviar el nom del fitxer
	    	    			pb = new ProcessBuilder("/bin/sh", "-c", "mv /files/" + s + 
	    							" /files/" + FilenameUtils.removeExtension(s) + userName + "." + FilenameUtils.getExtension("/files/" + s));
							System.out.println("Run (importFile) rename command " + pb.toString());
							process = pb.start();

							System.out.println("Error (importFile) rename stream:" + pb.toString());
							errorStream = process.getErrorStream();
							printStream(errorStream);

							process.waitFor();

							System.out.println("Output (importFile) rename stream:");
							inputStream = process.getInputStream();
							printStream(inputStream);
	    	    		}
	            	}
	            }
	            else { // Else (!zip)
		            // Canviar el nom del fitxer
	            	itemGetName = item.getName();
		            fileNameWithoutExtension = FilenameUtils.removeExtension(itemGetName);
		            extension = FilenameUtils.getExtension("/files/"+itemGetName);
		            System.out.println("bz2 extension ???: " + extension);
		            if (extension.equalsIgnoreCase("pbf") || extension.equalsIgnoreCase("bz2")) {
		            	System.out.println("importFile bz2 extension");
		            	fileNameWithoutExtension = FilenameUtils.removeExtension(fileNameWithoutExtension);
		            	extension = "osm." + extension;
		            	System.out.println("importFile fileNameWithoutExtension: " + fileNameWithoutExtension +
		            			", extension: " + extension);
		            }
		            // filetxt = FileUtils.readFileToString(f);
		            exactName = fileNameWithoutExtension + userName + "." + extension;
		            exactNameWithoutExtension = fileNameWithoutExtension + userName;
		            
					ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "mv /files/" + itemGetName + 
							" /files/" + exactName);
					System.out.println("Run (importFile) command " + pb.toString());
					Process process = pb.start();
	
					System.out.println("Error (importFile) stream:" + pb.toString());
					InputStream errorStream = process.getErrorStream();
					printStream(errorStream);
	
					process.waitFor();
	
					System.out.println("Output (importFile) stream:");
					InputStream inputStream = process.getInputStream();
					printStream(inputStream);
	            }
	            
	            // Atributs per insertar
	            ExtensioDeFitxer extensioDeFitxer = new ExtensioDeFitxer(extension);
	            System.out.println("ExtensioDeFitxer: " + extensioDeFitxer.getExtensio() +
	            		", extensio: " + extension);
				java.util.Date today = new java.util.Date();
	            System.out.println("File field " + name + " with file name " + itemGetName + " detected.");
	            
				// Insert into DB
	            if (extension.equalsIgnoreCase("osm.pbf") || extension.equalsIgnoreCase("osm.bz2")) {
		            String type = "";
	            	for (int j = 0; j < 4; j++) {
			    		if (j == 0) type = "line";
			    		else if (j == 1) type = "point";
			    		else if (j == 2) type = "polygon";
			    		else type = "roads";
		            	// 1
			    		addFitxer(new Fitxer(fileNameWithoutExtension + type + userName + ".shp", userName, 
		            			1, new java.sql.Date(today.getTime()), fileNameWithoutExtension + type + userName,
		            				extensioDeFitxer, false, null));
			    		addVersioFitxer(new VersioFitxer(fileNameWithoutExtension + type + userName + ".shp",
			            		1, null, null, null));
			    		formatService.addInformacioGeografica(new InformacioGeografica(fileNameWithoutExtension + type + 
			            		userName + ".shp", "aquesta es la info", 1));
			            // 2
		            	addFitxer(new Fitxer(fileNameWithoutExtension + type + userName + "2.shp", userName, 
		            			2, new java.sql.Date(today.getTime()), fileNameWithoutExtension + type + userName + "2",
		            				extensioDeFitxer, false, null));
		            	addVersioFitxer(new VersioFitxer(fileNameWithoutExtension + type + userName + "2.shp",
			            		2, null, null, null));
		            	formatService.addInformacioGeografica(new InformacioGeografica(fileNameWithoutExtension + type + 
			            		userName + "2.shp", "aquesta es la info", 2));
		            }
	            }
	            else {
		            addFitxer(new Fitxer(exactName, userName, 1, new java.sql.Date(today.getTime()),
		            		exactNameWithoutExtension.toLowerCase(), extensioDeFitxer, false, null));
		            addVersioFitxer(new VersioFitxer(exactName, 1, null, null, null));
		            formatService.addInformacioGeografica(new InformacioGeografica(exactName, "aquesta es la info", 1));
	            }
	            // Number of files X User
	            numeroDeFitxers = fitxersPerUsuari(userName); // La nova fila
	            if (extension.equalsIgnoreCase("osm.pbf") || extension.equalsIgnoreCase("osm.bz2")) numeroDeFitxers -= 3;
	            System.out.println("numeroDeFitxers: " +  numeroDeFitxers);
	            
				// Write number of files X User
	            response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.append(String.valueOf(numeroDeFitxers));
				out.close(); // to the response
	        }       
	    } 
	    catch (FileUploadException ex)
	    {
	        System.out.println("FileUploadException: " + ex);;
	    }
	}

	private String getList(String filePath) {
		FileInputStream fis = null;
		ZipInputStream zipIs = null;
		ZipEntry zEntry = null;
		String entries = "";
		try {
			fis = new FileInputStream(filePath);
            zipIs = new ZipInputStream(new BufferedInputStream(fis));
            while((zEntry = zipIs.getNextEntry()) != null){
                entries += (zEntry.getName() + ";");
            }
            zipIs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		if (entries != null && entries.length() > 0 && entries.charAt(entries.length()-1)==';')
			entries = entries.substring(0, entries.length()-1);
		return entries;
	}	

	public int fitxersPerUsuari(String userName) {
        PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
        int numberOfRows = 0;
        ResultSet rs = null;
        try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
		    String sql1 = "SELECT COUNT(*) FROM fitxer WHERE idusuari = ? AND numerodeversio = ?";
		    System.out.println("fitxersPerUsuari: " + sql1 + " " + userName + " " + 1);
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, userName);
			pstmt1.setInt(2, 1);
			rs = pstmt1.executeQuery();
			if (rs.next()) numberOfRows = rs.getInt(1);
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
		return numberOfRows;
	}

	public void addFitxer(Fitxer fitxer) {
        PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
		try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			String columnsDest = "idfitxer, idusuari, numerodeversio, date, nomfitxer, extensiodefitxer, modificat, info";
			String sql1 = "INSERT INTO fitxer (" + columnsDest + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			System.out.println("SQL Statement: " + sql1);
			
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, fitxer.getIdFitxer()); // Cal afegir l'usuari i la versió
			pstmt1.setString(2, fitxer.getIdUsuari());
			pstmt1.setInt(3, fitxer.getNumeroDeVersio());
			pstmt1.setDate(4, fitxer.getDate());
			pstmt1.setString(5, fitxer.getNomDeFitxer());
			pstmt1.setString(6, fitxer.getExtensioDeFitxer().getExtensio());
			pstmt1.setBoolean(7, fitxer.isModificat());
			pstmt1.setString(8, fitxer.getInfo());
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

	public void deleteFitxer(String fileName, String username) {
		PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
        String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
        String exactNameWithoutExtension = fileNameWithoutExtension.toLowerCase() + username;
        String extension = FilenameUtils.getExtension("/files/"+fileName);
        String exactName = exactNameWithoutExtension + "." + extension;
        String exactName2 = exactNameWithoutExtension + "2." + extension;
        try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			// Eliminar taules creades del fitxer
			// Versio 1
			String sql1 = "DROP TABLE IF EXISTS " + exactNameWithoutExtension;
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.execute();	
			// Versio 2
			sql1 = "DROP TABLE IF EXISTS " + exactNameWithoutExtension + "2";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.execute();	
			
			// Eliminar informacio geografica
			sql1 = "DELETE FROM informaciogeografica WHERE idinformaciogeografica = ? AND numerodeversio = ?";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, exactName);
			pstmt1.setInt(2, 1);
			int rowsAffected1 = pstmt1.executeUpdate();
			// Versio 2
			sql1 = "DELETE FROM informaciogeografica WHERE idinformaciogeografica = ? AND numerodeversio = ?";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, exactName2);
			pstmt1.setInt(2, 2);
			int rowsAffected2 = pstmt1.executeUpdate();
			System.out.println("informacio geografica rowsAffected 1:" + rowsAffected1 + ", 2:" + rowsAffected2);
			
			// Eliminar versiofitxer
			sql1 = "DELETE FROM versiofitxer WHERE idversiofitxer = ? AND numerodeversio = ?";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, exactName);
			pstmt1.setInt(2, 1);
			rowsAffected1 = pstmt1.executeUpdate();
			// Versio 2
			sql1 = "DELETE FROM versiofitxer WHERE idversiofitxer = ? AND numerodeversio = ?";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, exactName2);
			pstmt1.setInt(2, 2);
			rowsAffected2 = pstmt1.executeUpdate();
			System.out.println("versiofitxer rowsAffected 1:" + rowsAffected1 + ", 2:" + rowsAffected2);
			
			
			// Eliminar files del fitxer
			// Versio 1
			sql1 = "DELETE FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, exactNameWithoutExtension);
			pstmt1.setInt(3, 1);
			rowsAffected1 = pstmt1.executeUpdate();
			// Versio 2
			sql1 = "DELETE FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, exactNameWithoutExtension+"2");
			pstmt1.setInt(3, 2);
			rowsAffected2 = pstmt1.executeUpdate();
			System.out.println("rowsAffected 1:" + rowsAffected1 + ", 2:" + rowsAffected2);
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
	
	public String editFitxer(String fileName, String username) {
		System.out.println("editFitxer");
		PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
        String fileNameWithoutExtension, almostFinalResponse, finalResponse, exactNameWithoutExtension;
        // Inicialització
        almostFinalResponse = finalResponse = "";
        fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
        exactNameWithoutExtension = fileNameWithoutExtension + username;
        ResultSet rs = null;
        int numberOfRows = 0;
        try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			String sql1 = "SELECT COUNT(*) FROM fitxer WHERE idusuari = ? AND nomfitxer = ? AND numerodeversio = ?";
			System.out.println("sql1 editFitxer: " + sql1 + " " + username + " " + exactNameWithoutExtension+"2");
        	pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, exactNameWithoutExtension+"2");
			pstmt1.setInt(3, 2);
			rs = pstmt1.executeQuery();
			if (rs.next()) numberOfRows = rs.getInt(1);
			if (numberOfRows == 1) {
				System.out.println("numberOfRows editFitxer == 1");
    			sql1 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?"; // 2
    			System.out.println("sql2 editFitxer: " + sql1);
    			pstmt1 = connectionImportFileService.prepareStatement(sql1);
    			pstmt1.setString(1, exactNameWithoutExtension.toLowerCase());
    			rs = pstmt1.executeQuery();
    			while (rs.next())
    				almostFinalResponse += (rs.getString(1) + ", ");
    			System.out.println("almostFinalResponse editFitxer: " + almostFinalResponse);
    			finalResponse += almostFinalResponse.substring(0, almostFinalResponse.length()-2);
    			// 3.2 FILES DE LA TAULA DE L'ARXIU
    		    sql1 = "SELECT COUNT(*) FROM " + exactNameWithoutExtension; // 2
    		    System.out.println("sql3 editFitxer: " + sql1);
    		    pstmt1 = connectionImportFileService.prepareStatement(sql1);
    			rs = pstmt1.executeQuery();
    			if (rs.next()) numberOfRows = rs.getInt(1);
    			finalResponse += " (" + numberOfRows + ");";
			}
			else { // No s'ha aplicat cap canvi
				System.out.println("numberOfRows editFitxer == 0");
				finalResponse = "NO";
			}
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
        System.out.println("finalResponse editFitxer: " + finalResponse);
        return finalResponse;
	}
	
	public void printStream(InputStream stream) throws IOException {
	    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	        System.out.println(inputLine);
	    in.close();
	}
	
	public static boolean isZipFile(File file) throws IOException {
	      if(file.isDirectory()) {
	          return false;
	      }
	      if(!file.canRead()) {
	          throw new IOException("Cannot read file "+file.getAbsolutePath());
	      }
	      if(file.length() < 4) {
	          return false;
	      }
	      DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
	      int test = in.readInt();
	      in.close();
	      return test == 0x504b0304;
	  }

	public void addVersioFitxer(VersioFitxer versioFitxer) {
        PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
		try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			String columnsDest = "idversiofitxer, numerodeversio, estil, coordenades, filtre";
			String sql1 = "INSERT INTO versiofitxer (" + columnsDest + ") VALUES (?, ?, ?, ?, ?)";
			System.out.println("SQL Statement: " + sql1);
			
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, versioFitxer.getIdversiofitxer()); // Cal afegir l'usuari i la versió
			pstmt1.setInt(2, versioFitxer.getNumerodeversio());
			pstmt1.setString(3, versioFitxer.getEstil());
			pstmt1.setString(4, versioFitxer.getCoordenades());
			pstmt1.setString(5, versioFitxer.getFiltre());
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
	
	public void editVersioFitxer(String fileName, String key, String info) {
        PreparedStatement pstmt1 = null;
        Connection connectionImportFileService = null;
		try {
			Class.forName("org.postgresql.Driver");
			connectionImportFileService = java.sql.DriverManager.getConnection("jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");
			
			String sql1 = "UPDATE versiofitxer SET " + key + " = ? WHERE idversiofitxer = ? "
					+ "AND numerodeversio = ?";
			System.out.println("SQL Statement editVersioFitxer: " + sql1);
			
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, info);
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
}
