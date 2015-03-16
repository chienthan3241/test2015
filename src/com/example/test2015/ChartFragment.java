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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ChartFragment extends Fragment{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

//        View rootView = inflater.inflate(R.layout.fragment_charts, container, false);
//        ImageButton billboardbtn = (ImageButton) rootView.findViewById(R.id.imagebillboard);         
//        ImageButton imageitunesbtn = (ImageButton) rootView.findViewById(R.id.imageitunes);
//        ImageButton googlePlaybtn = (ImageButton) rootView.findViewById(R.id.imagegoogleplay);
//        ImageButton spotifybtn = (ImageButton) rootView.findViewById(R.id.imagespotify);
//        ImageButton amazonbtn = (ImageButton) rootView.findViewById(R.id.imageamazon);
//        ImageButton napsterbtn = (ImageButton) rootView.findViewById(R.id.imagenapster);
//        ImageButton lastfmbtn = (ImageButton) rootView.findViewById(R.id.imagelastfm);
//        
//        //chart billboard
//        billboardbtn.setOnClickListener(new View.OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				((MainActivity) getActivity()).Chartsname ="BILLBOARD";
//				((MainActivity) getActivity()).Chartstype = "hot-100";
//				((MainActivity) getActivity()).Chartslimit = "100";
//				((MainActivity) getActivity()).Chartscountry = "World-wide";
//				((MainActivity) getActivity()).Chartstimeunit = "Weekly";
//				((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//				String rq = "";
//				rq = "http://billboard.com/rss/charts/hot-100";
//				StringRequest getRequest = new StringRequest(Request.Method.GET, rq, 
//					new Response.Listener<String>() {
//						@Override
//						public void onResponse(String response) {
//							((MainActivity) getActivity()).Chartsxml = response;
//							FragmentManager fm = getFragmentManager();
//							FragmentTransaction ft = fm.beginTransaction();
//							ChartsContentFragment rep = new ChartsContentFragment();
//							ft.remove(ChartFragment.this);
//							ft.add(android.R.id.content, rep);
//							ft.addToBackStack(null);
//							ft.commit();
//							MainActivity.currentaction="CHART_LIST";							
//						}
//					},
//					new Response.ErrorListener() {
//						@Override
//						public void onErrorResponse(VolleyError arg0) {
//							Log.v("Error.Response", "error");								
//						}
//					}
//				);
//				
//				((MainActivity) getActivity()).queue.add(getRequest);
//			}
//		});
//        
//        // chart itunes
//        imageitunesbtn.setOnClickListener(new View.OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				((MainActivity) getActivity()).Chartsname ="ITUNES";
//				((MainActivity) getActivity()).Chartstype = "Top Songs";
//				((MainActivity) getActivity()).Chartslimit = "100";
//				((MainActivity) getActivity()).Chartscountry = "United States";
//				((MainActivity) getActivity()).Chartstimeunit = "Daily";
//				((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//				String rq = "";
//				rq = "https://itunes.apple.com/us/rss/topsongs/limit=100/json";
//				JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
//					    new Response.Listener<JSONObject>() 
//					    {
//					        @Override
//					        public void onResponse(JSONObject response) {  
//					        	((MainActivity) getActivity()).Chartsjson = response;      
//					        	FragmentManager fm = getFragmentManager();
//								FragmentTransaction ft = fm.beginTransaction();
//								ChartsContentFragment rep = new ChartsContentFragment();								
//								ft.remove(ChartFragment.this);
//								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");								
//								ft.addToBackStack(null);
//								ft.commit();
//								MainActivity.currentaction="CHART_LIST";
//					        }
//					    }, 
//					    new Response.ErrorListener() 
//					    {
//					         @Override
//					         public void onErrorResponse(VolleyError error) {            
//					            Log.v("Error.Response", "error");
//					       }						
//					    }
//					);				
//				((MainActivity) getActivity()).httpsqueue.add(getRequest);
//			}
//		});
//        
//        // Googleplay Chart
//        googlePlaybtn.setOnClickListener(new View.OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				((MainActivity) getActivity()).Chartsname ="GOOGLEPLAY";
//				((MainActivity) getActivity()).Chartstype = "Top Songs";
//				((MainActivity) getActivity()).Chartslimit = "60";
//				((MainActivity) getActivity()).Chartstimeunit = "Daily";
//				((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//				JsonObjectRequest country_request = new JsonObjectRequest(Request.Method.GET, "http://ip-api.com/json", null, 
//					new Response.Listener<JSONObject>() {
//					    @Override
//					    public void onResponse(JSONObject response) {   
//					    	try {
//					    		((MainActivity) getActivity()).Chartscountry =response.getString("country");
//								String rq = "";
//								rq = "https://play.google.com/store/music/collection/topselling_paid_track";
//								StringRequest postRequest = new StringRequest(Request.Method.POST, rq, 
//									new Response.Listener<String>() {
//										@Override
//										public void onResponse(String response) {
//											((MainActivity) getActivity()).Chartsxml=response;							
//											FragmentManager fm = getFragmentManager();
//											FragmentTransaction ft = fm.beginTransaction();
//											ChartsContentFragment rep = new ChartsContentFragment();
//											ft.remove(ChartFragment.this);
//											ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
//											ft.addToBackStack(null);
//											ft.commit();
//											MainActivity.currentaction="CHART_LIST";
//										}
//									},
//									new Response.ErrorListener() {
//										@Override
//										public void onErrorResponse(VolleyError arg0) {
//											Log.v("Error.Response", "error");								
//										}
//									}					
//								)
//								//for top200 googleplay reseviert => change start param to 60,120,180.
//								{
//									protected Map<String, String> getParams() throws com.android.volley.AuthFailureError{
//										Map<String, String> params = new HashMap<String, String>();
//										params.put("start", "0");
//										params.put("num", "60");
//										params.put("numChildren", "0");
//										params.put("ipf","1");
//										params.put("xhr","1");
//										return params;
//									}
//								}
//								;								
//								((MainActivity) getActivity()).httpsqueue.add(postRequest);
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//					    }
//					}, 
//					new Response.ErrorListener() 
//					{
//					     @Override
//					     public void onErrorResponse(VolleyError error) {            
//					        Log.v("Error.Response", "error");
//					   }						
//					}
//				);
//				((MainActivity) getActivity()).queue.add(country_request);
//			}
//		});
//        
//        
//        // Spotify Chart
//        spotifybtn.setOnClickListener(new View.OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				((MainActivity) getActivity()).Chartsname ="SPOTIFY";
//				((MainActivity) getActivity()).Chartstype = "Top Streamed";
//				((MainActivity) getActivity()).Chartslimit = "all";
//				((MainActivity) getActivity()).Chartscountry = "United States";
//				((MainActivity) getActivity()).Chartstimeunit = "Daily";
//				((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//				String rq = "";
//				rq = "http://charts.spotify.com/api/tracks/most_streamed/us/daily/latest";
//				JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
//					    new Response.Listener<JSONObject>() 
//					    {
//					        @Override
//					        public void onResponse(JSONObject response) {   
//					        	((MainActivity) getActivity()).Chartsjson=response;         
//					        	FragmentManager fm = getFragmentManager();
//								FragmentTransaction ft = fm.beginTransaction();
//								ChartsContentFragment rep = new ChartsContentFragment();
//								ft.remove(ChartFragment.this);
//								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
//								ft.addToBackStack(null);
//								ft.commit();
//								MainActivity.currentaction="CHART_LIST";
//					        }
//					    }, 
//					    new Response.ErrorListener() 
//					    {
//					         @Override
//					         public void onErrorResponse(VolleyError error) {            
//					            Log.v("Error.Response", "error");
//					       }						
//					    }
//					);				
//				
//				((MainActivity) getActivity()).queue.add(getRequest);		
//			}
//		});
//        
//        
//        // Amazone Charts
//        amazonbtn.setOnClickListener(new View.OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				((MainActivity) getActivity()).Chartsname ="AMAZON";
//				((MainActivity) getActivity()).Chartstype = "Physical Albums";
//				((MainActivity) getActivity()).Chartslimit = "10";
//				((MainActivity) getActivity()).Chartscountry = "United States";
//				((MainActivity) getActivity()).Chartstimeunit = "Daily";
//				((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//				String rq = "";
//				rq = "http://amazon.com/gp/rss/bestsellers/music/ref=zg_bs_music_rsslink";
//				StringRequest getRequest = new StringRequest(Request.Method.GET, rq, 
//						new Response.Listener<String>() {
//							@Override
//							public void onResponse(String response) {
//								((MainActivity) getActivity()).Chartsxml=response;
//								FragmentManager fm = getFragmentManager();
//								FragmentTransaction ft = fm.beginTransaction();
//								ChartsContentFragment rep = new ChartsContentFragment();
//								ft.remove(ChartFragment.this);
//								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
//								ft.addToBackStack(null);
//								ft.commit();
//								MainActivity.currentaction="CHART_LIST";
//							}
//						},
//						new Response.ErrorListener() {
//							@Override
//							public void onErrorResponse(VolleyError arg0) {
//								Log.v("Error.Response", "error");								
//							}
//						}
//					);		
//				
//				((MainActivity) getActivity()).queue.add(getRequest);			
//			}
//		});
//        
//        //Napster bzw. Rhapsody Charts
//        napsterbtn.setOnClickListener(new View.OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				((MainActivity) getActivity()).Chartsname ="NAPSTER";
//				((MainActivity) getActivity()).Chartstype = "Top Songs";
//				((MainActivity) getActivity()).Chartslimit = "30";
//				((MainActivity) getActivity()).Chartscountry = "United States";
//				((MainActivity) getActivity()).Chartstimeunit = "Daily";
//				((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//				String rq = "";
//				rq = "http://api.rhapsody.com/v1/tracks/top?apikey=ZmRkNjg4ODMtMjgyZi00Nzc2LTlmMjQtZDdkM2RmYTExNTEx&limit=30&catalog=US";
//				JsonArrayRequest getRequest = new JsonArrayRequest(rq, 
//						new Response.Listener<JSONArray>() {
//							@Override
//							public void onResponse(JSONArray response) {
//								((MainActivity) getActivity()).Chartsjsonarr=response;
//								FragmentManager fm = getFragmentManager();
//								FragmentTransaction ft = fm.beginTransaction();
//								ChartsContentFragment rep = new ChartsContentFragment();
//								ft.remove(ChartFragment.this);
//								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
//								ft.addToBackStack(null);
//								ft.commit();
//								MainActivity.currentaction="CHART_LIST";
//							}					
//						},
//						new Response.ErrorListener() 
//					    {
//					         @Override
//					         public void onErrorResponse(VolleyError error) {            
//					            Log.v("Error.Response", "error");
//					       }						
//					    });				
//
//				((MainActivity) getActivity()).queue.add(getRequest);
//			}
//		});
//        
//        //Lastfm Charts
//        lastfmbtn.setOnClickListener(new View.OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				((MainActivity) getActivity()).Chartsname ="LASTFM";
//				((MainActivity) getActivity()).Chartstype = "Top Songs";
//				((MainActivity) getActivity()).Chartslimit = "30";
//				((MainActivity) getActivity()).Chartscountry = "United States";
//				((MainActivity) getActivity()).Chartstimeunit = "Daily";
//				((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//				String rq = "";
//				rq = "http://ws.audioscrobbler.com/2.0/?method=geo.getTopTracks&country=UNITED%20STATES&api_key=d6ef461c9610dc5d695efce1bbab90c1&format=json&limit=30";
//				JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
//					    new Response.Listener<JSONObject>() 
//					    {
//					        @Override
//					        public void onResponse(JSONObject response) {   
//					        	((MainActivity) getActivity()).Chartsjson=response;         
//					        	FragmentManager fm = getFragmentManager();
//								FragmentTransaction ft = fm.beginTransaction();
//								ChartsContentFragment rep = new ChartsContentFragment();
//								ft.remove(ChartFragment.this);
//								ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
//								ft.addToBackStack(null);
//								ft.commit();
//								MainActivity.currentaction="CHART_LIST";
//					        }
//					    }, 
//					    new Response.ErrorListener() 
//					    {
//					         @Override
//					         public void onErrorResponse(VolleyError error) {            
//					            Log.v("Error.Response", "error");
//					       }						
//					    }
//					);				
//				
//				((MainActivity) getActivity()).queue.add(getRequest);
//				
//			}
//		});
		View rootView = inflater.inflate(R.layout.fragment_charts, container, false);
		GridView gridview = (GridView) rootView.findViewById(R.id.chartgridview);
		gridview.setAdapter(new ImageAdapter((MainActivity) getActivity()));
		
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String rq = "";
				StringRequest sgetRequest = null;
				JsonObjectRequest jgetRequest = null;
				JsonArrayRequest jagetRequest = null;
				switch (position) {
				case 0:
					//bilboard
					((MainActivity) getActivity()).Chartsname ="BILLBOARD";
					((MainActivity) getActivity()).Chartstype = "hot-100";
					((MainActivity) getActivity()).Chartslimit = "100";
					((MainActivity) getActivity()).Chartscountry = "World-wide";
					((MainActivity) getActivity()).Chartstimeunit = "Weekly";
					((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//					String rq = "";
					rq = "http://billboard.com/rss/charts/hot-100";
					sgetRequest = new StringRequest(Request.Method.GET, rq, 
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								((MainActivity) getActivity()).Chartsxml = response;
								FragmentManager fm = getFragmentManager();
								FragmentTransaction ft = fm.beginTransaction();
								ChartsContentFragment rep = new ChartsContentFragment();
								ft.remove(ChartFragment.this);
								ft.add(android.R.id.content, rep);
								ft.addToBackStack(null);
								ft.commit();
								MainActivity.currentaction="CHART_LIST";							
							}
						},
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError arg0) {
								Log.v("Error.Response", "error");								
							}
						}
					);
					
					MainActivity.queue.add(sgetRequest);
					break;

				case 1:
					//itunes
					((MainActivity) getActivity()).Chartsname ="ITUNES";
					((MainActivity) getActivity()).Chartstype = "Top Songs";
					((MainActivity) getActivity()).Chartslimit = "100";
					((MainActivity) getActivity()).Chartscountry = "United States";
					((MainActivity) getActivity()).Chartstimeunit = "Daily";
					((MainActivity) getActivity()).Chartstimeinterval = "lastest";
//					String rq = "";
					rq = "https://itunes.apple.com/us/rss/topsongs/limit=100/json";
					jgetRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
						    new Response.Listener<JSONObject>() 
						    {
						        @Override
						        public void onResponse(JSONObject response) {  
						        	((MainActivity) getActivity()).Chartsjson = response;      
						        	FragmentManager fm = getFragmentManager();
									FragmentTransaction ft = fm.beginTransaction();
									ChartsContentFragment rep = new ChartsContentFragment();								
									ft.remove(ChartFragment.this);
									ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");								
									ft.addToBackStack(null);
									ft.commit();
									MainActivity.currentaction="CHART_LIST";
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
					((MainActivity) getActivity()).httpsqueue.add(jgetRequest);
				
					break;
					
				case 2:
					// google play
					((MainActivity) getActivity()).Chartsname ="GOOGLEPLAY";
					((MainActivity) getActivity()).Chartstype = "Top Songs";
					((MainActivity) getActivity()).Chartslimit = "60";
					((MainActivity) getActivity()).Chartstimeunit = "Daily";
					((MainActivity) getActivity()).Chartstimeinterval = "lastest";
					jgetRequest = new JsonObjectRequest(Request.Method.GET, "http://ip-api.com/json", null, 
						new Response.Listener<JSONObject>() {
						    @Override
						    public void onResponse(JSONObject response) {   
						    	try {
						    		((MainActivity) getActivity()).Chartscountry =response.getString("country");
									String rq = "";
									rq = "https://play.google.com/store/music/collection/topselling_paid_track";
									StringRequest postRequest = new StringRequest(Request.Method.POST, rq, 
										new Response.Listener<String>() {
											@Override
											public void onResponse(String response) {
												((MainActivity) getActivity()).Chartsxml=response;							
												FragmentManager fm = getFragmentManager();
												FragmentTransaction ft = fm.beginTransaction();
												ChartsContentFragment rep = new ChartsContentFragment();
												ft.remove(ChartFragment.this);
												ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
												ft.addToBackStack(null);
												ft.commit();
												MainActivity.currentaction="CHART_LIST";
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
									((MainActivity) getActivity()).httpsqueue.add(postRequest);
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
					MainActivity.queue.add(jgetRequest);
					break;
					
				case 3:
					//spotify
					((MainActivity) getActivity()).Chartsname ="SPOTIFY";
					((MainActivity) getActivity()).Chartstype = "Top Streamed";
					((MainActivity) getActivity()).Chartslimit = "all";
					((MainActivity) getActivity()).Chartscountry = "United States";
					((MainActivity) getActivity()).Chartstimeunit = "Daily";
					((MainActivity) getActivity()).Chartstimeinterval = "lastest";
					rq = "";
					rq = "http://charts.spotify.com/api/tracks/most_streamed/us/daily/latest";
					jgetRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
						    new Response.Listener<JSONObject>() 
						    {
						        @Override
						        public void onResponse(JSONObject response) {   
						        	((MainActivity) getActivity()).Chartsjson=response;         
						        	FragmentManager fm = getFragmentManager();
									FragmentTransaction ft = fm.beginTransaction();
									ChartsContentFragment rep = new ChartsContentFragment();
									ft.remove(ChartFragment.this);
									ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
									ft.addToBackStack(null);
									ft.commit();
									MainActivity.currentaction="CHART_LIST";
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
					
					MainActivity.queue.add(jgetRequest);
					break;
				
				case 4:
					//amazon
					((MainActivity) getActivity()).Chartsname ="AMAZON";
					((MainActivity) getActivity()).Chartstype = "Physical Albums";
					((MainActivity) getActivity()).Chartslimit = "10";
					((MainActivity) getActivity()).Chartscountry = "United States";
					((MainActivity) getActivity()).Chartstimeunit = "Daily";
					((MainActivity) getActivity()).Chartstimeinterval = "lastest";
					rq = "";
					rq = "http://amazon.com/gp/rss/bestsellers/music/ref=zg_bs_music_rsslink";
					sgetRequest = new StringRequest(Request.Method.GET, rq, 
							new Response.Listener<String>() {
								@Override
								public void onResponse(String response) {
									((MainActivity) getActivity()).Chartsxml=response;
									FragmentManager fm = getFragmentManager();
									FragmentTransaction ft = fm.beginTransaction();
									ChartsContentFragment rep = new ChartsContentFragment();
									ft.remove(ChartFragment.this);
									ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
									ft.addToBackStack(null);
									ft.commit();
									MainActivity.currentaction="CHART_LIST";
								}
							},
							new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError arg0) {
									Log.v("Error.Response", "error");								
								}
							}
						);		
					
					MainActivity.queue.add(sgetRequest);
					break;
				
				case 5:
					//napter
					((MainActivity) getActivity()).Chartsname ="NAPSTER";
					((MainActivity) getActivity()).Chartstype = "Top Songs";
					((MainActivity) getActivity()).Chartslimit = "30";
					((MainActivity) getActivity()).Chartscountry = "United States";
					((MainActivity) getActivity()).Chartstimeunit = "Daily";
					((MainActivity) getActivity()).Chartstimeinterval = "lastest";
					rq = "";
					rq = "http://api.rhapsody.com/v1/tracks/top?apikey=ZmRkNjg4ODMtMjgyZi00Nzc2LTlmMjQtZDdkM2RmYTExNTEx&limit=30&catalog=US";
					jagetRequest = new JsonArrayRequest(rq, 
							new Response.Listener<JSONArray>() {
								@Override
								public void onResponse(JSONArray response) {
									((MainActivity) getActivity()).Chartsjsonarr=response;
									FragmentManager fm = getFragmentManager();
									FragmentTransaction ft = fm.beginTransaction();
									ChartsContentFragment rep = new ChartsContentFragment();
									ft.remove(ChartFragment.this);
									ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
									ft.addToBackStack(null);
									ft.commit();
									MainActivity.currentaction="CHART_LIST";
								}					
							},
							new Response.ErrorListener() 
						    {
						         @Override
						         public void onErrorResponse(VolleyError error) {            
						            Log.v("Error.Response", "error");
						       }						
						    });				
	
					MainActivity.queue.add(jagetRequest);
					break;
					
				case 6:
					//lastfm
					((MainActivity) getActivity()).Chartsname ="LASTFM";
					((MainActivity) getActivity()).Chartstype = "Top Songs";
					((MainActivity) getActivity()).Chartslimit = "30";
					((MainActivity) getActivity()).Chartscountry = "United States";
					((MainActivity) getActivity()).Chartstimeunit = "Daily";
					((MainActivity) getActivity()).Chartstimeinterval = "lastest";
					rq = "";
					rq = "http://ws.audioscrobbler.com/2.0/?method=geo.getTopTracks&country=UNITED%20STATES&api_key=d6ef461c9610dc5d695efce1bbab90c1&format=json&limit=30";
					jgetRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
						    new Response.Listener<JSONObject>() 
						    {
						        @Override
						        public void onResponse(JSONObject response) {   
						        	((MainActivity) getActivity()).Chartsjson=response;         
						        	FragmentManager fm = getFragmentManager();
									FragmentTransaction ft = fm.beginTransaction();
									ChartsContentFragment rep = new ChartsContentFragment();
									ft.remove(ChartFragment.this);
									ft.add(android.R.id.content, rep, "CHART_SINGLE_DETAIL_FRAGMENT");
									ft.addToBackStack(null);
									ft.commit();
									MainActivity.currentaction="CHART_LIST";
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
					
					MainActivity.queue.add(jgetRequest);
					break;
					
				}
				
			}
			
		});
        return rootView;
    }
}
