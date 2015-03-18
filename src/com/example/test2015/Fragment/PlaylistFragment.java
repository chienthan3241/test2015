package com.example.test2015.Fragment;

import com.example.test2015.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlaylistFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
/*		View rootView = inflater.inflate(R.layout.grid_item, container, false);
		ImageView imageView = (ImageView) rootView.findViewById(R.id.gridcover);
		TextView adsa = (TextView) rootView.findViewById(R.id.gridtitle);
		adsa.setText("pkpfkasfk#üep");
//		imageView.setBackgroundResource(R.drawable.amazon_store);
		MainActivity.setproxy();
		Picasso pic = Picasso.with(((MainActivity) getActivity()));
		pic.setIndicatorsEnabled(true);
		pic
         .load("https://cms-assets.tutsplus.com/uploads/users/21/posts/19431/featured_image/CodeFeature.jpg")
         .placeholder(R.drawable.itunes_store)
         .error(R.drawable.lastfm_store)
         .into(imageView);
         */
         
//		View rootView = inflater.inflate(R.layout.fragment_grid_charts_content, container, false);
//		View header = inflater.inflate(R.layout.chart_header, container,false);
//		LinearLayout headerContainer = (LinearLayout) rootView.findViewById(R.id.header);
//		headerContainer.addView(header);
		
		return rootView;
	}

}
