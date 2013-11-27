package com.rampgreen.caretakermobile.model;


public abstract class BaseDeleteSettings implements Setting{
	private String uniqueID = "empty";
	private String userID = "empty";
	private String biometricID = "empty";// is the disease type : GSR, Heart Rate and all.

	public BaseDeleteSettings(String userId)
	{
		this.userID = userId;
	}

	public BaseDeleteSettings(String userId, String biometricID)
	{
		this.userID = userId;
		this.biometricID = biometricID;
	}

	public BaseDeleteSettings(String userId, String biometricID, String uniqueID)
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
