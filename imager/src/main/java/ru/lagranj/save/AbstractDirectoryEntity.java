package ru.lagranj.save;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractDirectoryEntity {
	private String pathToDir;
	
	public AbstractDirectoryEntity(String path) throws SaveException {
		pathToDir = path;
		File rootDir = new File(pathToDir);
		if (!rootDir.exists()) {
			throw new SaveException("Directory not exist: " + pathToDir);
		}
		if (!rootDir.canWrite()) {
			throw new SaveException("There is no access for writing to: " + pathToDir);
		}
		if (rootDir.isFile()) {
			throw new SaveException("Not a directory: " + pathToDir);
		}
	}
	
	public String getFinalName() throws SaveException {
		File dirFileEntity = new File(pathToDir);
		List<File> rootContent = new ArrayList<>(Arrays.asList(dirFileEntity.listFiles()));
		return getLocalName(rootContent);
	}
	
	private String getLocalName(List<File> rootContent) throws SaveException {
		if (rootContent.size() == 0) {
			return getFirstDirName();
		} else {
			List<File> acceptableDirs = new ArrayList<>();
			for (File currFile : rootContent) {
				if (isAcceptableDir(currFile)) {
					acceptableDirs.add(currFile);
				}
			}
			if (acceptableDirs.size() == 0) {
				return getFirstDirName();
			}
			Collections.sort(acceptableDirs, getComparator());
			File lastFolder = acceptableDirs.get(acceptableDirs.size() - 1);
			return getResultDirName(lastFolder);
		}		
	}
	
	public abstract String getFirstDirName();
	
	public abstract boolean isAcceptableDir(File file);
	
	public abstract Comparator<File> getComparator();
	
	public abstract String getResultDirName(File file);
	
}
