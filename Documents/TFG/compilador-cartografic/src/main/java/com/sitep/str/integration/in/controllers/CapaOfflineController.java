package com.sitep.str.integration.in.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.CapaOfflineService;
import com.sitep.str.integration.in.impl.CapaOfflineServiceImpl;

public class CapaOfflineController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private CapaOfflineService capaOffline = new CapaOfflineServiceImpl();

	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName, fileNameWithoutExtension, extension, geometry;
		extension = geometry = "";
		
		// Obtenir nom de la taula
		fileName = request.getParameter("name");
		System.out.println("fileName: " + fileName);
		fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
		
		// Obtenir extensio
        extension = FilenameUtils.getExtension("/files/"+fileName);
		
        System.out.println("extension: " + extension);
		if (extension.equalsIgnoreCase("shp")) geometry = "geom"; // geom -> .SHP
		System.out.println("geometry: " + geometry);
		capaOffline.getLayerOffline(fileNameWithoutExtension, geometry, response);
	}

}
