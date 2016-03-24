package com.sitep.str.integration.in.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.FormatService;
import com.sitep.str.integration.in.impl.FormatServiceImpl;

public class FormatServiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FormatService formatService = new FormatServiceImpl();
	
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line, fileName, fileNameWithoutExtension, function;
		
		while ((line = reader.readLine()) != null) buffer.append(line);
		
		fileName = buffer.toString();
		fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
		function = request.getParameter("function");
		
		System.out.println("filenameWithoutExtension" + fileNameWithoutExtension);
		System.out.println("filename" + fileName);

		System.out.println("vectoriseAndUploadFileToDatabase: " + fileName + fileNameWithoutExtension);
		if (function.equalsIgnoreCase("vc")) {
		//	formatService.vectoriseAndUploadFileToDatabase(fileName, fileNameWithoutExtension, response, request);
		}
		else if (function.equalsIgnoreCase("bg")) {
//			formatService.getBackgroundPost(request);
		}
	}
	
	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName, function, exactFileName, exactFileNameWithExtension, fileNameWithoutExtension, userName, extension, geometry;
		function = fileName = exactFileName = exactFileNameWithExtension = fileNameWithoutExtension = userName = extension = geometry = "";
		
		// Obtenir nom de la taula
		fileName = request.getParameter("filename");
		userName = request.getParameter("username");
		extension = request.getParameter("extension");
		function = request.getParameter("function");
		System.out.println("fileName: " + fileName + " & userName: " + userName + " & extension: "
				+ extension);
		fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
		
		// Obtenir nom
		exactFileName = fileNameWithoutExtension + userName;
		exactFileNameWithExtension = fileNameWithoutExtension + userName + "." + extension;
		System.out.println("Exact name: " + exactFileName);
		System.out.println("Exact name with extension: " + exactFileNameWithExtension);
//      extension = FilenameUtils.getExtension("/files/"+exactFileName);
		
		if (extension.equalsIgnoreCase("shp")) geometry = "geom"; // geom -> .SHP
		else if (extension.equalsIgnoreCase("kml")) geometry = "wkb_geometry"; // geom -> .KML
		else if (extension.equalsIgnoreCase("csv")) geometry = "geometria"; // geom -> .CSV
		System.out.println("geometry: " + geometry);
		try {
			if (function.equalsIgnoreCase("ly")) {
//				formatService.getLayer(exactFileName, fileNameWithoutExtension, geometry, response, userName);
			}
			else if (function.equalsIgnoreCase("bg")) {
//				formatService.getBackground(response);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
