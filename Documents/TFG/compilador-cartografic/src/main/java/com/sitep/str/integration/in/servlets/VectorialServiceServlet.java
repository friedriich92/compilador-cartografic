package com.sitep.str.integration.in.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.VectorialService;
import com.sitep.str.integration.in.impl.VectorialServiceImpl;

public class VectorialServiceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private VectorialService carregadorFitxersVectorial = new VectorialServiceImpl();
	
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line, fileName, fileNameWithoutExtension;
		while ((line = reader.readLine()) != null) buffer.append(line);
		fileName = buffer.toString();
		fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
		System.out.println("filenameWithoutExtension" + fileNameWithoutExtension);
		System.out.println("filename" + fileName);
		carregadorFitxersVectorial.vectoriseAndUploadFileToDatabase(fileName, fileNameWithoutExtension, response, request);
		/*if (request.getParameter("info").equalsIgnoreCase("osm")) {
			fileNameWithoutExtension = FilenameUtils.removeExtension(fileNameWithoutExtension);
			System.out.println("vectoriseAndUploadOsmFileToDatabase: " + fileName + fileNameWithoutExtension);
			carregadorFitxersVectorial.vectoriseAndUploadOsmFileToDatabase(fileName, fileNameWithoutExtension, response, request);
		} else {*/
		System.out.println("vectoriseAndUploadFileToDatabase: " + fileName + fileNameWithoutExtension);
		carregadorFitxersVectorial.vectoriseAndUploadFileToDatabase(fileName, fileNameWithoutExtension, response, request);
		/*}*/
	}
	
}
