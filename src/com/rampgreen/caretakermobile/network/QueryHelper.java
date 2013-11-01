package com.rampgreen.caretakermobile.network;

import java.util.HashMap;
import java.util.Map;

import com.rampgreen.caretakermobile.model.GeneralData;

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
}