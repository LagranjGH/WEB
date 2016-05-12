package ru.lagranj.test;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import ru.lagranj.config.ConfigLoader;
import ru.lagranj.config.ImagerConfig;
import ru.lagranj.util.AppUtil;

public class ConfigTest {
	
	@Test
	public void testLoader() {
		Properties prop = ConfigLoader.loadProperties();
		Assert.assertNotNull(prop);
		Assert.assertTrue(prop.size() > 0);
	}
	
	@Test
	public void testFileAccept() {
		Assert.assertTrue(ImagerConfig.isAcceptableFile("123.jpEG"));
	}
	
	@Test
	public void testRootDir() {
		String rootDir = ImagerConfig.getRootDirectory();
		Assert.assertFalse(AppUtil.isNullOrEmpty(rootDir));
	}
	
	@Test
	public void testSubDirSize() {
		int size = ImagerConfig.getMaxDirectorySize();
		Assert.assertTrue(size > 0);
	}
}
