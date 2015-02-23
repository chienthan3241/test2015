package com.example.test2015;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SingleSearchContentFragment extends Fragment {	
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_search_content, container, false);
		ListView listview = (ListView) rootView.findViewById(R.id.listsearch);		
		JSONArray items = null;
		JSONObject album = null;
		JSONObject item = null;		
		MainActivity.clearSearchList();
		if(MainActivity.getSearchformat()=="TRACK"){			
			try {				
				items = MainActivity.getSearchjson().getJSONObject("tracks").getJSONArray("items");
				for(int i = 0; i<items.length();i++){
					item = items.getJSONObject(i);
					single_track track_tmp = new single_track();
					track_tmp.setId(item.getString("id"));
					track_tmp.setTitle(item.getString("name"));
					track_tmp.setArtist(item.getJSONArray("artists").getJSONObject(0).getString("name"));
					track_tmp.setThumbnailUrl(item.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url"));
					track_tmp.setInfo("Album: "+item.getJSONObject("album").getString("name")+
									"\t Disc_nr: "+item.getString("disc_number")+
									"\t track_nr: "+item.getString("track_number") + 
									"\t popularity: "+item.getString("popularity") + 
									"\t duration: "+ String.format("%.2f", (float)(Integer.parseInt(item.getString("duration_ms"))/60000.0f))+" min.");
					MainActivity.addSearchItem(track_tmp);
				}
				//config search header
				ViewGroup header = (ViewGroup)inflater.inflate(R.layout.simple_header, listview, false);	
				TextView headertitle = (TextView) header.findViewById(R.id.search_title);
				TextView headerartist = (TextView) header.findViewById(R.id.search_artist);
				TextView headertype = (TextView) header.findViewById(R.id.search_type);	
				TextView headertxt = (TextView) header.findViewById(R.id.header_txt);
				headertxt.setText("Serch parameters:");
				headertitle.setText("title: "+MainActivity.getSearch_title_txt());
				headerartist.setText("artist: "+MainActivity.getSearch_artist_txt());
				headertype.setText(MainActivity.getSearchformat());
				
				listview.addHeaderView(header,null,false);
				MainActivity.getSearchListAdapter().notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(MainActivity.getSearchformat()=="ALBUM_EXPAND"){
			try {
				album = MainActivity.getSearchjson();
				items = MainActivity.getSearchjson().getJSONObject("tracks").getJSONArray("items");
				for(int i = 0; i<items.length();i++){
					item = items.getJSONObject(i);
					single_track track_tmp = new single_track();
					track_tmp.setId(item.getString("id"));
					track_tmp.setTitle(item.getString("name"));
					track_tmp.setArtist(item.getJSONArray("artists").getJSONObject(0).getString("name"));
					track_tmp.setThumbnailUrl(album.getJSONArray("images").getJSONObject(0).getString("url"));
					track_tmp.setInfo("Album: "+album.getString("name")+
									"\t Disc_nr: "+item.getString("disc_number")+
									"\t track_nr: "+item.getString("track_number") + 
//									"\t popularity: "+item.getString("popularity") + 
									"\t duration: "+ String.format("%.2f", (float)(Integer.parseInt(item.getString("duration_ms"))/60000.0f))+" min.");
					MainActivity.addSearchItem(track_tmp);
				}
				//config search header
				ViewGroup header = (ViewGroup)inflater.inflate(R.layout.simple_header, listview, false);	
				TextView headertitle = (TextView) header.findViewById(R.id.search_title);
				TextView headerartist = (TextView) header.findViewById(R.id.search_artist);
				TextView headertype = (TextView) header.findViewById(R.id.search_type);	
				TextView headertxt = (TextView) header.findViewById(R.id.header_txt);
				headertxt.setText("Album Details:");
				headertitle.setText("Album's name: "+MainActivity.getSearch_title_txt());
				headerartist.setText("artist: "+MainActivity.getSearch_artist_txt());
				headertype.setText(MainActivity.getSearchformat());
				
				listview.addHeaderView(header,null,false);
				MainActivity.getSearchListAdapter().notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		listview.setAdapter(MainActivity.getSearchListAdapter());
		
		// show warning only
		if(MainActivity.getSearchListItem().size()==0){
			final AlertDialog arlertDialog = new AlertDialog.Builder(rootView.getContext()).create();
			arlertDialog.setTitle("warning!!");
	        arlertDialog.setMessage("nothing founded??please go back and try again!!!");
	        arlertDialog.setButton("OK", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					arlertDialog.cancel();						
				}
			});
	        arlertDialog.show();		        
		}		
		
		
		return rootView;
	}
}
