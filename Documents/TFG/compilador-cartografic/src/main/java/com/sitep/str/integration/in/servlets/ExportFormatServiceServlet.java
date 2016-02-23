package com.sitep.str.integration.in.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.ExportFormatService;
import com.sitep.str.integration.in.impl.ExportFormatServiceImpl;

public class ExportFormatServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ExportFormatService exportFormatService = new ExportFormatServiceImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exportFormatService.exportFormat(request);	 
	}
}
