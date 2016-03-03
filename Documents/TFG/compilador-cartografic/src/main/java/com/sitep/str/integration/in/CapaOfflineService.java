package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

public interface CapaOfflineService<FitxerCapa> {
	
	public void DBConnection();
	
	public void getLayerOffline(String capa, String atributGeometria, HttpServletResponse response) throws IOException;
	
	public void printStream (InputStream stream) throws IOException;

}
