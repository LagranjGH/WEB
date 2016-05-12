package ru.lagranj.service;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ru.lagranj.util.LogUtil;

public class StartServlet implements Servlet {
	private ServletConfig config;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public ServletConfig getServletConfig() {
		return config;
	}

	@Override
	public String getServletInfo() {
		return "Start servlet";
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		config = servletConfig;
		
		LogUtil.info("Starting iMager application.");
	}

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		
	}

}
