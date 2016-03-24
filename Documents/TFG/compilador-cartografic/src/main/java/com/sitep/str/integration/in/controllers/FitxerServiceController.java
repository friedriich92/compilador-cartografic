package com.sitep.str.integration.in.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.FitxerService;
import com.sitep.str.integration.in.impl.FitxerServiceImpl;

public class FitxerServiceController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private FitxerService importarFitxerService = new FitxerServiceImpl();
	
	protected void doPost(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
		try {
			String userName = request.getParameter("name");
			importarFitxerService.importFile(request, response, userName);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }
}
