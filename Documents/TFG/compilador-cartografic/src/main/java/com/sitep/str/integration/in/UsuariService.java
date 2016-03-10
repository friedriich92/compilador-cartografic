package com.sitep.str.integration.in;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public interface UsuariService<Usuari> {

	public void DBConnection();
	
	public void registerUser(String userInformation) throws IOException, SQLException;

	public void registerUser2(String username, String password, String email, String role) throws IOException, SQLException;

	public int getUserConfirmation(String userInformation) throws SQLException;

	public String getFiles(String userName);
}
