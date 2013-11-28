package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost.OnTabChangeListener;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.util.Constants;

public class ActivityProfile extends BaseActivity implements OnTabChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_profile);
		
//		setTitle("Activity Profile");
		setHeader("Activity Profile", true, true, false);
		
//		setTabs();
//		setOnTabChangeListener(this);
//		mTabHost.setCurrentTab(4);
	}

	@Override
	public void onResponse(JSONObject response)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onErrorResponse(VolleyError error)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabChanged(String tabId)
	{
		Intent intent;
		int tabNum = Integer.parseInt(tabId);

		switch (tabNum) {
		case 0:
			intent = new Intent(getApplicationContext(), FragmentChangeActivity.class);
			intent.putExtra(Constants.BUNDLE_KEY_USERS, BeanController.getUserBean());
			startActivity(intent);
			break;

		case 1:
			intent = new Intent(this, SelfScreen.class);
			startActivity(intent);
			break;

		case 2:
			intent = new Intent(this, UsersCaretakers.class);
			startActivity(intent);
			break;

		case 3:
			intent = new Intent(this, SendReceived_Request.class);
			startActivity(intent);
			break;

		case 4:
			
			break;

		default:
			break;
		}
	}
}
