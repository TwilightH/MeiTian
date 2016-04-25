package com.meitian.activity;

import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.List;

import com.meitian.R;
import com.meitian.adapter.myPagerAdapter;
import com.meitian.model.City;
import com.meitian.model.WeatherDes;
import com.meitian.service.AutoUpdate;
import com.meitian.tools.HandlerJson;
import com.meitian.tools.HttpCallbackListener;
import com.meitian.tools.SendHttpRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity implements OnClickListener {
	private Button btn_add_city;
	private Button btn_refresh_city;
	private ViewPager vp_viewpager;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private List<String> cityData = new ArrayList<String>();
	private List<View> viewList = new ArrayList<View>();
	private List<WeatherDes> weatherDesList = new ArrayList<WeatherDes>();
	private List<String> cityName = new ArrayList<String>();
	private View view;
	private int cityCount = 0;
	private myPagerAdapter pagerAdapter;
	private ProgressDialog progressDialog;
	public static int position;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_activity);
		cityData.clear();
		weatherDesList.clear();
		cityName.clear();
		pref = getSharedPreferences("citydata", MODE_PRIVATE);
		for (int i = 0; i < pref.getInt("int", 0); i++) {
			cityData.add(pref.getString((i + 3) + "", "没有数据"));
			WeatherDes weatherDes = HandlerJson.handler(cityData.get(i));
			weatherDesList.add(weatherDes);
			cityName.add(weatherDes.getRealtime_cityName());
			// Log.d("cityname", weatherDes.getRealtime_cityName());
		}
		cityCount = pref.getInt("int", 0);
		btn_add_city = (Button) findViewById(R.id.btn_add_city);
		btn_refresh_city = (Button) findViewById(R.id.btn_refresh_city);
		vp_viewpager = (ViewPager) findViewById(R.id.vp_viewpager);

		btn_add_city.setOnClickListener(this);
		btn_refresh_city.setOnClickListener(this);
		initView();
	
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		cityData.clear();
		weatherDesList.clear();
		cityName.clear();
		pref = getSharedPreferences("citydata", MODE_PRIVATE);
		for (int i = 0; i < pref.getInt("int", 0); i++) {
			cityData.add(pref.getString((i + 3) + "", "没有数据"));
			WeatherDes weatherDes = HandlerJson.handler(cityData.get(i));
			weatherDesList.add(weatherDes);
			cityName.add(weatherDes.getRealtime_cityName());
			Log.d("cityname", weatherDes.getRealtime_cityName());
		}
		cityCount = pref.getInt("int", 0);
		vp_viewpager.setAdapter(null);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub

		viewList.clear();

		for (int i = 0; i < cityCount; i++) {

			view = LayoutInflater.from(this).inflate(R.layout.pager_view, null);
			TextView tv_realtime_updatetime;
			tv_realtime_updatetime = (TextView) view.findViewById(R.id.tv_realtime_updatetime);
			tv_realtime_updatetime.setText(weatherDesList.get(i).getRealtime_updateTime());
			TextView tv_realtime_city_name;
			tv_realtime_city_name = (TextView) view.findViewById(R.id.tv_realtime_city_name);
			tv_realtime_city_name.setText(weatherDesList.get(i).getRealtime_cityName());
			TextView tv_realtime_weather_info;
			tv_realtime_weather_info = (TextView) view.findViewById(R.id.tv_realtime_weather_info);
			tv_realtime_weather_info.setText(weatherDesList.get(i).getRealtime_info());
			TextView tv_realtime_temperature;
			tv_realtime_temperature = (TextView) view.findViewById(R.id.tv_realtime_temperature);
			tv_realtime_temperature.setText(weatherDesList.get(i).getRealtime_temperature() + "℃");
			TextView tv_pm25_quality;
			tv_pm25_quality = (TextView) view.findViewById(R.id.tv_pm25_quality);
			tv_pm25_quality.setText(weatherDesList.get(i).getPm_quality());
			TextView tv_realtime_date;
			tv_realtime_date = (TextView) view.findViewById(R.id.tv_realtime_date);
			tv_realtime_date.setText(weatherDesList.get(i).getRealtime_date());

			TextView tv_weather_day_info;
			String[] response = dealWeatherMsg(weatherDesList.get(i).getWeather_day1());
			tv_weather_day_info = (TextView) view.findViewById(R.id.tv_weather_day_info);
			tv_weather_day_info.setText(response[1]);
			TextView tv_weather_day_temp;
			tv_weather_day_temp = (TextView) view.findViewById(R.id.tv_weather_day_temp);
			tv_weather_day_temp.setText(response[2] + "℃");
			TextView tv_weather_day_direct;
			tv_weather_day_direct = (TextView) view.findViewById(R.id.tv_weather_day_direct);
			tv_weather_day_direct.setText(response[3]);
			TextView tv_weather_day_power;
			tv_weather_day_power = (TextView) view.findViewById(R.id.tv_weather_day_power);
			tv_weather_day_power.setText(response[4]);
			TextView tv_weather_sunrise;
			tv_weather_sunrise = (TextView) view.findViewById(R.id.tv_weather_sunrise);
			tv_weather_sunrise.setText(response[5]);

			TextView tv_weather_night_info;
			String[] resNight = dealWeatherMsg(weatherDesList.get(i).getWeather_night1());
			tv_weather_night_info = (TextView) view.findViewById(R.id.tv_weather_night_info);
			tv_weather_night_info.setText(resNight[1]);
			TextView tv_weather_night_temp;
			tv_weather_night_temp = (TextView) view.findViewById(R.id.tv_weather_night_temp);
			tv_weather_night_temp.setText(resNight[2] + "℃");
			TextView tv_weather_night_direct;
			tv_weather_night_direct = (TextView) view.findViewById(R.id.tv_weather__night_direct);
			tv_weather_night_direct.setText(resNight[3]);
			TextView tv_weather_night_power;
			tv_weather_night_power = (TextView) view.findViewById(R.id.tv_weather_night_power);
			tv_weather_night_power.setText(resNight[4]);
			TextView tv_weather_sunset;
			tv_weather_sunset = (TextView) view.findViewById(R.id.tv_weather_sunset);
			tv_weather_sunset.setText(resNight[5]);

			TextView tv_weather_week1;
			tv_weather_week1 = (TextView) view.findViewById(R.id.tv_weather_week1);
			tv_weather_week1.setText("周" + weatherDesList.get(i).getWeather_week2());
			TextView tv_weather_week2;
			tv_weather_week2 = (TextView) view.findViewById(R.id.tv_weather_week2);
			tv_weather_week2.setText("周" + weatherDesList.get(i).getWeather_week3());
			TextView tv_weather_week3;
			tv_weather_week3 = (TextView) view.findViewById(R.id.tv_weather_week3);
			tv_weather_week3.setText("周" + weatherDesList.get(i).getWeather_week4());
			TextView tv_weather_week4;
			tv_weather_week4 = (TextView) view.findViewById(R.id.tv_weather_week4);
			tv_weather_week4.setText("周" + weatherDesList.get(i).getWeather_week5());
			TextView tv_weather_week5;
			tv_weather_week5 = (TextView) view.findViewById(R.id.tv_weather_week5);
			tv_weather_week5.setText("周" + weatherDesList.get(i).getWeather_week6());
			TextView tv_weather_week6;
			tv_weather_week6 = (TextView) view.findViewById(R.id.tv_weather_week6);
			tv_weather_week6.setText("周" + weatherDesList.get(i).getWeather_week7());

			TextView tv_weather_day1;
			String[] resday1 = dealWeatherMsg(weatherDesList.get(i).getWeather_day2());
			tv_weather_day1 = (TextView) view.findViewById(R.id.tv_weather_day1);
			tv_weather_day1.setText(resday1[1]);
			TextView tv_weather_temp1;
			String[] resnight1 = dealWeatherMsg(weatherDesList.get(i).getWeather_night2());
			tv_weather_temp1 = (TextView) view.findViewById(R.id.tv_weather_temp1);
			tv_weather_temp1.setText(resday1[2] + "/" + resnight1[2] + "℃");

			TextView tv_weather_day2;
			String[] resday2 = dealWeatherMsg(weatherDesList.get(i).getWeather_day3());
			tv_weather_day2 = (TextView) view.findViewById(R.id.tv_weather_day2);
			tv_weather_day2.setText(resday2[1]);
			TextView tv_weather_temp2;
			tv_weather_temp2 = (TextView) view.findViewById(R.id.tv_weather_temp2);
			String[] resnight2 = dealWeatherMsg(weatherDesList.get(i).getWeather_night3());
			tv_weather_temp2.setText(resday2[2] + "/" + resnight2[2] + "℃");

			TextView tv_weather_day3;
			String[] resday3 = dealWeatherMsg(weatherDesList.get(i).getWeather_day4());
			tv_weather_day3 = (TextView) view.findViewById(R.id.tv_weather_day3);
			tv_weather_day3.setText(resday3[1]);
			TextView tv_weather_temp3;
			tv_weather_temp3 = (TextView) view.findViewById(R.id.tv_weather_temp3);
			String[] resnight3 = dealWeatherMsg(weatherDesList.get(i).getWeather_night4());
			tv_weather_temp3.setText(resday3[2] + "/" + resnight3[2] + "℃");

			TextView tv_weather_day4;
			String[] resday4 = dealWeatherMsg(weatherDesList.get(i).getWeather_day5());
			tv_weather_day4 = (TextView) view.findViewById(R.id.tv_weather_day4);
			tv_weather_day4.setText(resday4[1]);
			TextView tv_weather_temp4;
			tv_weather_temp4 = (TextView) view.findViewById(R.id.tv_weather_temp4);
			String[] resnight4 = dealWeatherMsg(weatherDesList.get(i).getWeather_night5());
			tv_weather_temp4.setText(resday4[2] + "/" + resnight4[2] + "℃");

			TextView tv_weather_day5;
			String[] resday5 = dealWeatherMsg(weatherDesList.get(i).getWeather_day6());
			tv_weather_day5 = (TextView) view.findViewById(R.id.tv_weather_day5);
			tv_weather_day5.setText(resday5[1]);
			TextView tv_weather_temp5;
			tv_weather_temp5 = (TextView) view.findViewById(R.id.tv_weather_temp5);
			String[] resnight5 = dealWeatherMsg(weatherDesList.get(i).getWeather_night6());
			tv_weather_temp5.setText(resday5[2] + "/" + resnight5[2] + "℃");

			TextView tv_weather_day6;
			String[] resday6 = dealWeatherMsg(weatherDesList.get(i).getWeather_day7());
			tv_weather_day6 = (TextView) view.findViewById(R.id.tv_weather_day6);
			tv_weather_day6.setText(resday6[1]);
			TextView tv_weather_temp6;
			tv_weather_temp6 = (TextView) view.findViewById(R.id.tv_weather_temp6);
			String[] resnight6 = dealWeatherMsg(weatherDesList.get(i).getWeather_night7());
			tv_weather_temp6.setText(resday6[2] + "/" + resnight6[2] + "℃");

			Button btn_show_detail;
			btn_show_detail = (Button) view.findViewById(R.id.btn_show_detail);
			btn_show_detail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					View detailView = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.detail_weather, null);
					TextView tv_datail_des = (TextView) detailView.findViewById(R.id.tv_detail_des);
					tv_datail_des.setText(weatherDesList.get(position).getPm_des());
					TextView tv_detail_pm10, tv_detail_pm25;
					tv_detail_pm10 = (TextView) detailView.findViewById(R.id.tv_detail_pm10);
					tv_detail_pm10.setText(weatherDesList.get(position).getPm_pm10());
					tv_detail_pm25 = (TextView) detailView.findViewById(R.id.tv_detail_pm25);
					tv_detail_pm25.setText(weatherDesList.get(position).getPm_pm25());

					TextView tv_kongtiao = (TextView) detailView.findViewById(R.id.tv_detail_kongtiao);
					tv_kongtiao.setText(dealDetailMsg(weatherDesList.get(position).getLife_kongtiao()));

					TextView tv_yundong = (TextView) detailView.findViewById(R.id.tv_detail_yundong);
					tv_yundong.setText(dealDetailMsg(weatherDesList.get(position).getLife_yundong()));

					TextView tv_xiche = (TextView) detailView.findViewById(R.id.tv_detail_xiche);
					tv_xiche.setText(dealDetailMsg(weatherDesList.get(position).getLife_xiche()));

					TextView tv_ziwaixian = (TextView) detailView.findViewById(R.id.tv_detail_ziwaixian);
					tv_ziwaixian.setText(dealDetailMsg(weatherDesList.get(position).getLife_ziwaixian()));

					TextView tv_ganmao = (TextView) detailView.findViewById(R.id.tv_detail_ganmao);
					tv_ganmao.setText(dealDetailMsg(weatherDesList.get(position).getLife_ganmao()));

					TextView tv_chuanyi = (TextView) detailView.findViewById(R.id.tv_detail_chuanyi);
					tv_chuanyi.setText(dealDetailMsg(weatherDesList.get(position).getLife_chuanyi()));

					TextView tv_wuran = (TextView) detailView.findViewById(R.id.tv_detail_wuran);
					tv_wuran.setText(dealDetailMsg(weatherDesList.get(position).getLife_wuran()));

					AlertDialog.Builder dialog = new AlertDialog.Builder(WeatherActivity.this);
					// dialog.setView(null);
					dialog.setTitle("天气详情");
					dialog.setView(detailView);
					dialog.show();
				}
			});

			view.setTag(true);
			viewList.add(view);
		}
		pagerAdapter = new myPagerAdapter(viewList, weatherDesList);
		vp_viewpager.setAdapter(pagerAdapter);
	}

	protected String dealDetailMsg(String msg) {
		// TODO Auto-generated method stub
		String str = msg.replace("\"", "");
		int index1 = str.indexOf("[");
		int index2 = str.indexOf("]");
		String str2 = str.substring(index1 + 1, index2);
		return str2;
	}

	private String[] dealWeatherMsg(String msg) {
		// TODO Auto-generated method stub
		String str = msg.replace("\"", "");
		int index1 = str.indexOf("[");
		int index2 = str.indexOf("]");
		String str2 = str.substring(index1 + 1, index2);
		String[] response = str2.split(",");
		return response;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_add_city:
			Intent intent = new Intent(this, SearcherActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_refresh_city:
			// weatherDesList.clear();
			if(weatherDesList.size()>0){
				refreshWeather();
			}else{
				Toast.makeText(this, "请添加城市后再刷新", Toast.LENGTH_SHORT).show();
			}
			
		}
	}

	private void refreshWeather() {
		// TODO Auto-generated method stub
		showProgressDialog();
		SendHttpRequest.SendHttp(cityName.get(position), new HttpCallbackListener() {

			@Override
			public void onSuccess(final String response) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (response.length() > 50) {
							try {

								cityData.set(position, response);
								editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
								editor.putString((position + 3) + "", response);
								editor.commit();
								closeDialog();
								onRestart();
							} catch (Exception e) {
								Toast.makeText(WeatherActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
							}
						}
					}

				});
			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(WeatherActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	private void closeDialog() {
		// TODO Auto-generated method stub
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	private void showProgressDialog() {
		// TODO Auto-generated method stub
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("更新中...");
			progressDialog.setCancelable(false);
		}
		progressDialog.show();
	}
}
