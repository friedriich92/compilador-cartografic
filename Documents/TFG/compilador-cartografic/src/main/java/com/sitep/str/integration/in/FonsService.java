package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FonsService {
	
	/**
	 * getFons: funci� per a realitzar la conversi� d'un fitxer d'OpenStreetMaps (.osm)
	 * a un fitxer Mapspforge (.map)
	 */
	public void getBackground(HttpServletResponse response);
	
	public void getBackgroundDirtyVersion();
	
	public void printStream(InputStream stream) throws IOException;
	
	public void getBackgroundPost(HttpServletRequest request);
}
