package com.sitep.str.integration.in;

import java.io.IOException;

import com.sitep.str.integration.in.classes.Idioma;

@SuppressWarnings("hiding")
public interface IdiomaService<Idioma> {
	
	public void editIdioma(String id) throws IOException;
	
	public void addIdioma(Idioma idioma) throws IOException;

}
