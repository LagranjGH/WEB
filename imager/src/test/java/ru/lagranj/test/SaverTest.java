package ru.lagranj.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.lagranj.config.ImagerConfig;
import ru.lagranj.config.PropertyName;
import ru.lagranj.save.SaveException;
import ru.lagranj.save.Saver;

public class SaverTest extends BaseTest {	
	private static final String URL_PART = "http://mitsubishi-motors.ru/img/1604/";
	private static final String IMG_NAME = "file080416165203.jpg";		
	private static final String TMP_FILE_NAME = System.getProperty("user.home") + System.getProperty("file.separator") + "tmp_file.txt";
	
	private static boolean successCreateFile = false;
	
	private static String correctProperty;
	
	@Autowired
	private Saver saver; 
	
	@Autowired
	private ImagerConfig config;
	
    @BeforeClass
    public static void setUp() throws IOException {
    	File tmpFile = new File(TMP_FILE_NAME);
    	if (!tmpFile.exists()) {
    		successCreateFile = tmpFile.createNewFile(); 	
    	}
    }

    @AfterClass
    public static void tearDown() {
    	if (successCreateFile) {
    		File tmpFile = new File(TMP_FILE_NAME);
    		Assert.assertTrue(tmpFile.delete());
    	}
    }
	
	@Test
	public void testNotAcceptability() {
		String url = URL_PART + IMG_NAME + "g";
		SaveException exc = null;
		try {
			saver.saveImageFromURL(url);
		} catch (SaveException e) {
			exc = e;
		}
		Assert.assertNotNull(exc);
		Assert.assertTrue(exc.getMessage().contains("not acceptable"));
	}
	
	@Test
	public void testGetDirectorySuccess() {	
		Throwable thr = invokeGetDirectoryMethod("getDirectory");
		if (thr != null) {
			Assert.assertEquals("111", thr.getMessage());
		}
		Assert.assertNull(thr);
	}
	
	@Test
	public void testGetDirectoryNotExistRoot() {
		setIncorrectProperty(PropertyName.ROOT_DIR, "C:/folder13");
		Throwable thr = invokeGetDirectoryMethod("getDirectory");
		Assert.assertNotNull(thr);
		Assert.assertTrue(thr.getMessage().contains("not exist"));
		resetProperties(PropertyName.ROOT_DIR);
	}
	
	@Test
	public void testGetDirectoryNotDirectory() {
		Assert.assertTrue(successCreateFile);
		setIncorrectProperty(PropertyName.ROOT_DIR, TMP_FILE_NAME);
		Throwable thr = invokeGetDirectoryMethod("getDirectory");
		Assert.assertNotNull(thr);
		Assert.assertTrue(thr.getMessage().contains("Not a directory"));
		resetProperties(PropertyName.ROOT_DIR);
	}
	
	private Throwable invokeGetDirectoryMethod(String methodName) {
		Throwable thr = null;
		try {
			Method method = saver.getClass().getDeclaredMethod(methodName);
			method.setAccessible(true);
			method.invoke(saver);
		} catch ( NoSuchMethodException 
				| SecurityException 
				| IllegalAccessException 
				| IllegalArgumentException e) {
			thr = e;
		} catch (InvocationTargetException t) {
			Assert.assertTrue(t.getTargetException() instanceof SaveException);
			thr = t.getTargetException();
		} 
		return thr;
	}
	
	private void setIncorrectProperty(String name, String value) {
		String root = config.getRootDirectory();
		Assert.assertNotNull(root);
		Throwable thr = null;
		try {
			Field propField = config.getClass().getDeclaredField("prop");
			propField.setAccessible(true);
			Properties propValue = (Properties) propField.get(config);
			correctProperty = propValue.getProperty(name);
			propValue.setProperty(name, value);
			propField.set(config, propValue);
		} catch (NoSuchFieldException 
				| SecurityException 
				| IllegalAccessException 
				| IllegalArgumentException e) {
			thr = e;
		} 
		Assert.assertNull(thr);
	}
	
	private void resetProperties(String name) {
		Throwable thr = null;
		try {
			Field propField = config.getClass().getDeclaredField("prop");
			propField.setAccessible(true);
			Properties propValue = (Properties) propField.get(config.getClass().newInstance());
			propValue.setProperty(name, correctProperty);
			propField.set(config, propValue);
		} catch (NoSuchFieldException 
				| SecurityException 
				| IllegalAccessException 
				| IllegalArgumentException | InstantiationException e) {
			thr = e;
		}
		Assert.assertNull(thr);
	}
}
