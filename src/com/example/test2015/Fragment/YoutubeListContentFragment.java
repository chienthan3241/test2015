package com.example.test2015.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.test2015.DelayAutoCompleteTextView;
import com.example.test2015.MainActivity;
import com.example.test2015.R;
import com.example.test2015.single_track;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class YoutubeListContentFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_search_content, container, false);
		ListView listview = (ListView) rootView.findViewById(R.id.listsearch);		
		JSONArray items = null;
		JSONObject item = null;
		
		((MainActivity) getActivity()).YoutubeListItems.clear();
			try {
				items = ((MainActivity) getActivity()).searchyoutubejson.getJSONObject("feed").getJSONArray("entry");
				for(int i = 0; i<items.length();i++){
					item = items.getJSONObject(i);
					single_track track_tmp = new single_track();
					track_tmp.setId(item.getJSONObject("media$group").getJSONObject("yt$videoid").getString("$t"));
					track_tmp.setTitle(item.getJSONObject("title").getString("$t"));
					track_tmp.setArtist("");
					track_tmp.setThumbnailUrl(item.getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(0).getString("url"));
					track_tmp.setInfo("Uploader: "+item.getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t")+
									"\t duration: "+ String.format("%.2f", (float)(Integer.parseInt(item.getJSONObject("media$group").getJSONObject("yt$duration").getString("seconds"))/60.0f))+" min.");
					((MainActivity) getActivity()).YoutubeListItems.add(track_tmp);
				}
				
				//config search header
				ViewGroup header = (ViewGroup)inflater.inflate(R.layout.youtube_header, listview, false);	
				ImageView youtubelogo = (ImageView) header.findViewById(R.id.youtube_logo);
				Button youtubesubmit = (Button) header.findViewById(R.id.youtube_searchbtn);
				youtubelogo.setImageResource(R.drawable.youtube_logo);
				final DelayAutoCompleteTextView searchfield = (DelayAutoCompleteTextView) header.findViewById(R.id.youtube_search_str);	
				searchfield.setText((((MainActivity) getActivity()).search_youtube_title_txt.trim() + " " + ((MainActivity) getActivity()).search_youtube_artist_txt.trim()).trim());
				searchfield.setThreshold(3);				
				searchfield.setAdapter(MainActivity.youtubeAutocompleteadapter);
//				searchfield.setLoadingIndicator((android.widget.ProgressBar)header.findViewById(R.id.loading_indicator));
				searchfield.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String item = (String) parent.getItemAtPosition(position);
						searchfield.setText(item);						
					}
				});
				
				listview.addHeaderView(header,null,false);
				((MainActivity) getActivity()).SearchYoutubeListAdapter.notifyDataSetChanged();
				
				youtubesubmit.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String rq = "https://gdata.youtube.com/feeds/api/videos?q="+
									 searchfield.getText().toString().trim().replace(" ", "+")+
									"&orderby=relevance&start-index=1&max-results=20&v=2&alt=json";					
						JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
							    new Response.Listener<JSONObject>() 
							    {
							        @Override
							        public void onResponse(JSONObject response) {   
							        	((MainActivity) getActivity()).searchyoutubejson=response;
							        	((MainActivity) getActivity()).search_youtube_artist_txt = "";
							        	((MainActivity) getActivity()).search_youtube_title_txt = searchfield.getText().toString().trim();
										Fragment frg=null;
										frg = getFragmentManager().findFragmentByTag("YOUTUBE_DETAIL_FRAGMENT");									
										FragmentTransaction ft = getFragmentManager().beginTransaction();
										ft.detach(frg);
										ft.attach(frg);
										ft.commit();
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
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			listview.setAdapter(((MainActivity) getActivity()).SearchYoutubeListAdapter);
		
		
		/**
		 * listview items onclick listener
		 */
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {	
				String yid = "";				
					yid = ((MainActivity) getActivity()).YoutubeListItems.get(position-1).getId();				
				try{
			         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + yid));
			         startActivity(intent);                 
			    }catch (ActivityNotFoundException ex){
		             Intent intent=new Intent(Intent.ACTION_VIEW, 
		             Uri.parse("https://www.youtube.com/watch?v="+yid));
		             startActivity(intent);
		        }
			}			
		});		
		return rootView;
	}
}
