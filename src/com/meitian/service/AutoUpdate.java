package com.meitian.service;

import java.util.ArrayList;
import java.util.List;

import com.meitian.activity.WeatherActivity;
import com.meitian.adapter.myPagerAdapter;
import com.meitian.model.WeatherDes;
import com.meitian.tools.HandlerJson;
import com.meitian.tools.HttpCallbackListener;
import com.meitian.tools.SendHttpRequest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AutoUpdate extends Service {

	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private List<String> cityData = new ArrayList<String>();
	private List<String> cityName = new ArrayList<String>();
	private static int position = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				updateWeather();
			}
		}).start();
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int threeHour = 3 * 60 * 60 * 1000;
		long triggerAtTime = SystemClock.elapsedRealtime() + threeHour;
		Intent intent2 = new Intent(this, AutoUpdateReciver.class);
		PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent2, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pIntent);
		return super.onStartCommand(intent, flags, startId);
	}

	protected void updateWeather() {
		// TODO Auto-generated method stub
		cityData.clear();
		cityName.clear();
		pref = getSharedPreferences("citydata", MODE_PRIVATE);
		for (int i = 0; i < pref.getInt("int", 0); i++) {
			cityData.add(pref.getString((i + 3) + "", "没有数据"));
			cityName.add(pref.getString(i + "", "北京"));
		}
		int cityCount = pref.getInt("int", 0);
		switch (cityCount) {
		case 0:
			break;
		case 1:
			SendHttpRequest.SendHttp(cityName.get(0), new HttpCallbackListener() {
				@Override
				public void onSuccess(final String response) {
					// TODO Auto-generated method stub
					if (response.length() > 50) {
						try {
							cityData.set(0, response);
							editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
							editor.putString((3) + "", response);
							editor.commit();
							Log.d("service" + 0, response);
						} catch (Exception e) {
						}
					}
				}

				@Override
				public void onFail(Exception e) {
					// TODO Auto-generated method stub
				}
			});
			break;
		case 2:
			SendHttpRequest.SendHttp(cityName.get(0), new HttpCallbackListener() {
				@Override
				public void onSuccess(final String response) {
					// TODO Auto-generated method stub
					if (response.length() > 50) {
						try {
							cityData.set(0, response);
							editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
							editor.putString((3) + "", response);
							editor.commit();
							Log.d("service" + 0, response);
						} catch (Exception e) {
						}
					}
				}

				@Override
				public void onFail(Exception e) {
					// TODO Auto-generated method stub
				}
			});
			SendHttpRequest.SendHttp(cityName.get(1), new HttpCallbackListener() {
				@Override
				public void onSuccess(final String response) {
					// TODO Auto-generated method stub
					if (response.length() > 50) {
						try {
							cityData.set(1, response);
							editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
							editor.putString((4) + "", response);
							editor.commit();
							Log.d("service" + 1, response);
						} catch (Exception e) {
						}
					}
				}

				@Override
				public void onFail(Exception e) {
					// TODO Auto-generated method stub
				}
			});
			break;
		case 3:
			SendHttpRequest.SendHttp(cityName.get(0), new HttpCallbackListener() {
				@Override
				public void onSuccess(final String response) {
					// TODO Auto-generated method stub
					if (response.length() > 50) {
						try {
							cityData.set(0, response);
							editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
							editor.putString((3) + "", response);
							editor.commit();
							Log.d("service" + 0, response);
						} catch (Exception e) {
						}
					}
				}

				@Override
				public void onFail(Exception e) {
					// TODO Auto-generated method stub
				}
			});
			SendHttpRequest.SendHttp(cityName.get(1), new HttpCallbackListener() {
				@Override
				public void onSuccess(final String response) {
					// TODO Auto-generated method stub
					if (response.length() > 50) {
						try {
							cityData.set(1, response);
							editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
							editor.putString((4) + "", response);
							editor.commit();
							Log.d("service" + 1, response);
						} catch (Exception e) {
						}
					}
				}

				@Override
				public void onFail(Exception e) {
					// TODO Auto-generated method stub
				}
			});
			SendHttpRequest.SendHttp(cityName.get(2), new HttpCallbackListener() {
				@Override
				public void onSuccess(final String response) {
					// TODO Auto-generated method stub
					if (response.length() > 50) {
						try {
							cityData.set(2, response);
							editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
							editor.putString((5) + "", response);
							editor.commit();
							Log.d("service" + 2, response);
						} catch (Exception e) {
						}
					}
				}

				@Override
				public void onFail(Exception e) {
					// TODO Auto-generated method stub
				}
			});
			break;
		default:
			break;
		}
	}
}
