package com.netchus.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {

	public static final String APP_KEY = "appKey needed";
	
	public static void main(String[] args) {
		SensorService ss = new SensorServiceImpl();
		String tmp = ss.getResponse();
		System.out.println(tmp.toString());
		
//		ss.getResponseWithData();
		
	}
	
	
	@Override
	public String getResponse() {
		String tmp = null;
		try {
			tmp = sendGet();
			if(tmp!=null) {
				List<Map<String,String>> data = makeSensorData(tmp);
				for (Map<String,String> map : data) {
					System.out.println(map.entrySet());
					insert(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp;
	}


    
    public List<Map<String, String>> makeSensorData(String tmp) throws ParseException{
    	// 1. json parser로 자른다
    	JSONParser jsonParser = new JSONParser(); 
    	JSONObject jsonObject = (JSONObject)jsonParser.parse(tmp);
    	JSONObject jsonObj = (JSONObject)jsonObject.get("content");
    	JSONArray jsonArray = (JSONArray)jsonObj.get("data");
    	
    	System.out.println(jsonObj.get("result"));
    	
    	System.out.println(jsonArray.size());
    	
    	// 객체(map)을 담을 ArrayList 선언
    	List<Map<String,String>> jsonSensorList = new ArrayList<Map<String,String>>();
    	
    	for (@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonArray.iterator(); iterator.hasNext();) {
    		JSONObject metaObject = (JSONObject) iterator.next();
    		System.out.println(metaObject.get("meta").toString());
    		JSONObject object = (JSONObject) metaObject.get("meta");
    		
    		// 2. 자른 parse 데이터를 HashMap에 담고
    		// 담을 tmp map 선언
    		Map<String, String> jsonSensorsMap = new HashMap<String,String>();
    		
    		// 객체 잘라서 담기
    		jsonSensorsMap.put("sensorName", (String) object.get("sensorName"));
    		jsonSensorsMap.put("sensorId", (String) object.get("sensorId"));
    		
    		// System.out.println("sensor : " + jsonSensorsMap.entrySet());
    		
    		// 3. 만들어진 HashMap을 ArrayList에 담는다.
    		jsonSensorList.add(jsonSensorsMap);
    	}
    	
    	return jsonSensorList;
    }
    
    // git
    
    private int insert(List<Map<String, String>> data) {
    	
    	// 1. mapper instance member 선언하기
    	
    	// 1.1 mapper autowired로 객체 바인딩
    	
    	// 2. mapper insert 메소드 가지고 와서 쓰면 될듯
    	
    	return 0;
    }
    
    
    private String sendGet() throws Exception {
        String url = "https://sentry-api.rbinsight.co.kr/sentry/v1/sensor/WPAS_KTR_SENSOR?sensorStartTime=2020-11-11T15:02:00&sensorEndTime=2020-11-11T15:02:59&offset=0&limit=200";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //전송방식
        con.setRequestMethod("GET");
        //Request Header 정의
        con.setRequestProperty("url", url);
        con.setRequestProperty("appKey", APP_KEY);
        con.setConnectTimeout(10000);       //컨텍션타임아웃 10초
        con.setReadTimeout(5000);           //컨텐츠조회 타임아웃 5총
        
        int responseCode = con.getResponseCode();
        
        if(responseCode!=200) {
        	return null;
        }
        
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        Charset charset = Charset.forName("UTF-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),charset));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("조회결과 : " + response.toString());
        return response.toString();
    }
    
    @Override
    public List<Map<String,String>> getResponseWithData() {
    	
    	List<Map<String,String>> data = null;
    	// response와 동작은 같되 db로 insert하는 대신 controller로 list를 반환하는 메소드
    	String tmp = null;
		try {
			tmp = sendGet();
			if(tmp!=null) {
				data = makeSensorData(tmp);
				for (Map<String,String> map : data) 
					System.out.println(map.entrySet());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
    }
    
}
