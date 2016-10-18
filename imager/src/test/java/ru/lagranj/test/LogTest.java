package ru.lagranj.test;

import org.junit.Test;

import ru.lagranj.util.LogUtil;

public class LogTest extends BaseTest {

	@Test
	public void logInfoTest() {
		LogUtil.info("Test info msg.");
		LogUtil.info("Test info exception.", new RuntimeException("test msg excep."));
	}
	
	@Test
	public void logWarnTest() {
		LogUtil.info("Test warn msg.");
		LogUtil.info("Test warn exception.", new RuntimeException("test msg excep."));
	}
	
	@Test
	public void logErrorTest() {
		LogUtil.error("Test error msg.");
		LogUtil.error("Test error exception.", new RuntimeException("test msg excep."));
	}
	
}
