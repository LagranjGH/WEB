package ru.lagranj.save;

import org.springframework.beans.factory.annotation.Autowired;

import ru.lagranj.config.ImagerConfig;

public class DirectoryFactory {
	
	@Autowired
	private ImagerConfig config;
	
	public AbstractDirectoryEntity getDirectoryEntity(String path) throws SaveException {
		SimpleDirectoryEntity result = new SimpleDirectoryEntity(path);
		result.setConfig(config);
		return result;
	}
	
}
