package com.example.telstranewsfeed;



import java.util.HashMap;
import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Class for custom adapter for the list view to load images lazily
 */
public class LazyAdapter extends BaseAdapter {
	
	/** The Activity of the application */
	private Activity activity;
	
	/** List with the NewsFeed data */
	private List<HashMap<String, Object>> data;
	
	/** LayoutInflater to inflate news view list*/
	private static LayoutInflater inflater=null;
	
	/**  ImageLoader object to store the images in cache**/
	public ImageLoader imageLoader; 

	/**
	 * LazyAdapter constructor
	 * @param a Activity
	 * @param d NewsFeed Data
	 */
	public LazyAdapter(Activity a, NewsFeedData d) {
		activity = a;
		data=d.getNewsFeedlist();
		activity.setTitle(d.getTitle());
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader=new ImageLoader(activity.getApplicationContext());
	}
	
	/**
	 * return the count of list items
	 */
	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * Implementation of the getView method of custom adapter
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(convertView==null)
			vi = inflater.inflate(R.layout.list_item, null);

		TextView title = (TextView)vi.findViewById(R.id.title); // title
		TextView description = (TextView)vi.findViewById(R.id.description); // description 
		ImageView thumb_image = (ImageView)vi.findViewById(R.id.list_image); // thumb image

		HashMap<String, Object> newsFeed = new HashMap<String, Object>();
		newsFeed = data.get(position);

		// Setting all values in listview
		title.setText(newsFeed.get("title").toString());
		description.setText(newsFeed.get("description").toString());
		imageLoader.DisplayImage(newsFeed.get("imageHref").toString(), thumb_image);
		return vi;
	}
}