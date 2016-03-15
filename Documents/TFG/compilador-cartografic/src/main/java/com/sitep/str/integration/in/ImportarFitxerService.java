package com.sitep.str.integration.in;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ImportarFitxerService<Fitxer> {
	
	public void importFile(HttpServletRequest request, HttpServletResponse response, String userName) throws IOException, SQLException;
	
	public void addFitxer(Fitxer fitxer);

	public void deleteFitxer(String fileName, String username);

	public String editFitxer(String fileName, String username);
	
	public int fitxersPerUsuari(String userName);
}
