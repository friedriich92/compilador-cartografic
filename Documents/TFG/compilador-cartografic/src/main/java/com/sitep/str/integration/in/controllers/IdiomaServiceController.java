package com.sitep.str.integration.in.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.IdiomaService;
import com.sitep.str.integration.in.classes.Idioma;
import com.sitep.str.integration.in.impl.IdiomaServiceImpl;

public class IdiomaServiceController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IdiomaService idiomaService = new IdiomaServiceImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String info = request.getParameter("info");
		String idioma = request.getParameter("idioma");
		if (info.equalsIgnoreCase("add"))
			idiomaService.addIdioma(new Idioma(1, "X", "O"));
		else if (info.equalsIgnoreCase("edit"))
			idiomaService.editIdioma(idioma);
	}
}
