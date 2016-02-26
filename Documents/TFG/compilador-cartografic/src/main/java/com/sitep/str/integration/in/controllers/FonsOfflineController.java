package com.sitep.str.integration.in.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.FonsOfflineService;
import com.sitep.str.integration.in.impl.FonsOfflineServiceImpl;

public class FonsOfflineController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	FonsOfflineService fonsOfflineService = new FonsOfflineServiceImpl();

	/**
	 * doGet: servlet per a descarregar el fitxer
	 * @param request
	 * @param response
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*try {
			System.out.println();
			System.out.println("doGet FonsOffline");
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
			System.out.println("Error (doGet) FonsOffline: " + evt);
		}*/
		fonsOfflineService.getBackgroundOffline(response);
    }
	
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		fonsOfflineService.getBackgroundOfflinePost(request);
    }
}
