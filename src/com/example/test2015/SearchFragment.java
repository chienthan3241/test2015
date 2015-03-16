package com.example.test2015;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;


public class SearchFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        final RadioButton single 	= (RadioButton)rootView.findViewById(R.id.single);
        final DelayAutoCompleteTextView titletxt 	= (DelayAutoCompleteTextView)rootView.findViewById(R.id.titleSearch);
		final DelayAutoCompleteTextView artisttxt 	= (DelayAutoCompleteTextView)rootView.findViewById(R.id.artistSearch);        
		final Spinner limit 		= (Spinner)rootView.findViewById(R.id.limit);
		final RadioGroup search_type_radio_btn = (RadioGroup) rootView.findViewById(R.id.formatcheck);
		
		titletxt.setThreshold(2);
		titletxt.setAdapter(MainActivity.SpotAutoCompleteTitleAdapter);
		titletxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item = (String) parent.getItemAtPosition(position);
				titletxt.setText(item);						
			}
		});
		
		artisttxt.setThreshold(2);
		artisttxt.setAdapter(MainActivity.SpotAutoCompleteArtistAdapter);
		artisttxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item = (String) parent.getItemAtPosition(position);
				artisttxt.setText(item);				
			}
		});
		
        //button search clicked
        rootView.findViewById(R.id.searchbtn).setOnClickListener(new View.OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
							
				final String titletext = titletxt.getText().toString().trim();
				final String artisttext = artisttxt.getText().toString().trim();
				final String limittxt = limit.getSelectedItem().toString();
				((MainActivity) getActivity()).searchjson=null;								
				if(single.isChecked()){
					((MainActivity) getActivity()).search_format="TRACK";
					if(titletext.equals("") && artisttext.equals("")){						
						//show alert
						final AlertDialog arlertDialog = new AlertDialog.Builder(v.getContext()).create();
						arlertDialog.setTitle("warning!!");
				        arlertDialog.setMessage("wtf! search string??");
				        arlertDialog.setButton("OK", new DialogInterface.OnClickListener() {			
							@Override
							public void onClick(DialogInterface dialog, int which) {
								arlertDialog.cancel();
								titletxt.requestFocus();
							}
						});
				        arlertDialog.show();
				        titletxt.requestFocus();
					}else{
						//send volley request to spotify api
						String rq = "";
						rq = "https://api.spotify.com/v1/search?q=";
						if(!titletext.equals("")){
							rq=rq+"track:"+titletext.replace(" ", "+");
							if(!artisttext.equals(""))
								rq=rq+"+artist:"+artisttext.replace(" ", "+");
						}else{
							rq=rq+"artist:"+artisttext.replace(" ", "+");
						}
							rq=rq+"&limit="+limittxt+"&type=track";
							
						JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
							    new Response.Listener<JSONObject>() 
							    {
							        @Override
							        public void onResponse(JSONObject response) {   
							        	((MainActivity) getActivity()).searchjson=response;								          
							        	((MainActivity) getActivity()).search_title_txt=titletext;
							        	((MainActivity) getActivity()).search_artist_txt=artisttext;
							        	FragmentManager fm = getFragmentManager();
										FragmentTransaction ft = fm.beginTransaction();
										SingleSearchContentFragment rep = new SingleSearchContentFragment();
										ft.remove(SearchFragment.this);
										ft.add(android.R.id.content, rep, "SINGLE_DETAIL_FRAGMENT");
										ft.addToBackStack(null);
										ft.commit();
										MainActivity.currentaction="TRACK_LIST";
										((MainActivity) getActivity()).actionBar.hide();
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
				}else{
					((MainActivity) getActivity()).search_album_format="ALBUM";
					if(titletext.equals("") && artisttext.equals("")){						
						//show alert
						final AlertDialog arlertDialog = new AlertDialog.Builder(v.getContext()).create();
						arlertDialog.setTitle("warning!!");
				        arlertDialog.setMessage("wtf! search string??");
				        arlertDialog.setButton("OK", new DialogInterface.OnClickListener() {			
							@Override
							public void onClick(DialogInterface dialog, int which) {
								arlertDialog.cancel();
								titletxt.requestFocus();
							}
						});
				        arlertDialog.show();
				        titletxt.requestFocus();
					}else{
						//send volley request to spotify api
						String rq = "";
						rq = "https://api.spotify.com/v1/search?q=";
						if(!titletext.equals("")){
							rq=rq+"album:"+titletext.replace(" ", "+");
							if(!artisttext.equals(""))
								rq=rq+"+artist:"+artisttext.replace(" ", "+");
						}else{
							rq=rq+"artist:"+artisttext.replace(" ", "+");
						}
							rq=rq+"&limit="+limittxt+"&type=album";
							
						JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
							    new Response.Listener<JSONObject>() 
							    {
							        @Override
							        public void onResponse(JSONObject response) {   
							        	((MainActivity) getActivity()).searchalbumjson=response;								          
							        	((MainActivity) getActivity()).search_album_title_txt=titletext;
							        	((MainActivity) getActivity()).search_album_artist_txt=artisttext;
							        	FragmentManager fm = getFragmentManager();
										FragmentTransaction ft = fm.beginTransaction();
										AlbumSearchContentFragment rep = new AlbumSearchContentFragment();
										ft.remove(SearchFragment.this);
										ft.add(android.R.id.content, rep, "ALBUM_DETAIL_FRAGMENT");
										ft.addToBackStack(null);
										ft.commit();			
										MainActivity.currentaction="ALBUM_LIST";
										((MainActivity) getActivity()).actionBar.hide();
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
				}
			}
        	
        });         
        
        search_type_radio_btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(((RadioButton) rootView.findViewById(checkedId)).getText().equals("Single")){
					MainActivity.search_single_radio = true;
				}else{
					MainActivity.search_single_radio=false;
				}
				
			}
		});
        
        return rootView;
    }
	
}
