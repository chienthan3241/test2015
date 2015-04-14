package com.example.test2015.adapter;

import java.util.List;

import com.example.test2015.MainActivity;
import com.example.test2015.R;
import com.example.test2015.single_track;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChartGridAdapter extends BaseAdapter {
	private List<single_track> items;
	private LayoutInflater inflater;
	private Activity activity;
	
	public ChartGridAdapter(Activity activity, List<single_track> items){
		this.activity = activity;
		this.items = items;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "InflateParams", "UseValueOf" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(inflater == null)
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null)
			convertView = inflater.inflate(R.layout.fragment_grid_charts_content_item, null);
		ImageView thumb = (ImageView) convertView.findViewById(R.id.griditemcover);
		TextView title = (TextView) convertView.findViewById(R.id.griditemtitle);
		TextView artist = (TextView) convertView.findViewById(R.id.griditemartist);
		ImageView ratebgr = (ImageView) convertView.findViewById(R.id.ratestarbgr);
		ImageView rate = (ImageView) convertView.findViewById(R.id.ratestar);
		TextView preis = (TextView) convertView.findViewById(R.id.griditempreis);
		
		single_track item = items.get(position);
		MainActivity.setproxy();
		Picasso.with(activity.getBaseContext()).load(item.getThumbnailUrl()).error(R.drawable.googleplay_store).into(thumb);
		title.setText(new Integer(position+1).toString()+". "+item.getTitle());
		artist.setText(item.getArtist());
		if(MainActivity.Chartstype.equals("Top Albums") && (MainActivity.Chartsname.equals("GOOGLEPLAY"))){
			rate.setVisibility(View.VISIBLE);
			ratebgr.setVisibility(View.VISIBLE);
			ratebgr.getLayoutParams().width = (int) Integer.parseInt(item.getRate());
		}else{
			rate.setVisibility(View.GONE);
			ratebgr.setVisibility(View.GONE);
		}
		preis.setText(item.getPreis());
		return convertView;
	}

}
