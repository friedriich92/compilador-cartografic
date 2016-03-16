package com.sitep.str.integration.in.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.FonsService;
import com.sitep.str.integration.in.impl.FonsServiceImpl;

public class FonsServiceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	FonsService fonsService = new FonsServiceImpl();

	/**
	 * doGet: servlet per a descarregar el fitxer
	 * @param request
	 * @param response
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*try {
			System.out.println();
			System.out.println("doGet Fons");
			System.out.println("-----------------");
			
			ProcessBuilder pb = new ProcessBuilder
					("osmosis", "-v", "10", "--read-pbf", 
						"file=/home/tecnic/sample_osmosis.osm.pbf", "--mapfile-writer", "file=/home/tecnic/test.map");
			System.out.println("Run (doGet) osmosis command");
			Process process = pb.start();

		    System.out.println("Error (doGet) stream:" + pb.command());
		    InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);

		    process.waitFor();

		    System.out.println("Output (doGet) stream:" + pb.command());
		    InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
		}
		catch (Exception evt) {
			System.out.println("Error (doGet) Fons: " + evt);
		}*/
		fonsService.getBackground(response);
    }
	
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		fonsService.getBackgroundPost(request);
    }
}
