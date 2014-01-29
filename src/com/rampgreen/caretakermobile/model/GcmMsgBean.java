package com.rampgreen.caretakermobile.model;

import org.json.JSONObject;

public class GcmMsgBean {
	
	private String notificationTitle;
	private String notificationSubTitle;
	
	public String getNotificationTitle()
	{
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle)
	{
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationSubTitle()
	{
		return notificationSubTitle;
	}

	public void setNotificationSubTitle(String notificationSubTitle)
	{
		this.notificationSubTitle = notificationSubTitle;
	}
	
	public void parseGcmMsg(JSONObject gcmMsgJson) {
		String name = gcmMsgJson.optString("name");
		String biometricName = gcmMsgJson.optString("biometric_name");
		String biometricValue = gcmMsgJson.optString("value");
		String msg = gcmMsgJson.optString("message");
		
		notificationTitle = name;
		notificationSubTitle = msg;
	}

}
