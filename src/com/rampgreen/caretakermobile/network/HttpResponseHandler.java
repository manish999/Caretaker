package com.rampgreen.caretakermobile.network;

/**
 * @author Manish Pathak
 *
 *This interface is used to handle the http response
 *
 */
public interface HttpResponseHandler {

	public static final int REQUEST_FOR_FETCHING_ANALYTICS_DATA = 0;
	public static final int REQUEST_FOR_FETCHING_ACCOUNTS = 1;
	public static final int REQUEST_FOR_FETCHING_PROFILES = 2;
	
	void onResponseReceived(Object responseData, int fetchingType);
	void onError();
	void isInternetAvailable(boolean availablity);
	
	public interface TaskCallbacks {
		void onPreExecute();
		void onProgressUpdate(int percent);
		void onCancelled();
		void onPostExecute();
	}
}
