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
	private boolean notification = false;
	private ImageView imageUser = null;
	private int notificationCount;
	private boolean isOnDashboard = false;
	private int imageResId = R.drawable.user;
	private String uId;
	private String userName;
	private String gender;
	private String dob;
	private String eMailid;
	private String registrationdate;
	private String height;
	private String weight;
	private String devicid;
	
	private boolean userOnHomeScreen = false;

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
	
	private ArrayList<User> userList = new ArrayList<User>();

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

	public boolean isOnDashboard()
	{
		return isOnDashboard;
	}

	public void setOnDashboard(boolean isOnDashboard)
	{
		this.isOnDashboard = isOnDashboard;
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

	@Override
	public void populateBean(JSONObject jsonObject)
	{
		JSONArray jArray;
		try
		{
			jArray = jsonObject.getJSONArray("users");
			for(int i=0;i<jArray.length();i++){
				JSONObject objJson = jArray.getJSONObject(i);
				jsonObject = jArray.getJSONObject(i);
				User user = new User();

				user.uId = objJson.getString("u_id");
				user.userName = objJson.getString("firstname");
				user.gender = objJson.getString("gender");
				user.dob = objJson.getString("dob");
				user.eMailid = objJson.getString("emailid");
				user.registrationdate = objJson.getString("registrationdate");
				user.height = objJson.getString("height");
				user.weight = objJson.getString("weight");
				user.devicid = objJson.getString("d_id");
				userList.add(user);
			}
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
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

}
