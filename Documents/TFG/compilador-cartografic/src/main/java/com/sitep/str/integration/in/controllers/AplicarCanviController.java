package com.sitep.str.integration.in.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.sitep.str.integration.in.AplicarCanviService;
import com.sitep.str.integration.in.classes.FitxerVersio;
import com.sitep.str.integration.in.impl.AplicarCanviServiceImpl;

public class AplicarCanviController {

	AplicarCanviService<FitxerVersio> aplicarCanvi = new AplicarCanviServiceImpl();
	
	public void printSomething() {
		aplicarCanvi.printSomething();
	}
	
	public FitxerVersio getFitxerVersio(String idFitxerVersioV, String idFitxerVersioF) {
		return aplicarCanvi.getFitxerVersio(idFitxerVersioV, idFitxerVersioF);
	}

	public void changeCS(String fileName, String fileNameWithoutExtension, String extension, String coordenades, String username, HttpServletResponse response) throws SQLException, IOException, InterruptedException {
		aplicarCanvi.changeCS(fileName, fileNameWithoutExtension, extension, coordenades, username, response);
	}
	
	public void printStream(InputStream stream) throws IOException {
		aplicarCanvi.printStream(stream);
	}

	public void applyFilter(String fileName, String fileNameWithoutExtension, String extension, String info, String username, HttpServletResponse response) {
		aplicarCanvi.applyFilter(fileName, fileNameWithoutExtension, extension, info, username, response);
	}

	public void deleteFile(String fileName, String username) {
		aplicarCanvi.deleteFile(fileName, username);
	}

	public String editFile(String fileName, String username) {
		return aplicarCanvi.editFile(fileName, username);
	}
}
