package com.sitep.str.integration.in.classes;

public class FitxerVersio {
	Fitxer fitxer;
	Versio versio;
	InformacioGeografica informacioGeografica;
	ExtensioDeFitxer extensioDeFitxer;
	String coordenades;
	String estil;
	String filtre;
	
	public FitxerVersio() {
		super();
	}
	
	public FitxerVersio(Fitxer fitxer, Versio versio, InformacioGeografica informacioGeografica, ExtensioDeFitxer extensioDeFitxer, String coordenades, String estil, String filtre) {
		super();
		this.fitxer = fitxer;
		this.versio = versio;
		this.informacioGeografica = informacioGeografica;
		this.extensioDeFitxer = extensioDeFitxer;
		this.coordenades = coordenades;
		this.estil = estil;
		this.filtre = filtre;
	}
	/**
	 * @return the fitxer
	 */
	public Fitxer getFitxer() {
		return fitxer;
	}
	/**
	 * @param fitxer the fitxer to set
	 */
	public void setFitxer(Fitxer fitxer) {
		this.fitxer = fitxer;
	}
	/**
	 * @return the versio
	 */
	public Versio getVersio() {
		return versio;
	}
	/**
	 * @param versio the versio to set
	 */
	public void setVersio(Versio versio) {
		this.versio = versio;
	}
	/**
	 * @return the informacioGeografica
	 */
	public InformacioGeografica getInformacioGeografica() {
		return informacioGeografica;
	}
	/**
	 * @param informacioGeografica the informacioGeografica to set
	 */
	public void setInformacioGeografica(InformacioGeografica informacioGeografica) {
		this.informacioGeografica = informacioGeografica;
	}
	/**
	 * @return the extensioDeFitxer
	 */
	public ExtensioDeFitxer getExtensioDeFitxer() {
		return extensioDeFitxer;
	}
	/**
	 * @param extensioDeFitxer the extensioDeFitxer to set
	 */
	public void setExtensioDeFitxer(ExtensioDeFitxer extensioDeFitxer) {
		this.extensioDeFitxer = extensioDeFitxer;
	}
	/**
	 * @return the coordenades
	 */
	public String getCoordenades() {
		return coordenades;
	}
	/**
	 * @param coordenades the coordenades to set
	 */
	public void setCoordenades(String coordenades) {
		this.coordenades = coordenades;
	}
	/**
	 * @return the estil
	 */
	public String getEstil() {
		return estil;
	}
	/**
	 * @param estil the estil to set
	 */
	public void setEstil(String estil) {
		this.estil = estil;
	}
	/**
	 * @return the filtre
	 */
	public String getFiltre() {
		return filtre;
	}
	/**
	 * @param filtre the filtre to set
	 */
	public void setFiltre(String filtre) {
		this.filtre = filtre;
	}
	
	
}
