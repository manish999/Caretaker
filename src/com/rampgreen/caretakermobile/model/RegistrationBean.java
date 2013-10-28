package com.rampgreen.caretakermobile.model;

import org.json.JSONObject;

import com.rampgreen.caretakermobile.interfaces.Populator;


public class RegistrationBean extends GeneralData implements Populator{

	private String userName = "";
	private String password = "";
	private String age = "";
	private String height = "";
	private String weight = "";
	private String gender = "";
	private String accessToken = "";


	public RegistrationBean() {
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
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
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

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
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
	}
}
