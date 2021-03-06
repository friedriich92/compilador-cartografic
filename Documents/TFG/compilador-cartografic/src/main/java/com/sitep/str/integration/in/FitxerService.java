package com.sitep.str.integration.in;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.classes.VersioFitxer;

public interface FitxerService<Fitxer> {
	
	public void importFile(HttpServletRequest request, HttpServletResponse response, String userName) throws IOException, SQLException, InterruptedException;
	
	public void addFitxer(Fitxer fitxer);

	public void deleteFitxer(String fileName, String username);

	public String editFitxer(String fileName, String username);
	
	public int fitxersPerUsuari(String userName);
	
	public void printStream(InputStream stream) throws IOException;
	
	public void addVersioFitxer(VersioFitxer versioFitxer);
	
	public void editVersioFitxer(String fileName, String key, String info);

	
}
