package com.sitep.str.integration.in;

import java.io.IOException;
import java.sql.SQLException;

import com.sitep.str.integration.in.classes.Usuari;

@SuppressWarnings("hiding")
public interface UsuariService<Usuari> {
			
	public int registerUser(String userInformation) throws IOException, SQLException;

	public int getUserConfirmation(String userInformation) throws SQLException;

	public String getFiles(String userName);
	
	public void addUsuari(Usuari usuari);
	
}
