package com.sitep.str.integration.in.classes;

public class VersioFitxer {
	String idversiofitxer;
	int numerodeversio;
	String estil;
	String coordenades;
	String filtre;
	
	public VersioFitxer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param idversiofitxer
	 * @param numerodeversio
	 * @param estil
	 * @param coordenades
	 * @param filtre
	 */
	public VersioFitxer(String idversiofitxer, int numerodeversio, String estil, String coordenades, String filtre) {
		super();
		this.idversiofitxer = idversiofitxer;
		this.numerodeversio = numerodeversio;
		this.estil = estil;
		this.coordenades = coordenades;
		this.filtre = filtre;
	}

	/**
	 * @return the idversiofitxer
	 */
	public String getIdversiofitxer() {
		return idversiofitxer;
	}

	/**
	 * @param idversiofitxer the idversiofitxer to set
	 */
	public void setIdversiofitxer(String idversiofitxer) {
		this.idversiofitxer = idversiofitxer;
	}

	/**
	 * @return the numerodeversio
	 */
	public int getNumerodeversio() {
		return numerodeversio;
	}

	/**
	 * @param numerodeversio the numerodeversio to set
	 */
	public void setNumerodeversio(int numerodeversio) {
		this.numerodeversio = numerodeversio;
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
