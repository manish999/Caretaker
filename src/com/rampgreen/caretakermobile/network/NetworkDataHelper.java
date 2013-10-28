package com.rampgreen.caretakermobile.network;
//package in.rampgreen.analytics.network;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//
//import com.example.caretakermobile.BaseActivity;
//import com.example.caretakermobile.util.AppLog;
//import com.example.caretakermobile.util.AppSettings;
//import com.example.caretakermobile.util.Constants;
//import com.example.caretakermobile.util.StringUtils;
//
//public class NetworkDataHelper implements HttpResponseHandler, HttpResponseHandler.TaskCallbacks{
//
//	public static final String BUNDLE_KEY_QUERY = "BUNDLE_KEY_QUERY";
//	public static final String BUNDLE_KEY_REQUESTFOR = "BUNDLE_KEY_REQUESTFOR";
//	public static final String BUNDLE_KEY_ACCOUNT_TOKEN = "BUNDLE_KEY_ACCOUNT_TOKEN";
//	
//	public static final int TYPE_JSON_TOTALS_FORALLRESULTS = 0;
//	public static final int TYPE_JSON_ARRAY_TOTALS_ROWS = 1;
//
//	private Context context;
//	private ArrayList<AnalyticsResultBase> AnalyticsResults;
//	public ProgressDialog progressDialog;
//	private JsonParserCallBack activity;
//	private TaskCallbacks taskCallBackActivity;
//	private String profileID;
//	private String accountToken;
//
////	public NetworkDataHelper(Context context, BaseListActivity activity, String profileID, String accountToken) {
////		this.context = context;
////		this.activity = activity;
////		this.profileID = profileID;
////		this.accountToken = accountToken;
////	}
//
//	public NetworkDataHelper(Context context, BaseActivity activity, String profileID, String accountToken) {
//		this.context = context;
//		this.activity = activity;
//		this.profileID = profileID;
//		this.accountToken = accountToken;
//	}
//
////	public NetworkDataHelper(Context context, BaseSherlockFragment activity, String profileID, String accountToken) {
////		this.context = context;
////		this.activity = activity;
////		this.profileID = profileID;
////		this.accountToken = accountToken;
////		taskCallBackActivity = activity;
////	}
//
//	public NetworkDataHelper(Context context, BaseActivity activity) {
//		this.context = context;
//		this.activity = activity;
//	}
//
//	//	public NetworkDataHelper(Context context, BaseActivityGroup activity,  String profileID, String accountToken) {
//	//		this.context = context;
//	//		this.activity = activity;
//	//		this.profileID = profileID;
//	//		this.accountToken = accountToken;
//	//	}
//	int typeDataOption;
//	public String prepareQueryForRequest(int switchCaseOptions) {
//		typeDataOption = switchCaseOptions;
//		String filter = "";
//		String queryString = null;
//		String startdate = (String) AppSettings.getPrefernce(context, null, AppSettings.START_DATE, "");
//		String enddate = (String) AppSettings.getPrefernce(context, null, AppSettings.END_DATE, "");
//		String maxResults = (String) AppSettings.getPrefernce(context, null, AppSettings.MAX_RESULT, "500");
//		switch (switchCaseOptions) {
//		case Constants.DASHBOARD_OVERVIEW:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_Charts_Overview();
//			break;
//		case Constants.DASHBOARD_VISITS:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_Charts();
//			break;
//		case Constants.DASHBOARD_VISITORS:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_Charts();
//			break;
//		case Constants.DASHBOARD_PAGEVIEWS:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_Charts();
//			break;
//		case Constants.DASHBOARD_PAGEPERVISITS:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_Charts();
//			break;
//		case Constants.DASHBOARD_AVG_VISIT_DURATION:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_Charts();
//			break;
//		case Constants.DASHBOARD_BOUNCERATE:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_Charts();
//			break;
//		case Constants.DASHBOARD_NEW_VISITS:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_Charts();
//			break;
//			
//		case Constants.DEMOGRAPHIC_LANGUAGE:
//			queryString = QueryHelper.SystemQuery.GA_Query_Demograph_language();
//			break;
//		case Constants.DEMOGRAPHIC_COUNTRY:
//			queryString = QueryHelper.WidgetQuery.GA_Query_Country();
//			break;
//		case Constants.DEMOGRAPHIC_CITY:
//			queryString = QueryHelper.WidgetQuery.GA_Query_City();
//			break;
//			//system
//		case Constants.SYSTEM_BROSWSER:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_System_Browser_Charts();
//			break;
//		case Constants.SYSTEM_FALSH:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_System_Flash_Charts();
//			break;
//		case Constants.SYSTEM_OS:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_System_OS_Charts();
//			break;
//		case Constants.SYSTEM_JAVASUPPORT:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_System_Java_Charts();
//			break;
//		case Constants.SYSTEM_SERVICEPRO:
//			queryString = QueryHelper.DashBordQuery.GA_Query_Dashbord_System_ServicePro_Charts();
//			break;
//		case Constants.MOBILE_DEVICES:
//			queryString = QueryHelper.MobileQuery.GA_Query_Mobile_Device_Charts();
//			break;
//		case Constants.MOBILE__ISMOBILE:
//			queryString = QueryHelper.MobileQuery.GA_Query_Mobile_IsMobile();
//			break;
//		case Constants.MOBILE_OS:
//			queryString = QueryHelper.MobileQuery.GA_Query_Mobile_OS();
//			break;
//		case Constants.MOBILE_SERVICEPRO:
//			queryString = QueryHelper.MobileQuery.GA_Query_Mobile_ServicePro();
//			break;
//		case Constants.MOBILE_SCREENRES:
//			queryString = QueryHelper.MobileQuery.GA_Query_Mobile_ScreenRes();
//			break;
////		case Constants.VISITED_BY_COUNTRY:
////			queryString = QueryHelper.VisitQuery.GA_Query_Visits_By_Country();
////			break;
////		case Constants.VISITED_BY_CITY:
////			queryString = QueryHelper.WidgetQuery.GA_Query_City();
////			break;
////		case Constants.VISITED_BY_BROWSER:
////			queryString = QueryHelper.WidgetQuery.GA_Query_Browsers();
////			break;
//		case Constants.IMPRESSIONS_BY_DATE:
//			queryString = QueryHelper.AdWordsQuery.GA_Query_Impressions();
//			break;
//		case Constants.AD_MATHCED_QUERY:
//			queryString = QueryHelper.AdWordsQuery.GA_Query_MatchedQuery();
//			break;
//		case Constants.REPORTS_CAMPAIGN:
//			queryString = QueryHelper.RampReportsQuery.GA_Query_CampaignReport();
//			break;
//		case Constants.REPORTS_SEARCH_TERM:
//			queryString = QueryHelper.RampReportsQuery.GA_Query_SearchTermReport();
//			filter = filter+"&filters=ga:adClicks%3E0";
//			break;
//		case Constants.REPORTS_USER_LOCATION:
//			queryString = QueryHelper.RampReportsQuery.GA_Query_UserLocationReport();
//			break;
//
//		default:
//			break;
//		}
//		Object[] arrayOfObject = new Object[4];
//		arrayOfObject[0] =profileID;
//		arrayOfObject[1] = startdate;//VisitListActivity.this.correctedStartDateForQuery;
//		arrayOfObject[2] = enddate;//VisitListActivity.this.correctedEndDateForQuery;
//		arrayOfObject[3] = Integer.valueOf(maxResults);
//		AppLog.e(queryString);
//		//https://www.googleapis.com/analytics/v3/data/ga?ids=%s&dimensions=ga:date&metrics=ga:visitors,ga:visits, ,ga:newVisits,ga:visitBounceRate,ga:pageviews,ga:pageviewsPerVisit&sort=-ga:date&start-date=%s&end-date=%s&max-results=%s
//		String str = String.format(queryString, arrayOfObject)+filter;
//		AppLog.e(str);
//		return str;
//	}
//
//	/**
//	 * @param query
//	 * @param requestFor
//	 * @param isHandleOwnLoadingDialog if true, caller is handling own dialog by implementing HttpResponseHandler.TaskCallBack's methods 
//	 */
//	public void makeRequest(String query, int requestFor, boolean isHandleOwnLoadingDialog) {
//		TaskExecuter requester;
//		if(isHandleOwnLoadingDialog) {
//			requester = new TaskExecuter(context, this, query, accountToken, requestFor);
//		} else {
//			requester = new TaskExecuter(context, this, "Loading...", query, accountToken, requestFor);
//		}
//		//		Bundle bundle = new Bundle();
//		//		FragmentTask fragmentTask = new FragmentTask();
//		//		bundle.putString(BUNDLE_KEY_QUERY, query);
//		//		bundle.putInt(BUNDLE_KEY_REQUESTFOR, requestFor);
//		//		bundle.putString(BUNDLE_KEY_ACCOUNT_TOKEN, accountToken);
//		//		fragmentTask.setArguments(bundle);
//
//		//		DownloadProfiles requester = new DownloadProfiles(context, this, query, accountToken, requestFor);
//		requester.execute();
//	}
//
//	@Override
//	public void onResponseReceived(Object responseData, int fetchingType) {
//		switch (fetchingType) {
//		case HttpResponseHandler.REQUEST_FOR_FETCHING_ACCOUNTS:
//			break;
//		case HttpResponseHandler.REQUEST_FOR_FETCHING_PROFILES:
//			break;
//		case HttpResponseHandler.REQUEST_FOR_FETCHING_ANALYTICS_DATA:
//				parseReceivedData(responseData);
//			// return data to Calling Activity
//			activity.getProfileParsedData(AnalyticsResults);
//			break;
//		default:
//			break;
//		}
//
//	}
//
//	@Override
//	public void onError() {
//
//	}
//
//	private void parseReceivedDataForAllResults(String data) {
//
//		// data that is fetched from google analytics 
//		HashMap<Integer, HashMap<String, String>> outerHashMap;
//		AnalyticsResults = new ArrayList<AnalyticsResultBase>();
//		AnalyticsResultBase analyticsResultBase ;
//		try {
//			JSONObject localJSONObject = new JSONObject(data);
//			JSONArray jsonArrayRows = (JSONArray) localJSONObject.get("rows");
//			JSONArray jsonArrayColHeaders = (JSONArray) localJSONObject.get("columnHeaders");
//			JSONObject jsonObjectTotalResults = (JSONObject) localJSONObject.get("totalsForAllResults");
//			HashMap<String, String> innerHashMap = new HashMap<String, String>();
//			if (jsonObjectTotalResults != null) {
//				for (int j = 0;j < jsonArrayColHeaders.length(); j++) {
//					String name = jsonArrayColHeaders.getJSONObject(j).getString("name");
//					String value = jsonObjectTotalResults.optString(name);
//					if(StringUtils.notEmpty(value) && StringUtils.notEmpty(name)) {
//						innerHashMap.put(name, value);
//						AppLog.d("HASHMAP", name + " : " + value);
//					}
//				}
//				analyticsResultBase = JsonFactory.GetResultFromJson(innerHashMap);
//				AnalyticsResults.add(analyticsResultBase);
////			if (jsonArrayRows.length() > 0) {
////				for(int index = 0; index < jsonArrayRows.length(); index++){
////					JSONArray localJSONArray3 = jsonArrayRows.getJSONArray(index);
////					HashMap<String, String> innerHashMap = new HashMap<String, String>();
////					for (int j = 0;j < localJSONArray3.length(); j++) {
////						innerHashMap.put(jsonArrayColHeaders.getJSONObject(j).getString("name"), localJSONArray3.getString(j));
////					}
////					outerHashMap.put(Integer.valueOf(index), innerHashMap);
////				}
////				Set<Entry<Integer, HashMap<String, String>>> set = outerHashMap.entrySet();
////				Iterator<Entry<Integer, HashMap<String, String>>> it = set.iterator();
////				while (it.hasNext()) {
////					Map.Entry<Integer, HashMap<String, String>> entry = it.next();
////					AppLog.d("HASHMAP", entry.getKey() + " : " + entry.getValue());
////					analyticsResultBase = JsonFactory.GetResultFromJson(entry.getValue());
////					AnalyticsResults.add(analyticsResultBase);
////				} 
//			} else {
//				AppLog.e("GA response error");
//			}
//		} 
//		catch (JSONException e) {
//			AppLog.e(e.getMessage());
//		}
//		catch (Exception localException) {
//			AppLog.e(localException.getMessage());
//			AppLog.e("GA response error");
//		}
//		AppLog.d("Analytics", AnalyticsResults.toArray().toString());
//	
//		
//	}
//	
//	public void parseReceivedData (String data) {
//		// data that is fetched from google analytics 
//		HashMap<Integer, HashMap<String, String>> outerHashMap;
//		AnalyticsResults = new ArrayList<AnalyticsResultBase>();
//		AnalyticsResultBase analyticsResultBase ;
//		try {
//			JSONObject localJSONObject = new JSONObject(data);
//			JSONArray jsonArrayRows = (JSONArray) localJSONObject.get("rows");
//			JSONArray jsonArrayColHeaders = (JSONArray) localJSONObject.get("columnHeaders");
//			JSONObject jsonObjectTotalResults = (JSONObject) localJSONObject.get("totalsForAllResults");
//			outerHashMap = new HashMap<Integer, HashMap<String, String>>();
//			if (jsonArrayRows.length() > 0) {
//				for(int index = 0; index < jsonArrayRows.length(); index++){
//					JSONArray localJSONArray3 = jsonArrayRows.getJSONArray(index);
//					HashMap<String, String> innerHashMap = new HashMap<String, String>();
//					for (int j = 0;j < localJSONArray3.length(); j++) {
//						innerHashMap.put(jsonArrayColHeaders.getJSONObject(j).getString("name"), localJSONArray3.getString(j));
//					}
//					outerHashMap.put(Integer.valueOf(index), innerHashMap);
//				}
//				Set<Entry<Integer, HashMap<String, String>>> set = outerHashMap.entrySet();
//				Iterator<Entry<Integer, HashMap<String, String>>> it = set.iterator();
//				while (it.hasNext()) {
//					Map.Entry<Integer, HashMap<String, String>> entry = it.next();
//					AppLog.d("HASHMAP", entry.getKey() + " : " + entry.getValue());
//					analyticsResultBase = JsonFactory.GetResultFromJson(entry.getValue());
//					AnalyticsResults.add(analyticsResultBase);
//				} 
//			} else {
//				AppLog.e("GA response error");
//			}
//		} 
//		catch (JSONException e) {
//			AppLog.e(e.getMessage());
//		}
//		catch (Exception localException) {
//			AppLog.e(localException.getMessage());
//			AppLog.e("GA response error");
//		}
//		AppLog.d("Analytics", AnalyticsResults.toArray().toString());
//	}
//
//	public void parseReceivedData (Object data) {
//		if(data instanceof String) {
//			if(typeDataOption == Constants.DASHBOARD_OVERVIEW)
//				parseReceivedDataForAllResults((String)data);
//			else
//				parseReceivedData((String)data);
//		}
//
//	}
//
//	@Override
//	public void isInternetAvailable(boolean availablity) {
//
//	}
//
//	@Override
//	public void onPreExecute() {
//		if(taskCallBackActivity != null) {
//			taskCallBackActivity.onPreExecute();
//		}
//	}
//
//	@Override
//	public void onProgressUpdate(int percent) {
//		if(taskCallBackActivity != null) {
//			taskCallBackActivity.onProgressUpdate(percent);
//		}
//
//	}
//
//	@Override
//	public void onCancelled() {
//		if(taskCallBackActivity != null) {
//			taskCallBackActivity.onCancelled();
//		}
//
//	}
//
//	@Override
//	public void onPostExecute() {
//		if(taskCallBackActivity != null) {
//			taskCallBackActivity.onPostExecute();
//		}
//	}
//
//}
//
