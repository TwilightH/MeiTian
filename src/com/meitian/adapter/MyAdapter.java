package com.meitian.adapter;

import java.util.List;

import com.meitian.R;
import com.meitian.activity.SearcherActivity;
import com.meitian.model.City;
import com.meitian.model.WeatherDes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<City> {
	private int resourceId;
	private List<City> mcityList;
	private List<String>mcityDataList;
	private List<WeatherDes>mweatherDesList;

	public MyAdapter(Context context, int resource, List<City> objects,List<String> objects1,List<WeatherDes> objects2) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
		mcityList=objects;
		mcityDataList=objects1;
		mweatherDesList=objects2;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		City city=getItem(position);
		View view=LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView tv_list_city_item=(TextView)view.findViewById(R.id.tv_list_city_item);
		Button btn_list_city_item=(Button)view.findViewById(R.id.btn_list_city_item);
		tv_list_city_item.setText(city.getCityName());
		btn_list_city_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mcityList.remove(position);
				mcityDataList.remove(position);
				mweatherDesList.remove(position);
				notifyDataSetChanged();
				
			}
		});
		return view;
	}
}
