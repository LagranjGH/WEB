package ru.lagranj.util;

import java.io.File;

public class AppUtil {
	
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
	
}
