package com.example.test2015;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import android.annotation.SuppressLint;
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
	@SuppressLint("UseValueOf")
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
		
		//config search header
		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.chart_header, listview, false);	
		TextView chart_name_txt = (TextView) header.findViewById(R.id.chart_name_txt);
		TextView chart_type_txt = (TextView) header.findViewById(R.id.chart_type_txt);
		ImageView chart_thumbnail = (ImageView) header.findViewById(R.id.chart_thumbnail);
		final Spinner chart_type = (Spinner) header.findViewById(R.id.chart_type);
		TextView chart_country_txt = (TextView) header.findViewById(R.id.chart_country_txt);
		final Spinner chart_country = (Spinner) header.findViewById(R.id.chart_country);
		TextView chart_limit_txt = (TextView) header.findViewById(R.id.chart_limit_txt);
		final Spinner chart_limit = (Spinner) header.findViewById(R.id.chart_limit);
		TextView chart_timeunit_txt = (TextView) header.findViewById(R.id.chart_timeunit_txt);
		final Spinner chart_timeunit = (Spinner) header.findViewById(R.id.chart_timeunit);
		TextView chart_timeinterval_txt = (TextView) header.findViewById(R.id.chart_timeinterval_txt);
		Spinner chart_timeinterval = (Spinner) header.findViewById(R.id.chart_timeinterval);
		Button chart_submit = (Button) header.findViewById(R.id.chart_submit);
		ArrayList<String> chart_type_options = new ArrayList<String>();
		ArrayList<String> chart_country_options = new ArrayList<String>();
		ArrayList<String> chart_limit_options = new ArrayList<String>();
		ArrayList<String> chart_timeunit_options = new ArrayList<String>();
		ArrayList<String> chart_timeinterval_options = new ArrayList<String>();
		
		
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
				chart_thumbnail.setImageResource(R.drawable.billboard);
				chart_name_txt.setText("Charts provider: Billboard");
				
				chart_type_txt.setText("Charts type: ");	
				chart_type_options.add("hot-100");
				chart_type.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options));
				chart_type.setSelection(chart_type_options.indexOf("hot-100"));
				
				chart_country_txt.setText("Country: ");				
				chart_country_options.add("World wide");
				chart_country.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options));
				chart_country.setSelection(chart_country_options.indexOf("World wide"));
				
				chart_limit_txt.setText("Limit: ");
				chart_limit_options.add("100");
				chart_limit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options));
				chart_limit.setSelection(chart_limit_options.indexOf("100"));
				
				chart_timeunit_txt.setText("Time Unit: ");
				chart_timeunit_options.add("Weekly");
				chart_timeunit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options));
				chart_timeunit.setSelection(chart_timeunit_options.indexOf("Weekly"));
				
				chart_timeinterval_txt.setText("Time interval: ");				
				chart_timeinterval_options.add("lastest");
				chart_timeinterval.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options));
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
				if(MainActivity.getChartstype().equals("Top Songs")){
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
				}else {
					for(int i = 0; i<items.length();i++){
						item = items.getJSONObject(i);
						track_tmp = new single_track();
						track_tmp.setThumbnailUrl(item.getJSONArray("im:image").getJSONObject(0).getString("label"));
						track_tmp.setTitle(item.getJSONObject("im:name").getString("label"));
						track_tmp.setArtist(item.getJSONObject("im:artist").getString("label"));
						track_tmp.setInfo(
								"itunes preis: "+item.getJSONObject("im:price").getString("label")+" \t "+ 
								"itunes ID: "+item.getJSONObject("id").getJSONObject("attributes").getString("im:id")+ " \t "+
								"Genre: "+item.getJSONObject("category").getJSONObject("attributes").getString("label")+ " \t "+
								"Release: "+item.getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label")+" \n ©"+
								item.getJSONObject("rights").getString("label")
								
								);
						MainActivity.addChartsListItems(track_tmp);
					}
				}
				//config search header
				chart_thumbnail.setImageResource(R.drawable.itunes_store);
				chart_name_txt.setText("Charts provider: Itunes");
				
				chart_type_txt.setText("Charts type: ");				
				chart_type_options.add("Top Songs");
				chart_type_options.add("Top Albums");
				chart_type.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options));				
				chart_type.setSelection(chart_type_options.indexOf(MainActivity.getChartstype()));
				
				chart_country_txt.setText("Country: ");				
				final Map<String, String> country_ar = new HashMap<>();
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
//				country_ar.put("cn", "China");
				country_ar.put("co", "Colombia");
				country_ar.put("cr", "Costa Rica");
//				country_ar.put("hr", "Croatia");
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
//				country_ar.put("gy", "Guyana");
				country_ar.put("hn", "Honduras");
				country_ar.put("hk", "Hong Kong");				
				country_ar.put("hu", "Hungary");
//				country_ar.put("is", "Iceland");
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
//				country_ar.put("mg", "Madagascar");				
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
//				country_ar.put("pk", "Pakistan");
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
//				country_ar.put("kr", "South Korea");
				country_ar.put("es", "Spain");
				country_ar.put("lk", "Sri Lanka");
				country_ar.put("sz", "Swaziland");
				country_ar.put("se", "Sweden");
				country_ar.put("ch", "Switzerland");				
				country_ar.put("tw", "Taiwan");
				country_ar.put("tj", "Tajikistan");				
				country_ar.put("th", "Thailand");
				country_ar.put("tt", "Trinidad and Tobago");
//				country_ar.put("tn", "Tunisia");
				country_ar.put("tr", "Turkey");
				country_ar.put("tm", "Turkmenistan");
//				country_ar.put("tc", "Turks and Caicos Islands");
				country_ar.put("ug", "Uganda");
				country_ar.put("ua", "Ukraine");
				country_ar.put("ae", "United Arab Emirates");
				country_ar.put("gb", "United Kingdom");
				country_ar.put("us", "United States");
//				country_ar.put("uy", "Uruguay");
				country_ar.put("uz", "Uzbekistan");
				country_ar.put("ve", "Venezuela");
				country_ar.put("vn", "Vietnam");				
				country_ar.put("zw", "Zimbabwe");				
				@SuppressWarnings("rawtypes")
				Iterator entries = country_ar.entrySet().iterator();
				while (entries.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> thisEntry  = (Entry<String, String>) entries.next();
					chart_country_options.add(thisEntry.getValue()); 					
				}
				Collections.sort(chart_country_options);
				chart_country.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options));
				chart_country.setSelection(chart_country_options.indexOf(MainActivity.getChartscountry()));
				
				chart_limit_txt.setText("Limit: ");
				chart_limit_options.add("10");
				chart_limit_options.add("25");
				chart_limit_options.add("50");
				chart_limit_options.add("100");
				chart_limit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options));
				chart_limit.setSelection(chart_limit_options.indexOf(MainActivity.getChartslimit()));
				
				chart_timeunit_txt.setText("Time Unit: ");
				chart_timeunit_options.add("Daily");
				chart_timeunit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options));
				chart_timeunit.setSelection(chart_timeunit_options.indexOf("Daily"));
				
				chart_timeinterval_txt.setText("Time interval: ");
				chart_timeinterval_options.add("lastest");
				chart_timeinterval.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options));
				chart_timeinterval.setSelection(chart_timeinterval_options.indexOf("lastest"));
				
				listview.addHeaderView(header,null,false);
				MainActivity.getChartsListAdapter().notifyDataSetChanged();
				chart_submit.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						MainActivity.setChartsname("ITUNES");
						MainActivity.setChartstype(chart_type.getSelectedItem().toString());
						MainActivity.setChartslimit(chart_limit.getSelectedItem().toString());
						MainActivity.setChartscountry(chart_country.getSelectedItem().toString());
						MainActivity.setChartstimeunit("Daily");
						MainActivity.setChartstimeinterval("lastest");
						String map_key_cc = "";
						for (Entry<String, String> entry : country_ar.entrySet()){
							if(entry.getValue().equals(chart_country.getSelectedItem().toString())){
								map_key_cc = entry.getKey();
								break;
							}							
						}
						String rq = "";
						rq = "https://itunes.apple.com/"+map_key_cc+"/rss/";
						if(chart_type.getSelectedItem().toString().equals("Top Songs")){
							rq += "topsongs/limit=";
						}else{
							rq += "topalbums/limit=";
						}
						rq += chart_limit.getSelectedItem().toString()+"/json";
//						Log.v("request",rq);
						JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
							    new Response.Listener<JSONObject>() 
							    {
							        @Override
							        public void onResponse(JSONObject response) {   
							        	MainActivity.setChartsjson(response);     
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
		case "GOOGLEPLAY":
			Document doc = Jsoup.parse(MainActivity.getChartsxml());
			Elements gitems = doc.select("div.details");
			Elements gcovers = doc.select("div.cover");
			String grank = "";
			for(int i =0; i<gitems.size();i++){
				track_tmp = new single_track();				
				track_tmp.setThumbnailUrl(gcovers.get(i).select("img").first().attr("src"));
				if(gitems.get(i).select("a.title").first().text().indexOf(".") != -1){
					track_tmp.setTitle(gitems.get(i).select("a.title").first().text().substring(gitems.get(i).select("a.title").first().text().indexOf(".") + 1).trim());
					grank = gitems.get(i).select("a.title").first().text().substring(0, gitems.get(i).select("a.title").first().text().indexOf("."));
				}else{
					track_tmp.setTitle(gitems.get(i).select("a.title").first().text());
					grank = new Integer(i).toString();
				}
				track_tmp.setArtist(gitems.get(i).select("a.subtitle").first().text());
				track_tmp.setInfo(
						"Rank: " + grank + "\t"+
						"GooglePlay preis: "+gitems.get(i).select("span.display-price").first().text()						
						);
				MainActivity.addChartsListItems(track_tmp);
			}
			//config search header			
			chart_thumbnail.setImageResource(R.drawable.googleplay_store);
			chart_name_txt.setText("Charts provider: GooglePlay");
			
			chart_type_txt.setText("Charts type: ");				
			chart_type_options.add("Top Songs");
			chart_type_options.add("Top Albums");
			chart_type.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options));
			chart_type.setSelection(chart_type_options.indexOf(MainActivity.getChartstype()));
			
			chart_country_txt.setText("Country: ");
			chart_country_options.add(MainActivity.getChartscountry());
			chart_country.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options));
			chart_country.setSelection(chart_country_options.indexOf(MainActivity.getChartscountry()));
			
			chart_limit_txt.setText("Limit: ");
			chart_limit_options.add("60");
			chart_limit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options));
			chart_limit.setSelection(chart_limit_options.indexOf("60"));
			
			chart_timeunit_txt.setText("Time Unit: ");
			chart_timeunit_options.add("Daily");
			chart_timeunit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options));
			chart_timeunit.setSelection(chart_timeunit_options.indexOf("Daily"));
			
			chart_timeinterval_txt.setText("Time interval: ");
			chart_timeinterval_options.add("lastest");
			chart_timeinterval.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options));
			chart_timeinterval.setSelection(chart_timeinterval_options.indexOf("lastest"));
			
			listview.addHeaderView(header,null,false);
			MainActivity.getChartsListAdapter().notifyDataSetChanged();
			
			chart_submit.setOnClickListener(new View.OnClickListener() {					
				@Override
				public void onClick(View v) {
					// re-send billboard request
					MainActivity.setChartsname("GOOGLEPLAY");
					MainActivity.setChartstype(chart_type.getSelectedItem().toString());
					MainActivity.setChartslimit("60");
					MainActivity.setChartscountry(chart_country.getSelectedItem().toString());
					MainActivity.setChartstimeunit("Daily");
					MainActivity.setChartstimeinterval("lastest");
					String rq = "";
					if(chart_type.equals("Top Songs")){
						rq = "https://play.google.com/store/music/collection/topselling_paid_track";
					}else{
						rq = "https://play.google.com/store/music/collection/topselling_paid_album";
					}
					
					StringRequest postRequest = new StringRequest(Request.Method.POST, rq, 
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
						};							
					MainActivity.getQueue().add(postRequest);						
				}
			});
		break;
		
		case "SPOTIFY":
			try {
				items = MainActivity.getChartsjson().getJSONArray("tracks");
				for(int i = 0; i<items.length();i++){
					track_tmp = new single_track();
					item = items.getJSONObject(i);
					track_tmp.setThumbnailUrl(item.getString("artwork_url"));
					track_tmp.setArtist(item.getString("artist_name"));
					track_tmp.setTitle(item.getString("track_name"));
					track_tmp.setInfo(
						"Chart Rank: " + new Integer(i+1).toString() +"\t"+
						"Album name: " + item.getString("album_name")+"\t"+
						"Country: " + item.getString("country")+"\t"+
						"Date: " + item.getString("date")+"\t"+
						"Chart period: " + item.getString("window_type")+"\t"+
						"Numbers of streamed: " + item.getString("num_streams")
					);
					MainActivity.addChartsListItems(track_tmp);
				}
				
				//config search header			
				chart_thumbnail.setImageResource(R.drawable.spotify_store);
				chart_name_txt.setText("Charts provider: Spotify");
				
				chart_type_txt.setText("Charts type: ");				
				chart_type_options.add("Top Streamed");
				chart_type_options.add("Top Viral");
				chart_type.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options));
				chart_type.setSelection(chart_type_options.indexOf(MainActivity.getChartstype()));
				
				chart_country_txt.setText("Country: ");				
				final Map<String, String> country_ar = new HashMap<>();
				country_ar.put("ar", "Argentina");
				country_ar.put("au", "Australia");
				country_ar.put("at", "Austria");				
				country_ar.put("be", "Belgium");
				country_ar.put("bo", "Bolivia");
				country_ar.put("br", "Brazil");
				country_ar.put("bg", "Bulgaria");			
				country_ar.put("ca", "Canada");
				country_ar.put("cl", "Chile");
				country_ar.put("co", "Colombia");
				country_ar.put("cr", "Costa Rica");
				country_ar.put("cy", "Cyprus");
				country_ar.put("cz", "Czech Republic");				
				country_ar.put("dk", "Denmark");
				country_ar.put("do", "Dominican Republic");
				country_ar.put("ec", "Ecuador");
				country_ar.put("sv", "El Salvador");
				country_ar.put("ee", "Estonia");
				country_ar.put("fi", "Finland");
				country_ar.put("fr", "France");
				country_ar.put("de", "Germany");
				country_ar.put("gr", "Greece");
				country_ar.put("gt", "Guatemala");
				country_ar.put("hn", "Honduras");
				country_ar.put("hk", "Hong Kong");				
				country_ar.put("hu", "Hungary");
				country_ar.put("is", "Iceland");
				country_ar.put("ie", "Ireland");
				country_ar.put("it", "Italy");
				country_ar.put("lv", "Latvia");
				country_ar.put("li", "Liechtenstein");
				country_ar.put("lt", "Lithuania");
				country_ar.put("lu", "Luxembourg");				
				country_ar.put("my", "Malaysia");
				country_ar.put("mt", "Malta");
				country_ar.put("mu", "Mauritius");
				country_ar.put("mx", "Mexico");		
				country_ar.put("nl", "Netherlands");
				country_ar.put("nz", "New Zealand");
				country_ar.put("ni", "Nicaragua");
				country_ar.put("no", "Norway");
				country_ar.put("pa", "Panama");			
				country_ar.put("py", "Paraguay");
				country_ar.put("pe", "Peru");
				country_ar.put("ph", "Philippines");
				country_ar.put("pl", "Poland");
				country_ar.put("pt", "Portugal");
				country_ar.put("sg", "Singapore");
				country_ar.put("sk", "Slovakia");
				country_ar.put("es", "Spain");
				country_ar.put("se", "Sweden");
				country_ar.put("ch", "Switzerland");				
				country_ar.put("tw", "Taiwan");
				country_ar.put("tr", "Turkey");
				country_ar.put("gb", "United Kingdom");
				country_ar.put("us", "United States");
				country_ar.put("uy", "Uruguay");			
				@SuppressWarnings("rawtypes")
				Iterator entries = country_ar.entrySet().iterator();
				while (entries.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> thisEntry  = (Entry<String, String>) entries.next();
					chart_country_options.add(thisEntry.getValue()); 					
				}
				Collections.sort(chart_country_options);
				chart_country.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options));
				chart_country.setSelection(chart_country_options.indexOf(MainActivity.getChartscountry()));
				
				chart_limit_txt.setText("Limit: ");
				chart_limit_options.add("all");
				chart_limit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options));
				chart_limit.setSelection(chart_limit_options.indexOf("all"));
				
				chart_timeunit_txt.setText("Time Unit: ");
				chart_timeunit_options.add("daily");
				chart_timeunit_options.add("weekly");
				chart_timeunit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options));
				chart_timeunit.setSelection(chart_timeunit_options.indexOf(MainActivity.getChartstimeunit()));
				
				chart_timeinterval_txt.setText("Time interval: ");
				chart_timeinterval_options.add("latest");
				chart_timeinterval.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options));
				chart_timeinterval.setSelection(chart_timeinterval_options.indexOf("latest"));
				
				listview.addHeaderView(header,null,false);	
				MainActivity.getChartsListAdapter().notifyDataSetChanged();
				
				chart_submit.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						MainActivity.setChartsname("SPOTIFY");
						MainActivity.setChartstype(chart_type.getSelectedItem().toString());
						MainActivity.setChartslimit("all");
						MainActivity.setChartscountry(chart_country.getSelectedItem().toString());
						MainActivity.setChartstimeunit(chart_timeunit.getSelectedItem().toString());
						MainActivity.setChartstimeinterval("lastest");
						String map_key_cc = "";
						for (Entry<String, String> entry : country_ar.entrySet()){
							if(entry.getValue().equals(chart_country.getSelectedItem().toString())){
								map_key_cc = entry.getKey();
								break;
							}							
						}
						String rq = "";
						rq = "http://charts.spotify.com/api/tracks/";
						if(chart_type.getSelectedItem().toString().equals("Top Streamed")){
							rq += "most_streamed/";
						}else{
							rq += "most_viral/";
						}
						rq +=map_key_cc+"/";
						if(chart_timeunit.getSelectedItem().toString().equals("weekly")){
							rq += "weekly/latest";
						}else{
							rq += "daily/latest";
						}						
						JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
							    new Response.Listener<JSONObject>() 
							    {
							        @Override
							        public void onResponse(JSONObject response) {   
							        	MainActivity.setChartsjson(response);     
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
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		break;
		
		case "AMAZON":
			
			try {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(MainActivity.getChartsxml()));
				org.w3c.dom.Document ama_doc =  builder.parse(is);
				NodeList nodes = ama_doc.getElementsByTagName("item");
				for(int i = 0; i<nodes.getLength();i++){
					track_tmp = new single_track();
					Element element = (Element) nodes.item(i);
					NodeList des = element.getElementsByTagName("description");
					Element line = (Element) des.item(0);
					Document ama_jsoup_doc = Jsoup.parse(line.getTextContent());					
					track_tmp.setThumbnailUrl(ama_jsoup_doc.select("img").first().attr("src"));
					track_tmp.setTitle(ama_jsoup_doc.select("span.riRssTitle").first().select("a").text().trim());
					Log.v("sds",ama_jsoup_doc.select("span.riRssContributor").first().text().replace("~", "").trim());
					String ama_artist = ama_jsoup_doc.select("span.riRssContributor").first().text().replace("~", "").trim();
					if(ama_artist.contains("|")){						
						track_tmp.setArtist(ama_artist.substring(0, ama_artist.indexOf("|")).trim());
					}else{
						track_tmp.setArtist(ama_jsoup_doc.select("span.riRssContributor").first().text().replace("~", "").trim());
					}
					
					track_tmp.setInfo(
							"Rank: " + element.getElementsByTagName("title").item(0).getTextContent().split(":")[0].replace("#", "").trim()
					);	
					
					MainActivity.addChartsListItems(track_tmp);
				}
				
				//config search header
				chart_thumbnail.setImageResource(R.drawable.amazon_store);
				chart_name_txt.setText("Charts provider: Amazon");
				
				chart_type_txt.setText("Charts type: ");	
				chart_type_options.add("Physical Albums");
				chart_type_options.add("Digital Albums");
				chart_type_options.add("Digital Tracks");
				chart_type.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options));
				chart_type.setSelection(chart_type_options.indexOf(MainActivity.getChartstype()));
				
				chart_country_txt.setText("Country: ");	
				final Map<String, String> country_ar = new HashMap<>();
				country_ar.put(".com", "United States");
				country_ar.put(".fr", "France");
				country_ar.put(".co.jp", "Japan");				
				country_ar.put(".ca", "Canada");
				country_ar.put(".de", "Germany");
				country_ar.put(".es", "Spain");
				country_ar.put(".it", "Italy");			
				country_ar.put(".co.uk", "United Kingdom");			
				@SuppressWarnings("rawtypes")
				Iterator entries = country_ar.entrySet().iterator();
				while (entries.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> thisEntry  = (Entry<String, String>) entries.next();
					chart_country_options.add(thisEntry.getValue()); 					
				}
				Collections.sort(chart_country_options);
				chart_country.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options));
				chart_country.setSelection(chart_country_options.indexOf(MainActivity.getChartscountry()));
				
				chart_limit_txt.setText("Limit: ");
				chart_limit_options.add("10");
				chart_limit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options));
				chart_limit.setSelection(chart_limit_options.indexOf("10"));
				
				chart_timeunit_txt.setText("Time Unit: ");
				chart_timeunit_options.add("Daily");
				chart_timeunit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options));
				chart_timeunit.setSelection(chart_timeunit_options.indexOf("Daily"));
				
				chart_timeinterval_txt.setText("Time interval: ");				
				chart_timeinterval_options.add("lastest");
				chart_timeinterval.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options));
				chart_timeinterval.setSelection(chart_timeinterval_options.indexOf("lastest"));
				
				listview.addHeaderView(header,null,false);				
				MainActivity.getChartsListAdapter().notifyDataSetChanged();
				
				chart_submit.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						// re-send billboard request
						MainActivity.setChartsname("AMAZON");
						MainActivity.setChartstype(chart_type.getSelectedItem().toString());
						MainActivity.setChartslimit("10");
						MainActivity.setChartscountry(chart_country.getSelectedItem().toString());
						MainActivity.setChartstimeunit("Daily");
						MainActivity.setChartstimeinterval("lastest");
						String map_key_cc = "";
						for (Entry<String, String> entry : country_ar.entrySet()){
							if(entry.getValue().equals(chart_country.getSelectedItem().toString())){
								map_key_cc = entry.getKey();
								break;
							}							
						}
						String rq = "";
						rq = "http://amazon"+map_key_cc+"/gp/rss/bestsellers/";
						if(MainActivity.getChartstype().equals("Physical Albums")){
							rq+="music";
						}else if(MainActivity.getChartstype().equals("Digital Albums")){
							rq+="/dmusic/digital-music-album";
						}else{
							rq+="/dmusic/digital-music-track";
						}
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
				
				
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
		break;
		
		case "NAPSTER":
			items = MainActivity.getChartsjsonarr();
			try {
				for(int i = 0; i<items.length();i++){
					track_tmp = new single_track();
					item = items.getJSONObject(i);		
					if(MainActivity.getChartstype().equals("Top Albums")){
						track_tmp.setThumbnailUrl(item.getJSONArray("images").getJSONObject(0).getString("url"));
					}
					track_tmp.setArtist(item.getJSONObject("artist").getString("name"));
					track_tmp.setTitle(item.getString("name"));
					if(MainActivity.getChartstype().equals("Top Albums")){	
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						track_tmp.setInfo(						
							"Album type: " + item.getJSONObject("type").getString("name")+"\t"+							
							"Release: " + sdf.format(new Date(Long.parseLong(item.getString("released")))) 
						);
					}else{						
						track_tmp.setInfo(						
							"Album: " + item.getJSONObject("album").getString("name")+"\t"+							
							"Duration: " + String.format("%.2f", (float)Integer.parseInt(item.getString("duration"))/60.0f) + " min."
						);
					}
					
					MainActivity.addChartsListItems(track_tmp);
				}
				

				//config search header
				chart_thumbnail.setImageResource(R.drawable.napster_store);
				chart_name_txt.setText("Charts provider: Napster");
				
				chart_type_txt.setText("Charts type: ");
				chart_type_options.add("Top Songs");
				chart_type_options.add("Top Albums");					
				chart_type.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options));
				chart_type.setSelection(chart_type_options.indexOf(MainActivity.getChartstype()));
				
				chart_country_txt.setText("Country: ");	
				final Map<String, String> country_ar = new HashMap<>();
				country_ar.put("US", "United States");
				country_ar.put("DE", "Germany");			
				country_ar.put("GB", "United Kingdom");
				country_ar.put("FR", "France");
				country_ar.put("ES", "Spain");
				country_ar.put("IT", "Italy");
				country_ar.put("NL", "Netherlands");
				country_ar.put("BE", "Belgium");	
				@SuppressWarnings("rawtypes")
				Iterator entries = country_ar.entrySet().iterator();
				while (entries.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> thisEntry  = (Entry<String, String>) entries.next();
					chart_country_options.add(thisEntry.getValue()); 					
				}
				Collections.sort(chart_country_options);
				chart_country.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options));
				chart_country.setSelection(chart_country_options.indexOf(MainActivity.getChartscountry()));
				
				chart_limit_txt.setText("Limit: ");
				chart_limit_options.add("10");
				chart_limit_options.add("20");
				chart_limit_options.add("30");
				chart_limit_options.add("50");
				chart_limit_options.add("100");
				chart_limit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options));
				chart_limit.setSelection(chart_limit_options.indexOf(MainActivity.getChartslimit()));
				
				chart_timeunit_txt.setText("Time Unit: ");
				chart_timeunit_options.add("Daily");
				chart_timeunit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options));
				chart_timeunit.setSelection(chart_timeunit_options.indexOf("Daily"));
				
				chart_timeinterval_txt.setText("Time interval: ");				
				chart_timeinterval_options.add("lastest");
				chart_timeinterval.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options));
				chart_timeinterval.setSelection(chart_timeinterval_options.indexOf("lastest"));
				
				listview.addHeaderView(header,null,false);
				MainActivity.getChartsListAdapter().notifyDataSetChanged();
				
				chart_submit.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						// re-send billboard request
						MainActivity.setChartsname("NAPSTER");
						MainActivity.setChartstype(chart_type.getSelectedItem().toString());
						MainActivity.setChartslimit(chart_limit.getSelectedItem().toString());
						MainActivity.setChartscountry(chart_country.getSelectedItem().toString());
						MainActivity.setChartstimeunit("Daily");
						MainActivity.setChartstimeinterval("lastest");
						String map_key_cc = "";
						for (Entry<String, String> entry : country_ar.entrySet()){
							if(entry.getValue().equals(chart_country.getSelectedItem().toString())){
								map_key_cc = entry.getKey();
								break;
							}							
						}
						String rq = "";
						rq = "http://api.rhapsody.com/v1/";
						if(MainActivity.getChartstype().equals("Top Songs")){
							rq+="tracks";						
						}else{
							rq+="albums";
						}
						rq+="/top?apikey=ZmRkNjg4ODMtMjgyZi00Nzc2LTlmMjQtZDdkM2RmYTExNTEx&limit=";
						rq+=MainActivity.getChartslimit()+"&catalog="+map_key_cc;
						JsonArrayRequest getRequest = new JsonArrayRequest(rq, 
								new Response.Listener<JSONArray>() {
									@Override
									public void onResponse(JSONArray response) {
										MainActivity.setChartsjsonarr(response);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		break;
		
		case "LASTFM":
			try {
				items = MainActivity.getChartsjson().getJSONObject("toptracks").getJSONArray("track");
				for(int i = 0; i<items.length();i++){
					item = items.getJSONObject(i);
					track_tmp = new single_track();
					track_tmp.setTitle(item.getString("name"));
					track_tmp.setArtist(item.getJSONObject("artist").getString("name"));					
					if(!item.isNull("image")){
						track_tmp.setThumbnailUrl(item.getJSONArray("image").getJSONObject(0).getString("#text"));
					}
					String dur = "";
					if(!item.getString("duration").equals("")){
						dur = String.format("%.2f",(float) (Integer.parseInt(item.getString("duration"))/60.0f));
					}
					track_tmp.setInfo(
							"Rank: "+item.getJSONObject("@attr").getString("rank")+"\t"+
						    "Listeners: "+ item.getString("listeners")+"\t"+
						    
							"duration: " + dur
							);
					
					MainActivity.addChartsListItems(track_tmp);
				}
				
				//config search header
				chart_thumbnail.setImageResource(R.drawable.lastfm_store);
				chart_name_txt.setText("Charts provider: Lastfm radio Charts");
				
				chart_type_txt.setText("Charts type: ");
				chart_type_options.add("Top Songs");					
				chart_type.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_type_options));
				chart_type.setSelection(chart_type_options.indexOf(MainActivity.getChartstype()));
				
				chart_country_txt.setText("Country: ");	
				final Map<String, String> country_ar = new HashMap<>();
				country_ar.put("United%20States", "United States");
				country_ar.put("Germany", "Germany");			
				country_ar.put("United%20Kingdom", "United Kingdom");
				country_ar.put("France", "France");
				country_ar.put("Spain", "Spain");
				country_ar.put("Italy", "Italy");
				country_ar.put("Netherlands", "Netherlands");
				country_ar.put("Belgium", "Belgium");
				country_ar.put("Viet%20nam", "Viet Nam");
				@SuppressWarnings("rawtypes")
				Iterator entries = country_ar.entrySet().iterator();
				while (entries.hasNext()) {
					@SuppressWarnings("unchecked")
					Entry<String, String> thisEntry  = (Entry<String, String>) entries.next();
					chart_country_options.add(thisEntry.getValue()); 					
				}
				Collections.sort(chart_country_options);
				chart_country.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_country_options));
				chart_country.setSelection(chart_country_options.indexOf(MainActivity.getChartscountry()));
				
				chart_limit_txt.setText("Limit: ");
				chart_limit_options.add("10");
				chart_limit_options.add("20");
				chart_limit_options.add("30");
				chart_limit_options.add("50");
				chart_limit_options.add("100");
				chart_limit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_limit_options));
				chart_limit.setSelection(chart_limit_options.indexOf(MainActivity.getChartslimit()));
				
				chart_timeunit_txt.setText("Time Unit: ");
				chart_timeunit_options.add("Daily");
				chart_timeunit.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeunit_options));
				chart_timeunit.setSelection(chart_timeunit_options.indexOf("Daily"));
				
				chart_timeinterval_txt.setText("Time interval: ");				
				chart_timeinterval_options.add("lastest");
				chart_timeinterval.setAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,chart_timeinterval_options));
				chart_timeinterval.setSelection(chart_timeinterval_options.indexOf("lastest"));
				
				listview.addHeaderView(header,null,false);
				MainActivity.getChartsListAdapter().notifyDataSetChanged();
				
				chart_submit.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						// re-send billboard request
						MainActivity.setChartsname("LASTFM");
						MainActivity.setChartstype(chart_type.getSelectedItem().toString());
						MainActivity.setChartslimit(chart_limit.getSelectedItem().toString());
						MainActivity.setChartscountry(chart_country.getSelectedItem().toString());
						MainActivity.setChartstimeunit("Daily");
						MainActivity.setChartstimeinterval("lastest");
						String map_key_cc = "";
						for (Entry<String, String> entry : country_ar.entrySet()){
							if(entry.getValue().equals(chart_country.getSelectedItem().toString())){
								map_key_cc = entry.getKey();
								break;
							}							
						}
						String rq = "";
						rq = "http://ws.audioscrobbler.com/2.0/?method=geo.getTopTracks&country="+map_key_cc+"&api_key=d6ef461c9610dc5d695efce1bbab90c1&format=json&limit="+MainActivity.getChartslimit();
						JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, rq, null,
							    new Response.Listener<JSONObject>() 
							    {
							        @Override
							        public void onResponse(JSONObject response) {   
							        	MainActivity.setChartsjson(response);         
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
						// CHART ONITEMSCLICK
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
					
			}
			
		});
		
		return rootView;
	}
}
