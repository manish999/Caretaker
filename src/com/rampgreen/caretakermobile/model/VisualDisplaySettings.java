package com.rampgreen.caretakermobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.rampgreen.caretakermobile.interfaces.Populator;
import com.rampgreen.caretakermobile.util.AppLog;

import java.util.ArrayList;

public class VisualDisplaySettings extends BaseDeleteSettings implements Populator{
	private int x;
	private int y;
	
	public VisualDisplaySettings()
	{
		super("");
	}
	
	public VisualDisplaySettings(String userId, String diseaseType) {
		super(userId, diseaseType);
	}

	public VisualDisplaySettings(String userId, String diseaseType, String uniqueId)
	{
		super(userId, diseaseType, uniqueId);
	}


	@Override
	public ArrayList<?> getSettingList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateBean(JSONObject jsonObject)
	{
		String userId;
		JSONArray jsonUserArray;
		try
		{
			// it should be refilled each time response came.
//			ListHolder.getTextDisplaySettingList().clear();
			ListHolder.getVisualDisplaySettingsList().clear();
			jsonUserArray = jsonObject.getJSONArray("users");
			for(int i=0;i<jsonUserArray.length();i++){
				JSONObject jsonObjUserAndSetting = jsonUserArray.getJSONObject(i);
				JSONObject jsonObjUser = jsonObjUserAndSetting.getJSONObject("user");
				JSONObject jsonSettingObj = jsonObjUserAndSetting.getJSONObject("settings");

				userId = jsonObjUser.getString("u_id");
				Object obj = jsonSettingObj.get("textsetting");

				try
				{
					obj = jsonSettingObj.get("visualsetting");
					
					if(obj ==  null || obj instanceof JSONObject || obj.toString().equalsIgnoreCase("null")) {
						// visual display setting would be null, reset all visual settings 
					} else {
						JSONArray visualSettingArray = (JSONArray)obj;			
						parseVisualDisplaySetting(visualSettingArray, userId);
					}
				} catch (Exception e)
				{
					AppLog.e(e.getMessage());
				}	
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}


	}

	private void parseVisualDisplaySetting(JSONArray textSettingArray, String userId) throws JSONException {
		// parsing user's dashboared TextDisplay settings
		for(int index=0; index < textSettingArray.length(); index++){
			JSONObject jsonObjSetting = textSettingArray.getJSONObject(index);
			String disease = jsonObjSetting.getString("biometric_name");
			String visualSetting = jsonObjSetting.getString("visualsetting");
			String uniqueIdForDeletion = jsonObjSetting.getString("unique_id");
			String biometricid_pk = jsonObjSetting.getString("biometric_id");
			boolean diseaseValue;
			if(disease.equalsIgnoreCase("GSR")) {
				//				diseaseValue = textSetting.equalsIgnoreCase("1") ? true : false;
				if(visualSetting.equalsIgnoreCase("1")) {
					VisualDisplaySettings textDisplaySettings = new VisualDisplaySettings(userId, disease, uniqueIdForDeletion);
					ArrayList<VisualDisplaySettings> tdSettingList = ListHolder.getVisualDisplaySettingsList();
					tdSettingList.add(textDisplaySettings);
				}
				//				user.gsrVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;

			} else if(disease.equalsIgnoreCase("Heart Rate")) {
				if(visualSetting.equalsIgnoreCase("1")) {
					VisualDisplaySettings textDisplaySettings = new VisualDisplaySettings(userId, disease, uniqueIdForDeletion);
					ArrayList<VisualDisplaySettings> tdSettingList = ListHolder.getVisualDisplaySettingsList();
					tdSettingList.add(textDisplaySettings);
				}
				//				user.heartRateTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
				//				user.heartRateVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;

			} else if(disease.equalsIgnoreCase("Accelerometer")) {
				if(visualSetting.equalsIgnoreCase("1")) {
					VisualDisplaySettings textDisplaySettings = new VisualDisplaySettings(userId, disease, uniqueIdForDeletion);
					ArrayList<VisualDisplaySettings> tdSettingList = ListHolder.getVisualDisplaySettingsList();
					tdSettingList.add(textDisplaySettings);
				}
				//				user.accelerometerTextDisplay = textSetting.equalsIgnoreCase("1") ? true : false;
				//				user.accelerometerVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true : false;

			} else if(disease.equalsIgnoreCase("Temperature")) {
				if(visualSetting.equalsIgnoreCase("1")) {
					VisualDisplaySettings textDisplaySettings = new VisualDisplaySettings(userId, disease, uniqueIdForDeletion);
					ArrayList<VisualDisplaySettings> tdSettingList = ListHolder.getVisualDisplaySettingsList();
					tdSettingList.add(textDisplaySettings);
				}
			}
		}
	}

	@Override
	public String toMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

}