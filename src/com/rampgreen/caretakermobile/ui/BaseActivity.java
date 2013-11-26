package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;

/**
 * Base activity class is used for future purposes. we can add functionality to all classes that will extend it. 
 * 
 * @author Manish Pathak
 *
 */
abstract public class BaseActivity extends FragmentActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

	private ProgressDialog progressDialog;
	private static int numAsyncTasks;

	protected void showLoadingBar(){
		numAsyncTasks++;
		progressDialog = ProgressDialog.show(this, null, "Loading...", true);
	}

	protected void closeLoadingBar(){
		if (0 == --numAsyncTasks) {
			if(progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}
	}

	@Override
	public void onBackPressed()
	{
		// cancel all requests when user press back button while loading bar is shown. 
		if(progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			MyRequestQueue queue = MyVolley.getRequestQueue();
			queue.cancelAll();
		}
		super.onBackPressed();

	}
	
	public void setActionBarTitle(String title) {
	    setTitle(title);
	}

}
