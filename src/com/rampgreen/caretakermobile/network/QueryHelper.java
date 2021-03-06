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
	// ishu 
	
	public static Map<String, String> getRecivedQuery(String token) {
		HashMap<String, String> params = new HashMap<String, String>();	
		params.put("action", "caretaker");
		params.put("task", "get_caretaker_requests_recieved");		
		params.put("token", token);
		return params;
	}
	
	public static Map<String, String> getSentQuery(String token) {
		HashMap<String, String> params = new HashMap<String, String>();	
		params.put("action", "caretaker");
		params.put("task", "get_caretaker_requests_sent");		
		params.put("token", token);
		return params;
	}
	
	public static Map<String, String> responseAcceptQuery(String token,String request_id) {
		HashMap<String, String> params = new HashMap<String, String>();	
		params.put("action", "caretaker");
		params.put("task", "caretaker_response_accept");		
		params.put("token", token);
		params.put("request_id", request_id);
		return params;
	}
	
	public static Map<String, String> responseDeleteQuery(String token,String request_id) {
		HashMap<String, String> params = new HashMap<String, String>();	
		params.put("action", "caretaker");
		params.put("task", "caretaker_response_delete");		
		params.put("token", token);
		params.put("request_id", request_id);
		return params;
	}
	
	public static Map<String, String> responseIgnoreQuery(String token,String request_id) {
		HashMap<String, String> params = new HashMap<String, String>();	
		params.put("action", "caretaker");
		params.put("task", "caretaker_response_ignore");		
		params.put("token", token);
		params.put("request_id", request_id);
		return params;
	}
	
	public static Map<String, String> caretakerRequestSendQuery(String token,String emailid) {
		HashMap<String, String> params = new HashMap<String, String>();	
		params.put("action", "caretaker");
		params.put("task", "caretaker_request_add");		
		params.put("token", token);
		params.put("emailid", emailid);
		return params;
	}
	
	public static Map<String, String> getSensorDataQuery(String accessToken,String userid,String fromDate,String toDate) {
		HashMap<String, String> params = new HashMap<String, String>();	
		
		params.put("action", "sensor");
		params.put("task", "get_sensor_data");		
		params.put("from", fromDate);
		params.put("to", toDate);
		params.put("token", accessToken);
		params.put("user_id", userid);
		return params;
	}	
	
	// ishu //
	// rajeev
	public static Map<String, String> getMyCaretakersQuery(String accessToken) {
		HashMap<String, String> params = new HashMap<String, String>();	
		
		params.put("action", "caretaker");
		params.put("task", "get_caretaker_list");		
		params.put("token", accessToken);	
		return params;
	}
	public static Map<String, String> getMyUsersQuery(String accessToken) {
		HashMap<String, String> params = new HashMap<String, String>();	
		params.put("action", "caretaker");
		params.put("task", "get_users");		
		params.put("token", accessToken);	
		return params;
	}	
	
	public static Map<String, String> getMyCaretakersUsersQuery(String token) {
		HashMap<String, String> params = new HashMap<String, String>();	
		
		params.put("action", "caretaker");
		params.put("task", "get_caretaker_user_list");		
		//params.put("token", "b1916c6daa00b1d5d2297166008f3a7c4825e6f8");
		params.put("token", token);
		return params;
	}	
	
//	http://121.240.116.173/mwservice/index.php?
//		name=param&email=paramgir@gmail.com&registration_id=dabcdef&token=c632cebd15d3b5a9896227d0526ad46eecc8d451
	public static Map<String, String> createGCMRegQuery(String token, String name, String email, String registration_id) {
		HashMap<String, String> params = new HashMap<String, String>();	
		params.put("action", "gcm");
		params.put("task", "register_gcm_user");	
		params.put("name", name);
		params.put("email", email);
//		params.put("userid", userID);
		params.put("registration_id", registration_id);
		params.put("token", token);
		return params;
	}	
	public static Map<String, String>  changeAlertSetting(String token,String userId,String onOrOff,String type) {
		HashMap<String, String> params = new HashMap<String, String>();	
		
		params.put("action", "caretaker");
		params.put("task", "edit_notifications");		
		//params.put("token", "004b19a364e30cf4ebe5f651192724f4f72fae9f");
		params.put("token", token);
		params.put("user_id", userId);
		params.put("notification_val", onOrOff);
		
		params.put("type", type);
		
		
		
		return params;
	}	
	public static Map<String, String>  getAlertSettings(String token,String userId) {
		HashMap<String, String> params = new HashMap<String, String>();	
		
		params.put("action", "caretaker");
		params.put("task", "get_alerts");		
		params.put("token", token);
		params.put("user_id", userId);

		
		return params;
	}
	// rajeev//
	
}