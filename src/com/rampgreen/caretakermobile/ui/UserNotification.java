package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.ui.SelfScreen.GoogleMusicAdapter;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class UserNotification extends BaseActivity implements
		OnNavigationListener, OnTabChangeListener {

	private String[] CONTENT = new String[] { "Body Data" };
	// private UsernotificationAdapter adapter;
	private ViewPager pager;
	private TabPageIndicator indicator;
	private Bundle myBundle;
	private String[] mDisplayist;
	private TabHost mTabHostDays;
	FragmentPagerAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usernotificationmain);

		Intent myLocalIntent = getIntent();
		myBundle = myLocalIntent.getExtras();
		String title = myBundle.getString("title");
		
		CONTENT = new String[] { title + " Body Data" };
		myBundle.putString("userid", myBundle.getString("userid"));

		adapter = new UsernotificationAdapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		setTabs();
		super.setOnTabChangeListener(UserNotification.this);
		super.mTabHost.setCurrentTab(1);

		mDisplayist = getResources().getStringArray(R.array.SelfScreenDisplay);
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(this,
				R.array.SelfScreenDisplay, R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);
		getSupportActionBar().setSelectedNavigationItem(1);
		getSupportActionBar().show();
		// getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

	}

	/**
	 * The Class UsernotificationAdapter.
	 */
	class UsernotificationAdapter extends FragmentPagerAdapter implements
			IconPagerAdapter {
		public UsernotificationAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				Bundle bundle = new Bundle();
				bundle.putString("userid", myBundle.getString("userid"));
				// set Fragmentclass Arguments
				fragment = new FragmentUserNotification();
				fragment.setArguments(bundle);
				break;

			}
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length];
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}

		@Override
		public int getIconResId(int index) {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rampgreen.caretakermobile.ui.BaseActivity#btnHomeClick(android.view
	 * .View)
	 */
	public void btnHomeClick(View v) {
		Intent intent = new Intent(getApplicationContext(),
				UsersCaretakers.class);
		startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rampgreen.caretakermobile.ui.BaseActivity#btnSettingClick(android
	 * .view.View)
	 */
	public void btnSettingClick(View v) {
		isActivePopup();
	}

	/**
	 * Checks if is active popup.
	 * 
	 * @return true, if is active popup
	 */
	public boolean isActivePopup() {
		boolean isActivePopup = false;
		Bundle b = new Bundle();
		b.putBoolean("POPUP", Boolean.TRUE);
		Intent intent = new Intent();

		intent.setClass(this, SendReceived_Request.class);
		intent.putExtras(b);
		startActivity(intent);
		isActivePopup = true;
		return isActivePopup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Response.Listener#onResponse(java.lang.Object)
	 */
	@Override
	public void onResponse(JSONObject response) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android.volley.Response.ErrorListener#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actionbarsherlock.app.ActionBar.OnNavigationListener#
	 * onNavigationItemSelected(int, long)
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		Bundle bundle = new Bundle();
		Fragment fragment = null;
		Intent intent = null;
		bundle.putString("userid", myBundle.getString("userid"));
		bundle.putString("title", myBundle.getString("title"));		

		FragmentTransaction mFragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		if (mDisplayist[itemPosition].equals("Graphical view")) {
			intent = new Intent(getApplicationContext(), SelfScreen.class);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if (mDisplayist[itemPosition].equals("Text view")) {
//			fragment = new FragmentUserNotification();
//			fragment.setArguments(bundle);
//			mFragmentTransaction.replace(android.R.id.content, fragment);

		} else if (mDisplayist[itemPosition].equals("Calendar view")) {
			intent = new Intent(getApplicationContext(),
					FragmentCalendarView.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		mFragmentTransaction.commit();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.app.SherlockFragmentActivity#onCreateOptionsMenu
	 * (android.view.Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		SubMenu subMenu1 = menu.addSubMenu(0, 0, Menu.NONE, "Action Item");
		subMenu1.add(0, 1, Menu.NONE, "Alerts").setIcon(R.drawable.bell);
		subMenu1.add(0, 2, Menu.NONE, "Setting").setIcon(R.drawable.settings);

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_light);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.app.SherlockFragmentActivity#onOptionsItemSelected
	 * (android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Bundle bundle = new Bundle();
		Intent intent = null;
		bundle.putString("userid", myBundle.getString("userid"));
		bundle.putString("title", myBundle.getString("title"));
		switch (item.getItemId()) {
		case 1:
			intent = new Intent(getApplicationContext(), ActivityAlert.class);
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		case 2:
			intent = new Intent(getApplicationContext(), AlertSettings.class);
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
