package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VectorialService {
	
	public void vectoriseAndUploadFileToDatabase(String filename, String filenameWithoutExtension, HttpServletResponse response, HttpServletRequest request);

	public void printStream (InputStream stream) throws IOException;

	public void vectoriseAndUploadOsmFileToDatabase(String fileName, String fileNameWithoutExtension, HttpServletResponse response, HttpServletRequest request) throws IOException;
	
}
