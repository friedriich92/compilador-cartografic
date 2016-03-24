package com.sitep.str.integration.in;

import javax.sql.DataSource;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;

import org.w3c.dom.Document;

import com.sitep.str.integration.in.impl.AplicarCanviServiceImpl;

public class STRServicesIntegration{
	
//	static Logger log = Logger.getLogger(STRServicesIntegration.class);

	private String origens;
//	private SecureRandom random = new SecureRandom();
	
	private String DESC_TAULA_SAND_BOLCAT;
	
	private String urlService = "";
	private String iteratorxPath = "";

	private XPath xPath = null;
	
	public String getDESC_TAULA_SAND_BOLCAT() {
		return DESC_TAULA_SAND_BOLCAT;
	}

	public void setDESC_TAULA_SAND_BOLCAT(String desc_taula_sand_bolcat) {
		DESC_TAULA_SAND_BOLCAT = desc_taula_sand_bolcat;
	}

	private DataSource dataSource;
	
	public void loadData() {
		try {
			System.out.println();
			System.out.println("loadData STRServicesIntegration");
			System.out.println("-------------------------------");
			System.out.println("DONE STRServicesIntegration.java");
			AplicarCanviService ap = new AplicarCanviServiceImpl();
			ap.printSomething();
			/*UsuariService usuariService = new UsuariServiceImpl();
			usuariService.getFiles("ester");*/
		} catch (Exception tr) {
			System.out.println("Error loading data: " + tr);
		}
	}
	
	public java.sql.Date getCurrentDatetime() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
	
	protected Object read(String expression, Document xmlDocument, QName returnType) throws Exception {
		XPathExpression xPathExpression = xPath.compile(expression);
		return xPathExpression.evaluate(xmlDocument, returnType);
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setUrlService(String urlService) {
		this.urlService = urlService;
	}

	public String getUrlService() {
		return urlService;
	}

	public String getIteratorxPath() {
		return iteratorxPath;
	}

	public void setIteratorxPath(String iteratorxPath) {
		this.iteratorxPath = iteratorxPath;
	}


	public void setOrigens(String origens) {
		this.origens = origens;
	}

	public String getOrigens() {
		return origens;
	}
}