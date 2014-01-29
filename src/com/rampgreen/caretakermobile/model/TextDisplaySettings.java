package com.rampgreen.caretakermobile.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rampgreen.caretakermobile.interfaces.Populator;

import java.util.ArrayList;

public class TextDisplaySettings extends BaseDeleteSettings implements
		Populator {
	private String displayMsg;
	private Object obj_biometricValues;
	private JSONArray textbiometricValuesArray;
	private String gsr = "";
	private String pox_pulse = "";
	private String pox_hr = "";
	private String temp_ambient = "";
	private String temp_skin = "";
	private String acc_x = "";
	private String acc_y = "";
	private String acc_z = "";
	private String led1ac = "";
	private String led2ac = "";
	private String led1dc = "";
	private String led2dc = "";

	// private ArrayList<TextDisplaySettings> textDisplaySettingList = new
	// ArrayList<TextDisplaySettings>();;

	public TextDisplaySettings() {
		super("");// only called for populating the been on response
	}

	public TextDisplaySettings(String userId, String diseaseType) {
		super(userId, diseaseType);

	}

	public TextDisplaySettings(String userId, String diseaseType,
			String uniqueId) {
		super(userId, diseaseType, uniqueId);
	}

	// Add by ishu
//	public TextDisplaySettings(String userId, String diseaseType,
//			String uniqueId, String gsr, String temp_ambient, String temp_skin,String pox_hr,String pox_pulse,String acc_x,String acc_y,String acc_z) {
//		super(userId, diseaseType, uniqueId, gsr, temp_ambient, temp_skin,pox_hr,pox_pulse,acc_x,acc_y,acc_z);
//	}

	// end

	public TextDisplaySettings(String userId, String diseaseType,
			String uniqueId, String displayMsg) {
		this(userId, diseaseType, uniqueId);
		this.displayMsg = displayMsg;
	}

	public String getDisplayMsg() {
		return displayMsg;
	}

	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}

	@Override
	public ArrayList<?> getSettingList() {
		return null;
		// return textDisplaySettingList;
	}

	@Override
	public void populateBean(JSONObject jsonObject) {
		String userId;
		JSONArray jsonUserArray;
		try {
			// it should be refilled each time response came.
			ListHolder.getTextDisplaySettingList().clear();
			jsonUserArray = jsonObject.getJSONArray("users");
			for (int i = 0; i < jsonUserArray.length(); i++) {
				JSONObject jsonObjUserAndSetting = jsonUserArray
						.getJSONObject(i);
				JSONObject jsonObjUser = jsonObjUserAndSetting
						.getJSONObject("user");
				JSONObject jsonSettingObj = jsonObjUserAndSetting
						.getJSONObject("settings");
//				JSONObject jsonbiometricObj = null;
//				if (jsonObjUserAndSetting.isNull("sensor_values")) {
//
//				} else {
//					jsonbiometricObj = jsonObjUserAndSetting
//							.getJSONObject("sensor_values");
//					// obj_biometricValues =
//					// jsonbiometricObj.get("sensor_values");
//				}
				// else{jsonbiometricObj =
				// jsonObjUserAndSetting.getJSONObject("sensor_values");
				// obj_biometricValues = jsonSettingObj.get("sensor_values");}

				userId = jsonObjUser.getString("u_id");
				Object obj = jsonSettingObj.get("textsetting");
				// if(jsonSettingObj.get("sensor_values") != null){
				// obj_biometricValues = jsonSettingObj.get("sensor_values");
				// }

				try {
					if (obj == null || obj instanceof JSONObject) {
						// text display setting would be null, reset all text
						// settings
					} else {
						JSONArray textSettingArray = (JSONArray) obj;
//						if (jsonbiometricObj != null) {
//							textbiometricValuesArray = new JSONArray();
//							textbiometricValuesArray.put(jsonbiometricObj);
//						}
//						parseTextDisplaySetting(textSettingArray,
//								textbiometricValuesArray, userId);
						parseTextDisplaySetting(textSettingArray,userId);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				try {
					obj = jsonSettingObj.get("visualsetting");
					if (obj == null || obj instanceof JSONObject) {
						// visual display setting would be null, reset all
						// visual settings
					} else {
						JSONArray visualSettingArray = (JSONArray) obj;
						// parseVisualDisplaySetting(visualSettingArray,
						// userId);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String toMap() {
		// TODO Auto-generated method stub
		return null;
	}

	private void parseTextDisplaySetting(JSONArray textSettingArray,String userId)
			throws JSONException {
		// parsing user's dashboared TextDisplay settings JSONArray textbiometricValuesArray

		for (int index = 0; index < textSettingArray.length(); index++) {
			JSONObject jsonObjSetting = textSettingArray.getJSONObject(index);
			String disease = jsonObjSetting.getString("biometric_name");
			String textSetting = jsonObjSetting.getString("textsetting");
			String uniqueIdForDeletion = jsonObjSetting.getString("unique_id");
			String biometricid_pk = jsonObjSetting.getString("biometric_id");

//			if (textbiometricValuesArray != null) {
//			
//					JSONObject jsonObjSettingBV = textbiometricValuesArray
//							.getJSONObject(0);
//					gsr = jsonObjSettingBV.getString("gsr");
//					pox_pulse = jsonObjSettingBV.getString("pox_pulse");
//					pox_hr = jsonObjSettingBV.getString("pox_hr");
//					temp_ambient = jsonObjSettingBV.getString("temp_ambient");
//					temp_skin = jsonObjSettingBV.getString("temp_skin");
//					acc_x = jsonObjSettingBV.getString("acc_x");
//					acc_y = jsonObjSettingBV.getString("acc_y");
//					acc_z = jsonObjSettingBV.getString("acc_z");
//					led1ac = jsonObjSettingBV.getString("led1ac");
//					led2ac = jsonObjSettingBV.getString("led2ac");
//					led1dc = jsonObjSettingBV.getString("led1dc");
//					led2dc = jsonObjSettingBV.getString("led2dc");
//				}

				boolean diseaseValue;
				if (disease.equalsIgnoreCase("GSR")) {
					// diseaseValue = textSetting.equalsIgnoreCase("1") ? true :
					// false;
					if (textSetting.equalsIgnoreCase("1")) {
						TextDisplaySettings textDisplaySettings = new TextDisplaySettings(
								userId, disease, uniqueIdForDeletion);
						
//						TextDisplaySettings textDisplaySettings = new TextDisplaySettings(
//								userId, disease, uniqueIdForDeletion, gsr, "",
//								"","","","","","");
						ArrayList<TextDisplaySettings> tdSettingList = ListHolder
								.getTextDisplaySettingList();
						tdSettingList.add(textDisplaySettings);
					}
					// user.gsrVisualDisplay =
					// visualSetting.equalsIgnoreCase("1") ? true : false;

				} else if (disease.equalsIgnoreCase("Heart Rate")) {
					if (textSetting.equalsIgnoreCase("1")) {
						TextDisplaySettings textDisplaySettings = new TextDisplaySettings(
								userId, disease, uniqueIdForDeletion);
//						TextDisplaySettings textDisplaySettings = new TextDisplaySettings(
//								userId, disease, uniqueIdForDeletion,
//								"", "",
//								"",pox_hr,pox_pulse,"","","");
						ArrayList<TextDisplaySettings> tdSettingList = ListHolder
								.getTextDisplaySettingList();
						tdSettingList.add(textDisplaySettings);
					}
					// user.heartRateTextDisplay =
					// textSetting.equalsIgnoreCase("1") ? true : false;
					// user.heartRateVisualDisplay =
					// visualSetting.equalsIgnoreCase("1") ? true : false;

				} else if (disease.equalsIgnoreCase("SPO2")) {
					if (textSetting.equalsIgnoreCase("1")) {
						TextDisplaySettings textDisplaySettings = new TextDisplaySettings(
								userId, disease, uniqueIdForDeletion);
//						TextDisplaySettings textDisplaySettings = new TextDisplaySettings(
//								userId, disease, uniqueIdForDeletion, "", "",
//								"","","",acc_x,
//								acc_y, acc_z);
						ArrayList<TextDisplaySettings> tdSettingList = ListHolder
								.getTextDisplaySettingList();
						tdSettingList.add(textDisplaySettings);
					}
					// user.accelerometerTextDisplay =
					// textSetting.equalsIgnoreCase("1") ? true : false;
					// user.accelerometerVisualDisplay =
					// visualSetting.equalsIgnoreCase("1") ? true : false;

				} else if (disease.equalsIgnoreCase("Temperature_ambient")) {
					if (textSetting.equalsIgnoreCase("1")) {
						TextDisplaySettings textDisplaySettings = new TextDisplaySettings(
								userId, disease, uniqueIdForDeletion);
//						TextDisplaySettings textDisplaySettings = new TextDisplaySettings(
//								userId, disease, uniqueIdForDeletion,"", temp_ambient,
//								temp_skin,"","","",
//								"", "");
						ArrayList<TextDisplaySettings> tdSettingList = ListHolder
								.getTextDisplaySettingList();
						tdSettingList.add(textDisplaySettings);
						tdSettingList = ListHolder.getTextDisplaySettingList();
					}
					// user.tempratureTextDisplay =
					// textSetting.equalsIgnoreCase("1") ? true : false;
					// user.tempratureVisualDisplay =
					// visualSetting.equalsIgnoreCase("1") ? true : false;

				}			
			}
		}
	}

	// private void parseVisualDisplaySetting(JSONArray textSettingArray, String
	// userId) throws JSONException {
	// // parsing user's dashboared TextDisplay settings
	// for(int index=0; index < textSettingArray.length(); index++){
	// JSONObject jsonObjSetting = textSettingArray.getJSONObject(index);
	// String disease = jsonObjSetting.getString("biometric_name");
	// String visualSetting = jsonObjSetting.getString("visualsetting");
	// String uniqueIdForDeletion = jsonObjSetting.getString("unique_id");
	// String biometricid_pk = jsonObjSetting.getString("biometric_id");
	// boolean diseaseValue;
	// if(disease.equalsIgnoreCase("GSR")) {
	// // diseaseValue = textSetting.equalsIgnoreCase("1") ? true : false;
	// if(visualSetting.equalsIgnoreCase("1")) {
	// TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId,
	// disease);
	// ArrayList<TextDisplaySettings> tdSettingList =
	// ListHolder.getTextDisplaySettingList();
	// tdSettingList.add(textDisplaySettings);
	// }
	// // user.gsrVisualDisplay = visualSetting.equalsIgnoreCase("1") ? true :
	// false;
	//
	// } else if(disease.equalsIgnoreCase("Heart Rate")) {
	// if(visualSetting.equalsIgnoreCase("1")) {
	// TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId,
	// disease);
	// ArrayList<TextDisplaySettings> tdSettingList =
	// ListHolder.getTextDisplaySettingList();
	// tdSettingList.add(textDisplaySettings);
	// }
	// // user.heartRateTextDisplay = textSetting.equalsIgnoreCase("1") ? true :
	// false;
	// // user.heartRateVisualDisplay = visualSetting.equalsIgnoreCase("1") ?
	// true : false;
	//
	// } else if(disease.equalsIgnoreCase("Accelerometer")) {
	// if(visualSetting.equalsIgnoreCase("1")) {
	// TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId,
	// disease);
	// ArrayList<TextDisplaySettings> tdSettingList =
	// ListHolder.getTextDisplaySettingList();
	// tdSettingList.add(textDisplaySettings);
	// }
	// // user.accelerometerTextDisplay = textSetting.equalsIgnoreCase("1") ?
	// true : false;
	// // user.accelerometerVisualDisplay = visualSetting.equalsIgnoreCase("1")
	// ? true : false;
	//
	// } else if(disease.equalsIgnoreCase("Temperature")) {
	// if(visualSetting.equalsIgnoreCase("1")) {
	// TextDisplaySettings textDisplaySettings = new TextDisplaySettings(userId,
	// disease);
	// ArrayList<TextDisplaySettings> tdSettingList =
	// ListHolder.getTextDisplaySettingList();
	// tdSettingList.add(textDisplaySettings);
	// // tdSettingList = ListHolder.getTextDisplaySettingList();
	// }
	// // user.tempratureTextDisplay = textSetting.equalsIgnoreCase("1") ? true
	// : false;
	// // user.tempratureVisualDisplay = visualSetting.equalsIgnoreCase("1") ?
	// true : false;
	//
	// }
	// }
	// }
