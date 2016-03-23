package com.sitep.str.integration.in.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.CapaService;
import com.sitep.str.integration.in.impl.CapaServiceImpl;

public class CapaServiceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private CapaService capa = new CapaServiceImpl();

	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName, exactFileName, exactFileNameWithExtension, fileNameWithoutExtension, userName, extension, geometry;
		fileName = exactFileName = exactFileNameWithExtension = fileNameWithoutExtension = userName = extension = geometry = "";
		
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
//      extension = FilenameUtils.getExtension("/files/"+exactFileName);
		
		if (extension.equalsIgnoreCase("shp")) geometry = "geom"; // geom -> .SHP
		else if (extension.equalsIgnoreCase("kml")) geometry = "wkb_geometry"; // geom -> .KML
		else if (extension.equalsIgnoreCase("csv")) geometry = "geometria"; // geom -> .CSV
		System.out.println("geometry: " + geometry);
		try {
			capa.getLayer(exactFileName, fileNameWithoutExtension, geometry, response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
