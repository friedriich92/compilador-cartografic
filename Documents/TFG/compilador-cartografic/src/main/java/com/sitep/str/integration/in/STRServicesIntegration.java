package com.sitep.str.integration.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private Connection connection = null;
	
	public void loadData() {
		try {
			System.out.println();
			System.out.println("loadData STRServicesIntegration");
			System.out.println("-------------------------------");
			System.out.println("DONE STRServicesIntegration.java");
			AplicarCanviService ap = new AplicarCanviServiceImpl();
			ap.printSomething();
//			prova();
//			UsuariService usuariService = new UsuariServiceImpl();
//			usuariService.registerUser2(new BigInteger(20, random).toString(32), "12345", "ucomp@gmail.com", "analista"); // Arreglar
		} catch (Exception tr) {
			System.out.println("Error loading data: " + tr);
		}
	}
	
	@SuppressWarnings({ "unused", "null" })
	public void prova() {
		DBConnection();
		PreparedStatement pstmt1 = null;
		String sql1, almostFinal, finalResponse;
		int numberOfRows = 0;
		sql1 = finalResponse = almostFinal = "";
		ResultSet rs = null;
		try {
			System.out.println(finalResponse);
		} catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	    	try {
	    	  rs.close();
	    	  pstmt1.close();
	    	  connection.close();
	    	  } catch (SQLException e) {
	    		  e.printStackTrace();
	    		  }
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
	
	public void DBConnection() {
		System.out.println("PostgreSQL " + "JDBC Connection Testing");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://192.122.214.77:5432/osm", "postgres", "SiteP0305");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}