package com.sitep.str.integration.in.classes;

import java.sql.Date;

public class Fitxer {
	String idFitxer;
	String idUsuari;
	int numeroDeVersio;
	Date date;
	String nomDeFitxer;
	ExtensioDeFitxer extensioDeFitxer;
	boolean modificat;
	
	public Fitxer() {
		super();
	}

	/**
	 * @param idFitxer
	 * @param idUsuari
	 * @param numeroDeVersio
	 * @param date
	 * @param nomDeFitxer
	 * @param extensioDeFitxer
	 * @param modificat
	 */
	public Fitxer(String idFitxer, String idUsuari, int numeroDeVersio, Date date, String nomDeFitxer, ExtensioDeFitxer extensioDeFitxer, boolean modificat) {
		super();
		this.idFitxer = idFitxer;
		this.idUsuari = idUsuari;
		this.numeroDeVersio = numeroDeVersio;
		this.date = date;
		this.nomDeFitxer = nomDeFitxer;
		this.extensioDeFitxer = extensioDeFitxer;
		this.modificat = modificat;
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
	 * @return the idUsuari
	 */
	public String getIdUsuari() {
		return idUsuari;
	}

	/**
	 * @param idUsuari the idUsuari to set
	 */
	public void setIdUsuari(String idUsuari) {
		this.idUsuari = idUsuari;
	}

	/**
	 * @return the numeroDeVersio
	 */
	public int getNumeroDeVersio() {
		return numeroDeVersio;
	}

	/**
	 * @param numeroDeVersio the numeroDeVersio to set
	 */
	public void setNumeroDeVersio(int numeroDeVersio) {
		this.numeroDeVersio = numeroDeVersio;
	}

	/**
	 * @return the date
	 */
	public java.sql.Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the nomDeFitxer
	 */
	public String getNomDeFitxer() {
		return nomDeFitxer;
	}

	/**
	 * @param nomDeFitxer the nomDeFitxer to set
	 */
	public void setNomDeFitxer(String nomDeFitxer) {
		this.nomDeFitxer = nomDeFitxer;
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
	
}
