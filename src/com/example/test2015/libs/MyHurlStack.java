package com.example.test2015.libs;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.toolbox.HurlStack;
import com.example.test2015.MainActivity;

public class MyHurlStack extends HurlStack{

	@Override
	protected HttpURLConnection createConnection(URL url) throws IOException{		
	
		MainActivity.setproxy();
		HttpURLConnection returnThis = (HttpURLConnection) url.openConnection();
		return returnThis;
		
	}
}
