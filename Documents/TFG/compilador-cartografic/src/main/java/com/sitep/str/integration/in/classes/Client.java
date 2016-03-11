package com.sitep.str.integration.in.classes;

public class Client {
	String identificadorDeClient;
	String empresa;
	String telefon;
	String adreça;

	public Client() {
		super();
	}

	/**
	 * @param identificadordeclient2
	 * @param empresa
	 * @param telefon
	 * @param adreça
	 */
	public Client(String identificadordeclient2, String empresa, String telefon, String adreça) {
		super();
		this.identificadorDeClient = identificadordeclient2;
		this.empresa = empresa;
		this.telefon = telefon;
		this.adreça = adreça;
	}

	/**
	 * @return the identificadorDeClient
	 */
	public String getIdentificadorDeClient() {
		return identificadorDeClient;
	}

	/**
	 * @param identificadorDeClient the identificadorDeClient to set
	 */
	public void setIdentificadorDeClient(String identificadorDeClient) {
		this.identificadorDeClient = identificadorDeClient;
	}

	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the telefon
	 */
	public String getTelefon() {
		return telefon;
	}

	/**
	 * @param telefon the telefon to set
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * @return the adreça
	 */
	public String getAdreça() {
		return adreça;
	}

	/**
	 * @param adreça the adreça to set
	 */
	public void setAdreça(String adreça) {
		this.adreça = adreça;
	}

}
