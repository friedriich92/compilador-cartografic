package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.classes.FitxerVersio;

@SuppressWarnings("hiding")
public interface AplicarCanviService<FitxerVersio> {

	public void printSomething();
	
	public void listFitxerVersio();
	
	public void addFitxerVersio();
	
	public void deleteFitxerVersio();
	
	public void detailFitxerVersio();

	public void saveFitxerVersio();
	
	public FitxerVersio getFitxerVersio(String idFitxerVersioV, String idFitxerVersioF);

	public void changeCS(String fileName, String fileNameWithoutExtension, String extension, String coordenades, String username, HttpServletResponse response) throws SQLException, IOException, InterruptedException;

	public void DBConnection();
	
	public void printStream(InputStream stream) throws IOException;

	public void applyFilter(String fileName, String fileNameWithoutExtension, String extension, String info, String username, HttpServletResponse response);
}
