package com.example.test2015.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.test2015.MainActivity;
import com.example.test2015.R;
import com.example.test2015.single_track;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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
		((MainActivity) getActivity()).SearchListItems.clear();
		if(((MainActivity) getActivity()).search_format=="TRACK"){			
			try {				
				items = ((MainActivity) getActivity()).searchjson.getJSONObject("tracks").getJSONArray("items");
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
					((MainActivity) getActivity()).SearchListItems.add(track_tmp);
				}
				//config search header
				ViewGroup header = (ViewGroup)inflater.inflate(R.layout.simple_header, listview, false);	
				TextView headertitle = (TextView) header.findViewById(R.id.search_title);
				TextView headerartist = (TextView) header.findViewById(R.id.search_artist);
				TextView headertype = (TextView) header.findViewById(R.id.search_type);	
				TextView headertxt = (TextView) header.findViewById(R.id.header_txt);
				headertxt.setText("Serch parameters:");
				headertitle.setText("title: "+((MainActivity) getActivity()).search_title_txt);
				headerartist.setText("artist: "+((MainActivity) getActivity()).search_artist_txt);
				headertype.setText(((MainActivity) getActivity()).search_format);
				
				listview.addHeaderView(header,null,false);
				((MainActivity) getActivity()).SearchListAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(((MainActivity) getActivity()).search_format=="ALBUM_EXPAND"){
			try {
				album = ((MainActivity) getActivity()).searchjson;
				items = ((MainActivity) getActivity()).searchjson.getJSONObject("tracks").getJSONArray("items");
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
					((MainActivity) getActivity()).SearchListItems.add(track_tmp);
				}
				//config search header
				ViewGroup header = (ViewGroup)inflater.inflate(R.layout.simple_header, listview, false);	
				TextView headertitle = (TextView) header.findViewById(R.id.search_title);
				TextView headerartist = (TextView) header.findViewById(R.id.search_artist);
				TextView headertype = (TextView) header.findViewById(R.id.search_type);	
				TextView headertxt = (TextView) header.findViewById(R.id.header_txt);
				headertxt.setText("Album Details:");
				headertitle.setText("Album's name: "+((MainActivity) getActivity()).search_title_txt);
				headerartist.setText("artist: "+((MainActivity) getActivity()).search_artist_txt);
				headertype.setText(((MainActivity) getActivity()).search_format);
				
				listview.addHeaderView(header,null,false);
				((MainActivity) getActivity()).SearchListAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		listview.setAdapter(((MainActivity) getActivity()).SearchListAdapter);
		
		// show warning only
		if(((MainActivity) getActivity()).SearchListItems.size()==0){
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
		
		/**
		 * listview items onclick listener
		 */
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
					String rq = "https://gdata.youtube.com/feeds/api/videos?q="+
							((MainActivity) getActivity()).SearchListItems.get(position-1).getTitle().trim().replace(" ", "+")+"+"+
							((MainActivity) getActivity()).SearchListItems.get(position-1).getArtist().trim().replace(" ", "+")+
								"&orderby=relevance&start-index=1&max-results=20&v=2&alt=json";					
					JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
						    new Response.Listener<JSONObject>() 
						    {
						        @Override
						        public void onResponse(JSONObject response) {   
						        	((MainActivity) getActivity()).searchyoutubejson=response;
						        	((MainActivity) getActivity()).search_youtube_format="YOUTUBE_LIST";
						        	((MainActivity) getActivity()).search_youtube_title_txt=((MainActivity) getActivity()).SearchListItems.get(position-1).getTitle();
						        	((MainActivity) getActivity()).search_youtube_artist_txt=((MainActivity) getActivity()).SearchListItems.get(position-1).getArtist();          
						            
						            FragmentManager fm = getFragmentManager();
									FragmentTransaction ft = fm.beginTransaction();
									YoutubeListContentFragment rep = new YoutubeListContentFragment();
									ft.remove(SingleSearchContentFragment.this);
									ft.add(android.R.id.content, rep, "YOUTUBE_DETAIL_FRAGMENT");
									ft.addToBackStack(null);
									ft.commit();
									
									if(MainActivity.currentaction.equals("TRACK_LIST")){
										MainActivity.currentaction="YOUTUBE_LIST_T";
									}else{
										MainActivity.currentaction="YOUTUBE_LIST_A";
									}
						        }
						    }, 
						    new Response.ErrorListener() 
						    {
						         @Override
						         public void onErrorResponse(VolleyError error) {            
						            Log.v("Error.Response", "error");
						       }						
						    }
						);
					((MainActivity) getActivity()).httpsqueue.add(getRequest);	
			}
			
		});		
		return rootView;
	}
}
