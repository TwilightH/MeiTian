package com.meitian.adapter;

import java.util.List;

import com.meitian.R;
import com.meitian.activity.WeatherActivity;
import com.meitian.model.WeatherDes;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class myPagerAdapter extends PagerAdapter {
	private List<View> mviewList;
	private List<WeatherDes> mweatherDesList;

	public myPagerAdapter(List<View> listView, List<WeatherDes> weatherDesList) {
		// TODO Auto-generated constructor stub
		mviewList = listView;
		mweatherDesList = weatherDesList;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(mviewList.get(position));

	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(mviewList.get(position));
//		Log.d("22222222222",position+"");
		return mviewList.get(position);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		WeatherActivity.position=position;
//		Log.d("position", position+"");
		try{
			if(mviewList.get(position).getTag().equals(true)){			
				mviewList.get(position).setTag(false);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mviewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
