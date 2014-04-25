package com.mihir.weatherapp;


public class ListModel 
{
	private  String WindSpeed="";
	private  String Date="";
	private  String WeatherLabel="";
	private  String MinTemp="";
	private  String MaxTemp="";
	private  String Image; 
	
	/*********** Set Methods ******************/
	
	public void setDate(String Date)
	{
		this.Date = Date;
	}
	
	public void setWindSpeed(String WindSpeed)
	{
		this.WindSpeed = WindSpeed;
	}
	
	public void setWeatherLabel(String WeatherLabel)
	{
		this.WeatherLabel = WeatherLabel;
	}
	
	public void setMinTemp(String MinTemp)
	{
		this.MinTemp = MinTemp;
	}
	
	public void setMaxTemp(String MaxTemp)
	{
		this.MaxTemp = MaxTemp;
	}
	
	
	public void setImage(String Image)
	{
		this.Image = Image;
	}
	
	
	/*********** Get Methods ****************/
	public String getWeatherLabel()
	{
		return this.WeatherLabel;
	}
	
	public String getWindSpeed()
	{
		return this.WindSpeed;
	}
	
	public String getMinTemp()
	{
		return this.MinTemp;
	}
	
	public String getMaxTemp()
	{
		return this.MaxTemp;
	}
	
	public String getDate()
	{
		return this.Date;
	}
	
	public String getImage()
	{
		return this.Image;
	}

}
