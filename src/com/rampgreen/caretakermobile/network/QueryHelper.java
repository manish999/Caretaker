package com.rampgreen.caretakermobile.network;

import java.util.HashMap;
import java.util.Map;

/**
 * This class helps to make a http request query
 * 
 *  @author Manish Pathak
 */
public class QueryHelper {

	private static Map<String, String> ConvertQueryToMap(Query paramQuery) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", paramQuery.getAction());
		params.put("task", paramQuery.getTask());
		return params;
	}

	public static Map<String, String> createLoginQuery(String userName, String passWord) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "user");
		params.put("task", "authenticate");
		params.put("username", userName);
		params.put("password", passWord);
		return params;
	}

	public static Map<String, String> createRegistrationQuery(String userName, String salutation, String firstName, String middleName, String lastName, String password,String dateOfBirth, String height, String weight, String gender) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "user");
		params.put("task", "register");
		params.put("username", userName);
//		params.put("salutation", salutation);
//		params.put("firstname", firstName);
//		params.put("middlename", middleName);
		params.put("firstname", lastName);
		params.put("password", password);
		params.put("dob", dateOfBirth);
		params.put("height", height);
		params.put("weight", weight);
		params.put("gender", gender);
		return params;
	}
	
	public static Map<String, String> createUserProfileQuery(String accessToken) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "user");
		params.put("task", "get_user_profile");
		params.put("token", accessToken);
		return params;
	}
	
	public static Map<String, String> createCareTakerProfileQuery(String accessToken) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "caretaker");
		params.put("task", "get_caretaker_profile");
		params.put("token", accessToken);
		return params;
	}
	
	/**
	 * get all users(grandma, grandpa etc) associated of login user
	 * 
	 * @param accessToken
	 * @return 
	 */
	public static Map<String, String> createAllUsersQuery(String accessToken) {
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("action", "caretaker");
		params.put("task", "get_users");
		params.put("token", accessToken);
		return params;
	}
	
	public static Map<String, String> createAddUserIconQuery(String accessToken, String requestId, String biometricValue) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "caretaker");
		params.put("task", "edit_settings");
		params.put("token", accessToken);
		params.put("request_id", requestId);
		params.put("biometric_val", biometricValue);
		params.put("type", "homescreen");
		return params;
	}
	
	public static Map<String, String> createAddNotificationQuery(String accessToken, String requestId, String biometricValue) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "caretaker");
		params.put("task", "edit_settings");
		params.put("token", accessToken);
		params.put("request_id", requestId);
		params.put("biometric_val", biometricValue);
		params.put("type", "notification");
		return params;
	}
	
	public static Map<String, String> createAddTextDisplayQuery(String accessToken, String requestId, String biometricId, String biometricValue) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "caretaker");
		params.put("task", "edit_settings");
		params.put("token", accessToken);
		params.put("request_id", requestId);
		params.put("biometric_id", biometricId);
		params.put("biometric_val", biometricValue);
		params.put("type", "text");
		return params;
	}
	
	public static Map<String, String> createAddVisualDisplayQuery(String accessToken, String requestId, String biometricId, String biometricValue) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("action", "caretaker");
		params.put("task", "edit_settings");
		params.put("token", accessToken);
		params.put("request_id", requestId);
		params.put("biometric_id", biometricId);
		params.put("biometric_val", biometricValue);
		params.put("type", "visual");
		return params;
	}

}