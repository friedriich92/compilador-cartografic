package com.sitep.str.integration.in;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public interface IdiomaService<Idioma> {
	
	public void exportFormat(HttpServletRequest request) throws IOException;

}
