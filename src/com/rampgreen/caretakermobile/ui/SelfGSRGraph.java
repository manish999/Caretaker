package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.interfaces.IChart;
import com.rampgreen.caretakermobile.util.SensorValuesGSR;

public class SelfGSRGraph extends BaseActivity {

	private IChart[] mCharts = new IChart[] {new SensorValuesGSR()};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int length = mCharts.length;
		
		Intent intent = mCharts[0].execute(this); 
		startActivity(intent);
	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

}
