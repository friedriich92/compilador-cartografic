package com.sitep.str.integration.in.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.ImportFileService;
import com.sitep.str.integration.in.impl.ImportFileServiceImpl;

public class ImportFileServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ImportFileService importFileService = new ImportFileServiceImpl();
	
	protected void doPost(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
		try {
			String userName = request.getParameter("name");
			importFileService.importFile(request, userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
}
