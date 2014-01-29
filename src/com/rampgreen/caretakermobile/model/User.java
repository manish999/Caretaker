package com.rampgreen.caretakermobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ImageView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.interfaces.Populator;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable, Populator
{
	private ImageView imageUser = null;
	private int notificationCount;
	private int imageResId = R.drawable.user;;
	private String uId;
	private String userName;
	private String gender;
	private String dob;
	private String eMailid;
	private String registrationdate;
	private String height;
	private String weight;
	private String devicid;
	private String requestId;
	private String deviceId;// this id is used for web socket connection to router application through cloud.
	private String GraphNmae;
	private String BiometricNmae;
	
	private boolean userOnHomeScreen = false;
	private boolean notification = false;

	private boolean gsrNotification = false;
	private boolean heartRateNotification = false;
	private boolean accelerometerNotification = false;
	private boolean tempratureNotification = false;
	
	private boolean gsrTextDisplay = false;
	private boolean heartRateTextDisplay = false;
	private boolean accelerometerTextDisplay = false;
	private boolean tempratureTextDisplay = false;
	
	private boolean gsrVisualDisplay = false;
	private boolean heartRateVisualDisplay = false;
	private boolean accelerometerVisualDisplay = false;
	private boolean tempratureVisualDisplay = false;
	
	public User(String userName, boolean userOnHomeScreen,
			boolean gsrNotification, boolean heartRateNotification,
			boolean accelerometerNotification, boolean tempratureNotification,
			boolean gsrTextDisplay, boolean heartRateTextDisplay,
			boolean accelerometerTextDisplay, boolean tempratureTextDisplay,
			boolean gsrVisualDisplay, boolean heartRateVisualDisplay,
			boolean accelerometerVisualDisplay, boolean tempratureVisualDisplay)
	{
		super();
		this.userName = userName;
		this.userOnHomeScreen = userOnHomeScreen;
		this.gsrNotification = gsrNotification;
		this.heartRateNotification = heartRateNotification;
		this.accelerometerNotification = accelerometerNotification;
		this.tempratureNotification = tempratureNotification;
		this.gsrTextDisplay = gsrTextDisplay;
		this.heartRateTextDisplay = heartRateTextDisplay;
		this.accelerometerTextDisplay = accelerometerTextDisplay;
		this.tempratureTextDisplay = tempratureTextDisplay;
		this.gsrVisualDisplay = gsrVisualDisplay;
		this.heartRateVisualDisplay = heartRateVisualDisplay;
		this.accelerometerVisualDisplay = accelerometerVisualDisplay;
		this.tempratureVisualDisplay = tempratureVisualDisplay;
	}

	
	private static ArrayList<User> userList = new ArrayList<User>();

	public User(String Uid,String Username,String Gender,String DOB,String Emailid,String Registrationdate,String Height,String Weight,String Devicid) {
		this.uId = Uid;
		this.userName = Username;
		this.gender = Gender;
		this.dob = DOB;
		this.eMailid = Emailid;
		this.registrationdate = Registrationdate;
		this.height = Height;
		this.weight = Weight;
		this.devicid = Devicid;
	}

	public User () {

	}
	
	public User (String UserID,String GraphName,String BiometricNmae) {	
		this.uId = UserID;
		this.GraphNmae = GraphName;
		this.BiometricNmae = BiometricNmae;
	}
	
	/**
	 * @param name
	 * @param notification
	 * @param imageUser
	 * @param notificationCount
	 */
	public User(String userName, boolean notification, ImageView imageUser,
			int notificationCount)
	{
		this.userName = userName;
		this.notification = notification;
		this.imageUser = imageUser;
		this.notificationCount = notificationCount;
	}

	public String GetBiometricNmae(){
		return BiometricNmae;
	}
	public String GetGraphNmae(){
		return GraphNmae;
	}
	public boolean isNotification()
	{
		return notification;
	}

	public void setNotification(boolean notification)
	{
		this.notification = notification;
	}

	public ImageView getImageUser()
	{
		return imageUser;
	}

	public void setImageUser(ImageView imageUser)
	{
		this.imageUser = imageUser;
	}

	public int getNotificationCount()
	{
		return notificationCount;
	}

	public void setNotificationCount(int notificationCount)
	{
		this.notificationCount = notificationCount;
	}

	public int getImageResId()
	{
		return imageResId;
	}

	public void setImageResId(int imageResId)
	{
		this.imageResId = imageResId;
	}

	public String getUid()
	{
		return uId;
	}

	public void setUid(String uid)
	{
		uId = uid;
	}

	public String getUsername()
	{
		return userName;
	}

	public void setUsername(String username)
	{
		userName = username;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getDOB()
	{
		return dob;
	}

	public void setDOB(String dOB)
	{
		dob = dOB;
	}

	public String getEmailid()
	{
		return eMailid;
	}

	public void setEmailid(String emailid)
	{
		eMailid = emailid;
	}

	public String getRegistrationdate()
	{
		return registrationdate;
	}

	public void setRegistrationdate(String registrationdate)
	{
		this.registrationdate = registrationdate;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public String getWeight()
	{
		return weight;
	}

	public void setWeight(String weight)
	{
		this.weight = weight;
	}

	public String getDevicid()
	{
		return devicid;
	}

	public void setDevicid(String devicid)
	{
		this.devicid = devicid;
	}
	
	public boolean isUserOnHomeScreen()
	{
		return userOnHomeScreen;
	}

	public void setUserOnHomeScreen(boolean userOnHomeScreen)
	{
		this.userOnHomeScreen = userOnHomeScreen;
	}

	public boolean isGsrNotification()
	{
		return gsrNotification;
	}

	public void setGsrNotification(boolean gsrNotification)
	{
		this.gsrNotification = gsrNotification;
	}

	public boolean isHeartRateNotification()
	{
		return heartRateNotification;
	}

	public void setHeartRateNotification(boolean heartRateNotification)
	{
		this.heartRateNotification = heartRateNotification;
	}

	public boolean isAccelerometerNotification()
	{
		return accelerometerNotification;
	}

	public void setAccelerometerNotification(boolean accelerometerNotification)
	{
		this.accelerometerNotification = accelerometerNotification;
	}

	public boolean isTempratureNotification()
	{
		return tempratureNotification;
	}

	public void setTempratureNotification(boolean tempratureNotification)
	{
		this.tempratureNotification = tempratureNotification;
	}

	public boolean isGsrTextDisplay()
	{
		return gsrTextDisplay;
	}

	public void setGsrTextDisplay(boolean gsrTextDisplay)
	{
		this.gsrTextDisplay = gsrTextDisplay;
	}

	public boolean isHeartRateTextDisplay()
	{
		return heartRateTextDisplay;
	}

	public void setHeartRateTextDisplay(boolean heartRateTextDisplay)
	{
		this.heartRateTextDisplay = heartRateTextDisplay;
	}

	public boolean isAccelerometerTextDisplay()
	{
		return accelerometerTextDisplay;
	}

	public void setAccelerometerTextDisplay(boolean accelerometerTextDisplay)
	{
		this.accelerometerTextDisplay = accelerometerTextDisplay;
	}

	public boolean isTempratureTextDisplay()
	{
		return tempratureTextDisplay;
	}

	public void setTempratureTextDisplay(boolean tempratureTextDisplay)
	{
		this.tempratureTextDisplay = tempratureTextDisplay;
	}

	public boolean isGsrVisualDisplay()
	{
		return gsrVisualDisplay;
	}

	public void setGsrVisualDisplay(boolean gsrVisualDisplay)
	{
		this.gsrVisualDisplay = gsrVisualDisplay;
	}

	public boolean isHeartRateVisualDisplay()
	{
		return heartRateVisualDisplay;
	}

	public void setHeartRateVisualDisplay(boolean heartRateVisualDisplay)
	{
		this.heartRateVisualDisplay = heartRateVisualDisplay;
	}

	public boolean isAccelerometerVisualDisplay()
	{
		return accelerometerVisualDisplay;
	}

	public void setAccelerometerVisualDisplay(boolean accelerometerVisualDisplay)
	{
		this.accelerometerVisualDisplay = accelerometerVisualDisplay;
	}

	public boolean isTempratureVisualDisplay()
	{
		return tempratureVisualDisplay;
	}

	public void setTempratureVisualDisplay(boolean tempratureVisualDisplay)
	{
		this.tempratureVisualDisplay = tempratureVisualDisplay;
	}

	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	@Override
	public void populateBean(JSONObject jsonObject)
	{
		JSONArray jsonUserArray;
		try
		{
			// it should be refilled each time response came.
			userList.clear();
			jsonUserArray = jsonObject.getJSONArray("users");
			for(int i=0;i<jsonUserArray.length();i++){
				JSONObject jsonObjUserAndSetting = jsonUserArray.getJSONObject(i);
				JSONObject jsonObjUser = jsonObjUserAndSetting.getJSONObject("user");
				JSONObject jsonObj = jsonObjUserAndSetting.getJSONObject("settings");
				
				jsonObject = jsonUserArray.getJSONObject(i);
				User user = new User();

				// parsing userlist data
				user.uId = jsonObjUser.getString("u_id");
				user.userName = jsonObjUser.getString("firstname");
				user.gender = jsonObjUser.getString("gender");
				user.dob = jsonObjUser.getString("dob");
				user.eMailid = jsonObjUser.getString("emailid");
				user.registrationdate = jsonObjUser.getString("registrationdate");
				user.height = jsonObjUser.getString("height");
				user.weight = jsonObjUser.getString("weight");
				user.devicid = jsonObjUser.getString("d_id");
				user.requestId = jsonObjUser.getString("request_id");
				// these two settings are part of jsonObjectUser, should be part of setting object. 
				user.userOnHomeScreen = jsonObjUser.getString("ishomescreen").equalsIgnoreCase("1") ? true : false;
				user.notification = jsonObjUser.getString("isnotificationon").equalsIgnoreCase("1") ? true : false;
				
				// parsing user's dashboared settings
//				for(int index=0; index < jsonArraySetting.length(); index++){
//					JSONObject jsonObjSetting = jsonArraySetting.getJSONObject(index);
//					String disease = jsonObjSetting.getString("biometricname");
//					String textSetting = jsonObjSetting.getString("textsetting");
//					String visualSetting = jsonObjSetting.getString("visualsetting");
//					String biometricid_pk = jsonObjSetting.getString("biometricid_pk");
//					
//					if(disease.equalsIgnoreCase("GSR")) {
//						user.gsrTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
//						user.gsrVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
//						
//					} else if(disease.equalsIgnoreCase("Heart Rate")) {
//						user.heartRateTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
//						user.heartRateVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
//						
//					} else if(disease.equalsIgnoreCase("Accelerometer")) {
//						user.accelerometerTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
//						user.accelerometerVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
//						
//					} else if(disease.equalsIgnoreCase("Temperature")) {
//						user.tempratureTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
//						user.tempratureVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
//						
//					}
//				}
				userList.add(user);
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String toMap()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<User> getUserList()
	{
		return userList;
	}
	
	public ArrayList<User> getDeleteSetting() {
		return null;
	}
	
//	public TextDisplaySettings getDeleteTextDisplaySetting(String userId, String diseaseType) {
//		return new TextDisplaySettings(userId, diseaseType);
//	}
}
