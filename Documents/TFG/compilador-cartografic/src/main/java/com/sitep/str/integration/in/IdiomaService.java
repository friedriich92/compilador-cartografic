package com.sitep.str.integration.in;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.sitep.str.integration.in.classes.Idioma;

public interface IdiomaService {
	
	public void editIdioma(String id) throws IOException;
	
	public void addIdioma(Idioma idioma) throws IOException;

}
