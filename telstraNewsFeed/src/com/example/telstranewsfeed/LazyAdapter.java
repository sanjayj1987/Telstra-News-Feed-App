package com.example.telstranewsfeed;



import java.util.HashMap;
import java.util.List;

import com.example.newsfeed.R;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private List<HashMap<String, Object>> data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 

	public LazyAdapter(Activity a, List<HashMap<String, Object>> d) {
		activity = a;
		data=d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader=new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(convertView==null)
			vi = inflater.inflate(R.layout.list_row, null);

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