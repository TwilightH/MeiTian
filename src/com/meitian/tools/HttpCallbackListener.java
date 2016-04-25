package com.meitian.tools;

public interface HttpCallbackListener {
	void onSuccess(String response);
	void onFail(Exception e);
}
