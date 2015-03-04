package com.example.test2015;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ChartFragment extends Fragment{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_charts, container, false);
        ImageButton billboardbtn = (ImageButton) rootView.findViewById(R.id.imagebillboard);         
        ImageButton imageitunesbtn = (ImageButton) rootView.findViewById(R.id.imageitunes);
        ImageButton googlePlaybtn = (ImageButton) rootView.findViewById(R.id.imagegoogleplay);
        ImageButton spotifybtn = (ImageButton) rootView.findViewById(R.id.imagespotify);
        ImageButton amazonbtn = (ImageButton) rootView.findViewById(R.id.imageamazon);
        ImageButton napsterbtn = (ImageButton) rootView.findViewById(R.id.imagenapster);
        ImageButton lastfmbtn = (ImageButton) rootView.findViewById(R.id.imagelastfm);
        
        //chart billboard
        billboardbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				MainActivity.setChartsname("BILLBOARD");
				MainActivity.setChartstype("hot-100");
				MainActivity.setChartslimit("100");
				MainActivity.setChartscountry("World-wide");
				MainActivity.setChartstimeunit("Weekly");
				MainActivity.setChartstimeinterval("lastest");
				String rq = "";
				rq = "http://billboard.com/rss/charts/hot-100";
				StringRequest getRequest = new StringRequest(Request.Method.GET, rq, 
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							MainActivity.setChartsxml(response);
							FragmentManager fm = getFragmentManager();
							FragmentTransaction ft = fm.beginTransaction();
							ChartsContentFragment rep = new ChartsContentFragment();
							ft.addToBackStack("aabc");
							ft.hide(ChartFragment.this);
							ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
							ft.commit();
							MainActivity.setCurrentaction("CHART_LIST");
						}
					},
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError arg0) {
							Log.v("Error.Response", "error");								
						}
					}
				);
				
				MainActivity.getQueue().add(getRequest);
			}
		});
        
        // chart itunes
        imageitunesbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				MainActivity.setChartsname("ITUNES");
				MainActivity.setChartstype("Top Songs");
				MainActivity.setChartslimit("100");
				MainActivity.setChartscountry("United States");
				MainActivity.setChartstimeunit("Daily");
				MainActivity.setChartstimeinterval("lastest");
				String rq = "";
				rq = "https://itunes.apple.com/us/rss/topsongs/limit=100/json";
				JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
					    new Response.Listener<JSONObject>() 
					    {
					        @Override
					        public void onResponse(JSONObject response) {   
					        	MainActivity.setChartsjson(response);         
					        	FragmentManager fm = getFragmentManager();
								FragmentTransaction ft = fm.beginTransaction();
								ChartsContentFragment rep = new ChartsContentFragment();
								ft.addToBackStack("aabc");
								ft.hide(ChartFragment.this);
								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
								ft.commit();
								MainActivity.setCurrentaction("CHART_LIST");
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
		});
        
        // Googleplay Chart
        googlePlaybtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {				
				MainActivity.setChartsname("GOOGLEPLAY");
				MainActivity.setChartstype("Top Songs ");
				MainActivity.setChartslimit("60");
				JsonObjectRequest country_request = new JsonObjectRequest(Request.Method.GET, "http://ip-api.com/json", null, 
					new Response.Listener<JSONObject>() {
					    @Override
					    public void onResponse(JSONObject response) {   
					    	try {
								MainActivity.setChartscountry(response.getString("country"));
								String rq = "";
								rq = "https://play.google.com/store/music/collection/topselling_paid_track";
								StringRequest postRequest = new StringRequest(Request.Method.POST, rq, 
									new Response.Listener<String>() {
										@Override
										public void onResponse(String response) {
											MainActivity.setChartsxml(response);							
											FragmentManager fm = getFragmentManager();
											FragmentTransaction ft = fm.beginTransaction();
											ChartsContentFragment rep = new ChartsContentFragment();
											ft.addToBackStack("aabc");
											ft.hide(ChartFragment.this);
											ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
											ft.commit();
											MainActivity.setCurrentaction("CHART_LIST");
										}
									},
									new Response.ErrorListener() {
										@Override
										public void onErrorResponse(VolleyError arg0) {
											Log.v("Error.Response", "error");								
										}
									}					
								)
								//for top200 googleplay reseviert => change start param to 60,120,180.
								{
									protected Map<String, String> getParams() throws com.android.volley.AuthFailureError{
										Map<String, String> params = new HashMap<String, String>();
										params.put("start", "0");
										params.put("num", "60");
										params.put("numChildren", "0");
										params.put("ipf","1");
										params.put("xhr","1");
										return params;
									}
								}
								;								
								MainActivity.gethttpsQueue().add(postRequest);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
				MainActivity.getQueue().add(country_request);
				MainActivity.setChartstimeunit("Daily");
				MainActivity.setChartstimeinterval("lastest");
				
				
			}
		});
        
        
        // Spotify Chart
        spotifybtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				MainActivity.setChartsname("SPOTIFY");
				MainActivity.setChartstype("Top Streamed");
				MainActivity.setChartslimit("all");
				MainActivity.setChartscountry("United States");
				MainActivity.setChartstimeunit("daily");
				MainActivity.setChartstimeinterval("lastest");
				String rq = "";
				rq = "http://charts.spotify.com/api/tracks/most_streamed/us/daily/latest";
				JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
					    new Response.Listener<JSONObject>() 
					    {
					        @Override
					        public void onResponse(JSONObject response) {   
					        	MainActivity.setChartsjson(response);         
					        	FragmentManager fm = getFragmentManager();
								FragmentTransaction ft = fm.beginTransaction();
								ChartsContentFragment rep = new ChartsContentFragment();
								ft.addToBackStack("aabc");
								ft.hide(ChartFragment.this);
								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
								ft.commit();
								MainActivity.setCurrentaction("CHART_LIST");
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
				
				MainActivity.getQueue().add(getRequest);		
			}
		});
        
        
        // Amazone Charts
        amazonbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				MainActivity.setChartsname("AMAZON");
				MainActivity.setChartstype("Physical Albums");
				MainActivity.setChartslimit("10");
				MainActivity.setChartscountry("United States");
				MainActivity.setChartstimeunit("daily");
				MainActivity.setChartstimeinterval("lastest");
				String rq = "";
				rq = "http://amazon.com/gp/rss/bestsellers/music/ref=zg_bs_music_rsslink";
				StringRequest getRequest = new StringRequest(Request.Method.GET, rq, 
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								MainActivity.setChartsxml(response);
								FragmentManager fm = getFragmentManager();
								FragmentTransaction ft = fm.beginTransaction();
								ChartsContentFragment rep = new ChartsContentFragment();
								ft.addToBackStack("aabc");
								ft.hide(ChartFragment.this);
								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
								ft.commit();
								MainActivity.setCurrentaction("CHART_LIST");
							}
						},
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError arg0) {
								Log.v("Error.Response", "error");								
							}
						}
					);		
				
				MainActivity.getQueue().add(getRequest);			
			}
		});
        
        //Napster bzw. Rhapsody Charts
        napsterbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				MainActivity.setChartsname("NAPSTER");
				MainActivity.setChartstype("Top Songs");
				MainActivity.setChartslimit("30");
				MainActivity.setChartscountry("United States");
				MainActivity.setChartstimeunit("daily");
				MainActivity.setChartstimeinterval("lastest");
				String rq = "";
				rq = "http://api.rhapsody.com/v1/tracks/top?apikey=ZmRkNjg4ODMtMjgyZi00Nzc2LTlmMjQtZDdkM2RmYTExNTEx&limit=30&catalog=US";
				JsonArrayRequest getRequest = new JsonArrayRequest(rq, 
						new Response.Listener<JSONArray>() {
							@Override
							public void onResponse(JSONArray response) {
								MainActivity.setChartsjsonarr(response);
								FragmentManager fm = getFragmentManager();
								FragmentTransaction ft = fm.beginTransaction();
								ChartsContentFragment rep = new ChartsContentFragment();
								ft.addToBackStack("aabc");
								ft.hide(ChartFragment.this);
								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
								ft.commit();
								MainActivity.setCurrentaction("CHART_LIST");
							}					
						},
						new Response.ErrorListener() 
					    {
					         @Override
					         public void onErrorResponse(VolleyError error) {            
					            Log.v("Error.Response", "error");
					       }						
					    });				

				MainActivity.getQueue().add(getRequest);
			}
		});
        
        //Lastfm Charts
        lastfmbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				MainActivity.setChartsname("LASTFM");
				MainActivity.setChartstype("Top Songs");
				MainActivity.setChartslimit("30");
				MainActivity.setChartscountry("United States");
				MainActivity.setChartstimeunit("daily");
				MainActivity.setChartstimeinterval("lastest");
				String rq = "";
				rq = "http://ws.audioscrobbler.com/2.0/?method=geo.getTopTracks&country=UNITED%20STATES&api_key=d6ef461c9610dc5d695efce1bbab90c1&format=json&limit=30";
				JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
					    new Response.Listener<JSONObject>() 
					    {
					        @Override
					        public void onResponse(JSONObject response) {   
					        	MainActivity.setChartsjson(response);         
					        	FragmentManager fm = getFragmentManager();
								FragmentTransaction ft = fm.beginTransaction();
								ChartsContentFragment rep = new ChartsContentFragment();
								ft.addToBackStack("aabc");
								ft.hide(ChartFragment.this);
								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
								ft.commit();
								MainActivity.setCurrentaction("CHART_LIST");
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
				
				MainActivity.getQueue().add(getRequest);
				
			}
		});
        
        return rootView;
    }
}
