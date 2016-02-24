package com.sitep.str.integration.in.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sitep.str.integration.in.CarregadorDeFitxersVectorialsService;

public class CarregadorDeFitxersVectorialsServiceImpl implements CarregadorDeFitxersVectorialsService {

	public void vectoriseAndUploadFileToDatabase(String filename, String filenameWithoutExtension) {
		try {
			System.out.println("carregar fitxer vectorial");
			System.out.println("-----------------------");
			System.out.println("DONE CarregadorFitxersVectorials.java");
			ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "shp2pgsql -s 26986 /files/"+filename+" public."+filenameWithoutExtension+" | psql -h localhost -d osm -U postgres");
			System.out.println("Run (carregarFitxerVectorial) shp2pgsql command " + pb.toString());
			Process process = pb.start();

		    System.out.println("Error (carregarFitxerVectorial) stream:");
		    InputStream errorStream = process.getErrorStream();
		    printStream(errorStream);

		    process.waitFor();

		    System.out.println("Output (carregarFitxerVectorial) stream:");
		    InputStream inputStream = process.getInputStream();
		    printStream(inputStream);
		}
		catch (Exception evt) {
			System.out.println("Error CarregadorFitxersVectorials: " + evt);
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
