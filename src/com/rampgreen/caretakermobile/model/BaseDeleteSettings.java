package com.rampgreen.caretakermobile.model;

import android.provider.Contacts.SettingsColumns;

import java.util.ArrayList;

abstract class BaseDeleteSettings implements Setting{
	private String uniqueID = "mama";
	private String userID = "mama";
	private String biometricID = "mama";// is the disease type : GSR, Heart Rate and all.

	public BaseDeleteSettings(String userId)
	{
		this.userID = userId;
	}

	public BaseDeleteSettings(String userId, String biometricID)
	{
		this.userID = userId;
		this.biometricID = biometricID;
	}

	public BaseDeleteSettings(String uniqueID, String userId, String biometricID)
	{
		this.userID = userId;
		this.biometricID = biometricID;
		this.uniqueID = uniqueID;
	}

	public String getUniqueID()
	{
		return uniqueID;
	}

	public String getUserID()
	{
		return userID;
	}

	public String getBiometricID()
	{
		return biometricID;
	}

	public void setBiometricID(String biometricID)
	{
		this.biometricID = biometricID;
	}
}


