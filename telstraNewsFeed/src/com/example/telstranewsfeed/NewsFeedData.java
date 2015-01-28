package com.example.telstranewsfeed;

import java.util.HashMap;
import java.util.List;

/**
 * Data object holding the NewsFeed 
 */
public class NewsFeedData {

	/** Title for action bar*/
	private String title;
	
	/** List to hold the NewsFeed*/
	private List<HashMap<String, Object>> newsFeedlist;
	
	/**
	 * getter for title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * setter for title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * getter for news feed list
	 * @return List
	 */
	public List<HashMap<String, Object>> getNewsFeedlist() {
		return newsFeedlist;
	}
	
	/**
	 * setter for news feed list
	 * @param newsFeedlists
	 */	
	public void setNewsFeedlist(List<HashMap<String, Object>> newsFeedlist) {
		this.newsFeedlist = newsFeedlist;
	}
	
	
}
