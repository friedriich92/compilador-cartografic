package com.sitep.str.integration.in.servlets;

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

public class UsuariServiceServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
		private UsuariService usuariService = new UsuariServiceImpl();
		
		protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				StringBuilder myBuffer = new StringBuilder();
				BufferedReader myReader = request.getReader();
				String myInput;
				String userName = request.getParameter("userSession");
				int getConfirmation = 0;
				String finalResponse = "NO";
				
				while ((myInput = myReader.readLine()) != null) 
					myBuffer.append(myInput);
				String functionChoice = myBuffer.toString();
				System.out.println("function choice " + functionChoice.substring(0, 1) + ".");
				
				if (functionChoice.substring(0, 1).matches("0")) {
					int registerOption = usuariService.registerUser(functionChoice);
					System.out.println("registerResponse: " + String.valueOf(registerOption));
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.append(String.valueOf(registerOption));
					out.close(); // to the response
				}
				else if (functionChoice.substring(0, 1).matches("1")) {
					getConfirmation = usuariService.getUserConfirmation(functionChoice);
					if (getConfirmation == 1) {
						finalResponse = usuariService.getFiles(userName); // creates a String
					}
					System.out.println("finalResponse: " + finalResponse);
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.append(finalResponse);
					out.close(); // to the response
				}
				else {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

}
