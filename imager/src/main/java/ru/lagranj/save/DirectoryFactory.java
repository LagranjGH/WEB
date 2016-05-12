package ru.lagranj.save;

public class DirectoryFactory {
	
	public static AbstractDirectoryEntity getDirectoryEntity(String path) throws SaveException {
		return new SimpleDirectoryEntity(path);
	}
	
}
