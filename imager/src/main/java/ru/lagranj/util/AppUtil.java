package ru.lagranj.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppUtil.class);
	
	public static boolean isNullOrEmpty(String value) {
		return (value == null) || value.isEmpty();
	}
	
	public static String concat(Object ...args) {
		if (args == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		for(Object curr: args) {
			sb.append(curr);
		}
		return sb.toString();
	}

	public static long folderSize(File directory) {
	    long length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += folderSize(file);
	    }
	    return length;
	}
	
	public static Properties loadProperties() {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = AppUtil.class.getClassLoader().getResourceAsStream(ImagerConstants.FILE_CONFIG_NAME);
			if (in == null) {
				LOGGER.error("File not found: " + ImagerConstants.FILE_CONFIG_NAME);
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
					LOGGER.warn("Error while closing stream.");
				}
			}
		}
		LOGGER.info("Properties successefully updated.");
		return prop;
	}
}
