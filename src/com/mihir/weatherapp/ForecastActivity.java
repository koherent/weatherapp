package com.mihir.weatherapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

public class ForecastActivity extends ActionBarActivity {

	public  ForecastActivity CustomListView = null;
	public  ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
	GridView list;
	CustomAdapter adapter;
	SharedPreferences sharedPreferences;
	String savedValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast);
		
		setListdata();
		CustomListView = this;
		
		Resources res =getResources(); 
        list=(GridView)findViewById(R.id.gridView1);
        
        if(!isTablet(ForecastActivity.this)){
        list.setNumColumns(2);
        }
        adapter=new CustomAdapter(CustomListView, CustomListViewValuesArr,res);
        list.setAdapter(adapter);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forecast, menu);
		return true;
	}

	public void onItemClick(int mPosition) {
		// TODO Auto-generated method stub
		
	}

	
	public void setListdata(){
		for(int m=0;m<=4; m++){
			final ListModel sched = new ListModel();
	      /******* Firstly take data in model object ******/
			long time = Integer.parseInt(LoadString("Date"+m)) * (long) 1000;
		    Date date = new Date(time);
		    SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy");
		    format.setTimeZone(TimeZone.getTimeZone("IST"));
		    Log.d("date", format.format(date));
			
		 sched.setDate(format.format(date).toString());
	     sched.setMaxTemp(LoadString("TempMax"+m)+"\u2103");
	     sched.setMinTemp(LoadString("TempMin"+m)+"\u2103");
	     sched.setWindSpeed(LoadString("WindSpeed"+m)+"KMPH");
	     sched.setWeatherLabel(LoadString("Desc"+m));
	     if(LoadString("Desc"+m).equals("light rain"))
	     { 
	    	 sched.setImage("lightrain");
	     }else{
	    	 sched.setImage("clearsky");
	     }
		/******** Take Model Object in ArrayList **********/
		   CustomListViewValuesArr.add(sched);

		}
	}

	
	public String LoadString(String key){
	       sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	       savedValue = sharedPreferences.getString(key,"");
	       Log.v("SavedVal", savedValue);
	       return savedValue;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(ForecastActivity.this, MainActivity.class);
		finish();
		startActivity(i); 
	}
	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
}
