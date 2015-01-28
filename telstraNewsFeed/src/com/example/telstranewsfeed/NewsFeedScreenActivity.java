package com.example.telstranewsfeed;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This class manages the life cycle events of the News Feed screen Activity
 */
public class NewsFeedScreenActivity extends Activity {
	// All static variables
	static final String URL = "https://dl.dropboxusercontent.com/u/746330/facts.json";
	// XML node keys
	static final String KEY_TITLE = "title";
	static final String KEY_DESCRIPTIOn = "description";
	static final String KEY_THUMB_URL = "thumb_url";
	private ProgressDialog progD;
	ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsfeedscreen);
		progD=new ProgressDialog(this);
		downloadTask();
	}


    /**
     * This method is used to download the data from the URL.
     */
	private void downloadTask(){


		if(checkInternetConenction()) {

			list = (ListView) findViewById(R.id.list);

			// Register the context menu for actions
			registerForContextMenu(list);
			callbackListener.showProgressDialog();
			// Creating a new non-ui thread task to download json data 
			DownloadTask downloadTask = new DownloadTask(callbackListener);

			// Starting the download process
			downloadTask.execute(NewsFeedScreenActivity.URL);
		} else {
			// notify user you are not online
			showAlertDialog(this , "Internet Connection", "No Network Connectivity");
		}




	}


	/**
	 * This method checks whether the device has Internet connectivity.
	 * returns true id available else false. 
	 */
	private boolean checkInternetConenction(){
		boolean internetStatus = false;
		ConnectivityManager check = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

		if ( check.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED 
				|| check.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ) {



			internetStatus =  true;

		}
		else if ( check.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED 
				|| check.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {


		}
		return internetStatus;

	}

  /**
   * Update the list view with the data 
   * @param adapter
   */
	public void updateUI(LazyAdapter adapter){

		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

	}


	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * */
	public void showAlertDialog(final Activity activity, String title, String message) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting OK Button
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(Intent.ACTION_MAIN);
				i.addCategory(Intent.CATEGORY_HOME);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
				startActivity(i);
				dialog.dismiss();
				activity.finish();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}


	/**
	 * Function to display exit Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * */
	public void exitAlertDialog(final Activity activity, String title, String message) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting OK Button
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(Intent.ACTION_MAIN);
				i.addCategory(Intent.CATEGORY_HOME);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
				startActivity(i);
				dialog.dismiss();
				activity.finish();


			}
		});


		// Setting Cancel Button
		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	public void onBackPressed() {

		exitAlertDialog(this, "Exit Application","Are you sure you want to exit the application!!!");

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId() ) {
		case R.id.refresh: downloadTask();

		break;

		default:
			break;
		}
		return true;
	}

	INetworkListener callbackListener = new INetworkListener() {
		@Override
		public void onPostExecute(NewsFeedData result) {

			if (progD != null && progD.isShowing())
				progD.dismiss();
			if (result != null) { 
				LazyAdapter adapter;
				adapter=new LazyAdapter(NewsFeedScreenActivity.this, result);

				updateUI(adapter);
			} else {
				showAlertDialog(NewsFeedScreenActivity.this , "Download Failed", "Please try again");
			}

		}

		/**
		 * Shows the progress dialog
		 */
		public void showProgressDialog() {
			progD = ProgressDialog.show(NewsFeedScreenActivity.this, null, null);
			progD.setContentView(new ProgressBar(NewsFeedScreenActivity.this));
		}



		@Override
		public void onPreExecute() {

			showProgressDialog();
		}
	};



}

