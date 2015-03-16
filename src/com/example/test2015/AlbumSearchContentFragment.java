package com.example.test2015;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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

public class AlbumSearchContentFragment extends Fragment {
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_search_content, container, false);
		ListView listview = (ListView) rootView.findViewById(R.id.listsearch);		
		JSONArray items = null;
		JSONObject item = null;
		((MainActivity) getActivity()).SearchAlbumListItems.clear();
		try {				
			items = ((MainActivity) getActivity()).searchalbumjson.getJSONObject("albums").getJSONArray("items");
			for(int i = 0; i<items.length();i++){
				item = items.getJSONObject(i);
				single_track track_tmp = new single_track();
				track_tmp.setId(item.getString("id"));
				track_tmp.setTitle(item.getString("name"));
				track_tmp.setArtist(item.getString("album_type"));
				track_tmp.setThumbnailUrl(item.getJSONArray("images").getJSONObject(0).getString("url"));
				track_tmp.setInfo(item.getString("id"));
				((MainActivity) getActivity()).SearchAlbumListItems.add(track_tmp);
			}
			//config search header
			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.simple_header, listview, false);	
			TextView headertitle = (TextView) header.findViewById(R.id.search_title);
			TextView headerartist = (TextView) header.findViewById(R.id.search_artist);
			TextView headertype = (TextView) header.findViewById(R.id.search_type);	
			TextView headertxt = (TextView) header.findViewById(R.id.header_txt);
			headertxt.setText("Search parameters:");
			headertitle.setText("title: "+((MainActivity) getActivity()).search_album_title_txt);
			headerartist.setText("artist: "+((MainActivity) getActivity()).search_album_artist_txt);
			headertype.setText(((MainActivity) getActivity()).search_album_format);
			
			listview.addHeaderView(header,null,false);				
			((MainActivity) getActivity()).SearchAlbumListAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listview.setAdapter(((MainActivity) getActivity()).SearchAlbumListAdapter);
		// show warning only
		if(((MainActivity) getActivity()).SearchAlbumListItems.size()==0){
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
					int position, long id) {
				
					String rq = "https://api.spotify.com/v1/albums/"+((MainActivity) getActivity()).SearchAlbumListItems.get(position-1).getId().toString();
					JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
						    new Response.Listener<JSONObject>() 
						    {
						        @Override
						        public void onResponse(JSONObject response) {   
						        	((MainActivity) getActivity()).searchjson=response;
						        	((MainActivity) getActivity()).search_format="ALBUM_EXPAND";
						            try {
						            	((MainActivity) getActivity()).search_title_txt=response.getString("name");
						            	((MainActivity) getActivity()).search_artist_txt=response.getJSONArray("artists").getJSONObject(0).getString("name");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}          
						            
						            FragmentManager fm = getFragmentManager();
									FragmentTransaction ft = fm.beginTransaction();
									SingleSearchContentFragment rep = new SingleSearchContentFragment();
									ft.remove(AlbumSearchContentFragment.this);
									ft.add(android.R.id.content, rep, "SINGLE_DETAIL_FRAGMENT");
									ft.addToBackStack(null);
									ft.commit();
									MainActivity.currentaction="TRACK_LIST_A";									
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
