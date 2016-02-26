package com.sitep.str.integration.in.controllers;

import javax.servlet.http.HttpServlet;

import com.sitep.str.integration.in.AplicarCanviService;
import com.sitep.str.integration.in.classes.FitxerVersio;
import com.sitep.str.integration.in.impl.AplicarCanviServiceImpl;

public class AplicarCanviController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	AplicarCanviService<FitxerVersio> applicarCanvi = new AplicarCanviServiceImpl();

	public void addFitxerVersio() {
		applicarCanvi.addFitxerVersio();
	}
	
	public void deleteFitxerVersio() {
		applicarCanvi.deleteFitxerVersio();
	}
	
	public void listFitxerVersio() {
		applicarCanvi.listFitxerVersio();
	}
	
	public void detailFitxerVersio() {
		applicarCanvi.detailFitxerVersio();
	}
	
	public void saveFitxerVersio() {
		applicarCanvi.saveFitxerVersio();
	}
	
	public void printSomething() {
		applicarCanvi.printSomething();
	}
	
	public FitxerVersio getFitxerVersio(String idFitxerVersioV, String idFitxerVersioF) {
		return applicarCanvi.getFitxerVersio(idFitxerVersioV, idFitxerVersioF);
	}
}
