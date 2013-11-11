package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.AppLog;

public class ActivityNetwork extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_underdevelopment);
		AppLog.showToast(this, "Home screen is under development.");
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
