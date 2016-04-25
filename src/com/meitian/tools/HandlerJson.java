package com.meitian.tools;

import org.json.JSONArray;
import org.json.JSONObject;

import com.meitian.model.WeatherDes;

public class HandlerJson {
	

	public static WeatherDes handler(String str) {
		try{
			WeatherDes weatherDes=new WeatherDes();
			JSONObject jsonObject=new JSONObject(str);
			String reason=jsonObject.getString("reason");
			if(reason.equals("successed!")){
				JSONObject resultObject=jsonObject.getJSONObject("result");
				JSONObject dataObject=resultObject.getJSONObject("data");
				//解析realtime中的对象
				JSONObject realtimeObject=dataObject.getJSONObject("realtime");
				JSONObject windObject=realtimeObject.getJSONObject("wind");
				JSONObject realWeatherObject=realtimeObject.getJSONObject("weather");
				weatherDes.setRealtime_updateTime(realtimeObject.getString("time"));
				weatherDes.setRealtime_date(realtimeObject.getString("date"));
				weatherDes.setRealtime_cityName(realtimeObject.getString("city_name"));
				weatherDes.setRealtime_week(realtimeObject.getString("week"));
				weatherDes.setRealtime_moon(realtimeObject.getString("moon"));
				//解析realtime中的wind
				weatherDes.setRealtime_windSpeed(windObject.getString("windspeed"));
				weatherDes.setRealtime_direct(windObject.getString("direct"));
				weatherDes.setRealtime_power(windObject.getString("power"));
				//解析realtime中的weather
				weatherDes.setRealtime_humidity(realWeatherObject.getString("humidity"));
				weatherDes.setRealtime_img(realWeatherObject.getString("img"));
				weatherDes.setRealtime_info(realWeatherObject.getString("info"));
				weatherDes.setRealtime_temperature(realWeatherObject.getString("temperature"));
				
				//解析life中的对象
				JSONObject lifeObject=dataObject.getJSONObject("life");
				JSONObject lifeInfoObject=lifeObject.getJSONObject("info");
				weatherDes.setLife_kongtiao(lifeInfoObject.getString("kongtiao"));
				weatherDes.setLife_yundong(lifeInfoObject.getString("yundong"));
				weatherDes.setLife_ziwaixian(lifeInfoObject.getString("ziwaixian"));
				weatherDes.setLife_ganmao(lifeInfoObject.getString("ganmao"));
				weatherDes.setLife_xiche(lifeInfoObject.getString("xiche"));
				weatherDes.setLife_wuran(lifeInfoObject.getString("wuran"));
				weatherDes.setLife_chuanyi(lifeInfoObject.getString("chuanyi"));
				
				//解析weather对象
				JSONArray weaherObject=dataObject.getJSONArray("weather");
				JSONObject weatherObject1=weaherObject.getJSONObject(0);
				weatherDes.setWeather_date1(weatherObject1.getString("date"));
				JSONObject weatherInfoObject1=weatherObject1.getJSONObject("info");
				weatherDes.setWeather_night1(weatherInfoObject1.getString("night"));
				weatherDes.setWeather_day1(weatherInfoObject1.getString("day"));
				weatherDes.setWeather_week1(weatherObject1.getString("week"));
				weatherDes.setWeather_nongli1(weatherObject1.getString("nongli"));
				
				JSONObject weatherObject2=weaherObject.getJSONObject(1);
				weatherDes.setWeather_date2(weatherObject2.getString("date"));
				JSONObject weatherInfoObject2=weatherObject2.getJSONObject("info");
				weatherDes.setWeather_night2(weatherInfoObject2.getString("night"));
				weatherDes.setWeather_day2(weatherInfoObject2.getString("day"));
				weatherDes.setWeather_week2(weatherObject2.getString("week"));
				weatherDes.setWeather_nongli2(weatherObject2.getString("nongli"));
				
				JSONObject weatherObject3=weaherObject.getJSONObject(2);
				weatherDes.setWeather_date3(weatherObject3.getString("date"));
				JSONObject weatherInfoObject3=weatherObject3.getJSONObject("info");
				weatherDes.setWeather_night3(weatherInfoObject3.getString("night"));
				weatherDes.setWeather_day3(weatherInfoObject3.getString("day"));
				weatherDes.setWeather_week3(weatherObject3.getString("week"));
				weatherDes.setWeather_nongli3(weatherObject3.getString("nongli"));
				
				JSONObject weatherObject4=weaherObject.getJSONObject(3);
				weatherDes.setWeather_date4(weatherObject4.getString("date"));
				JSONObject weatherInfoObject4=weatherObject4.getJSONObject("info");
				weatherDes.setWeather_night4(weatherInfoObject4.getString("night"));
				weatherDes.setWeather_day4(weatherInfoObject4.getString("day"));
				weatherDes.setWeather_week4(weatherObject4.getString("week"));
				weatherDes.setWeather_nongli4(weatherObject4.getString("nongli"));
				
				JSONObject weatherObject5=weaherObject.getJSONObject(4);
				weatherDes.setWeather_date5(weatherObject5.getString("date"));
				JSONObject weatherInfoObject5=weatherObject5.getJSONObject("info");
				weatherDes.setWeather_night5(weatherInfoObject5.getString("night"));
				weatherDes.setWeather_day5(weatherInfoObject5.getString("day"));
				weatherDes.setWeather_week5(weatherObject5.getString("week"));
				weatherDes.setWeather_nongli5(weatherObject5.getString("nongli"));
				
				JSONObject weatherObject6=weaherObject.getJSONObject(5);
				weatherDes.setWeather_date6(weatherObject6.getString("date"));
				JSONObject weatherInfoObject6=weatherObject6.getJSONObject("info");
				weatherDes.setWeather_night6(weatherInfoObject6.getString("night"));
				weatherDes.setWeather_day6(weatherInfoObject6.getString("day"));
				weatherDes.setWeather_week6(weatherObject6.getString("week"));
				weatherDes.setWeather_nongli6(weatherObject6.getString("nongli"));
				
				JSONObject weatherObject7=weaherObject.getJSONObject(6);
				weatherDes.setWeather_date7(weatherObject7.getString("date"));
				JSONObject weatherInfoObject7=weatherObject7.getJSONObject("info");
				weatherDes.setWeather_night7(weatherInfoObject7.getString("night"));
				weatherDes.setWeather_day7(weatherInfoObject7.getString("day"));
				weatherDes.setWeather_week7(weatherObject7.getString("week"));
				weatherDes.setWeather_nongli7(weatherObject7.getString("nongli"));
				
				//解析pm25
				JSONObject pmObject=dataObject.getJSONObject("pm25");
				JSONObject pm25Object=pmObject.getJSONObject("pm25");
				weatherDes.setPm_pm25(pm25Object.getString("pm25"));
				weatherDes.setPm_pm10(pm25Object.getString("pm10"));
				weatherDes.setPm_level(pm25Object.getString("level"));
				weatherDes.setPm_quality(pm25Object.getString("quality"));
				weatherDes.setPm_des(pm25Object.getString("des"));
				return weatherDes;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
