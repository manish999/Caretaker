package com.rampgreen.caretakermobile.network;
//package com.example.caretakermobile.network;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.json.JSONObject;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.Request.Method;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.example.caretakermobile.BaseActivity;
//import com.example.caretakermobile.MyVolley;
//import com.example.caretakermobile.util.AppLog;
//
//public class VolleyController implements HttpResponseHandler,
//HttpResponseHandler.TaskCallbacks, TaskExecuter.DoInBackground,
//Response.Listener<JSONObject>, Response.ErrorListener {
//
//	private Context context;
//	//	private ArrayList<AnalyticsResultBase> AnalyticsResults;
//	public ProgressDialog progressDialog;
//	//	private JsonParserCallBack activity;
//	private TaskCallbacks taskCallBackActivity;
//	private String accountToken;
//	private VollyReponseHandler activity;
//	private JsonObjectRequest jsonObjectRequest;
//
//	// send request and receive the response , send response to parsercontroller
//	public VolleyController(Context context, BaseActivity activity, String accountToken) {
//		this.context = context;
//		this.activity = activity;
//		this.accountToken = accountToken;
//	}
//
//	public String prepareQueryForRequest(String url, HashMap< String, String> paramMap, int volleyHTTPMethodType) 
//	{
//		if(volleyHTTPMethodType == Request.Method.GET) 
//		{
//			if(paramMap != null && paramMap.size() > 0 ) 
//			{
//				url = createGETUrl(url, paramMap);
//			}
//			jsonObjectRequest = new JsonObjectRequest(Method.GET, url, null, this, this);
//		} else if (volleyHTTPMethodType == Request.Method.POST) {
//			// extract key-value from hashmap  
//		} else {
//			// left implementation for delete and put method type 
//		}
//		return "";
//	}
//
//	/**
//	 * @param query
//	 * @param requestFor
//	 * @param isHandleOwnLoadingDialog if true, caller is handling own dialog by implementing HttpResponseHandler.TaskCallBack's methods 
//	 */
//	public void makeRequest(String query, int requestFor, boolean isHandleOwnLoadingDialog) {
//		if(isHandleOwnLoadingDialog) {
//			//			requester = new TaskExecuter(context, this, query, accountToken, requestFor);
//			TaskExecuter.run(context, this, query, accountToken, requestFor);
//		} else {
//			TaskExecuter.run(context, this, "Loading...", query, accountToken, requestFor);
//			//			requester = new TaskExecuter(context, this, "Loading...", query, accountToken, requestFor);
//		}
//	}
//
//	@Override
//	public void onResponseReceived(Object responseData, int fetchingType) {
//		//		send response to parser Controller and get the model
//		AppLog.showToast(context, "response receieved "+responseData.toString());
//		switch (fetchingType) {
//		case HttpResponseHandler.REQUEST_FOR_FETCHING_ACCOUNTS:
//			break;
//		case HttpResponseHandler.REQUEST_FOR_FETCHING_PROFILES:
//			break;
//		case HttpResponseHandler.REQUEST_FOR_FETCHING_ANALYTICS_DATA:
//			parseReceivedData(responseData);
//			// return data to Calling Activity
//			//			activity.getProfileParsedData(AnalyticsResults);
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
//	private void parseReceivedDataForAllResults(String data) {}
//
//	public void parseReceivedData (String data) {}
//
//	public void parseReceivedData (Object data) {
//		//		if(data instanceof String) {
//		//			if(typeDataOption == Constants.DASHBOARD_OVERVIEW)
//		//				parseReceivedDataForAllResults((String)data);
//		//			else
//		//				parseReceivedData((String)data);
//		//		}
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
//	@Override
//	public String backgroundTask(int requestFor) {
//		//		if(taskCallBackActivity != null) {
//		//			taskCallBackActivity.doInBackground();
//		//		}
//		RequestQueue queue = MyVolley.getRequestQueue();
//		switch (requestFor) {
//		case 0:
//
//			break;
//		case 1:
//
//			break;
//		case 2:
//
//			break;
//		case 3:
//
//			break;
//
//		default:
//			break;
//		}
//
//		String url = "http://echo.jsontest.com/key/value/one/two";
//
//		queue.add(jsonObjectRequest);
//		return "";
//	}
//
//	@Override
//	public void onErrorResponse(VolleyError error) {
//		Toast.makeText(context, "vollyerror", Toast.LENGTH_SHORT).show();
//	}
//
//	@Override
//	public void onResponse(JSONObject response) {
//		Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
//		activity.onResponseReceived(response, 0);
//	}
//
//	private String createGETUrl (String url, HashMap< String, String> paramMap) 
//	{ 
//		int index = 0;
//		for (Iterator<Entry<String, String>> it = paramMap.entrySet().iterator(); it.hasNext(); ++index )
//		{
//			Map.Entry entry = (Map.Entry) it.next();
//			String key = (String) entry.getKey();
//			String value = (String) entry.getValue();
//			if(index == 0) {
//				url = url + "?";
//			}  else {
//				url = url + "&";
//			}
//			url += key + "=" + value;
//		}
//		return url;
//	}
//}
