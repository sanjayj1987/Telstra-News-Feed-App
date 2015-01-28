package com.example.telstranewsfeed;


/**
 * Interface for publishing the network success/failure
 * 
 */
public interface INetworkListener {

	/**
	 * Show progress bar
	 */
	void showProgressDialog();
	
	void onPreExecute();

	void onPostExecute(NewsFeedData result);
}
