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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


public class SearchFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        final RadioButton single 	= (RadioButton)rootView.findViewById(R.id.single);
        final EditText titletxt 	= (EditText)rootView.findViewById(R.id.titleSearch);
		final EditText artisttxt 	= (EditText)rootView.findViewById(R.id.artistSearch);        
		final Spinner limit 		= (Spinner)rootView.findViewById(R.id.limit);
        //button search clicked
        rootView.findViewById(R.id.searchbtn).setOnClickListener(new View.OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
							
				final String titletext = titletxt.getText().toString().trim();
				final String artisttext = artisttxt.getText().toString().trim();
				final String limittxt = limit.getSelectedItem().toString();
				MainActivity.setSearchjson(null);								
				if(single.isChecked()){
					MainActivity.setSearchformat("TRACK");
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
							        	MainActivity.setSearchjson(response);								          
							        	MainActivity.setSearch_title_txt(titletext);
							        	MainActivity.setSearch_artist_txt(artisttext);
							        	FragmentManager fm = getFragmentManager();
										FragmentTransaction ft = fm.beginTransaction();
										SingleSearchContentFragment rep = new SingleSearchContentFragment();
										ft.addToBackStack("xyz");
										ft.hide(SearchFragment.this);
										ft.add(android.R.id.content, rep);
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
						
					MainActivity.gethttpsQueue().add(getRequest);
					}
				}else{
					MainActivity.setSearchformat("ALBUM");
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
							        	MainActivity.setSearchjson(response);								          
							        	MainActivity.setSearch_title_txt(titletext);
							        	MainActivity.setSearch_artist_txt(artisttext);
							        	FragmentManager fm = getFragmentManager();
										FragmentTransaction ft = fm.beginTransaction();
										SingleSearchContentFragment rep = new SingleSearchContentFragment();
										ft.addToBackStack("xyz");
										ft.hide(SearchFragment.this);
										ft.add(android.R.id.content, rep);
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
						
					MainActivity.gethttpsQueue().add(getRequest);
					}
				}
			}
        	
        });
         
        return rootView;
    }
}
