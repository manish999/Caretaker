package com.rampgreen.caretakermobile.network;
//package com.example.caretakermobile.network;
//
//import java.io.IOException;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//
//import com.example.caretakermobile.util.AppLog;
//import com.example.caretakermobile.util.StringUtils;
//
// abstract class CommonTaskExecuter  extends AsyncTask<String, String, String> {
//	
//	public static final String INTERNET_NOT_AVAILABLE = "internet";
//	private static int numAsyncTasks;
//	
//	private Context context;
//	private ProgressDialog progressDialog;
//	private String downloadTitle;
//	private String url;
//	private String data;
//	private HttpResponseHandler httpResponseHandler;
//	private HttpResponseHandler.TaskCallbacks taskCallBacks;
//	private String result;
//	
//	protected int requestFor;
//	protected TaskExecuter.DoInBackground backgroundTask;
//	
//	abstract protected String doInBackground();
//
//	public CommonTaskExecuter(Context context, VolleyController volleyController, String downloadTitle, String url, String data, int requestFor) {
//		this.context = context;
//		this.downloadTitle = downloadTitle;
//		this.url = url;
//		this.data = data;
//		httpResponseHandler = volleyController;
//		backgroundTask = volleyController;
//		this.requestFor = requestFor;
//	}
//
//	public CommonTaskExecuter(Context context, VolleyController volleyController, String url, String data, int requestFor) {
//		this.context = context;
//		this.url = url;
//		this.data = data;
//		httpResponseHandler = volleyController;
//		taskCallBacks = volleyController;
//		backgroundTask = volleyController;
//		this.requestFor = requestFor;
//	}
//	/**
//	 * Method doInBackground.
//	 * @param arg0 String[]
//	 * @return String
//	 */
//	protected String doInBackground(String... arg0) {
//		if(isCancelled())
//			return "";
////		if(requestFor == HttpResponseHandler.REQUEST_FOR_FETCHING_ANALYTICS_DATA)
////			return Http.makeHttpGetRequest(url, data);
////		else if(requestFor == HttpResponseHandler.REQUEST_FOR_FETCHING_ACCOUNTS)
////			return Http.getAccounts(url, data);
////		else if(requestFor == HttpResponseHandler.REQUEST_FOR_FETCHING_PROFILES)
////			return Http.getProperties(url, data);
////		else 
//			return doInBackground();
//	}
//
//	/**
//	 * Method onPostExecute.
//	 * @param response String
//	 */
//	protected void onPostExecute(String response) {
//		if (0 == --numAsyncTasks) {
//			if(progressDialog != null && progressDialog.isShowing())
//				progressDialog.dismiss();
//			else if(taskCallBacks != null)
//				taskCallBacks.onPostExecute();
//			if(StringUtils.notEmpty(response)) {
//				httpResponseHandler.onResponseReceived(response, requestFor);
//			} else {
//				httpResponseHandler.onError();
//			}
//		}
//	}
//
//	protected void onPreExecute() {
//		super.onPreExecute();
//		numAsyncTasks++;
//		if(! checkInternetConnection(context)) {
//			cancel(true); // this call leads to call onCancelled and onpostexecute will never be called. 
//		} else if( downloadTitle != null){
//			progressDialog = ProgressDialog.show(context, null, downloadTitle, true);
//		} else if(taskCallBacks != null)
//			taskCallBacks.onPreExecute();
//	}
//
//	// not called below API 11
//	@Override
//	protected void onCancelled(String result) {
//		handleCancelled(result);
//		if(taskCallBacks != null) {
//			--numAsyncTasks;
//			taskCallBacks.onCancelled();
//		}
//	}
//
//	@Override
//	protected void onCancelled() {
//		handleCancelled(this.result);
//		if(taskCallBacks != null) {
//			--numAsyncTasks;
//			taskCallBacks.onCancelled();
//		}
//	}
//	
//	@Override
//	protected void onProgressUpdate(String... values) {
//		super.onProgressUpdate(values);
//		if(taskCallBacks != null) {
//			taskCallBacks.onProgressUpdate(20);
//		}
//	}
//	
//	private void handleCancelled(String result) {
//		if(StringUtils.isEmpty(result)) {
//			AppLog.logToast(context, "Internet connection not available, please check the internet settings");
//			showSettingDialog();
//			httpResponseHandler.isInternetAvailable(false);
//		} else {
//			httpResponseHandler.isInternetAvailable(true);
//		}
//	}
//
//	private void showSettingDialog() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setMessage("You need a network connection to use this application. please turn on mobile network or Wi-Fi settings.")
//		.setCancelable(false)
//		.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//				dialog.dismiss();
//				context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
//			}
//		})
//		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//				dialog.dismiss();
//			}
//		});
//		builder.show();
//	}
//	
//	public static boolean checkInternetConnection(Context paramContext) {
//		//need manifest permission <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
//		boolean haveConnectedWifi = false;
//		boolean haveConnectedMobile = false;
//
//		ConnectivityManager cm = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
//		for (NetworkInfo ni : netInfo) {
//			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
//				if (ni.isConnected())
//					haveConnectedWifi = true;
//			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
//				if (ni.isConnected())
//					haveConnectedMobile = true;
//		}
//		return haveConnectedWifi || haveConnectedMobile;
//	}
//}
