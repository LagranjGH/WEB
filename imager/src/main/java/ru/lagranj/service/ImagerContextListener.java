package ru.lagranj.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ru.lagranj.util.ImagerConstants;

public class ImagerContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String pathToApp = event.getServletContext().getRealPath("/");
		System.setProperty(ImagerConstants.ROOT_PATH, pathToApp);
	}

}
