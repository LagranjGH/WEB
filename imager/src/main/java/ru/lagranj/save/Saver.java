package ru.lagranj.save;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import ru.lagranj.config.ImagerConfig;
import ru.lagranj.util.AppUtil;
import ru.lagranj.util.ImagerConstants;
import ru.lagranj.util.LogUtil;

public class Saver {

	public static void saveImageFromURL(final String imageUrl) throws SaveException {
		if (!ImagerConfig.isAcceptableFile(imageUrl)) {
			//На самом деле URL не обязан указывать на картинку непосредственно
			throw new SaveException("Image file not acceptable!");
		}
		URL url;
		try {
			url = new URL(imageUrl);
		} catch (MalformedURLException e) {
			throw new SaveException("Bad URL: " + imageUrl + ". Cause: " + e.getMessage());
		}
		
		String directory = getDirectory();
		prepareDirectory(directory);
		String newFileName = getNewFileName(directory, imageUrl);	
		saveNewFile(url, directory, newFileName);
	}

	//main logic below
	private static String getDirectory() throws SaveException {
		String rootPath = ImagerConfig.getRootDirectory();
		AbstractDirectoryEntity rootDir = DirectoryFactory.getDirectoryEntity(rootPath);
		String finalDirName = AppUtil.concat(rootPath, ImagerConstants.FILE_SEPARATOR, rootDir.getFinalName());
		return finalDirName;
	}

	private static void prepareDirectory(String directory) throws SaveException {
		try {
			File targetDir = new File(directory);
			if(!targetDir.exists()) {
				if(!targetDir.mkdirs()) {
					throw new SaveException("Could not create target directory: " + directory);
				}
			}
		} catch (SaveException e) {
			throw e;
		} catch (Exception e) {
			throw new SaveException("Could not create target directory: " + directory + ". Cause: " + e.getMessage());
		}
	}
	
	private static String getNewFileName(String directory, String imageUrl) throws SaveException {
		AbstractFileEntity newFileEntity = FileFactory.getFileEntity(directory, imageUrl);
		return newFileEntity.getFinalFileName();
	}
	
	private static void saveNewFile(URL url, String directory, String newFileName) throws SaveException {
		String finalName = AppUtil.concat(directory, ImagerConstants.FILE_SEPARATOR, newFileName);
		File destination = new File(finalName);
		try {
			FileUtils.copyURLToFile(url, destination);
		} catch (IOException e) {
			throw new SaveException("Could not save image. Cause: " + e.getMessage());
		}
		LogUtil.info("Successfully saved to " + finalName);
	}
}
