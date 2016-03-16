package com.sitep.str.integration.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FonsPost extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * doPost: servlet per pujar el fitxer
	 * @param request
	 * @param response
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FonsPost fonsPost = new FonsPost();
		fonsPost.getBackgroundPost(request);
    }
	
	public void getBackgroundPost(HttpServletRequest request) {
		try {
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
//			int i = 0;
//			String[] boundingBoxArray = new String[4];
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
//				if (line.equals(",")) {
//					boundingBoxArray[i] = buffer.toString();
//					buffer = new StringBuilder();
//					++i;
//				}
//				else buffer.append(line);
			}
			String data = buffer.toString();
			System.out.println("String field " + data + " readed.");
			
			System.out.println();
			System.out.println("doPost FonsPost");
			System.out.println("-----------------");
			
			ProcessBuilder pb = new ProcessBuilder
					("osmosis", "--read-pbf", "file=/home/tecnic/spain.osm.pbf", "--mapfile-writer", 
							"file=/home/tecnic/test.map", "bbox="+data+"");
			
			System.out.println("Run (doPost) osmosis command");
			Process process = pb.start();

		    System.out.println("Error (doPost) stream:" + pb.command());
		    InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);

		    process.waitFor();

		    System.out.println("Output (doPost) stream:" + pb.command());
		    InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
		}
		catch (Exception evt) {
			System.out.println("Error (doPost) FonsPost: " + evt);
		}
	}
	
	private void printStream (InputStream stream) throws IOException
	{
	    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	        System.out.println(inputLine);
	    in.close();
	}
}
