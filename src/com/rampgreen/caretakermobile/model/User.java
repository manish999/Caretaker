package com.rampgreen.caretakermobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.interfaces.Populator;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable, Populator
{
	private String name = "";

	private boolean notification = false;
	private ImageView imageUser = null;
	private int notificationCount;
	private boolean isOnDashboard = false;
	private int imageResId = R.drawable.user;
	private String Uid;
	private String Username;
	private String Gender;
	private String DOB;
	private String Emailid;
	private String Registrationdate;
	private String Height;
	private String Weight;
	private String Devicid;
	private ArrayList<User> userList = new ArrayList<User>();

	public User(String Uid,String Username,String Gender,String DOB,String Emailid,String Registrationdate,String Height,String Weight,String Devicid) {
		this.Uid = Uid;
		this.Username = Username;
		this.Gender = Gender;
		this.DOB = DOB;
		this.Emailid = Emailid;
		this.Registrationdate = Registrationdate;
		this.Height = Height;
		this.Weight = Weight;
		this.Devicid = Devicid;
	}

	public User () {

	}
	/**
	 * @param name
	 * @param notification
	 * @param imageUser
	 * @param notificationCount
	 */
	public User(String name, boolean notification, ImageView imageUser,
			int notificationCount)
	{
		this.name = name;
		this.notification = notification;
		this.imageUser = imageUser;
		this.notificationCount = notificationCount;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
		return Uid;
	}

	public void setUid(String uid)
	{
		Uid = uid;
	}

	public String getUsername()
	{
		return Username;
	}

	public void setUsername(String username)
	{
		Username = username;
	}

	public String getGender()
	{
		return Gender;
	}

	public void setGender(String gender)
	{
		Gender = gender;
	}

	public String getDOB()
	{
		return DOB;
	}

	public void setDOB(String dOB)
	{
		DOB = dOB;
	}

	public String getEmailid()
	{
		return Emailid;
	}

	public void setEmailid(String emailid)
	{
		Emailid = emailid;
	}

	public String getRegistrationdate()
	{
		return Registrationdate;
	}

	public void setRegistrationdate(String registrationdate)
	{
		Registrationdate = registrationdate;
	}

	public String getHeight()
	{
		return Height;
	}

	public void setHeight(String height)
	{
		Height = height;
	}

	public String getWeight()
	{
		return Weight;
	}

	public void setWeight(String weight)
	{
		Weight = weight;
	}

	public String getDevicid()
	{
		return Devicid;
	}

	public void setDevicid(String devicid)
	{
		Devicid = devicid;
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

				user.Uid = objJson.getString("u_id");
				user.Username = objJson.getString("firstname");
				user.name = objJson.getString("firstname");
				user.Gender = objJson.getString("gender");
				user.DOB = objJson.getString("dob");
				user.Emailid = objJson.getString("emailid");
				user.Registrationdate = objJson.getString("registrationdate");
				user.Height = objJson.getString("height");
				user.Weight = objJson.getString("weight");
				user.Devicid = objJson.getString("d_id");
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
