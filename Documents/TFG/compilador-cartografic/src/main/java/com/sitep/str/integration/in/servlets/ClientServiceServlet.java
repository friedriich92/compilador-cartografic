package com.sitep.str.integration.in.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.sitep.str.integration.in.ClientService;
import com.sitep.str.integration.in.classes.Client;
import com.sitep.str.integration.in.impl.ClientServiceImpl;

public class ClientServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClientService<Client> clientService = new ClientServiceImpl();

	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			clientService.registerClients(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		System.out.println("Clients done");
	}
}
