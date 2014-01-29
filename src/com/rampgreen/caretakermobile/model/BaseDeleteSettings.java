package com.rampgreen.caretakermobile.model;


public abstract class BaseDeleteSettings implements Setting{
	private String uniqueID = "empty";
	private String userID = "empty";
	private String biometricID = "empty";// is the disease type : GSR, Heart Rate and all.
	private String gsr = "";
	private String temp_ambient = "";
	private String temp_skin = "";
	private String pox_hr = "";
	private String pox_pulse = "";
	private String acc_x = "";
	private String acc_y = "";
	private String acc_z = "";

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

//	public BaseDeleteSettings(String userId, String biometricID,
//			String uniqueID, String gsr, String temp_ambient, String temp_skin,String pox_hr,String pox_pulse,String acc_x,String acc_y,String acc_z) {
//		this.userID = userId;
//		this.biometricID = biometricID;
//		this.uniqueID = uniqueID;
//		this.gsr = gsr;
//		this.temp_ambient = temp_ambient;
//		this.temp_skin = temp_skin;		
//		this.pox_hr = pox_hr;
//		this.pox_pulse = pox_pulse;
//		this.acc_x = acc_x;
//		this.acc_y = acc_y;
//		this.acc_z = acc_z;
//	}

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
	
//	public String getgsr()
//	{
//		return gsr;
//	}
//	
//	public String gettemp_ambient()
//	{
//		return temp_ambient;
//	}
//	
//	public String gettemp_skin()
//	{
//		return temp_skin;
//	}
//	
//	public String getpox_hr()
//	{
//		return pox_hr;
//	}
//	public String getpox_pulse()
//	{
//		return pox_pulse;
//	}
//	public String getacc_x()
//	{
//		return acc_x;
//	}
//	public String getacc_y()
//	{
//		return acc_y;
//	}
//	public String getacc_z()
//	{
//		return acc_z;
//	}
}
