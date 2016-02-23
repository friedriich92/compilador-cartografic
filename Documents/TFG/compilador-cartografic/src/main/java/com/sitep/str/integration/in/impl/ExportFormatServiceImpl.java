package com.sitep.str.integration.in.impl;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import com.sitep.str.integration.in.ExportFormatService;

public class ExportFormatServiceImpl implements ExportFormatService {

	public void exportFormat(HttpServletRequest request) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
			}
		String data = buffer.toString();
		System.out.println("String field " + data + " readed.");
	}

}
