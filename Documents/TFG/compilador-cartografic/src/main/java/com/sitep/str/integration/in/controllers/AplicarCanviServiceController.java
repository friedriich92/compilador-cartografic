package com.sitep.str.integration.in.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.AplicarCanviService;
import com.sitep.str.integration.in.impl.AplicarCanviServiceImpl;

public class AplicarCanviServiceController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	AplicarCanviService aplicarCanvi = new AplicarCanviServiceImpl();
	
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName, fileNameWithoutExtension, extension, username, c, info, resposta;
		extension = username = c = info = resposta = "";
		
		// Obtenir nom de la taula
		fileName = request.getParameter("name");
		username = request.getParameter("user");
		c = request.getParameter("c");
		info = request.getParameter("info");
				
		System.out.println("fileName: " + fileName);
		fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
		
		// Obtenir extensio
        extension = FilenameUtils.getExtension("/files/"+fileName);
		
        System.out.println("extension: " + extension);
        System.out.println("username: " + username);
        System.out.println("info: " + info);
        System.out.println("c: " + c);
		try {
			if (c.equalsIgnoreCase("coord"))
				aplicarCanvi.changeCS(fileName, fileNameWithoutExtension, extension, info, username, response);
			else if (c.equalsIgnoreCase("filtre"))
				aplicarCanvi.applyFilter(fileName, fileNameWithoutExtension, extension, info, username, response);
			else if (c.equalsIgnoreCase("eliminar"))
				aplicarCanvi.deleteFile(fileName, username);
			else if (c.equalsIgnoreCase("desfer")) {
				resposta = aplicarCanvi.editFile(fileName, username);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.append(resposta);
				out.close(); // to the response
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
