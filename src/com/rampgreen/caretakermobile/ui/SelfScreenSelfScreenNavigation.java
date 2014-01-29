package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.Constants;

public class SelfScreenSelfScreenNavigation extends BaseActivity implements
		ActionBar.OnNavigationListener {

	private String[] mDisplayist;
	Bundle myBundle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.text);
		Intent myLocalIntent = getIntent();
		myBundle = myLocalIntent.getExtras();		

		// Inflate the custom view
		View customNav = LayoutInflater.from(this).inflate(
				R.layout.selfscreen_navigation, null);

		mDisplayist = getResources().getStringArray(R.array.SelfScreenDisplay);

//		Context context = getSupportActionBar().getThemedContext();
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(
				this, R.array.SelfScreenDisplay,
				R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		((Button) customNav.findViewById(R.id.ButAlert))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(SelfScreenSelfScreenNavigation.this,
								"Button Alert", Toast.LENGTH_SHORT).show();

					}
				});

		((Button) customNav.findViewById(R.id.ButProfile))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(SelfScreenSelfScreenNavigation.this,
								"Button Profile", Toast.LENGTH_SHORT).show();

					}
				});

		getSupportActionBar().setCustomView(customNav);
		getSupportActionBar().setDisplayShowCustomEnabled(true);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);

		getSupportActionBar().show();
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		Bundle bundle = new Bundle();
		Fragment fragment =null;
		 Intent intent = null;
		bundle.putString("userid", myBundle.getString("userid"));
		FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		if (mDisplayist[itemPosition].equals("Graphical view")) {			
			 intent = new Intent(getApplicationContext(),
					 SelfScreen.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);			
		} else if (mDisplayist[itemPosition].equals("Text view")) {	
//			fragment = new FragmentUserNotification();
//			fragment.setArguments(bundle);
//            mFragmentTransaction.replace(android.R.id.content,fragment);
			intent = new Intent(getApplicationContext(),
					UserNotification.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);				
           
		} else if (mDisplayist[itemPosition].equals("Calendar view")) {
			 intent = new Intent(getApplicationContext(),
					 FragmentCalendarView.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);	
		}		
		mFragmentTransaction.commit();
		return true;
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
