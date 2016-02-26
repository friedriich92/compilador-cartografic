package com.sitep.str.integration.in.classes;

public class Fitxer {
	String idFitxer;
	boolean modificat;
	ExtensioDeFitxer extensioDeFitxer;
	int idUsuari;

	public Fitxer() {
		super();
	}

	/**
	 * @param idFitxer
	 * @param modificat
	 * @param extensioDeFitxer
	 * @param idUsuari
	 */
	public Fitxer(String idFitxer, boolean modificat, ExtensioDeFitxer extensioDeFitxer, int idUsuari) {
		super();
		this.idFitxer = idFitxer;
		this.modificat = modificat;
		this.extensioDeFitxer = extensioDeFitxer;
		this.idUsuari = idUsuari;
	}

	/**
	 * @return the idFitxer
	 */
	public String getIdFitxer() {
		return idFitxer;
	}

	/**
	 * @param idFitxer the idFitxer to set
	 */
	public void setIdFitxer(String idFitxer) {
		this.idFitxer = idFitxer;
	}

	/**
	 * @return the modificat
	 */
	public boolean isModificat() {
		return modificat;
	}

	/**
	 * @param modificat the modificat to set
	 */
	public void setModificat(boolean modificat) {
		this.modificat = modificat;
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

	public int getIdUsuari() {
		return idUsuari;
	}

	public void setIdUsuari(int idUsuari) {
		this.idUsuari = idUsuari;
	}
}
