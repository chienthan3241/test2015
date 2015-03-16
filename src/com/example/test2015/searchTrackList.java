package com.example.test2015;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class searchTrackList extends BaseAdapter {
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<single_track> trackItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public searchTrackList(Activity activity, List<single_track> trackItems) {
		this.activity = activity;
		this.trackItems = trackItems;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(inflater == null)
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(view == null)
			view = inflater.inflate(R.layout.track_row, null);
		if(imageLoader==null)
				imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) view.findViewById(R.id.track_thumbnail);
		TextView title = (TextView) view.findViewById(R.id.track_title);
		TextView info = (TextView) view.findViewById(R.id.track_info);
		ImageButton dwnbtn = (ImageButton) view.findViewById(R.id.imagedownload);
		ImageButton addbtn = (ImageButton) view.findViewById(R.id.imageadd);
		if(!(MainActivity.getCurrentaction().equals("YOUTUBE_LIST") || MainActivity.getCurrentaction().equals("YOUTUBE_LIST_A") || MainActivity.getCurrentaction().equals("YOUTUBE_LIST_T"))){
			dwnbtn.setVisibility(View.GONE);
			addbtn.setVisibility(View.GONE);
		}
		dwnbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("abc","download");			
			}
		});
		addbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("abc","add");			
			}
		});
		//row items
		single_track track = trackItems.get(position);
		if(track.getImgFromLocal()){
			//thumbnail
			//thumbNail.setImageResource(view.getContext().getResources().getIdentifier(track.getLocalImg(), "drawable", view.getContext().getPackageName()));
			thumbNail.setDefaultImageResId(track.getLocalImg());
		}else{
			//thumbnail
			thumbNail.setImageUrl(track.getThumbnailUrl(), imageLoader);
		}
		//title
		title.setText(track.getTitle()+" - "+ track.getArtist());
		info.setText(track.getInfo());
		
		return view;		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trackItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return trackItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
