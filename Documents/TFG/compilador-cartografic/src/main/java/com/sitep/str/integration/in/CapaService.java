package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

public interface CapaService {
		
	public void getLayer(String capa, String filename, String atributGeometria, HttpServletResponse response) throws IOException, InterruptedException;
	
	public void printStream (InputStream stream) throws IOException;

}
