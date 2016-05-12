package ru.lagranj.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import ru.lagranj.save.SaveException;
import ru.lagranj.save.SimpleURLFileEntity;
import ru.lagranj.util.AppUtil;
import ru.lagranj.util.ImagerConstants;

public class FileEntityTest {
	private static final String TEST_DIR = "C:/tmp";
	private static final String TEST_FILE_NAME = "123.jpg";

	@Test
	public void testCorrectName() throws SaveException {
		SimpleURLFileEntity entity = new SimpleURLFileEntity(TEST_DIR, TEST_FILE_NAME);
		String fileName = entity.getFinalFileName();
		Assert.assertEquals(TEST_FILE_NAME, fileName);
	}
	
	@Test
	public void testIncorrectName() {
		SaveException ex = null;
		try {
			SimpleURLFileEntity entity = new SimpleURLFileEntity(TEST_DIR, "1223");
			entity.getFinalFileName();
		} catch (SaveException e) {
			ex = e;
		}
		Assert.assertNotNull(ex);
		Assert.assertTrue(ex.getMessage().contains("name to save"));
	}
	
	@Test
	public void testGeneratedName() throws SaveException {
		String testNameBefore = "111.txt";
		String testNameAfter = "111_1.txt";
		try {
			File tempFile = new File(AppUtil.concat(TEST_DIR, ImagerConstants.FILE_SEPARATOR, testNameBefore));
			File tempFileExpected = new File(AppUtil.concat(TEST_DIR, ImagerConstants.FILE_SEPARATOR, testNameAfter));
			Assert.assertFalse(tempFile.exists());
			Assert.assertFalse(tempFileExpected.exists());
			tempFile.createNewFile();			
			SimpleURLFileEntity entity = new SimpleURLFileEntity(TEST_DIR, testNameBefore);
			String fileName = entity.getFinalFileName();
			if (tempFile.exists()) {
				tempFile.delete();
			}
			Assert.assertEquals(testNameAfter, fileName);
			Assert.assertFalse(tempFile.exists());
			Assert.assertFalse(tempFileExpected.exists());
		} catch(IOException e) {
			throw new SaveException(e.getMessage());
		}
	}
}
