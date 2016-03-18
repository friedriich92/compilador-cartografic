package com.sitep.str.integration.in.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.FitxerService;
import com.sitep.str.integration.in.classes.ExtensioDeFitxer;
import com.sitep.str.integration.in.classes.Fitxer;
import com.sitep.str.integration.in.classes.InformacioGeografica;
import com.sitep.str.integration.in.classes.VersioFitxer;

public class FitxerServiceImpl implements FitxerService {
	
	public void importFile(HttpServletRequest request, HttpServletResponse response, String userName) throws IOException, SQLException, InterruptedException {
		int numeroDeFitxers = 0;
		String fileNameWithoutExtension, extension, exactName, exactNameWithoutExtension, filetxt;
		fileNameWithoutExtension = extension = exactName = exactNameWithoutExtension = filetxt = "";
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
	            
	            // Canviar el nom del fitxer
	            fileNameWithoutExtension = FilenameUtils.removeExtension(item.getName());
	            extension = FilenameUtils.getExtension("/files/"+item.getName());
	            filetxt = FileUtils.readFileToString(f);
	            exactName = fileNameWithoutExtension + userName + "." + extension;
	            exactNameWithoutExtension = fileNameWithoutExtension + userName;
	            
				ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "mv /files/" + item.getName() + 
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
	            
	            // Atributs Fitxer
	            ExtensioDeFitxer extensioDeFitxer = new ExtensioDeFitxer(extension);
				java.util.Date today = new java.util.Date();
	            System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
	            
				// Insert into DB
	            addFitxer(new Fitxer(exactName, userName, 1, new java.sql.Date(today.getTime()),
	            		exactNameWithoutExtension.toLowerCase(), extensioDeFitxer, false));
	            addVersioFitxer(new VersioFitxer(exactName, 1, null, null, null));
	            addInformacioGeografica(new InformacioGeografica(exactName, "aquesta es la info", 1));
	            
	            // Number of files X User
	            numeroDeFitxers = fitxersPerUsuari(userName); // La nova fila => abans+1
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
			
			String columnsDest = "idfitxer, idusuari, numerodeversio, date, nomfitxer, extensiodefitxer, modificat";
			String sql1 = "INSERT INTO fitxer (" + columnsDest + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
			System.out.println("SQL Statement: " + sql1);
			
			pstmt1 = connectionImportFileService.prepareStatement(sql1);
			pstmt1.setString(1, fitxer.getIdFitxer()); // Cal afegir l'usuari i la versió
			pstmt1.setString(2, fitxer.getIdUsuari());
			pstmt1.setInt(3, fitxer.getNumeroDeVersio());
			pstmt1.setDate(4, fitxer.getDate());
			pstmt1.setString(5, fitxer.getNomDeFitxer());
			pstmt1.setString(6, fitxer.getExtensioDeFitxer().toString());
			pstmt1.setBoolean(7, fitxer.isModificat());
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
        String exactNameWithoutExtension = fileNameWithoutExtension + username;
        String extension = FilenameUtils.getExtension("/files/"+fileName);
        String exactName = exactNameWithoutExtension + "." + extension;
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
			pstmt1.setString(1, exactName);
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
			pstmt1.setString(1, exactName);
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
}
