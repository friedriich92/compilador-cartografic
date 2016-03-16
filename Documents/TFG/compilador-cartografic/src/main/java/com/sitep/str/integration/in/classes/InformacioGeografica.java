package com.sitep.str.integration.in.classes;

public class InformacioGeografica {
	String idInformacioGeografica;
	String informacio;
	int numerodeversio;
	
	public InformacioGeografica() {
		super();
	}

	/**
	 * @param idInformacioGeografica
	 * @param informacio
	 * @param numerodeversio
	 */
	public InformacioGeografica(String idInformacioGeografica, String informacio, int numerodeversio) {
		super();
		this.idInformacioGeografica = idInformacioGeografica;
		this.informacio = informacio;
		this.numerodeversio = numerodeversio;
	}

	/**
	 * @return the idInformacioGeografica
	 */
	public String getIdInformacioGeografica() {
		return idInformacioGeografica;
	}

	/**
	 * @param idInformacioGeografica the idInformacioGeografica to set
	 */
	public void setIdInformacioGeografica(String idInformacioGeografica) {
		this.idInformacioGeografica = idInformacioGeografica;
	}

	/**
	 * @return the informacio
	 */
	public String getInformacio() {
		return informacio;
	}

	/**
	 * @param informacio the informacio to set
	 */
	public void setInformacio(String informacio) {
		this.informacio = informacio;
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
}
