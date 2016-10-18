package ru.lagranj.save;

public class FileFactory {

	public static AbstractFileEntity getFileEntity(String dirName, String newFileName) throws SaveException {
		SimpleURLFileEntity result = new SimpleURLFileEntity(dirName, newFileName);
		return result;
	}
	
}
