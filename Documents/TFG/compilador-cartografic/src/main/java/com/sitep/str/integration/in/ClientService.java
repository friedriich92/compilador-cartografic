package com.sitep.str.integration.in;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;

public interface ClientService<Client> {

	void registerClients(HttpServletRequest request) throws IOException, FileUploadException;
	
	void addClient(Client client);

}
