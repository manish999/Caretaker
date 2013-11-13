package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;

public class HomeActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_underdevelopment);
		Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();

        String strfname = myBundle.getString("title");
		setTitle(strfname);
//		AppLog.showToast(this, "Home screen is under development.");
	}

	@Override
	public void onResponse(JSONObject response)
	{

	}

	@Override
	public void onErrorResponse(VolleyError error)
	{

	}

}
