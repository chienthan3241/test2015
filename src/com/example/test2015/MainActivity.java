package com.example.test2015;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
	private static List<single_track> SearchAlbumListItems = new ArrayList<single_track>();
	public static List<single_track> getSearchAlbumListItems() {
		return SearchAlbumListItems;
	}
	public static void addSearchAlbumListItems(single_track item){
		SearchAlbumListItems.add(item);
	}
	public static void clearSearchAlbumListItems (){
		SearchAlbumListItems.clear();
	}	
	//------------------
	private static JSONObject searchalbumjson = null;
	public static JSONObject getsearchalbumjson(){
		return searchalbumjson;
	}
	public static void setsearchalbumjson(JSONObject obj){
		searchalbumjson = obj;
	}
	//------------------
	private static String search_album_format = "";
	public static String getSearchAlbumformat(){
		return search_album_format;
	}
	public static void setSearchAlbumformat(String obj){
		search_album_format = obj;
	}
	//------------------
	private static String search_album_title_txt = "";
	public static String getSearch_album_title_txt(){
		return search_album_title_txt;
	}
	public static void setSearch_album_title_txt(String obj){
		search_album_title_txt = obj;
	}
	//------------------
	private static String search_album_artist_txt = "";
	public static String getSearch_album_artist_txt(){
		return search_album_artist_txt;
	}
	public static void setSearch_album_artist_txt(String obj){
		search_album_artist_txt = obj;
	}
	//------------------
	public static searchTrackList SearchListAdapter;
	public static searchTrackList getSearchListAdapter() {
		return SearchListAdapter;
	}
	//------------------
	public static searchTrackList SearchAlbumListAdapter;
	public static searchTrackList getSearchAlbumListAdapter() {
		return SearchAlbumListAdapter;
	}
	//------------------
	private static List<single_track> YoutubeListItems = new ArrayList<single_track>();
	public static List<single_track> getYoutubeListItems() {
		return YoutubeListItems;
	}
	public static void addYoutubeListItems(single_track item){
		YoutubeListItems.add(item);
	}
	public static void clearYoutubeListItems (){
		YoutubeListItems.clear();
	}
	//------------------
	private static JSONObject searchyoutubejson = null;
	public static JSONObject getsearchyoutubejson(){
		return searchyoutubejson;
	}
	public static void setsearchyoutubejson(JSONObject obj){
		searchyoutubejson = obj;
	}
	//------------------
	private static String search_youtube_format = "";
	public static String getsearch_youtube_format(){
		return search_youtube_format;
	}
	public static void setsearch_youtube_format(String obj){
		search_youtube_format = obj;
	}
	//------------------
	private static String search_youtube_title_txt = "";
	public static String getsearch_youtube_title_txt(){
		return search_youtube_title_txt;
	}
	public static void setsearch_youtube_title_txt(String obj){
		search_youtube_title_txt = obj;
	}
	//------------------
	private static String search_youtube_artist_txt = "";
	public static String getsearch_youtube_artist_txt(){
		return search_youtube_artist_txt;
	}
	public static void setsearch_youtube_artist_txt(String obj){
		search_youtube_artist_txt = obj;
	}
	//------------------
	public static searchTrackList SearchYoutubeListAdapter;
	public static searchTrackList getSearchYoutubeListAdapter() {
		return SearchYoutubeListAdapter;
	}
	//------------------
	public static String currentaction ="";
	public static void setCurrentaction(String obj){
		currentaction = obj;
	}
	public static String getCurrentaction(){
		return currentaction;
	}
	//------------------	
	private static List<single_track> ChartsListItems = new ArrayList<single_track>();
	public static List<single_track> getChartsListItems() {
		return ChartsListItems;
	}
	public static void addChartsListItems(single_track item){
		ChartsListItems.add(item);
	}
	public static void clearChartsListItems (){
		ChartsListItems.clear();
	}
	//------------------
	public static searchTrackList ChartsListAdapter;
	public static searchTrackList getChartsListAdapter() {
		return ChartsListAdapter;
	}
	//------------------
	private static JSONObject Chartsjson = null;
	public static JSONObject getChartsjson(){
		return Chartsjson;
	}
	public static void setChartsjson(JSONObject obj){
		Chartsjson = obj;
	}
	//------------------
	private static JSONArray Chartsjsonarr = null;
	public static JSONArray getChartsjsonarr(){
		return Chartsjsonarr;
	}
	public static void setChartsjsonarr(JSONArray obj){
		Chartsjsonarr = obj;
	}
	//------------------
	private static String Chartsxml = null;
	public static String getChartsxml(){
		return Chartsxml;
	}
	public static void setChartsxml(String obj){
		Chartsxml = obj;
	}
	//------------------
	private static String Chartsname ="";
	private static String Chartstype ="";
	private static String Chartslimit="";
	private static String Chartscountry ="";
	private static String Chartstimeunit = "";
	private static String Chartstimeinterval ="";
	public static void setChartsname(String obj) {
		Chartsname = obj;
	}
	public static String getChartsname(){
		return Chartsname;
	}
	public static void setChartstype(String obj){
		Chartstype = obj;
	}
	public static String getChartstype(){
		return Chartstype;		
	}
	public static void setChartslimit(String obj){
		Chartslimit = obj;
	}
	public static String getChartslimit(){
		return Chartslimit;		
	}
	public static void setChartscountry(String obj){
		Chartscountry = obj;
	}
	public static String getChartscountry(){
		return Chartscountry;		
	}
	public static void setChartstimeunit(String obj){
		Chartstimeunit = obj;
	}
	public static String getChartstimeunit(){
		return Chartstimeunit;		
	}
	public static void setChartstimeinterval(String obj){
		Chartstimeinterval = obj;
	}
	public static String getChartstimeinterval(){
		return Chartstimeinterval;		
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
		actionBar.addTab(actionBar.newTab().setText("  Charts").setIcon(R.drawable.charts_tab_icon).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("  Search").setIcon(R.drawable.search_tab_icon).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("  Play List(building..)").setIcon(R.drawable.playlist_tab_icon).setTabListener(this));      
        
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
