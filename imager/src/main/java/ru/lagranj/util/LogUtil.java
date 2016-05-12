package ru.lagranj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);
	
	public static void info(String msg) {
		LOGGER.info(msg);
	}
	
	public static void info(String msg, Throwable t) {
		LOGGER.info(msg, t);
	}
	
	public static void warn(String msg) {
		LOGGER.warn(msg);
	}
	
	public static void warn(String msg, Throwable t) {
		LOGGER.warn(msg, t);
	}
	
	public static void error(String msg) {
		LOGGER.error(msg);
	}
	
	public static void error(String msg, Throwable t) {
		LOGGER.error(msg, t);
	}
}
