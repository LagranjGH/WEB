package ru.lagranj.dao;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.*;

import ru.lagranj.util.ImagerConstants;
import ru.lagranj.util.LogUtil;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class XmlPropertyDaoImpl implements PropertyDao {
			
	NodeList getNodesForQuery(String xQuery) {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = domFactory.newDocumentBuilder();
	        Document doc = builder.parse(
	        		XmlPropertyDaoImpl.class.getClassLoader().getResourceAsStream(ImagerConstants.FILE_CONFIG_NAME));
	        
	        XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr
             = xpath.compile(xQuery);

            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            return nodes;
		} catch (Exception e) {
			LogUtil.error("Error while load properties: ", e);
			return null;
		}
	}
	
	@Override
	public String getPropertyByName(String name) {
		String query = String.format("settings/setting[@name=%s]", name);
		NodeList nodeList = getNodesForQuery(query);
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.hasAttributes()) {
					NamedNodeMap attrs = node.getAttributes();
					return attrs.getNamedItem(name).getNodeValue();
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, String> getProperties() {
		Map<String, String> result = new HashMap<>();
		String query = "settings/setting";
		NodeList nodeList = getNodesForQuery(query);
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.hasAttributes()) {
					NamedNodeMap attrs = node.getAttributes();
					String name = attrs.getNamedItem("name").getNodeValue();
					String value = attrs.getNamedItem("value").getNodeValue();
					if (name != null && value != null) {						
						result.put(name, value);
					}
				}
			}
		}
		return result;
	}

	@Override
	public void setProperty(String name, String value) {
		// TODO Auto-generated method stub
		
	}

}
