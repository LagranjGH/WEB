package ru.lagranj.test;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.lagranj.config.ImagerConfig;
import ru.lagranj.util.AppUtil;

public class ConfigTest extends BaseTest {
	
	@Autowired
	private ImagerConfig config;
	
	@Test
	public void testLoader() {
		Properties prop = AppUtil.loadProperties();
		Assert.assertNotNull(prop);
		Assert.assertTrue(prop.size() > 0);
	}
	
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
	public void testSubDirSize() {
		int size = config.getMaxDirectorySize();
		Assert.assertEquals(32, size);
	}
}
