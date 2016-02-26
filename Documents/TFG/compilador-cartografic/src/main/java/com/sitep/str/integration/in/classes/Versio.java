package com.sitep.str.integration.in.classes;

public class Versio {
	int idVersió;
	int numeroDeVersio;
	
	public Versio() {
		super();
	}
	
	public Versio(int idVersió, int numeroDeVersio) {
		super();
		this.idVersió = idVersió;
		this.numeroDeVersio = numeroDeVersio;
	}
	
	/**
	 * @return the idVersió
	 */
	public int getIdVersió() {
		return idVersió;
	}
	
	/**
	 * @param idVersió the idVersió to set
	 */
	public void setIdVersió(int idVersió) {
		this.idVersió = idVersió;
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
}
