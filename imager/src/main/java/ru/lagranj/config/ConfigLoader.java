package ru.lagranj.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
	private static final String FILE_CONF = "imager.properties";
	
	public static Properties loadProperties() {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = ConfigLoader.class.getClassLoader().getResourceAsStream(FILE_CONF);
			if (in == null) {
				LOGGER.error("File not found: " + FILE_CONF);
				return prop;
			}
			prop.load(in);
		} catch (IOException e) {
			LOGGER.error("Can not load properties.", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.info("Error while closing stream.");
				}
			}
		}
		return prop;
	}
}
