package ru.lagranj.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		sampleHandle(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		sampleHandle(req, resp);
	}
	
	private void sampleHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>");
			sb.append("<HEAD>");
				sb.append("<TITLE>Welcome to iMager</TITLE>");
			sb.append("</HEAD>");
			sb.append("<BODY>");
				sb.append("<H2>Hello from iMager application!</H2>");
			sb.append("</BODY>");
		sb.append("</HTML>");
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println(sb.toString());
	}
}
