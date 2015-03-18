package com.example.test2015.adapter;

import com.example.test2015.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class ChartsRootAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private RelativeLayout.LayoutParams mImageViewLayoutParams;
	private int mItemHeight = 0;
	private int mNumColumns = 0;
	
	public ChartsRootAdapter(Activity activity) {
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mImageViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}
	
	// set numcols
	public void setNumColumns(int numColumns) {
	this.mNumColumns = numColumns;
	}
	 
	public int getNumColumns() {
	return this.mNumColumns;
	}
	 
	// set photo item height
	public void setItemHeight(int height) {
		if (height == this.mItemHeight) {
			return;
		}
		this.mItemHeight = height;
		this.mImageViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, this.mItemHeight);
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {						
		if (convertView == null)
			convertView = inflater.inflate(R.layout.grid_item, null);  
			ImageView cover = (ImageView) convertView.findViewById(R.id.gridcover);
			TextView title = (TextView) convertView.findViewById(R.id.gridtitle);
			cover.setLayoutParams(mImageViewLayoutParams);
			if(cover.getLayoutParams().height != mItemHeight){
				cover.setLayoutParams(mImageViewLayoutParams);
			}
			
			cover.setImageResource(mThumbIds[position % mThumbIds.length]);
			title.setText(contents[position % contents.length]);
		return convertView;
	}
	
	// references to our images
    private int[] mThumbIds = {
//            R.drawable.billboard, 
    		R.drawable.itunes_store,
            R.drawable.googleplay_store, R.drawable.spotify_store,
            R.drawable.amazon_store, R.drawable.napster_logo,
            R.drawable.lastfm_store
    };
    private String[] contents = {
//    		"Billboard Hot 100",
    		"Itunes Top Selling","Google Play Charts","Spotify Charts","Amazon Top Selling","Napster-Rhapsody Charts","Lastfm Radio Charts"
    };

}
