package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

public interface CarregadorDeFitxersVectorialsService<FitxerVectorial> {
	
	public void vectoriseAndUploadFileToDatabase(String filename, String filenameWithoutExtension, HttpServletResponse response);

	public void printStream (InputStream stream) throws IOException;
	
}
