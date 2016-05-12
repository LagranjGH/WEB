package ru.lagranj.save;

public class FileFactory {

	public static AbstractFileEntity getFileEntity(String dirName, String newFileName) throws SaveException {
		return new SimpleURLFileEntity(dirName, newFileName);
	}
	
}
