package ru.lagranj.save;

public abstract class AbstractFileEntity {
	protected String dirName;
	protected String newFileName;
	
	public AbstractFileEntity(String dirName, String newFileName) throws SaveException {
		this.dirName = dirName;
		this.newFileName = newFileName;
	}

	public abstract String getFinalFileName() throws SaveException;
	
	public abstract String generateCorrectName(String originalName) throws SaveException;
	
}
