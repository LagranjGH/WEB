package ru.lagranj.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import ru.lagranj.dao.PropertyDao;
import ru.lagranj.util.AppUtil;
import ru.lagranj.util.ImagerConstants;
import ru.lagranj.util.LogUtil;

public class ImagerConfig {	
	
	@Autowired
	private PropertyDao propDao;
	
	private volatile Map<String, String> prop = new HashMap<String, String>();
	
	@Scheduled(fixedRate = ImagerConstants.RELOAD_PROPERTIES_PERIOD * 1000)
	private void refresh() {
		Map<String, String> propTmp = propDao.getProperties();
		prop = propTmp;
	}
	
	public void doRefresh() {
		refresh();
	}
	
	public boolean isAcceptableFile(String fileName) {
		if (AppUtil.isNullOrEmpty(fileName)) {
			LogUtil.warn("File name is empty.");
			return false;
		}
		String accept = prop.get(PropertyName.FILE_ACCEPT);
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
	
	public String getRootDirectory() {
		String rootDir = prop.get(PropertyName.ROOT_DIR);
		if (AppUtil.isNullOrEmpty(rootDir)) {
			rootDir = System.getProperty("user.home");
			LogUtil.warn("Using default root directory: " + rootDir);
		}
		return rootDir;
	}
	
	public int getMaxDirectorySize() {
		String dirSize = prop.get(PropertyName.SUBDIR_SIZE);
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
	
	public boolean isAutoClosable(){
		String autoClose = prop.get(PropertyName.AUTO_CLOSE);
		boolean result = ImagerConstants.DEFAULT_AUTO_CLOSE;
		if (AppUtil.isNullOrEmpty(autoClose)) {
			LogUtil.warn("By default autoclosable parameter is " + result);
			return result;
		}
		result = Boolean.valueOf(autoClose);
		return result;
	}
	
	public int getAutoClosePeriod() {
		String periodProp = prop.get(PropertyName.AUTO_CLOSE_PERIOD);
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
