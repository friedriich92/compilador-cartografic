package com.sitep.str.integration.in.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.sitep.str.integration.in.controllers.AplicarCanviController;

public class AplicarCanviServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	AplicarCanviController aplicarCanvi = new AplicarCanviController();

	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName, fileNameWithoutExtension, extension, username, c, info;
		extension = username = c = info = "";
		
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
