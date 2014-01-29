package com.rampgreen.caretakermobile.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.AlertSettingsDTO;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.SettingsDTO;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.ui.UserNotification.UsernotificationAdapter;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class AlertSettings extends BaseActivity implements OnItemClickListener,OnTabChangeListener {
	public static final String[] titles = new String[] {
			"You've been sitting for too long. Maybe you should stretch",
			"Your heart rate is 120/Min", "Your temperature is now 101" };

	ListView listView;
	List<String> rowItems;
	private MyRequestQueue queue = MyVolley.getRequestQueue();
	private String userId;
	private Bundle myBundle;
	private TabHost mTabHostDays;
	private ViewPager pager;
	private TabPageIndicator indicator;	
	FragmentPagerAdapter adapter;
	//private User user;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Intent myLocalIntent = getIntent();
		myBundle = myLocalIntent.getExtras();
		
		this.userId = myBundle.getString("userid");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert_settings);

		String token = BeanController.getLoginBean().getAccessToken();

		Map<String, String> alertSettingParam = QueryHelper.getAlertSettings(
				token, userId);

		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, alertSettingParam,
				AlertSettings.this, AlertSettings.this);

		customRequest.setTag(this);

		queue.add(customRequest);

		adapter = new AlertsettingAdapter(getSupportFragmentManager());	
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		setTabs();
		super.setOnTabChangeListener(AlertSettings.this);
		super.mTabHost.setCurrentTab(1);
	}

	/**
	 * The Class UsernotificationAdapter.
	 */
	class AlertsettingAdapter extends FragmentPagerAdapter implements
	IconPagerAdapter {
		public AlertsettingAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment =null;
			switch (position) {
			case 0:					
				break;

			}
			return fragment;
		}

		@Override
		public int getIconResId(int index) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}		
	}
	
	/*
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) {
	 * 
	 * }
	 */

	@Override
	public void onResponse(JSONObject response) {
		AppLog.e(response.toString());
		Gson gson = new GsonBuilder().create();
		AlertSettingsDTO alertSettingsDTO = gson.fromJson(response.toString(),
				AlertSettingsDTO.class);
		
		if (alertSettingsDTO.getAlerts() != null) {
//			System.out.println("Fall:" + response + " ---"
//					+ alertSettingsDTO.getAlerts().getFall());
			TextView txtfitness = (TextView) findViewById(R.id.fitness_heading);
			txtfitness.setText("Fitness Alerts for " + myBundle.getString("title"));
			
			TextView txthealth = (TextView) findViewById(R.id.heading_health);
			txthealth.setText("Health Alerts for " + myBundle.getString("title"));
			
			TextView txtemergency = (TextView) findViewById(R.id.heading_emergency);
			txtemergency.setText("Emergency Alerts for " + myBundle.getString("title"));
			
			setSettings(alertSettingsDTO.getAlerts());
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		AppLog.e(error.toString());
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	}

	public void onToggleClickedExcercise(View view) {
		ToggleButton tb = (ToggleButton) view;
		AppLog.d(AppLog.APP_TAG, "Excercise ");
		boolean on = ((ToggleButton) view).isChecked();
		String onOff = null;
		if (on) {
			onOff = "1";
		} else {
			onOff = "0";
		}
		String settingType = "no_exercise";
		saveSettings(onOff, settingType);
	}

	public void onToggleClickedSteps(View view) {
		ToggleButton tb = (ToggleButton) view;
		AppLog.d(AppLog.APP_TAG, "Steps");
		boolean on = ((ToggleButton) view).isChecked();
		String onOff = null;
		if (on) {
			onOff = "1";
		} else {
			onOff = "0";
		}
		String settingType = "steps_day";
		saveSettings(onOff, settingType);
	}

	public void onToggleClickedSitting(View view) {
		ToggleButton tb = (ToggleButton) view;
		AppLog.d(AppLog.APP_TAG, "Sitting");
		boolean on = ((ToggleButton) view).isChecked();
		String onOff = null;
		if (on) {
			onOff = "1";
		} else {
			onOff = "0";
		}
		String settingType = "sitting_hrs";
		saveSettings(onOff, settingType);
	}

	public void onToggleClickedTemp(View view) {
		ToggleButton tb = (ToggleButton) view;
		AppLog.d(AppLog.APP_TAG, "Temprature");
		boolean on = ((ToggleButton) view).isChecked();
		String onOff = null;
		if (on) {
			onOff = "1";
		} else {
			onOff = "0";
		}
		String settingType = "temperature";
		saveSettings(onOff, settingType);
	}

	public void onToggleClickedStress(View view) {
		ToggleButton tb = (ToggleButton) view;
		AppLog.d(AppLog.APP_TAG, "Stress");
		boolean on = ((ToggleButton) view).isChecked();
		String onOff = null;
		if (on) {
			onOff = "1";
		} else {
			onOff = "0";
		}
		String settingType = "stress";
		saveSettings(onOff, settingType);
	}

	public void onToggleClickedFall(View view) {
		ToggleButton tb = (ToggleButton) view;
		AppLog.d(AppLog.APP_TAG, "Fall");
		boolean on = ((ToggleButton) view).isChecked();
		String onOff = null;
		if (on) {
			onOff = "1";
		} else {
			onOff = "0";
		}
		String settingType = "fall";
		saveSettings(onOff, settingType);
	}

	public void onToggleClickedFever(View view) {
		ToggleButton tb = (ToggleButton) view;
		AppLog.d(AppLog.APP_TAG, "Fever");
		boolean on = ((ToggleButton) view).isChecked();
		String onOff = null;
		if (on) {
			onOff = "1";
		} else {
			onOff = "0";
		}
		
		String settingType = "fever";
		saveSettings(onOff, settingType);
	}

	private void saveSettings(String onOff, String settingType) {
		String token = BeanController.getLoginBean().getAccessToken();
		

		Map<String, String> alertSettingParam = QueryHelper.changeAlertSetting(
				token, userId, onOff, settingType);

		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, alertSettingParam,
				AlertSettings.this, AlertSettings.this);
		customRequest.setTag(this);
		
		queue.add(customRequest);

	}

	private void setSettings(SettingsDTO settingsDTO) {
		boolean b = Boolean.FALSE;
				b = ("1".equals(settingsDTO.getNo_exercise())?Boolean.TRUE:Boolean.FALSE);
				((ToggleButton) findViewById(R.id.toggle_excercise)).setChecked(b);
		
				b = ("1".equals(settingsDTO.getSteps_day())?Boolean.TRUE:Boolean.FALSE);
				((ToggleButton) findViewById(R.id.toggle_steps)).setChecked(b);
		
				b = ("1".equals(settingsDTO.getSitting_hrs())?Boolean.TRUE:Boolean.FALSE);
				((ToggleButton) findViewById(R.id.toggle_sitting)).setChecked(b);
		
				b = ("1".equals(settingsDTO.getTemperature())?Boolean.TRUE:Boolean.FALSE);
				((ToggleButton) findViewById(R.id.toggle_temp)).setChecked(b);
		
				b = ("1".equals(settingsDTO.getStress())?Boolean.TRUE:Boolean.FALSE);
				((ToggleButton) findViewById(R.id.toggle_stress)).setChecked(b);
		
				b = ("1".equals(settingsDTO.getFall())?Boolean.TRUE:Boolean.FALSE);
				((ToggleButton) findViewById(R.id.toggle_fall)).setChecked(b);
		
				b = ("1".equals(settingsDTO.getFever())?Boolean.TRUE:Boolean.FALSE);
				((ToggleButton) findViewById(R.id.toggle_fever)).setChecked(b);
	}
	
	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onCreateOptionsMenu(android.view.Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		 super.onCreateOptionsMenu(menu);
		    SubMenu subMenu1 = menu.addSubMenu(0, 0, Menu.NONE,"Action Item");
	        subMenu1.add(0, 1, Menu.NONE,"Alerts").setIcon(R.drawable.bell);
	        subMenu1.add(0, 2, Menu.NONE,"Setting").setIcon(R.drawable.settings);  
	        subMenu1.add(0, 3, Menu.NONE,"Display Data").setIcon(R.drawable.text_slider);

	        MenuItem subMenu1Item = subMenu1.getItem();
	        subMenu1Item.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_light);
	        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		    return super.onCreateOptionsMenu(menu);
	}
	
	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Bundle bundle = new Bundle();	
		Intent intent = null;
		bundle.putString("userid", myBundle.getString("userid"));	
		bundle.putString("title", myBundle.getString("title"));
		switch (item.getItemId()) {
		case 1:
			intent = new Intent(getApplicationContext(),
					 ActivityAlert.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);	
			return true;
		case 2:
			intent = new Intent(getApplicationContext(),
					 AlertSettings.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);	
			return true;
		case 3:
			intent = new Intent(getApplicationContext(),
					 SelfScreen.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);	
			return true;
		default:		
			return super.onOptionsItemSelected(item);
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	@Override
	public void onTabChanged(String tabId) {
		Intent intent;
		int tabNum = Integer.parseInt(tabId);

		switch (tabNum) {
		case 0:
			intent = new Intent(getApplicationContext(),
					FragmentChangeActivity.class);
			intent.putExtra(Constants.BUNDLE_KEY_USERS,
					BeanController.getUserBean());
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 1:

			break;

		case 2:
			intent = new Intent(this, UsersCaretakers.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			break;

		case 3:
			intent = new Intent(this, SendReceived_Request.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 4:
			intent = new Intent(this, Rainbow.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}