package com.sitep.str.integration.in.classes;

public class Idioma {
	int identificador;
	String encatala;
	String encastella;

	public Idioma() {
		super();
	}

	/**
	 * @param identificador
	 * @param encatala
	 * @param encastella
	 */
	public Idioma(int identificador, String encatala, String encastella) {
		super();
		this.identificador = identificador;
		this.encatala = encatala;
		this.encastella = encastella;
	}

	/**
	 * @return the identificador
	 */
	public int getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the encatala
	 */
	public String getEncatala() {
		return encatala;
	}

	/**
	 * @param encatala the encatala to set
	 */
	public void setEncatala(String encatala) {
		this.encatala = encatala;
	}

	/**
	 * @return the encastella
	 */
	public String getEncastella() {
		return encastella;
	}

	/**
	 * @param encastella the encastella to set
	 */
	public void setEncastella(String encastella) {
		this.encastella = encastella;
	}
}
