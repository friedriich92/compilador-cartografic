package com.sitep.str.integration.in.classes;

public class InformacioGeografica {
	int idInformacioGeografica;
	String informacio;
	
	public InformacioGeografica() {
		super();
	}

	public InformacioGeografica(int idInformacioGeografica, String informacio) {
		super();
		this.idInformacioGeografica = idInformacioGeografica;
		this.informacio = informacio;
	}

	/**
	 * @return the idInformacioGeografica
	 */
	public int getIdInformacioGeografica() {
		return idInformacioGeografica;
	}

	/**
	 * @param idInformacioGeografica the idInformacioGeografica to set
	 */
	public void setIdInformacioGeografica(int idInformacioGeografica) {
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
}
