package com.mihir.weatherapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.preference.PreferenceManager;

public class MainActivity extends ActionBarActivity {

	String CityName;
	private static String URL = "http://api.openweathermap.org/data/2.5/forecast/daily";
	private ProgressDialog simpleWaitDialog;
	SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Button enter = (Button) findViewById(R.id.button1);
		
		
		
		
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				TextView input = (TextView) findViewById(R.id.editText1);
				if(input.getText().toString().equals("")){
					Toast.makeText(MainActivity.this, "Please Enter Name of the City!", Toast.LENGTH_LONG).show();
				}
				else{
					if(isNetworkAvailable()){
					DownloadData dd = new DownloadData();
					dd.execute(input.getText().toString());
					}else{
						Toast.makeText(MainActivity.this, "Internet Connection Unavailable!", Toast.LENGTH_LONG).show();
					}
					
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private class DownloadData extends AsyncTask<String, String, Boolean> 
    {

		@Override
		protected Boolean doInBackground(String... arg0) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			Log.v("PassParam", arg0[0].toString());
            JSONParser jsonparser = new JSONParser();
            String url_compat = arg0[0].replace(" ", "%20");
			
            JSONObject json = jsonparser.makeHttpRequest(URL+"?"+"q="+url_compat+"&mode=json&units=metric&cnt=5&APPID=14c8cd5a164a987bba1586484fac082a", "POST", params);
            try {
            	if(json.getString("cod")=="404"){
            		return false;
            	}else{
				Log.v("CITY", json.getJSONObject("city").getString("name").toString());
				Log.v("ArrayLength",String.valueOf(json.getJSONArray("list").length()));
				SaveString("City", json.getJSONObject("city").getString("name").toString());
				for(int x=0;x<5;x++){
					SaveString("Date"+x,json.getJSONArray("list").getJSONObject(x).getString("dt"));
					SaveString("TempDay"+x,json.getJSONArray("list").getJSONObject(x).getJSONObject("temp").getString("day"));
					SaveString("TempMin"+x,json.getJSONArray("list").getJSONObject(x).getJSONObject("temp").getString("min"));
					SaveString("TempMax"+x,json.getJSONArray("list").getJSONObject(x).getJSONObject("temp").getString("max"));
					SaveString("TempNight"+x,json.getJSONArray("list").getJSONObject(x).getJSONObject("temp").getString("night"));
					SaveString("TempEve"+x,json.getJSONArray("list").getJSONObject(x).getJSONObject("temp").getString("eve"));
					SaveString("TempMorn"+x,json.getJSONArray("list").getJSONObject(x).getJSONObject("temp").getString("morn"));
					SaveString("Desc"+x,json.getJSONArray("list").getJSONObject(x).getJSONArray("weather").getJSONObject(0).getString("description"));
					SaveString("WindSpeed"+x,json.getJSONArray("list").getJSONObject(x).getString("speed"));
					
					}
				Intent i = new Intent(MainActivity.this, ForecastActivity.class);
				finish();
				startActivity(i); 
				
            	return true;
            	}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
          
            
//            Log.v("JSON", json.toString());
			
			
		}
		
		 @Override
         protected void onPreExecute() 
         {
             simpleWaitDialog = ProgressDialog.show(MainActivity.this,
                     "Please Wait", "Loading...");
          }

         protected void onPostExecute(JSONObject json) 
         {
        	  
         	simpleWaitDialog.dismiss();
         	
         }
		
		
    }

	public void SaveString(String key, String value){
	       sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	       SharedPreferences.Editor editor = sharedPreferences.edit();
	       editor.putString(key, value);
	       Log.v(key, value);
	       editor.commit();
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
