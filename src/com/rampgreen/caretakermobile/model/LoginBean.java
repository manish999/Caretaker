package com.rampgreen.caretakermobile.model;

import org.json.JSONObject;

import com.rampgreen.caretakermobile.interfaces.Populator;
import com.rampgreen.caretakermobile.util.AppLog;


public class LoginBean extends GeneralData implements Populator{

	private String userName;
	private String accessToken;
	private String deviceId;


	public LoginBean() {
		userName = "";
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
		AppLog.e("**********************************************");
		AppLog.e("ACCESS TOKEN"+accessToken);
		AppLog.e("**********************************************");
	}

	@Override
	public String toMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateBean(JSONObject jsonObject)
	{
		accessToken = jsonObject.optString("access_token", "");
		deviceId = jsonObject.optString("device_id", "");
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}
}
