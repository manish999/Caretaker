package com.rampgreen.caretakermobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rampgreen.caretakermobile.interfaces.Populator;

import java.util.ArrayList;

public class TextDisplaySettings  extends BaseDeleteSettings implements Populator{
	private String displayMsg;
//	private ArrayList<TextDisplaySettings> textDisplaySettingList = new ArrayList<TextDisplaySettings>();; 

	public TextDisplaySettings()
	{
		super("");// only called for populating the been on response
	}
	public TextDisplaySettings(String userId, String diseaseType)
	{
		super(userId, diseaseType);
	
	}

	public TextDisplaySettings(String userId, String diseaseType, String displayMsg)
	{
		this(userId, diseaseType);
		this.displayMsg = displayMsg;
	}

	public String getDisplayMsg()
	{
		return displayMsg;
	}

	public void setDisplayMsg(String displayMsg)
	{
		this.displayMsg = displayMsg;
	}

	@Override
	public ArrayList<?> getSettingList()
	{
		return null;
//		return textDisplaySettingList;
	}

//	public void addItem (TextDisplaySettings item)
//	{
//		textDisplaySettingList.add(item);
//	}

	@Override
	public void populateBean(JSONObject jsonObject)
	{

		String userId;
		JSONArray jsonUserArray;
		try
		{
			// it should be refilled each time response came.
			ListHolder.getTextDisplaySettingList().clear();
			jsonUserArray = jsonObject.getJSONArray("users");
			for(int i=0;i<jsonUserArray.length();i++){
				JSONObject jsonObjUserAndSetting = jsonUserArray.getJSONObject(i);
				JSONObject jsonObjUser = jsonObjUserAndSetting.getJSONObject("user");
				JSONArray jsonArraySetting = jsonObjUserAndSetting.getJSONArray("settings");
				
				jsonObject = jsonUserArray.getJSONObject(i);
//				User user = new User();
//
//				// parsing userlist data
				userId = jsonObjUser.getString("u_id");
//				user.userName = jsonObjUser.getString("firstname");
//				user.gender = jsonObjUser.getString("gender");
//				user.dob = jsonObjUser.getString("dob");
//				user.eMailid = jsonObjUser.getString("emailid");
//				user.registrationdate = jsonObjUser.getString("registrationdate");
//				user.height = jsonObjUser.getString("height");
//				user.weight = jsonObjUser.getString("weight");
//				user.devicid = jsonObjUser.getString("d_id");
//				user.requestId = jsonObjUser.getString("request_id");
//				// these two settings are part of jsonObjectUser, should be part of setting object. 
//				user.userOnHomeScreen = jsonObjUser.getString("ishomescreen").equalsIgnoreCase("1") ? true : false;
//				user.notification = jsonObjUser.getString("isnotificationon").equalsIgnoreCase("1") ? true : false;
				
				// parsing user's dashboared settings
				for(int index=0; index < jsonArraySetting.length(); index++){
					JSONObject jsonObjSetting = jsonArraySetting.getJSONObject(index);
					String disease = jsonObjSetting.getString("biometricname");
					String textSetting = jsonObjSetting.getString("textsetting");
					String visualSetting = jsonObjSetting.getString("visualsetting");
					String biometricid_pk = jsonObjSetting.getString("biometricid_pk");
					boolean diseaseValue;
					if(disease.equalsIgnoreCase("GSR")) {
//						diseaseValue = textSetting.equalsIgnoreCase("1") ? true : false;
						if(textSetting.equalsIgnoreCase("1")) {
							TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease);
							ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
							tdSettingList.add(textDisplaySettings);
						}
//						user.gsrVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
						
					} else if(disease.equalsIgnoreCase("Heart Rate")) {
						if(textSetting.equalsIgnoreCase("1")) {
							TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease);
							ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
							tdSettingList.add(textDisplaySettings);
						}
//						user.heartRateTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
//						user.heartRateVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
						
					} else if(disease.equalsIgnoreCase("Accelerometer")) {
						if(textSetting.equalsIgnoreCase("1")) {
							TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease);
							ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
							tdSettingList.add(textDisplaySettings);
						}
//						user.accelerometerTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
//						user.accelerometerVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
						
					} else if(disease.equalsIgnoreCase("Temperature")) {
						if(textSetting.equalsIgnoreCase("1")) {
							TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease);
							ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
							tdSettingList.add(textDisplaySettings);
							tdSettingList = ListHolder.getTextDisplaySettingList();
						}
//						user.tempratureTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
//						user.tempratureVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
						
					}
				}
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
}