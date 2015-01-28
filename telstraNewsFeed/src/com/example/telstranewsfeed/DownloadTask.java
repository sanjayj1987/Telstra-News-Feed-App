package com.example.telstranewsfeed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/** AsyncTask to download json data */
public class DownloadTask extends AsyncTask<String, Integer, String> {

	/** Data received from the URl */
	String data = null;
	/** Listener reference */
	private INetworkListener mlistener;

	/** constructor*/
	public DownloadTask(INetworkListener listener) {
		mlistener = listener;
	}

	@Override
	protected String doInBackground(String... url) {

		try {

			data = downloadUrl(url[0]);

			if (data == null) {
				return null;
			}

		} catch (Exception e) {
			Log.d("Background Task", e.toString());
		}
		Log.d("Background Task", data);
		return data;
	}

	@Override
	protected void onPostExecute(String result) {

		// The parsing of the xml data is done in a non-ui thread
		ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask(
				mlistener);

		// Start parsing xml data
		listViewLoaderTask.execute(result);

	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			return null;
		} finally {
			iStream.close();
		}

		return data;
	}
}

/** AsyncTask to parse json data and load ListView */
class ListViewLoaderTask extends AsyncTask<String, Void, NewsFeedData> {

	Context mContext;
	private INetworkListener mlistener;

	public ListViewLoaderTask(INetworkListener listener) {
		mlistener = listener;
	}

	JSONObject jObject;

	// Doing the parsing of xml data in a non-ui thread
	@Override
	protected NewsFeedData doInBackground(String... strJson) {

		try {
			jObject = new JSONObject(strJson[0]);
			JSONParser countryJsonParser = new JSONParser();
			countryJsonParser.parse(jObject);

		} catch (Exception e) {
			return null;
		}

		// Instantiating json parser class
		JSONParser newsFeedJsonParser = new JSONParser();
		NewsFeedData newsFeedData = null;
		;

		try {
			// Getting the parsed data as a List construct
			newsFeedData = newsFeedJsonParser.parse(jObject);
		} catch (Exception e) {
			return null;
		}

		return newsFeedData;

	}

	@Override
	protected void onPostExecute(NewsFeedData result) {

		mlistener.onPostExecute(result);

	}

}
