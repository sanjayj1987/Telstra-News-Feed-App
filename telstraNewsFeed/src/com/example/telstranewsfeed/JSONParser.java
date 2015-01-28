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
	
	/**
	 * Receives a JSONObject and returns a list
	 * @param jObject
	 * @return NewsFeedData
	 */
	public  NewsFeedData parse(JSONObject jObject){		
		
		JSONArray jNewsFeed = null;
		NewsFeedData newsFeedData=null;
		try {		
			// Retrieves all the elements in the news feed data array 
			
			newsFeedData=new NewsFeedData();
			newsFeedData.setTitle(jObject.get("title").toString());
			// Invoking getNewsData with the array of json object
			 // where each json object represent a NewsData Object
			jNewsFeed = jObject.getJSONArray("rows");
			newsFeedData.setNewsFeedlist(getNewsData(jNewsFeed));

		} catch (JSONException e) {
			return null;
		}
		
		 
		return newsFeedData;
	}
	
	/**
	 * Parses each news feed and adds to the List
	 * @param jNewsFeed
	 * @return
	 */
	private List<HashMap<String, Object>> getNewsData(JSONArray jNewsFeed){
		
		Log.d("JSON length",""+jNewsFeed.length());
		
		int newsDataCount = jNewsFeed.length();
		List<HashMap<String, Object>> newsDataList = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> newsData = null;	

		// Taking each NewsFeed, parses and adds to list object 
		for(int i=0; i<newsDataCount;i++){
			try {
				
				newsData = getNews((JSONObject)jNewsFeed.get(i));
				
				newsDataList.add(newsData);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	
		
		return newsDataList;
	}
	
	/**
	 *  Parsing the News Data JSON object 
	 * @param jNewsData
	 * @return HashMap containing the news feed
	 */
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
			
			
		} catch (JSONException e) {			
			return null;
		}	
		Log.d("JSON object list ",""+newsMap);
		return newsMap;
	}
}