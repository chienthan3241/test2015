package com.example.test2015;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class YoutubeListContentFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_search_content, container, false);
		ListView listview = (ListView) rootView.findViewById(R.id.listsearch);		
		JSONArray items = null;
		JSONObject item = null;
		MainActivity.clearYoutubeListItems();
		try {
			items = MainActivity.getsearchyoutubejson().getJSONObject("feed").getJSONArray("entry");
			for(int i = 0; i<items.length();i++){
				item = items.getJSONObject(i);
				single_track track_tmp = new single_track();
				track_tmp.setId(item.getJSONObject("media$group").getJSONObject("yt$videoid").getString("$t"));
				track_tmp.setTitle(item.getJSONObject("title").getString("$t"));
				track_tmp.setArtist("");
				track_tmp.setThumbnailUrl(item.getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(0).getString("url"));
				track_tmp.setInfo("Uploader: "+item.getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t")+
								"\t duration: "+ String.format("%.2f", (float)(Integer.parseInt(item.getJSONObject("media$group").getJSONObject("yt$duration").getString("seconds"))/60.0f))+" min.");
				MainActivity.addYoutubeListItems(track_tmp);
			}
			
			//config search header
			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.simple_header, listview, false);	
			TextView headertitle = (TextView) header.findViewById(R.id.search_title);
			TextView headerartist = (TextView) header.findViewById(R.id.search_artist);
			TextView headertype = (TextView) header.findViewById(R.id.search_type);	
			TextView headertxt = (TextView) header.findViewById(R.id.header_txt);
			headertxt.setText("Youtube Results:");
			headertitle.setText("Youtube's search string: "+MainActivity.getsearch_youtube_title_txt().trim().replace(" ", "+")+"+"+MainActivity.getsearch_youtube_artist_txt().trim().replace(" ", "+"));
			headerartist.setVisibility(View.GONE);
			headertype.setText(MainActivity.getsearch_youtube_format());
			
			listview.addHeaderView(header,null,false);
			MainActivity.getSearchYoutubeListAdapter().notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listview.setAdapter(MainActivity.getSearchYoutubeListAdapter());
		
		/**
		 * listview items onclick listener
		 */
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				String yid = MainActivity.getYoutubeListItems().get(position-1).getId();
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
