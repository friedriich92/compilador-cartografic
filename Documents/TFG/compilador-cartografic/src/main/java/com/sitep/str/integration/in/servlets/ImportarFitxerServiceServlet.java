package com.sitep.str.integration.in.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.ImportarFitxerService;
import com.sitep.str.integration.in.classes.Fitxer;
import com.sitep.str.integration.in.impl.ImportarFitxerServiceImpl;

public class ImportarFitxerServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ImportarFitxerService<Fitxer> importarFitxerService = new ImportarFitxerServiceImpl();
	
	protected void doPost(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
		try {
			String userName = request.getParameter("name");
			importarFitxerService.importFile(request, response, userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
}
