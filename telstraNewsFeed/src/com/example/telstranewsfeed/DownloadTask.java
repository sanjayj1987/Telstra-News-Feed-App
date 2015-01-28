package com.example.telstranewsfeed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;



import android.app.Activity;
import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;



/** AsyncTask to download json data */
public class DownloadTask extends AsyncTask<String, Integer, String>{
	String data = null;
	Context mcontext;

	public DownloadTask(Context context) {
		mcontext=context;
	}


	@Override
	protected String doInBackground(String... url) {

		try{

			data = downloadUrl(url[0]);

		}catch(Exception e){
			Log.d("Background Task",e.toString());
		}
		Log.d("Background Task",data);
		return data;
	}

	@Override
	protected void onPostExecute(String result) {

		// The parsing of the xml data is done in a non-ui thread 
		ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask(mcontext);

		// Start parsing xml data
		listViewLoaderTask.execute(result);                        

	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException{
		String data = "";
		InputStream iStream = null;
		try{
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url 
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url 
			urlConnection.connect();

			// Reading data from url 
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

			StringBuffer sb  = new StringBuffer();

			String line = "";
			while( ( line = br.readLine())  != null){
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		}catch(Exception e){
			Log.d("Exception while downloading url", e.toString());
		}finally{
			iStream.close();
		}



		return data;
	}
}


/** AsyncTask to parse json data and load ListView */
class ListViewLoaderTask extends AsyncTask<String, Void ,List<HashMap<String, Object>>>{

	Context mContext;

	public ListViewLoaderTask(Context context) {
		mContext=context;
	}

	JSONObject jObject;
	// Doing the parsing of xml data in a non-ui thread 
	@Override
	protected List<HashMap<String, Object>> doInBackground(String... strJson) {
		try{
			jObject = new JSONObject(strJson[0]);
			JSONParser countryJsonParser = new JSONParser();
			countryJsonParser.parse(jObject);


		}catch(Exception e){
			Log.d("JSON Exception1",e.toString());
		}

		// Instantiating json parser class
		JSONParser newsFeedJsonParser = new JSONParser();


		// A list object to store the parsed countries list
		List<HashMap<String, Object>> newsFeedList = null;

		try{
			// Getting the parsed data as a List construct
			newsFeedList = newsFeedJsonParser.parse(jObject);
		}catch(Exception e){
			Log.d("Exception",e.toString());
		}	       

		return newsFeedList;



	}

	@Override
	protected void onPostExecute(List<HashMap<String, Object>> result) {

		LazyAdapter adapter;
		adapter=new LazyAdapter((Activity)mContext, result);



		MainActivity maActivity= (MainActivity)mContext;
		maActivity.updateUI(adapter);
		maActivity.setMainTitle(JSONParser.mTitle);


	}





}



