package com.meitian.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.meitian.model.WeatherDes;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

public class SendHttpRequest {

	private static final String hHTTP_START = "http://op.juhe.cn/onebox/weather/query?cityname=";
	private static final String HTTP_KEY = "&key=35144988b96490f70c66d51d695d5cc4";

	public static void SendHttp(final String name, final HttpCallbackListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				try {
					URL url = new URL(hHTTP_START + name + HTTP_KEY);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					connection.setReadTimeout(5000);
					connection.connect();
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder reBuilder = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						reBuilder.append(line);
					}
					if (listener != null) {
						listener.onSuccess(reBuilder.toString());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					if (listener != null) {
						listener.onFail(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
