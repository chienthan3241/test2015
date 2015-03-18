package com.example.test2015.adapter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.test2015.MainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


public class SpotifyTitleSearchAdapter extends BaseAdapter implements Filterable{

	private static final String MAX_RESULTS = "10";
    private Context mContext;
    private List<String> resultList = new ArrayList<String>();
    private String field = "";
    
    public SpotifyTitleSearchAdapter(Context context, String search_field){
    	field = search_field;
    	mContext = context;
    }    

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return resultList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);			 
		}
		((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).toString());
		
		return convertView;
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter(){

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if(constraint!=null){
					List<String> items = spotgetsuggest(mContext, constraint.toString());
					filterResults.values = items;
					filterResults.count = items.size();
				}
				return filterResults;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if(results!=null && results.count > 0 ){
					resultList = (List<String>) results.values;
					notifyDataSetChanged();
				}else{
					notifyDataSetInvalidated();
				}
				
			}
			
		};
		return filter;
	}
	
	
	private List<String> spotgetsuggest(Context context, String str){
		final List<String> rs = new ArrayList<String>();
		String rq = "";
		StringBuilder jsonResults = new StringBuilder();
		rq = "https://api.spotify.com/v1/search?q=";
		if(field.equals("artist")){
			rq+= str.trim().replace(" ", "+")+"*&type=artist&limit="+MAX_RESULTS;
		}else{
			if(MainActivity.search_single_radio){
				rq+= str.trim().replace(" ", "+")+"*&type=track&limit="+MAX_RESULTS;
			}else{
				rq+= str.trim().replace(" ", "+")+"*&type=album&limit="+MAX_RESULTS;
			}
		}
		
		MainActivity.setproxy();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL (rq).openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn!= null){
				conn.disconnect();
			}
		}
		try {
			JSONObject obj = new JSONObject(jsonResults.toString());
			JSONArray items = new JSONArray();
			if(field.equals("artist")){
				items = obj.getJSONObject("artists").getJSONArray("items");
			}else{
				if(MainActivity.search_single_radio){
					items = obj.getJSONObject("tracks").getJSONArray("items");
				}else{
					items = obj.getJSONObject("albums").getJSONArray("items");
				}
			}
			for(int i = 0; i < items.length(); i++){
				rs.add(items.getJSONObject(i).getString("name"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}

}
