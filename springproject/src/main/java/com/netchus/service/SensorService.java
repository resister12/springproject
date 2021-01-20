package com.netchus.service;

import java.util.List;
import java.util.Map;

public interface SensorService {
	
	public String getResponse(); 
	
	public List<Map<String,String>> getResponseWithData();
	
}
