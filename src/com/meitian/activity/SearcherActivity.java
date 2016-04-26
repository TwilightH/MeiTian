package com.meitian.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.meitian.R;
import com.meitian.adapter.MyAdapter;
import com.meitian.model.City;
import com.meitian.model.WeatherDes;
import com.meitian.service.AutoUpdate;
import com.meitian.tools.HandlerJson;
import com.meitian.tools.HttpCallbackListener;
import com.meitian.tools.SendHttpRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SearcherActivity extends Activity {
	private EditText ed_search;
	private Button btn_search;
	private Button btn_search_back;
	private ListView lv_city_name;
	private List<City> cityList = new ArrayList<City>();
	private List<String> cityDataList = new ArrayList<String>();
	private MyAdapter adapter;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private List<WeatherDes> weatherDesList = new ArrayList<WeatherDes>();
	private ProgressDialog progressDialog;
	private ToggleButton btn_search_clock;
	private static Boolean isAutoUpdate = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searcher_acitvity);
		ed_search = (EditText) findViewById(R.id.et_search);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_search_back = (Button) findViewById(R.id.btn_search_back);
		lv_city_name = (ListView) findViewById(R.id.list_city_name);

		pref = getSharedPreferences("citydata", MODE_PRIVATE);
		for (int i = 0; i < pref.getInt("int", 0); i++) {
			City city = new City(pref.getString(i + "", "北京"), pref.getString((i + 3) + "", "没有数据"));
			cityList.add(city);
			cityDataList.add(city.getCityData());
			WeatherDes weatherDes = HandlerJson.handler(cityDataList.get(i));
			weatherDesList.add(weatherDes);
		}

		final Intent intentSearvice = new Intent(SearcherActivity.this, AutoUpdate.class);
		btn_search_clock = (ToggleButton) findViewById(R.id.btn_search_clock);
		if (isAutoUpdate = pref.getBoolean("ischecked", false)) {
			btn_search_clock.setBackground(getDrawable(R.drawable.clockon));
			btn_search_clock.setChecked(true);
		}
		btn_search_clock.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton tButton, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (pref.getInt("int", 0) > 0) {
						btn_search_clock.setBackground(getDrawable(R.drawable.clockon));
						startService(intentSearvice);
						editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
						isAutoUpdate = true;
						editor.putBoolean("ischecked", isAutoUpdate);
						editor.commit();
						Toast.makeText(SearcherActivity.this, "已打开自动更新天气，更新周期为3小时", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SearcherActivity.this, "请先添加城市再进行此操作", Toast.LENGTH_SHORT).show();
					}
				} else {
					if (pref.getInt("int", 0) > 0) {
						btn_search_clock.setBackground(getDrawable(R.drawable.clockoff));
						editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
						isAutoUpdate = false;
						editor.putBoolean("ischecked", isAutoUpdate);
						editor.commit();
						Toast.makeText(SearcherActivity.this, "已关闭自动更新天气", Toast.LENGTH_SHORT).show();
						stopService(intentSearvice);
					} else {
						Toast.makeText(SearcherActivity.this, "请先添加城市再进行此操作", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String str = ed_search.getText().toString();
				Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]+");
				Matcher matcher = pattern.matcher(str);
				if (matcher.matches()) {
					if (cityList.size() < 3 || cityList.size() == 0) {
						Boolean add = true;
						for (int i = 0; i < cityList.size(); i++) {
							if (str.equals(cityList.get(i).getCityName())) {
								Toast.makeText(SearcherActivity.this, str + "已经存在,请重新输入", Toast.LENGTH_SHORT).show();
								add = false;
								break;
							}
						}
						if (add) {
							showProgressDialog();
							SendHttpRequest.SendHttp(str, new HttpCallbackListener() {

								@Override
								public void onSuccess(final String response) {
									// TODO Auto-generated method stub

									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											if (response.length() > 50) {
												try {
													WeatherDes weatherDes = HandlerJson.handler(response);
													weatherDesList.add(weatherDes);
													City city = new City(weatherDes.getRealtime_cityName(), response);
													cityList.add(city);
													cityDataList.add(city.getCityData());
													adapter.notifyDataSetChanged();
													saveCityData();
													closeDialog();
												} catch (Exception e) {
													closeDialog();
													Toast.makeText(SearcherActivity.this, "输入错误，请重新输入",
															Toast.LENGTH_SHORT).show();
												}
											}
										}
									});
								}

								@Override
								public void onFail(Exception e) {
									// TODO Auto-generated method stub
									Toast.makeText(SearcherActivity.this, "网络连接失败",
											Toast.LENGTH_SHORT).show();
									closeDialog();
								}
							});
							;
						}
					} else {
						Toast.makeText(SearcherActivity.this, "你已添加三个城市", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(SearcherActivity.this, str + "输入有误", Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn_search_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				saveCityData();
				Intent intent = new Intent(SearcherActivity.this, WeatherActivity.class);
				startActivity(intent);
				finish();
			}
		});

		adapter = new MyAdapter(SearcherActivity.this, R.layout.list_city_name_item, cityList, cityDataList,
				weatherDesList);
		lv_city_name.setAdapter(adapter);
		lv_city_name.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void saveCityData() {
		editor = getSharedPreferences("citydata", MODE_PRIVATE).edit();
		int i = 0;
		for (; i < cityList.size(); i++) {
			editor.putString(i + "", cityList.get(i).getCityName());
			editor.putString((i + 3) + "", cityDataList.get(i));
		}
		editor.putInt("int", i);
		editor.commit();
		i = 0;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		saveCityData();
		Intent intent = new Intent(SearcherActivity.this, WeatherActivity.class);
		startActivity(intent);
		finish();
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
			progressDialog.setMessage("查询中...");
			progressDialog.setCancelable(false);
		}
		progressDialog.show();
	}
}
