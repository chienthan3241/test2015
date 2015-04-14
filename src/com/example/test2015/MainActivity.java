package com.example.test2015;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.test2015.adapter.ChartGridAdapter;
import com.example.test2015.adapter.SpotifyTitleSearchAdapter;
import com.example.test2015.adapter.TabsPagerAdapter;
import com.example.test2015.adapter.YoutubeAutoCompleteAdapter;
import com.example.test2015.adapter.searchTrackList;
import com.example.test2015.libs.MyHttpsUrlStack;
import com.example.test2015.libs.MyHurlStack;

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
	public static  RequestQueue queue = null;
	public static RequestQueue getRequestQueue(){
		return queue;
	}
	public  RequestQueue httpsqueue = null;
	public  JSONObject searchjson = null;
	public  String search_format = "";
	public  String search_title_txt = "";
	public  String search_artist_txt = "";
	public  List<single_track> SearchListItems = new ArrayList<single_track>();
	public  List<single_track> SearchAlbumListItems = new ArrayList<single_track>();
	public  JSONObject searchalbumjson = null;
	public  String search_album_format = "";
	public  String search_album_title_txt = "";
	public  String search_album_artist_txt = "";
	public  searchTrackList SearchListAdapter;
	public  searchTrackList SearchAlbumListAdapter;
	public  List<single_track> YoutubeListItems = new ArrayList<single_track>();
	public  JSONObject searchyoutubejson = null;
	public  String search_youtube_format = "";
	public  String search_youtube_title_txt = "";
	public  String search_youtube_artist_txt = "";
	public  searchTrackList SearchYoutubeListAdapter;
	public static  String currentaction ="";
	public static String getCurrentaction(){
		return currentaction;
	}
	public  List<single_track> ChartsListItems = new ArrayList<single_track>();
	public  searchTrackList ChartsListAdapter;
	public ChartGridAdapter ChartsGridAdapter;
	public  JSONObject Chartsjson = null;
	public  JSONArray Chartsjsonarr = null;
	public  String Chartsxml = null;
	public static String Chartsname ="";
	public  static String Chartstype ="";
	public  String Chartslimit="";
	public  String Chartscountry ="";
	public  String Chartstimeunit = "";
	public  String Chartstimeinterval ="";
	public ActionBar actionBar=null;
	public static YoutubeAutoCompleteAdapter youtubeAutocompleteadapter = null;

	public static SpotifyTitleSearchAdapter SpotAutoCompleteTitleAdapter = null;
	public static SpotifyTitleSearchAdapter SpotAutoCompleteArtistAdapter = null;
	public static Boolean search_single_radio = true;
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
	private static ViewPager mViewPager;
	public static ViewPager getmViewPager() {
		return mViewPager;
	}
	private TabsPagerAdapter mAdapter;
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
	    SearchAlbumListAdapter = new searchTrackList(this, SearchAlbumListItems);
	    SearchYoutubeListAdapter = new searchTrackList(this, YoutubeListItems);
	    ChartsListAdapter = new searchTrackList(this, ChartsListItems);
	    youtubeAutocompleteadapter = new YoutubeAutoCompleteAdapter(this);
	    SpotAutoCompleteTitleAdapter = new SpotifyTitleSearchAdapter(this, "title");
	    SpotAutoCompleteArtistAdapter = new SpotifyTitleSearchAdapter(this, "artist");
	    ChartsGridAdapter = new ChartGridAdapter(this, ChartsListItems);
	    
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		actionBar = getActionBar();
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
		actionBar.addTab(actionBar.newTab().setText("  Charts").setIcon(R.drawable.charts_tab_icon).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("  Search").setIcon(R.drawable.search_tab_icon).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("  Play List(building..)").setIcon(R.drawable.playlist_tab_icon).setTabListener(this));      
        
	}
	
	@Override
	public void onBackPressed() {
		if(currentaction.equals("CHART_LIST") || currentaction.equals("ALBUM_LIST") || currentaction.equals("TRACK_LIST") ){
			currentaction = "";
			actionBar.show();
		}else if(currentaction.equals("YOUTUBE_LIST")){
			currentaction="CHART_LIST";
		}else if(currentaction.equals("YOUTUBE_LIST_A")){
			currentaction="TRACK_LIST_A";
		}else if(currentaction.equals("YOUTUBE_LIST_T")){
			currentaction="TRACK_LIST";
		}else if(currentaction.equals("TRACK_LIST_A")){
			currentaction="ALBUM_LIST";
		}		
		super.onBackPressed();		
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
	
	public static void setproxy(){
		Authenticator authenticator = new Authenticator() {
	        public PasswordAuthentication getPasswordAuthentication() {
	            return (new PasswordAuthentication("manh-cuong.tran",
	                    "Password2013".toCharArray()));
	        }
	    };
		Authenticator.setDefault(authenticator);
		Properties systemProperties = System.getProperties();
		//systemProperties.setProperty("https.proxySet", "true");
		systemProperties.setProperty("http.proxyHost","10.149.18.180");
		systemProperties.setProperty("http.proxyPort","8080");
		systemProperties.setProperty("https.proxyHost","10.149.18.180");
		systemProperties.setProperty("https.proxyPort","8080");
	}

}
