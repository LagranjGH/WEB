package ru.lagranj.test;

import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.lagranj.save.SaveException;
import ru.lagranj.save.SimpleDirectoryEntity;

public class DirEntityTest {
	private static final String TEST_DIR_EMPTY = "C:/tmp/test";
	private static final String TEST_DIR_EXIST = "C:/tmp/test2";
	private static final String TEST_DIR_FULL = "C:/tmp/test3";
	private static final String TEST_DIR_CORRECT_NAMES = "C:/tmp/test4";
	
	@BeforeClass
	public static void setUp() {
		File file = new File(TEST_DIR_EMPTY);
		Assert.assertTrue(file.exists());
		file = new File(TEST_DIR_EXIST);
		Assert.assertTrue(file.exists());
		file = new File(TEST_DIR_FULL);
		Assert.assertTrue(file.exists());
	}
	
	@Test
	public void testDirInEmptyRoot() throws SaveException {
		SimpleDirectoryEntity dirEntity = new SimpleDirectoryEntity(TEST_DIR_EMPTY);
		String targetDirName = dirEntity.getFinalName();
		Assert.assertEquals("1",targetDirName);
	}
	
	@Test
	public void testDirExistInRoot() throws SaveException {
		SimpleDirectoryEntity dirEntity = new SimpleDirectoryEntity(TEST_DIR_EXIST);
		String targetDirName = dirEntity.getFinalName();
		Assert.assertEquals("2", targetDirName);
	}
	
	@Test
	public void testDirNewDir() throws SaveException {
		SimpleDirectoryEntity dirEntity = new SimpleDirectoryEntity(TEST_DIR_FULL);
		String targetDirName = dirEntity.getFinalName();
		Assert.assertEquals("3", targetDirName);
	}
	
	@Test
	public void testComparingDirNames() throws SaveException {
		SimpleDirectoryEntity dirEntity = new SimpleDirectoryEntity(TEST_DIR_CORRECT_NAMES);
		String targetDirName = dirEntity.getFinalName();
		Assert.assertEquals("10", targetDirName);
	}
}
