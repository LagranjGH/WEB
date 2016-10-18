package ru.lagranj.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.lagranj.config.ImagerConfig;
import ru.lagranj.save.SaveException;
import ru.lagranj.save.Saver;
import ru.lagranj.util.ImagerConstants;

public class MainHandlerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainHandlerServlet.class);

	@Autowired
	private Saver saver; 
	
	@Autowired
	private ImagerConfig config;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>");
			sb.append("<HEAD>");
				sb.append("<TITLE>Info</TITLE>");
			sb.append("</HEAD>");
			sb.append("<BODY>");
				sb.append("<H2>There are no actions for HTTP method GET</H2>");
			sb.append("</BODY>");
		sb.append("</HTML>");
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println(sb.toString());
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imageUrl = req.getParameter(ImagerConstants.IMG_PARAM_NAME);
		LOGGER.info("Saving URL: " + imageUrl);
		String result;
		try {
			saver.saveImageFromURL(imageUrl);
			result = "SUCCESSFUL";
		} catch (SaveException e) {
			LOGGER.error(e.getMessage());
			result = "ERROR";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>");
			sb.append("\n<HEAD>");
				sb.append("\n<TITLE>Result</TITLE>");
				if (config.isAutoClosable()) {
					sb.append(createScript());
				}
			sb.append("\n</HEAD>");
			sb.append("\n<BODY>");
				sb.append("\n<H3>Result: " + result + "</H3>");
			sb.append("\n</BODY>");
		sb.append("</HTML>");
		
		PrintWriter out = resp.getWriter();
		out.println(sb.toString());
	}
	
	private String createScript() {
		StringBuilder script = new StringBuilder();
		script.append("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		script.append("\n<script type=\"text/javascript\">");
		script.append("\nfunction closeWindow() {");
		script.append("\nwindow.open('','_parent','');window.close();}");
		script.append("\nvar timer = window.setTimeout(closeWindow," + config.getAutoClosePeriod() + ");");
		script.append("\n</script>");
		return script.toString();
	}
}
