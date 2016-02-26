package com.sitep.str.integration.in;

import com.sitep.str.integration.in.classes.FitxerVersio;

@SuppressWarnings("hiding")
public interface AplicarCanviService<FitxerVersio> {

	public void printSomething();
	
	public void listFitxerVersio();
	
	public void addFitxerVersio();
	
	public void deleteFitxerVersio();
	
	public void detailFitxerVersio();

	public void saveFitxerVersio();
	
	public FitxerVersio getFitxerVersio(String idFitxerVersioV, String idFitxerVersioF);
}
