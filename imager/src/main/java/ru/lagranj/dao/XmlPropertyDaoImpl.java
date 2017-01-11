package ru.lagranj.dao;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.lagranj.util.ImagerConstants;
import ru.lagranj.util.LogUtil;

public class XmlPropertyDaoImpl implements PropertyDao {
			
	private Document getDocument() {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = domFactory.newDocumentBuilder();
	        Document doc = builder.parse(
	        		XmlPropertyDaoImpl.class.getClassLoader().getResourceAsStream(ImagerConstants.FILE_CONFIG_NAME));
	        return doc;
		} catch (Exception e) {
			LogUtil.error("Error parsing document: ", e);
			return null;
		}
	}
	
	private NodeList getNodesForQuery(String xQuery) {
		return getNodesForQuery(getDocument(), xQuery);
	}
	
	private NodeList getNodesForQuery(Document doc, String xQuery) {
		try {
	        if (doc != null) {	        	
	        	XPath xpath = XPathFactory.newInstance().newXPath();
	        	XPathExpression expr = xpath.compile(xQuery);      	
	        	Object result = expr.evaluate(doc, XPathConstants.NODESET);
	        	NodeList nodes = (NodeList) result;
	        	return nodes;
	        }
		} catch (Exception e) {
			LogUtil.error("Error while load properties: ", e);
		}
		return null;
	}
	
	private void saveChange(Document doc) {
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			Source input = new DOMSource(doc);
			URL url = XmlPropertyDaoImpl.class.getClassLoader().getResource(ImagerConstants.FILE_CONFIG_NAME);
			String path = url.toURI().getPath();
			Result output = new StreamResult(new File(path));

			transformer.transform(input, output);
		} catch (Exception e) {
			LogUtil.error("Can not save property: ", e);
		}

	}
	
	@Override
	public String getPropertyByName(String name) {
		String query = String.format("settings/setting[@name='%s']", name);
		NodeList nodeList = getNodesForQuery(query);
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.hasAttributes()) {
					NamedNodeMap attrs = node.getAttributes();
					return attrs.getNamedItem("name").getNodeValue();
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
		Document doc = getDocument();
		String query = String.format("settings/setting[@name='%s']", name);
		NodeList nodeList = getNodesForQuery(doc, query);
		if (nodeList != null) {
			boolean isChanged = false;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.hasAttributes()) {
					NamedNodeMap attrs = node.getAttributes();
					attrs.getNamedItem("value").setNodeValue(value);
					isChanged = true;
					break;
				}
			}
			if (isChanged) {				
				saveChange(doc);
			}
		}
	}

}
