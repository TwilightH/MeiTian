package com.meitian.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReciver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(arg0,AutoUpdate.class);
		arg0.startService(intent);
	}

}
