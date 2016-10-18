package ru.lagranj.save;

public abstract class AbstractFileEntity {
	protected String dirName;
	protected String newFileName;
	
	public AbstractFileEntity(String dirName, String newFileName) throws SaveException {
		this.dirName = dirName;
		this.newFileName = newFileName;
	}

	protected abstract String generateCorrectName(String originalName) throws SaveException;

	/**
	 * Возвращает окончательное имя файла с полным путем, в который будет сохранено изображение. 
	 * 
	 * @return
	 * @throws SaveException
	 */
	public abstract String getFinalFileName() throws SaveException;
	
}
