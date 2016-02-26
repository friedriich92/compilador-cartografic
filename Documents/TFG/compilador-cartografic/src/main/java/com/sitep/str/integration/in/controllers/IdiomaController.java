package com.sitep.str.integration.in.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.IdiomaService;
import com.sitep.str.integration.in.impl.IdiomaServiceImpl;

public class IdiomaController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IdiomaService idiomaService = new IdiomaServiceImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		idiomaService.exportFormat(request);	 
	}
}
