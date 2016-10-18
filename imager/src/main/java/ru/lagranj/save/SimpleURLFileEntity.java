package ru.lagranj.save;

import java.io.File;

import ru.lagranj.util.AppUtil;
import ru.lagranj.util.ImagerConstants;

public class SimpleURLFileEntity extends AbstractFileEntity {

	public SimpleURLFileEntity(String dirName, String newFileName) throws SaveException {
		super(dirName, newFileName);
	}

	@Override
	public String getFinalFileName() throws SaveException {
		String originalName = getOnlyFileName(newFileName);
		checkName(originalName);
		File targetFile = new File(AppUtil.concat(dirName, ImagerConstants.FILE_SEPARATOR, originalName));
		if (!targetFile.exists()) {
			return originalName;
		}
		return generateCorrectName(originalName);
	}

	@Override
	protected String generateCorrectName(String originalName) throws SaveException {
		String name = onlyName(originalName);
		String ext = onlyExtension(originalName);
		File file;
		int number = 0;
		String newName;
		do {
			number++;
			newName = AppUtil.concat(name, ImagerConstants.STRING_UNDERSCORE, number, ImagerConstants.STRING_DOT, ext);
			file = new File(AppUtil.concat(dirName, ImagerConstants.FILE_SEPARATOR, newName));
		} while (file.exists());
		return newName;
	}
	
	private String getOnlyFileName(String inputFileName) {
		int ind = inputFileName.lastIndexOf("/");
		if (ind == -1) {
			ind = inputFileName.indexOf("\\");
		}
		if (ind == -1) {
			return inputFileName;
		}
		String result = inputFileName.substring(ind + 1);
		return result;
	}
	
	private void checkName(String originalName) throws SaveException {
		int ind = originalName.indexOf(ImagerConstants.STRING_DOT);
		if (ind == -1) {
			throw new SaveException("Incorrect file name to save: " + originalName);
		}
	}
	
	private String onlyName(String fileName) {
		int ind = fileName.lastIndexOf(ImagerConstants.STRING_DOT);
		String result = fileName.substring(0, ind);
		return result;
	}
	
	private String onlyExtension(String fileName) {
		int ind = fileName.lastIndexOf(ImagerConstants.STRING_DOT);
		String result = fileName.substring(ind + 1);
		return result;
	}	

}
