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

	public TextDisplaySettings(String userId, String diseaseType, String uniqueId)
	{
		super(userId, diseaseType, uniqueId);
	}

	public TextDisplaySettings(String userId, String diseaseType, String uniqueId, String displayMsg)
	{
		this(userId, diseaseType, uniqueId);
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
				JSONObject jsonSettingObj = jsonObjUserAndSetting.getJSONObject("settings");

				userId = jsonObjUser.getString("u_id");
				Object obj = jsonSettingObj.get("textsetting");
				try
				{
					if(obj ==  null || obj instanceof JSONObject) {
						// text display setting would be null, reset all text settings 
					} else {
						JSONArray textSettingArray = (JSONArray)obj;
						parseTextDisplaySetting(textSettingArray, userId);
					}
				} catch (Exception e)
				{
					// TODO: handle exception
				}

				try
				{
					obj = jsonSettingObj.get("visualsetting");
					if(obj ==  null || obj instanceof JSONObject) {
						// visual display setting would be null, reset all visual settings 
					} else {
						JSONArray visualSettingArray = (JSONArray)obj;			
						//						parseVisualDisplaySetting(visualSettingArray, userId);
					}
				} catch (Exception e)
				{
					// TODO: handle exception
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

	private void parseTextDisplaySetting(JSONArray textSettingArray, String userId) throws JSONException {
		// parsing user's dashboared TextDisplay settings
		for(int index=0; index < textSettingArray.length(); index++){
			JSONObject jsonObjSetting = textSettingArray.getJSONObject(index);
			String disease = jsonObjSetting.getString("biometric_name");
			String textSetting = jsonObjSetting.getString("textsetting");
			String uniqueIdForDeletion = jsonObjSetting.getString("unique_id");
			String biometricid_pk = jsonObjSetting.getString("biometric_id");
			boolean diseaseValue;
			if(disease.equalsIgnoreCase("GSR")) {
				//				diseaseValue = textSetting.equalsIgnoreCase("1") ? true : false;
				if(textSetting.equalsIgnoreCase("1")) {
					TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease, uniqueIdForDeletion);
					ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
					tdSettingList.add(textDisplaySettings);
				}
				//				user.gsrVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;

			} else if(disease.equalsIgnoreCase("Heart Rate")) {
				if(textSetting.equalsIgnoreCase("1")) {
					TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease, uniqueIdForDeletion);
					ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
					tdSettingList.add(textDisplaySettings);
				}
				//				user.heartRateTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
				//				user.heartRateVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;

			} else if(disease.equalsIgnoreCase("Accelerometer")) {
				if(textSetting.equalsIgnoreCase("1")) {
					TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease, uniqueIdForDeletion);
					ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
					tdSettingList.add(textDisplaySettings);
				}
				//				user.accelerometerTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
				//				user.accelerometerVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;

			} else if(disease.equalsIgnoreCase("Temperature")) {
				if(textSetting.equalsIgnoreCase("1")) {
					TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease, uniqueIdForDeletion);
					ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
					tdSettingList.add(textDisplaySettings);
					tdSettingList = ListHolder.getTextDisplaySettingList();
				}
				//				user.tempratureTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
				//				user.tempratureVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;

			}
		}
	}

	//	private void parseVisualDisplaySetting(JSONArray textSettingArray, String userId) throws JSONException {
	//		// parsing user's dashboared TextDisplay settings
	//		for(int index=0; index < textSettingArray.length(); index++){
	//			JSONObject jsonObjSetting = textSettingArray.getJSONObject(index);
	//			String disease = jsonObjSetting.getString("biometric_name");
	//			String visualSetting = jsonObjSetting.getString("visualsetting");
	//			String uniqueIdForDeletion = jsonObjSetting.getString("unique_id");
	//			String biometricid_pk = jsonObjSetting.getString("biometric_id");
	//			boolean diseaseValue;
	//			if(disease.equalsIgnoreCase("GSR")) {
	//				//				diseaseValue = textSetting.equalsIgnoreCase("1") ? true : false;
	//				if(visualSetting.equalsIgnoreCase("1")) {
	//					TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease);
	//					ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
	//					tdSettingList.add(textDisplaySettings);
	//				}
	//				//				user.gsrVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
	//
	//			} else if(disease.equalsIgnoreCase("Heart Rate")) {
	//				if(visualSetting.equalsIgnoreCase("1")) {
	//					TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease);
	//					ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
	//					tdSettingList.add(textDisplaySettings);
	//				}
	//				//				user.heartRateTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
	//				//				user.heartRateVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
	//
	//			} else if(disease.equalsIgnoreCase("Accelerometer")) {
	//				if(visualSetting.equalsIgnoreCase("1")) {
	//					TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease);
	//					ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
	//					tdSettingList.add(textDisplaySettings);
	//				}
	//				//				user.accelerometerTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
	//				//				user.accelerometerVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
	//
	//			} else if(disease.equalsIgnoreCase("Temperature")) {
	//				if(visualSetting.equalsIgnoreCase("1")) {
	//					TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId, disease);
	//					ArrayList<TextDisplaySettings> tdSettingList = ListHolder.getTextDisplaySettingList();
	//					tdSettingList.add(textDisplaySettings);
	////					tdSettingList = ListHolder.getTextDisplaySettingList();
	//				}
	//				//				user.tempratureTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
	//				//				user.tempratureVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;
	//
	//			}
	//		}
	//	}
}