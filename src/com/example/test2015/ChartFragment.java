package com.example.test2015;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
							Log.v("kfpe",response);								
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
        
        return rootView;
    }
}
