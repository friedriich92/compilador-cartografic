package com.sitep.str.integration.in.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.FonsOfflineService;

public class FonsOfflineServiceImpl implements FonsOfflineService {

	public void getBackgroundOffline(HttpServletResponse response) {
	       response.setHeader("Content-disposition","attachment; filename=test.map");
	        File my_file = new File("/home/tecnic/test.map");
			
	        try {
	        	// This should send the file to browser
				OutputStream out = response.getOutputStream();
		        FileInputStream in = new FileInputStream(my_file);
		        byte[] buffer = new byte[4096];
		        int length;
		        while ((length = in.read(buffer)) > 0){
		           out.write(buffer, 0, length);
		           }
		        in.close();
		        out.flush();
		        System.out.println();
	        } catch (Exception e) {
	        	
	        }
	}

	public void getBackgroundOfflineDirtyVersion() {
		try {
//			ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "echo", "This is ProcessBuilder Example from JCG");
//			ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "osmosis");
//			ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "osmosis", "-v", "10", "--read-pbf", 
//					"file=C:\\Users\\fprat\\Downloads\\sample_osmosis.osm.pbf", "--mapfile-writer", "file=C:\\Users\\fprat\\Downloads\\test.map",
//					"&&", "cmd.exe", "/c", "echo", "done");
			ProcessBuilder pb = new ProcessBuilder
					("osmosis", "-v", "10", "--read-pbf", 
						"file=/home/tecnic/sample_osmosis.osm.pbf", "--mapfile-writer", "file=/home/tecnic/test.map");
//			System.out.println("Command (getFonsOffline): " + pb.command());
			System.out.println("Run (getFonsOffline) osmosis command");
			Process process = pb.start();

		    System.out.println("Error (FonsOffline) stream:");
		    InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);

		    process.waitFor();

		    System.out.println("Output (FonsOffline) stream:");
		    InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
		}
		catch (Exception evt) {
			System.out.println("Error (getFonsOffline) FonsOffline: " + evt);
		}
	}
	
	public void getBackgroundOfflinePost(HttpServletRequest request) {
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
			System.out.println("doPost FonsOfflinePost");
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
			System.out.println("Error (doPost) FonsOfflinePost: " + evt);
		}
	}

	public void printStream(InputStream stream) throws IOException {
	    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	        System.out.println(inputLine);
	    in.close();
	}

}
