package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;

public interface CarregadorFitxersVectorialsService {
	
	public void vectoriseAndUploadFileToDatabase(String filename, String filenameWithoutExtension);

	public void printStream (InputStream stream) throws IOException;
}
