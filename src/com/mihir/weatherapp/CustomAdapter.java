package com.mihir.weatherapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter   implements OnClickListener 
{
    
	/*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ListModel tempValues=null;
    int i=0;
    
    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(Activity a, ArrayList d,Resources resLocal) {
    	
    	/********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;
        
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() 
    {
    	
    	if(data.size()<=0)
    		return 1;
        return data.size();
    }

    public Object getItem(int position) 
    {
        return position;
    }

    public long getItemId(int position) 
    {
        return position;
    }
    
    /********* Create a holder to contain inflated xml file elements ***********/
    public static class ViewHolder
    {
    	public TextView date;
    	public TextView weatherattr;
        public TextView tempmin;
        public TextView tempmax;
        public TextView textWide;
        public TextView windspeed;
        public ImageView image;
    }

    /*********** Depends upon data size called for each row , Create each ListView row ***********/
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        View vi=convertView;
        ViewHolder holder;
        
        if(convertView==null)
        { 
        	
        	/********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflater.inflate(R.layout.row_grid, null); 
            
            /******** View Holder Object to contain tabitem.xml file elements ************/
            holder=new ViewHolder();
            holder.date = (TextView) vi.findViewById(R.id.date_text);
            holder.windspeed = (TextView) vi.findViewById(R.id.windspeed_attr);
            holder.weatherattr=(TextView) vi.findViewById(R.id.weather_attr);
            holder.tempmin=(TextView)vi.findViewById(R.id.tempmin_attr);
            holder.tempmax=(TextView)vi.findViewById(R.id.tempmax_attr);
            holder.image=(ImageView)vi.findViewById(R.id.item_image);
            
           /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        }
        else  
            holder=(ViewHolder)vi.getTag();
        
        if(data.size()<=0)
        {
        	holder.tempmin.setText("No Data");
            
        }
        else
        {
        	/***** Get each Model object from Arraylist ********/
	        tempValues=null;
	        tempValues = (ListModel) data.get(position);
	        
	   
	        /************  Set Model values in Holder elements ***********/
	         holder.weatherattr.setText(tempValues.getWeatherLabel());
	         holder.date.setText(tempValues.getDate());
	         holder.windspeed.setText(tempValues.getWindSpeed());
	         holder.tempmax.setText(tempValues.getMaxTemp());
	         holder.tempmin.setText(tempValues.getMinTemp());
	         holder.image.setImageResource(res.getIdentifier("com.mihir.weatherapp:drawable/"+tempValues.getImage(),null,null));
	         /******** Set Item Click Listner for LayoutInflater for each row ***********/
	         vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }
    
    @Override
    public void onClick(View v) 
    {
            Log.v("CustomAdapter", "=====Row button clicked");
    }
    
    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener
    	{           
        private int mPosition;
        
        OnItemClickListener(int position)
        {
        	 mPosition = position;
        }
        
        @Override
        public void onClick(View arg0) 
        {
        	ForecastActivity sct = (ForecastActivity)activity;
        	sct.onItemClick(mPosition);
        }               
    }   
}