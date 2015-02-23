package com.example.test2015;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import com.android.volley.toolbox.HurlStack;

public class MyHttpsUrlStack extends HurlStack{
	@Override
	protected HttpURLConnection createConnection(URL url) throws IOException{
		
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
		HttpsURLConnection returnThis = (HttpsURLConnection) url.openConnection();
		return returnThis;
		
	}

}
