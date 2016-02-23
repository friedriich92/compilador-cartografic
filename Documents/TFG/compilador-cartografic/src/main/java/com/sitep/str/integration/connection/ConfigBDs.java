package com.sitep.str.integration.connection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ConfigBDs {
	private static String BASECONFIGKEY;

//	static Logger log = Logger.getLogger(ConfigBDs.class);

	@SuppressWarnings("unchecked")
	public static List<String> getKeys(String origens) {
		try {
			BASECONFIGKEY = origens;
			List<String> keysList = new ArrayList<String>();
			CompositeConfiguration baseConfig2 = null;

			baseConfig2 = new CompositeConfiguration();
			baseConfig2.setDelimiterParsingDisabled(true);
			PropertiesConfiguration config = new PropertiesConfiguration();
			config.setDelimiterParsingDisabled(true);
			config.load(BASECONFIGKEY);
			baseConfig2.addConfiguration(config);
			Iterator<String> iterator = baseConfig2.getKeys();
			int contador = 1;
			while (iterator.hasNext()) {
				String element = iterator.next();
				element = element.split("\\.")[0];
				contador += 1;
				if (!keysList.contains(element))
					keysList.add(element);
			}
			return keysList;

		} catch (Exception ign) {
//			log.error("Error carregant fitxer de configuracio", ign);
			throw new RuntimeException(ign);
		}

	}

	public static String getConfig(String key) {
		return getConfig(key, BASECONFIGKEY);
	}

	public static String getConfig(String key, String resource) {
		try {

			CompositeConfiguration baseConfig2 = null;

			baseConfig2 = new CompositeConfiguration();
			baseConfig2.setDelimiterParsingDisabled(true);
			PropertiesConfiguration config = new PropertiesConfiguration();
			config.setDelimiterParsingDisabled(true);
			config.load(resource);
			baseConfig2.addConfiguration(config);

			Object value = baseConfig2.getProperty(key);
			if (value == null)
				return "";

			return "" + value;

		} catch (Exception ign) {
//			log.error("Error carregant fitxer de configuracio", ign);
			throw new RuntimeException(ign);
		}

	}

	public static String getBASECONFIGKEY() {
		return BASECONFIGKEY;
	}

	public static void setBASECONFIGKEY(String baseconfigkey) {
		BASECONFIGKEY = baseconfigkey;
	}

}
