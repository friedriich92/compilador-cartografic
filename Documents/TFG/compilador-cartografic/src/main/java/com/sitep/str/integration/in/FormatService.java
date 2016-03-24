package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.classes.InformacioGeografica;

@SuppressWarnings("hiding")
public interface FormatService<InformacioGeografica> {

	void getBackground(HttpServletResponse response);

	void getLayer(String capa, String filename, String atributGeometria, HttpServletResponse response, String userName) throws IOException, InterruptedException;

	void getBackgroundPost(String data);

	void vectoriseAndUploadFileToDatabase(String fileName, String fileNameWithoutExtension, HttpServletResponse response, HttpServletRequest request);

	void vectoriseAndUploadOsmFileToDatabase(String fileName, String fileNameWithoutExtension, HttpServletResponse response, HttpServletRequest request) throws IOException;
	
	String getFileInformation(String exactNameWithoutExtension);
	
	void printStream(InputStream stream) throws IOException;
	
	public void addInformacioGeografica(InformacioGeografica informacioGeografica);

	public void editInformacioGeografica(String string, String readFileToString);

}
