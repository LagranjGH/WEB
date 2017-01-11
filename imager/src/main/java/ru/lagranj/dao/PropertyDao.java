package ru.lagranj.dao;

import java.util.Map;

public interface PropertyDao {

	String getPropertyByName(String name);
	
	Map<String, String> getProperties(); 
	
	void setProperty(String name, String value);
	
}
