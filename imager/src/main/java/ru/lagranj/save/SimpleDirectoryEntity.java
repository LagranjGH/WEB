package ru.lagranj.save;

import java.io.File;
import java.util.Comparator;

import ru.lagranj.config.ImagerConfig;
import ru.lagranj.util.AppUtil;
import ru.lagranj.util.ImagerConstants;

public class SimpleDirectoryEntity extends AbstractDirectoryEntity {
	
	private ImagerConfig config;

	public void setConfig(ImagerConfig config) {
		this.config = config;
	}
	
	public SimpleDirectoryEntity(String path) throws SaveException {
		super(path);
	}

	@Override
	public String getFirstDirName() {
		return ImagerConstants.FIRST_DIRECTORY_NAME;
	}
	
	@Override
	public boolean isAcceptableDir(File currFile) {
		if(currFile.isFile()) {
			return false;
		}
		String currName = currFile.getName();
		if (currName.contains("_")) {
			return false;
		}
		try {
			Integer.parseInt(currName);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	@Override
	public Comparator<File> getComparator() {
		return new NumberNameFileComparator();
	}

	@Override
	public String getResultDirName(File lastFolder) {
		String lastName = lastFolder.getName();
		int newName = -1;
		try {
			newName = Integer.parseInt(lastName);
		} catch (NumberFormatException e) {
			return getFirstDirName();
		}
		if (AppUtil.folderSize(lastFolder) >= ImagerConstants.MULT_CONVERT_BYTES_TO_MEGABYTES * config.getMaxDirectorySize()) {
			newName++;
		}
		return Integer.toString(newName);
	}
	
	private static class NumberNameFileComparator implements Comparator<File> {
		
		@Override
		public int compare(File file1, File file2) {
			String fileName1 = file1.getName();
			String fileName2 = file2.getName();
			if (fileName1.length() == fileName2.length()) {
				return fileName1.compareTo(fileName2);
			}
			if (fileName1.length() < fileName2.length()) {
				return -1;
			} else {
				return 1;
			}
		}
		
	}
}
