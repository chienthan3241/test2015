package com.example.test2015.libs;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.android.volley.toolbox.HurlStack;
import com.example.test2015.MainActivity;

public class MyHttpsUrlStack extends HurlStack{
	@Override
	protected HttpURLConnection createConnection(URL url) throws IOException{
		
		MainActivity.setproxy();
		HttpsURLConnection returnThis = (HttpsURLConnection) url.openConnection();
		return returnThis;
		
	}

}
