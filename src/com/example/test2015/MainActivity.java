package com.example.test2015;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	/**
	 * all variables declare from here
	 */
	private static RequestQueue queue = null;
	public static RequestQueue getQueue(){
		return queue;
	}
	//------------------
	private static RequestQueue httpsqueue = null;
	public static RequestQueue gethttpsQueue(){
		return httpsqueue;
	}
	//------------------
	private static JSONObject searchjson = null;
	public static JSONObject getSearchjson(){
		return searchjson;
	}
	public static void setSearchjson(JSONObject obj){
		searchjson = obj;
	}
	//------------------
	private static String search_format = "";
	public static String getSearchformat(){
		return search_format;
	}
	public static void setSearchformat(String obj){
		search_format = obj;
	}
	//------------------
	private static String search_title_txt = "";
	public static String getSearch_title_txt(){
		return search_title_txt;
	}
	public static void setSearch_title_txt(String obj){
		search_title_txt = obj;
	}
	//------------------
	private static String search_artist_txt = "";
	public static String getSearch_artist_txt(){
		return search_artist_txt;
	}
	public static void setSearch_artist_txt(String obj){
		search_artist_txt = obj;
	}
	//------------------
	private static List<single_track> SearchListItems = new ArrayList<single_track>();
	public static List<single_track> getSearchListItem() {
		return SearchListItems;
	}
	public static void addSearchItem(single_track item){
		SearchListItems.add(item);
	}
	public static void clearSearchList (){
		SearchListItems.clear();
	}
	//------------------
	public static searchTrackList SearchListAdapter;
	public static searchTrackList getSearchListAdapter() {
		return SearchListAdapter;
	}
	//------------------
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private TabsPagerAdapter mAdapter;
	private String[] tabs = { "Chart", "Search", "Tab 3" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//check internet connect status      
        if(!isOnline()){
        	showAlert("internet connection","you muss have internet connection to access streaming Music!! \n try to connect with WLAN or 3G or LTE or something like that!!",true);
        }
		/**
		 * init variables
		 */		
		//init Volley http Request Queue
	    queue = Volley.newRequestQueue(this, new MyHurlStack());    
	    //init Volley https Request Queue
	    httpsqueue = Volley.newRequestQueue(this, new MyHttpsUrlStack());
	    
	    //init Search_list
	    SearchListAdapter=new searchTrackList(this, SearchListItems); 
	    
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		final ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });
		
		// Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }         
        
	}
	
	
	public boolean isOnline(){
    	ConnectivityManager cm =
    	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    	    return netInfo != null && netInfo.isConnectedOrConnecting();
    }
	
	public void showAlert(String title, String message, boolean quitwhenclick){
    	final AlertDialog arlertDialog = new AlertDialog.Builder(this).create();
        arlertDialog.setTitle(title);
        arlertDialog.setMessage(message);
        if(quitwhenclick){
	        arlertDialog.setButton("OK", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
		            System.exit(0);
				}
			});
        } else {
        	arlertDialog.setButton("OK", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					arlertDialog.cancel();
				}
			});
        }
        arlertDialog.show();
    }
	
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}	

}
