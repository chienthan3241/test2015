package com.example.test2015;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ChartsContentFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_search_content, container, false);
		ListView listview = (ListView) rootView.findViewById(R.id.listsearch);
		String text=null;	
		String info = "";
		single_track track_tmp = new single_track();
		JSONArray items = null;
		JSONObject item = null;	
		MainActivity.clearChartsListItems();
		
		switch (MainActivity.getChartsname()) {
		//BILLBOARD CHART
		case "BILLBOARD":
			try {
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(new StringReader(MainActivity.getChartsxml()));
				int eventType = xpp.getEventType();
				
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String name = xpp.getName();
					switch (eventType) {
					case XmlPullParser.START_TAG:
						if(name.equals("item")){
							track_tmp =  new single_track();
							info ="";
						}
						break;
					case XmlPullParser.TEXT:
		               text = xpp.getText();
		               break;
					case XmlPullParser.END_TAG:
						if(name.equals("item")){
							track_tmp.setInfo(info);
							MainActivity.addChartsListItems(track_tmp);
						}else if (name.equals("chart_item_title")){							
							track_tmp.setTitle(text);							
						}else if(name.equals("artist")){
							track_tmp.setArtist(text);
						}else if(name.equals("rank_this_week")){
							info += "Rank: "+text;
						}else if(name.equals("rank_last_week")){
							info += "\t Rank last week: "+text;
						}else if(name.equals("description")){
							info += "\t Description: "+text;
						}
						break;
					default:
						break;
					}
					eventType = xpp.next();
				}
				
				//config search header
				ViewGroup header = (ViewGroup)inflater.inflate(R.layout.chart_header, listview, false);	
				TextView chart_name_txt = (TextView) header.findViewById(R.id.chart_name_txt);
				TextView chart_type_txt = (TextView) header.findViewById(R.id.chart_type_txt);
				ImageView chart_thumbnail = (ImageView) header.findViewById(R.id.chart_thumbnail);
				Spinner chart_type = (Spinner) header.findViewById(R.id.chart_type);
				TextView chart_country_txt = (TextView) header.findViewById(R.id.chart_country_txt);
				Spinner chart_country = (Spinner) header.findViewById(R.id.chart_country);
				TextView chart_limit_txt = (TextView) header.findViewById(R.id.chart_limit_txt);
				Spinner chart_limit = (Spinner) header.findViewById(R.id.chart_limit);
				TextView chart_timeunit_txt = (TextView) header.findViewById(R.id.chart_timeunit_txt);
				Spinner chart_timeunit = (Spinner) header.findViewById(R.id.chart_timeunit);
				TextView chart_timeinterval_txt = (TextView) header.findViewById(R.id.chart_timeinterval_txt);
				Spinner chart_timeinterval = (Spinner) header.findViewById(R.id.chart_timeinterval);
				Button chart_submit = (Button) header.findViewById(R.id.chart_submit);
				
				
				chart_thumbnail.setImageResource(R.drawable.billboard);
				chart_name_txt.setText("Charts provider: Billboard");
				chart_type_txt.setText("Charts type: ");				
				ArrayList<String> chart_type_options = new ArrayList<String>();
				chart_type_options.add("hot-100");
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options);
				chart_type.setAdapter(adapter);
				chart_type.setSelection(chart_type_options.indexOf("hot-100"));
				chart_country_txt.setText("Country: ");
				ArrayList<String> chart_country_options = new ArrayList<String>();
				chart_country_options.add("World wide");
				ArrayAdapter<String> c_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options);
				chart_country.setAdapter(c_adapter);
				chart_country.setSelection(chart_country_options.indexOf("World wide"));
				chart_limit_txt.setText("Limit: ");
				ArrayList<String> chart_limit_options = new ArrayList<String>();
				chart_limit_options.add("100");
				ArrayAdapter<String> cl_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options);
				chart_limit.setAdapter(cl_adapter);
				chart_limit.setSelection(chart_limit_options.indexOf("100"));
				chart_timeunit_txt.setText("Time Unit: ");
				ArrayList<String> chart_timeunit_options = new ArrayList<String>();
				chart_timeunit_options.add("Weekly");
				ArrayAdapter<String> ctu_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options);
				chart_timeunit.setAdapter(ctu_adapter);
				chart_timeunit.setSelection(chart_timeunit_options.indexOf("Weekly"));
				chart_timeinterval_txt.setText("Time interval: ");
				ArrayList<String> chart_timeinterval_options = new ArrayList<String>();
				chart_timeinterval_options.add("lastest");
				ArrayAdapter<String> cti_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options);
				chart_timeinterval.setAdapter(cti_adapter);
				chart_timeinterval.setSelection(chart_timeinterval_options.indexOf("lastest"));
				
				listview.addHeaderView(header,null,false);
				MainActivity.getChartsListAdapter().notifyDataSetChanged();
				chart_submit.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						// re-send billboard request
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
									//reload current Fragment
									Fragment frg=null;
									frg = getFragmentManager().findFragmentByTag("CHART_SINGLE_DETAIL_FRAGMENT");									
									FragmentTransaction ft = getFragmentManager().beginTransaction();
									ft.detach(frg);
									ft.attach(frg);
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
				
			} catch (XmlPullParserException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
	    
			
		//Itunes charts
		case "ITUNES":
			try {
				items = MainActivity.getChartsjson().getJSONObject("feed").getJSONArray("entry");
				for(int i = 0; i<items.length();i++){
					item = items.getJSONObject(i);
					track_tmp = new single_track();
					track_tmp.setThumbnailUrl(item.getJSONArray("im:image").getJSONObject(0).getString("label"));
					track_tmp.setTitle(item.getJSONObject("im:name").getString("label"));
					track_tmp.setArtist(item.getJSONObject("im:artist").getString("label"));
					track_tmp.setInfo(
							item.getJSONObject("im:collection").getJSONObject("im:contentType").getJSONObject("im:contentType").getJSONObject("attributes").getString("label")+": "+
							item.getJSONObject("im:collection").getJSONObject("im:name").getString("label")+" \t "+
							"itunes preis: "+item.getJSONObject("im:price").getString("label")+" \t "+ 
							"itunes ID: "+item.getJSONObject("id").getJSONObject("attributes").getString("im:id")+ " \t "+
							"Genre: "+item.getJSONObject("category").getJSONObject("attributes").getString("label")+ " \t "+
							"Release: "+item.getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label")+" \n ©"+
							item.getJSONObject("rights").getString("label")
							
							);
					MainActivity.addChartsListItems(track_tmp);
				}
				//config search header
				ViewGroup header = (ViewGroup)inflater.inflate(R.layout.chart_header, listview, false);	
				TextView chart_name_txt = (TextView) header.findViewById(R.id.chart_name_txt);
				TextView chart_type_txt = (TextView) header.findViewById(R.id.chart_type_txt);
				ImageView chart_thumbnail = (ImageView) header.findViewById(R.id.chart_thumbnail);
				Spinner chart_type = (Spinner) header.findViewById(R.id.chart_type);
				TextView chart_country_txt = (TextView) header.findViewById(R.id.chart_country_txt);
				Spinner chart_country = (Spinner) header.findViewById(R.id.chart_country);
				TextView chart_limit_txt = (TextView) header.findViewById(R.id.chart_limit_txt);
				Spinner chart_limit = (Spinner) header.findViewById(R.id.chart_limit);
				TextView chart_timeunit_txt = (TextView) header.findViewById(R.id.chart_timeunit_txt);
				Spinner chart_timeunit = (Spinner) header.findViewById(R.id.chart_timeunit);
				TextView chart_timeinterval_txt = (TextView) header.findViewById(R.id.chart_timeinterval_txt);
				Spinner chart_timeinterval = (Spinner) header.findViewById(R.id.chart_timeinterval);
				Button chart_submit = (Button) header.findViewById(R.id.chart_submit);
				
				
				chart_thumbnail.setImageResource(R.drawable.itunes_store);
				chart_name_txt.setText("Charts provider: Itunes");
				chart_type_txt.setText("Charts type: ");				
				ArrayList<String> chart_type_options = new ArrayList<String>();
				chart_type_options.add("Top Songs");
				chart_type_options.add("Top Albums");
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options);
				chart_type.setAdapter(adapter);
				chart_type.setSelection(chart_type_options.indexOf(MainActivity.getChartstype()));
				chart_country_txt.setText("Country: ");
				
				ArrayList<String> chart_country_options = new ArrayList<String>();
				Map<String, String> country_ar = new HashMap<>();
				country_ar.put("ai", "Anguilla");
				country_ar.put("ag", "Antigua and Barbuda");
				country_ar.put("ar", "Argentina");
				country_ar.put("am", "Armenia");
				country_ar.put("au", "Australia");
				country_ar.put("at", "Austria");
				country_ar.put("az", "Azerbaijan");
				country_ar.put("bs", "Bahamas");
				country_ar.put("bh", "Bahrain");
				country_ar.put("by", "Belarus");				
				country_ar.put("be", "Belgium");
				country_ar.put("bz", "Belize");
				country_ar.put("bm", "Bermuda");
				country_ar.put("bo", "Bolivia");
				country_ar.put("bw", "Botswana");
				country_ar.put("br", "Brazil");
				country_ar.put("vg", "British Virgin Islands");
				country_ar.put("bn", "Brunei");
				country_ar.put("bg", "Bulgaria");
				country_ar.put("bf", "Burkina Faso");
				country_ar.put("kh", "Cambodia");				
				country_ar.put("ca", "Canada");
				country_ar.put("cv", "Cape Verde");
				country_ar.put("ky", "Cayman Islands");
				country_ar.put("cl", "Chile");
				country_ar.put("cn", "China");
				country_ar.put("co", "Colombia");
				country_ar.put("cr", "Costa Rica");
				country_ar.put("hr", "Croatia");
				country_ar.put("cy", "Cyprus");
				country_ar.put("cz", "Czech Republic");				
				country_ar.put("dk", "Denmark");
				country_ar.put("dm", "Dominica");
				country_ar.put("do", "Dominican Republic");
				country_ar.put("ec", "Ecuador");
				country_ar.put("eg", "Egypt");
				country_ar.put("sv", "El Salvador");
				country_ar.put("ee", "Estonia");
				country_ar.put("fj", "Fiji");
				country_ar.put("fi", "Finland");
				country_ar.put("fr", "France");				
				country_ar.put("gm", "Gambia");
				country_ar.put("de", "Germany");
				country_ar.put("gh", "Ghana");
				country_ar.put("gr", "Greece");
				country_ar.put("gd", "Grenada");
				country_ar.put("gt", "Guatemala");
				country_ar.put("gw", "Guinea-Bissau");
				country_ar.put("gy", "Guyana");
				country_ar.put("hn", "Honduras");
				country_ar.put("hk", "Hong Kong");				
				country_ar.put("hu", "Hungary");
				country_ar.put("is", "Iceland");
				country_ar.put("in", "India");
				country_ar.put("id", "Indonesia");
				country_ar.put("ie", "Ireland");
				country_ar.put("il", "Israel");
				country_ar.put("it", "Italy");
				country_ar.put("jp", "Japan");
				country_ar.put("jo", "Jordan");
				country_ar.put("kz", "Kazakhstan");				
				country_ar.put("ke", "Kenya");
				country_ar.put("kg", "Kyrgyzstan");
				country_ar.put("la", "Laos");
				country_ar.put("lv", "Latvia");
				country_ar.put("lb", "Lebanon");
				country_ar.put("li", "Liechtenstein");
				country_ar.put("lt", "Lithuania");
				country_ar.put("lu", "Luxembourg");
				country_ar.put("mo", "Macau");
				country_ar.put("mg", "Madagascar");				
				country_ar.put("my", "Malaysia");
				country_ar.put("mt", "Malta");
				country_ar.put("mu", "Mauritius");
				country_ar.put("mx", "Mexico");
				country_ar.put("fm", "Micronesia");
				country_ar.put("md", "Moldova");
				country_ar.put("mn", "Mongolia");
				country_ar.put("me", "Montenegro");
				country_ar.put("mz", "Mozambique");
				country_ar.put("na", "Namibia");				
				country_ar.put("np", "Nepal");				
				country_ar.put("nl", "Netherlands");
				country_ar.put("nz", "New Zealand");
				country_ar.put("ni", "Nicaragua");
				country_ar.put("ne", "Niger");
				country_ar.put("ng", "Nigeria");
				country_ar.put("no", "Norway");
				country_ar.put("om", "Oman");
				country_ar.put("pk", "Pakistan");
				country_ar.put("pa", "Panama");
				country_ar.put("pg", "Papua New Guinea");				
				country_ar.put("py", "Paraguay");
				country_ar.put("pe", "Peru");
				country_ar.put("ph", "Philippines");
				country_ar.put("pl", "Poland");
				country_ar.put("pt", "Portugal");
				country_ar.put("qa", "Qatar");				
				country_ar.put("ro", "Romania");
				country_ar.put("ru", "Russia");
				country_ar.put("kn", "Saint Kitts and Nevis");				
				country_ar.put("sa", "Saudi Arabia");
				country_ar.put("sg", "Singapore");
				country_ar.put("sk", "Slovakia");
				country_ar.put("si", "Slovenia");
				country_ar.put("za", "South Africa");
				country_ar.put("kr", "South Korea");
				country_ar.put("es", "Spain");
				country_ar.put("lk", "Sri Lanka");
				country_ar.put("sz", "Swaziland");
				country_ar.put("se", "Sweden");
				country_ar.put("ch", "Switzerland");				
				country_ar.put("tw", "Taiwan");
				country_ar.put("tj", "Tajikistan");				
				country_ar.put("th", "Thailand");
				country_ar.put("tt", "Trinidad and Tobago");
				country_ar.put("tn", "Tunisia");
				country_ar.put("tr", "Turkey");
				country_ar.put("tm", "Turkmenistan");
				country_ar.put("tc", "Turks and Caicos Islands");
				country_ar.put("ug", "Uganda");
				country_ar.put("ua", "Ukraine");
				country_ar.put("ae", "United Arab Emirates");
				country_ar.put("gb", "United Kingdom");
				country_ar.put("us", "United States");
				country_ar.put("uy", "Uruguay");
				country_ar.put("uz", "Uzbekistan");
				country_ar.put("ve", "Venezuela");
				country_ar.put("vn", "Vietnam");				
				country_ar.put("zw", "Zimbabwe");
				Iterator entries = country_ar.entrySet().iterator();
				while (entries.hasNext()) {
					Entry<String, String> thisEntry  = (Entry<String, String>) entries.next();
					chart_country_options.add(thisEntry.getValue()); 					
				}
				ArrayAdapter<String> c_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options);
				chart_country.setAdapter(c_adapter);
				chart_country.setSelection(chart_country_options.indexOf(MainActivity.getChartscountry()));
				chart_limit_txt.setText("Limit: ");
				ArrayList<String> chart_limit_options = new ArrayList<String>();
				chart_limit_options.add("10");
				chart_limit_options.add("25");
				chart_limit_options.add("50");
				chart_limit_options.add("100");
				ArrayAdapter<String> cl_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options);
				chart_limit.setAdapter(cl_adapter);
				chart_limit.setSelection(chart_limit_options.indexOf(MainActivity.getChartslimit()));
				chart_timeunit_txt.setText("Time Unit: ");
				ArrayList<String> chart_timeunit_options = new ArrayList<String>();
				chart_timeunit_options.add("Daily");
				ArrayAdapter<String> ctu_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options);
				chart_timeunit.setAdapter(ctu_adapter);
				chart_timeunit.setSelection(chart_timeunit_options.indexOf("Daily"));
				chart_timeinterval_txt.setText("Time interval: ");
				ArrayList<String> chart_timeinterval_options = new ArrayList<String>();
				chart_timeinterval_options.add("lastest");
				ArrayAdapter<String> cti_adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options);
				chart_timeinterval.setAdapter(cti_adapter);
				chart_timeinterval.setSelection(chart_timeinterval_options.indexOf("lastest"));
				
				listview.addHeaderView(header,null,false);
				MainActivity.getChartsListAdapter().notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		default:
			break;
		}	
			
		listview.setAdapter(MainActivity.getChartsListAdapter());	
		
		
		/**
		 * listview items onclick listener
		 */
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
					
					if(MainActivity.getChartsname().equals("BILLBOARD")){
						//BILBOARD CHART ONITEMSCLICK
						String rq = "https://gdata.youtube.com/feeds/api/videos?q="+
									MainActivity.getChartsListItems().get(position-1).getTitle().trim().replace(" ", "+")+"+"+
									MainActivity.getChartsListItems().get(position-1).getArtist().trim().replace(" ", "+")+
									"&orderby=relevance&start-index=1&max-results=20&v=2&alt=json";					
						JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
							    new Response.Listener<JSONObject>() 
							    {
							        @Override
							        public void onResponse(JSONObject response) {   
							            MainActivity.setsearchyoutubejson(response);
							            MainActivity.setsearch_youtube_format("YOUTUBE_LIST");
							            MainActivity.setsearch_youtube_title_txt(MainActivity.getChartsListItems().get(position-1).getTitle());
										MainActivity.setsearch_youtube_artist_txt(MainActivity.getChartsListItems().get(position-1).getArtist());          
							            
							            FragmentManager fm = getFragmentManager();
										FragmentTransaction ft = fm.beginTransaction();
										YoutubeListContentFragment rep = new YoutubeListContentFragment();
										ft.addToBackStack("abbccd");
										ft.hide(ChartsContentFragment.this);
										ft.add(android.R.id.content, rep, "YOUTUBE_DETAIL_FRAGMENT");
										ft.commit();
										MainActivity.setCurrentaction("YOUTUBE_LIST");
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
					
					}else if(MainActivity.getChartsname().equals("ITUNES")){
						//ITUNES CHART ONITEMSCLICK
						if(MainActivity.getChartstype().equals("Top Songs")){
							String rq = "https://gdata.youtube.com/feeds/api/videos?q="+
									MainActivity.getChartsListItems().get(position-1).getTitle().trim().replace(" ", "+")+"+"+
									MainActivity.getChartsListItems().get(position-1).getArtist().trim().replace(" ", "+")+
									"&orderby=relevance&start-index=1&max-results=20&v=2&alt=json";					
							JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
								    new Response.Listener<JSONObject>() 
								    {
								        @Override
								        public void onResponse(JSONObject response) {   
								            MainActivity.setsearchyoutubejson(response);
								            MainActivity.setsearch_youtube_format("YOUTUBE_LIST");
								            MainActivity.setsearch_youtube_title_txt(MainActivity.getChartsListItems().get(position-1).getTitle());
											MainActivity.setsearch_youtube_artist_txt(MainActivity.getChartsListItems().get(position-1).getArtist());          
								            
								            FragmentManager fm = getFragmentManager();
											FragmentTransaction ft = fm.beginTransaction();
											YoutubeListContentFragment rep = new YoutubeListContentFragment();
											ft.addToBackStack("abbccd");
											ft.hide(ChartsContentFragment.this);
											ft.add(android.R.id.content, rep, "YOUTUBE_DETAIL_FRAGMENT");
											ft.commit();
											MainActivity.setCurrentaction("YOUTUBE_LIST");
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
						}else if(MainActivity.getChartstype().equals("Top Albums")){
							//TODO 
						}
					}
			}
			
		});
		
		return rootView;
	}
}
