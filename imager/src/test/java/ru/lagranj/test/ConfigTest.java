package ru.lagranj.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.lagranj.config.ImagerConfig;
import ru.lagranj.util.AppUtil;

public class ConfigTest extends BaseTest {
	
	@Autowired
	private ImagerConfig config;
	
	@Test
	public void testFileAccept() {
		Assert.assertTrue(config.isAcceptableFile("123.jpEG"));
	}
	
	@Test
	public void testRootDir() {
		String rootDir = config.getRootDirectory();
		Assert.assertFalse(AppUtil.isNullOrEmpty(rootDir));
	}
	
	@Test
	public void testSubDirSize() throws InterruptedException {
		int size = config.getMaxDirectorySize();
		Assert.assertEquals(32, size);
	}
}
