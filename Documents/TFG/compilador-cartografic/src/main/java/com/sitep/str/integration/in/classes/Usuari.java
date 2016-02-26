package com.sitep.str.integration.in.classes;

public class Usuari {
	String identificadorDeUsuari; 
	String contrasenya; 
	String email;
	boolean connectat;
	RolDeUsuari rol; 
	Idioma idioma;
	
	public Usuari() {
		super();
	}

	public Usuari(String identificadorDeUsuari, String contrasenya, String email, boolean connectat, RolDeUsuari rol, Idioma idioma) {
		super();
		this.identificadorDeUsuari = identificadorDeUsuari;
		this.contrasenya = contrasenya;
		this.email = email;
		this.connectat = connectat;
		this.rol = rol;
		this.idioma = idioma;
	}

	/**
	 * @return the identificadorDeUsuari
	 */
	public String getIdentificadorDeUsuari() {
		return identificadorDeUsuari;
	}

	/**
	 * @param identificadorDeUsuari the identificadorDeUsuari to set
	 */
	public void setIdentificadorDeUsuari(String identificadorDeUsuari) {
		this.identificadorDeUsuari = identificadorDeUsuari;
	}

	/**
	 * @return the contrasenya
	 */
	public String getContrasenya() {
		return contrasenya;
	}

	/**
	 * @param contrasenya the contrasenya to set
	 */
	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the connectat
	 */
	public boolean isConnectat() {
		return connectat;
	}

	/**
	 * @param connectat the connectat to set
	 */
	public void setConnectat(boolean connectat) {
		this.connectat = connectat;
	}

	/**
	 * @return the rol
	 */
	public RolDeUsuari getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(RolDeUsuari rol) {
		this.rol = rol;
	}

	/**
	 * @return the idioma
	 */
	public Idioma getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}
}
