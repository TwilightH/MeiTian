package com.meitian.model;

public class City {
	private String cityName;
	private String cityData;
	
	public City(String cityName,String cityData){
		this.cityName=cityName;
		this.setCityData(cityData);
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityData() {
		return cityData;
	}
	public void setCityData(String cityData) {
		this.cityData = cityData;
	}

}
