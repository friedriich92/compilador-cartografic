package com.sitep.str.integration.in.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.UsuariService;
import com.sitep.str.integration.in.impl.UsuariServiceImpl;

public class UsuariController extends HttpServlet {
		private static final long serialVersionUID = 1L;
		private UsuariService usuariService = new UsuariServiceImpl();
		
		protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				StringBuilder myBuffer = new StringBuilder();
				BufferedReader myReader = request.getReader();
				String myInput;
				int getConfirmation = 0;
				String finalResponse = "NO";
				
				while ((myInput = myReader.readLine()) != null) 
					myBuffer.append(myInput);
				String functionChoice = myBuffer.toString();
				System.out.println("function choice " + functionChoice.substring(0, 1) + ".");
				
				if (functionChoice.substring(0, 1).matches("0")) usuariService.registerUser(functionChoice);
				else {
					getConfirmation = usuariService.getUserConfirmation(functionChoice);
					if (getConfirmation == 1) finalResponse = "SI"; // creates a String
					System.out.println("finalResponse: " + finalResponse);
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.append(finalResponse);
					out.close(); // to the response
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void registerUser2(String username, String password, String email, String role) throws IOException, SQLException {
			usuariService.registerUser2(username, password, email, role);
		}

}
