package com.example.test2015;

import org.json.JSONObject;

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
import android.widget.ImageButton;

public class ChartFragment extends Fragment{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_charts, container, false);
        ImageButton billboardbtn = (ImageButton) rootView.findViewById(R.id.imagebillboard);         
        ImageButton imageitunesbtn = (ImageButton) rootView.findViewById(R.id.imageitunes);
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
        
        return rootView;
    }
}
