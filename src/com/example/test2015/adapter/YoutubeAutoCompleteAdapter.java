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

import com.example.test2015.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class YoutubeAutoCompleteAdapter extends BaseAdapter implements Filterable{
    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<String> resultList = new ArrayList<String>();
    
    public YoutubeAutoCompleteAdapter(Context context){
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
					List<String> items = getsuggest(mContext, constraint.toString());
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
	
	@SuppressLint("UseValueOf")
	private List<String> getsuggest(Context context, String str) {
		final List<String> rs = new ArrayList<String>();
		String rq = "";
		StringBuilder jsonResults = new StringBuilder();
		rq = "http://suggestqueries.google.com/complete/search?output=firefox&ds=yt&q="+str+"&format="+new Integer(MAX_RESULTS).toString();
		
		//synchronous request:
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
			JSONArray obj = new JSONArray(jsonResults.toString()).getJSONArray(1);
			for(int i = 0; i < obj.length(); i++){
				rs.add(obj.getString(i));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
		
	}

}
