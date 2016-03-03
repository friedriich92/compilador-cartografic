package com.sitep.str.integration.in;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public interface ImportarFitxerService<Fitxer> {
	
	public void importFile(HttpServletRequest request, String userName) throws IOException, SQLException;
	
	public void DBConnection();
	
}
