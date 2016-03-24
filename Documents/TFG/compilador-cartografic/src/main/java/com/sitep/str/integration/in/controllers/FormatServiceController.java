package com.sitep.str.integration.in.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.FormatService;
import com.sitep.str.integration.in.classes.InformacioGeografica;
import com.sitep.str.integration.in.impl.FormatServiceImpl;

public class FormatServiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FormatService<InformacioGeografica> formatService = new FormatServiceImpl();
	
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line, fileName, fileNameWithoutExtension, boundingBox, function;
		function = request.getParameter("function");
		while ((line = reader.readLine()) != null) buffer.append(line);
		
		if (function.equalsIgnoreCase("vc")) {
			fileName = buffer.toString();
			fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
			
			System.out.println("filenameWithoutExtension" + fileNameWithoutExtension);
			System.out.println("filename" + fileName);
			System.out.println("vectoriseAndUploadFileToDatabase: " + fileName + fileNameWithoutExtension);

			formatService.vectoriseAndUploadFileToDatabase(fileName, fileNameWithoutExtension, response, request);
		}
		else if (function.equalsIgnoreCase("bg")) {
			boundingBox = buffer.toString();
			formatService.getBackgroundPost(boundingBox);
		}
	}
	
	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName, function, exactFileName, exactFileNameWithExtension, fileNameWithoutExtension, userName, extension, geometry;
		function = fileName = exactFileName = exactFileNameWithExtension = fileNameWithoutExtension = userName = extension = geometry = "";
		function = request.getParameter("function");
		try {
			if (function.equalsIgnoreCase("ly")) {
				// Obtenir nom de la taula
				fileName = request.getParameter("filename");
				userName = request.getParameter("username");
				extension = request.getParameter("extension");
				System.out.println("fileName: " + fileName + " & userName: " + userName + " & extension: "
						+ extension);
				fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
				
				// Obtenir nom
				exactFileName = fileNameWithoutExtension + userName;
				exactFileNameWithExtension = fileNameWithoutExtension + userName + "." + extension;
				System.out.println("Exact name: " + exactFileName);
				System.out.println("Exact name with extension: " + exactFileNameWithExtension);
//		      	extension = FilenameUtils.getExtension("/files/"+exactFileName);
				
				if (extension.equalsIgnoreCase("shp")) geometry = "geom"; // geom -> .SHP
				else if (extension.equalsIgnoreCase("kml")) geometry = "wkb_geometry"; // geom -> .KML
				else if (extension.equalsIgnoreCase("csv")) geometry = "geometria"; // geom -> .CSV
				System.out.println("geometry: " + geometry);
				formatService.getLayer(exactFileName, fileNameWithoutExtension, geometry, response, userName);
			}
			else if (function.equalsIgnoreCase("bg")) {
				formatService.getBackground(response);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
