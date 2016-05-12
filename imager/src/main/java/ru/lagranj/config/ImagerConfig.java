package ru.lagranj.config;

import java.util.Properties;

import ru.lagranj.util.AppUtil;
import ru.lagranj.util.ImagerConstants;
import ru.lagranj.util.LogUtil;

public class ImagerConfig {	
	private static Properties prop = new Properties();
	
	private static long lastTimestamp = 0;
	
	private static void refresh() {
		long currTimestamp = System.currentTimeMillis();
		if (currTimestamp > lastTimestamp + ImagerConstants.RELOAD_PROPERTIES_PERIOD * 1000) {
			prop = ConfigLoader.loadProperties();
			lastTimestamp = currTimestamp;
		}
	}
	
	public static boolean isAcceptableFile(String fileName) {
		refresh();
		if (AppUtil.isNullOrEmpty(fileName)) {
			LogUtil.warn("File name is empty.");
			return false;
		}
		String accept = prop.getProperty(PropertyName.FILE_ACCEPT);
		if (accept != null) {
			String[] fileTypes = accept.split("/");
			fileName = fileName.toLowerCase();
			for (String fileType : fileTypes) {
				if (fileName.endsWith(fileType.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getRootDirectory() {
		refresh();
		String rootDir = prop.getProperty(PropertyName.ROOT_DIR);
		if (AppUtil.isNullOrEmpty(rootDir)) {
			rootDir = System.getProperty("user.home");
			LogUtil.warn("Using default root directory: " + rootDir);
		}
		return rootDir;
	}
	
	public static int getMaxDirectorySize() {
		refresh();
		String dirSize = prop.getProperty(PropertyName.SUBDIR_SIZE);
		int size = ImagerConstants.DEFAULT_SUBDIRECTORY_SIZE;
		if (AppUtil.isNullOrEmpty(dirSize)) {
			LogUtil.warn("Using default subdirectory size: " + size);
			return size;
		}
		try {
			size = Integer.valueOf(dirSize);
		} catch(NumberFormatException e) {
			LogUtil.warn("Using default subdirectory size: " + size);
		}
		return size;
	}
	
	public static boolean isAutoClosable(){
		refresh();
		String autoClose = prop.getProperty(PropertyName.AUTO_CLOSE);
		boolean result = ImagerConstants.DEFAULT_AUTO_CLOSE;
		if (AppUtil.isNullOrEmpty(autoClose)) {
			LogUtil.warn("By default autoclosable parameter is " + result);
			return result;
		}
		result = Boolean.valueOf(autoClose);
		return result;
	}
	
	public static int getAutoClosePeriod() {
		refresh();
		String periodProp = prop.getProperty(PropertyName.AUTO_CLOSE_PERIOD);
		int period = ImagerConstants.DEFAULT_AUTO_CLOSE_PERIOD;
		if (AppUtil.isNullOrEmpty(periodProp)) {
			LogUtil.warn("Using default autoclose period = " + period);
			return period;
		}
		try {
			period = Integer.valueOf(periodProp);
		} catch(NumberFormatException e) {
			LogUtil.warn("Using default autoclose period = " + period);
		}
		return period;
	}
}
