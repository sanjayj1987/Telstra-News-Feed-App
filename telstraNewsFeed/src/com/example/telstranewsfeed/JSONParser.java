package com.example.telstranewsfeed;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/** A class to parse json data */
public class JSONParser {
	
	public static String mTitle;
	
	
	// Receives a JSONObject and returns a list
	public List<HashMap<String,Object>> parse(JSONObject jObject){		
		
		JSONArray jNewsFeed = null;
		try {		
			// Retrieves all the elements in the news feed data array 
			
			mTitle=jObject.get("title").toString();

			jNewsFeed = jObject.getJSONArray("rows");
			Log.d("JSON value",jNewsFeed.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		 // Invoking getNewsData with the array of json object
		 // where each json object represent a NewsData Object
		return getNewsData(jNewsFeed);
	}
	
	
	private List<HashMap<String, Object>> getNewsData(JSONArray jNewsFeed){
		
		Log.d("JSON length",""+jNewsFeed.length());
		
		int newsDataCount = jNewsFeed.length();
		List<HashMap<String, Object>> newsDataList = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> newsData = null;	

		// Taking each country, parses and adds to list object 
		for(int i=0; i<newsDataCount;i++){
			try {
				// Call getCountry with country JSON object to parse the country 
				newsData = getNews((JSONObject)jNewsFeed.get(i));
				
				newsDataList.add(newsData);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	
		
		return newsDataList;
	}
	
	// Parsing the News Data JSON object 
	private HashMap<String, Object> getNews(JSONObject jNewsData){

		HashMap<String, Object> newsMap = new HashMap<String, Object>();
		String title = "";
		String description="";
		
		String imageHref = "";		
		
		try {
			title = jNewsData.getString("title");
			description = jNewsData.getString("description");
			imageHref = jNewsData.getString("imageHref");			
			newsMap.put("title", title);
			newsMap.put("description", description);
			newsMap.put("imageHref", imageHref);
			//country.put("details", details);
			
		} catch (JSONException e) {			
			e.printStackTrace();
		}	
		Log.d("JSON object list ",""+newsMap);
		return newsMap;
	}
}