package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.DisplayCalendarView;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentCalendarView extends BaseActivity implements
Response.Listener<JSONObject>, Response.ErrorListener,
OnNavigationListener, OnTabChangeListener {

	private String token = BeanController.getLoginBean().getAccessToken();
	private GridView gridView;
	private DisplayCalendarView aDisplayCalendarView;
	private String[] days;
	private Bundle myBundle;
	private String[] mDisplayist;
	private TabHost mTabHostDays;
	private ViewPager pager;
	private TabPageIndicator indicator;
	FragmentPagerAdapter adapter;
	Date mDate;
	private Calendar calp;
	//	private Calendar caln;
	private DateFormat format;
	//	private DateFormat formatN;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendarview);

		Intent myLocalIntent = getIntent();
		myBundle = myLocalIntent.getExtras();

		String[] days = new String[8];
		Calendar c = Calendar.getInstance();

		// Set the calendar to Sunday of the current week

		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.add(Calendar.DAY_OF_WEEK, -8);
		// Print dates of the current week starting on Sunday
		DateFormat df = new SimpleDateFormat("EEE dd-MMM");

		calp=Calendar.getInstance();
		format=new SimpleDateFormat("EEE dd-MMM");
		format.format(c.getTime());
		calp=format.getCalendar();


		for (int i = 0; i < 8; i++) {
			days[i] = df.format(c.getTime());
			c.add(Calendar.DATE, 1);
		}			

		//		aDisplayCalendarView = new DisplayCalendarView(this, days);
		//		gridView = (GridView) findViewById(R.id.gridcalendarView);
		// // gridView.setBackgroundColor(Color.RED);
		// gridView.setVerticalSpacing(10);
		// gridView.setHorizontalSpacing(10);
		//
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, days);
		//

		View customNav = LayoutInflater.from(this).inflate(
				R.layout.listnavigationbtn, null);
		LinearLayout layoutContainer = (LinearLayout)customNav.findViewById(R.id.layout_container_btn);
		
		((ImageButton) customNav.findViewById(R.id.ib_prev))
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {	
				String[] days = new String[8];						
				calp.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				calp.add(Calendar.DAY_OF_WEEK, -8);						
				for (int i = 0; i < 8; i++) {
					days[i] = format.format(calp.getTime());
					calp.add(Calendar.DATE, 1);							
				}	
				try {
					format.parse(days[0]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				calp=format.getCalendar();
				DisplayData(days);						
			}
		});

		try {
			//			formatN=new SimpleDateFormat("EEE dd-MMM");
			format.parse(days[7]);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		calp=format.getCalendar();

		((ImageButton) customNav.findViewById(R.id.ib_next))
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] days = new String[8];						
				calp.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				calp.add(Calendar.DAY_OF_WEEK, 0);						
				for (int i = 0; i < 8; i++) {
					days[i] = format.format(calp.getTime());
					calp.add(Calendar.DATE, 1);							
				}	
				try {
					format.parse(days[7]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				calp=format.getCalendar();
				DisplayData(days);	
			}
		});
		
		// Calculate ActionBar height to set the buttons layout height
		int actionBarHeight = 0;
		TypedValue tv = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
		{
		    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
		    layoutContainer.getLayoutParams().height = actionBarHeight;
		}
		getSupportActionBar().setCustomView(customNav);
		getSupportActionBar().setDisplayShowCustomEnabled(true);


		mDisplayist = getResources().getStringArray(R.array.SelfScreenDisplay);
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(this,
				R.array.SelfScreenDisplay, R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);
		getSupportActionBar().setSelectedNavigationItem(2);
		getSupportActionBar().show();
		// getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		// getSupportActionBar().setSelectedNavigationItem(position);		

		//		aDisplayCalendarView = new DisplayCalendarView(this, days);
		//		gridView = (GridView) findViewById(R.id.gridcalendarView);
		//		gridView.setAdapter(aDisplayCalendarView);
		DisplayData(days);


		adapter = new AlertsettingAdapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		setTabs();
		super.setOnTabChangeListener(FragmentCalendarView.this);
		super.mTabHost.setCurrentTab(1);
	}

	private void DisplayData(String[] days)
	{
		aDisplayCalendarView = new DisplayCalendarView(this, days);
		gridView = (GridView) findViewById(R.id.gridcalendarView);
		aDisplayCalendarView.notifyDataSetChanged();
		gridView.setAdapter(aDisplayCalendarView);
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
			Fragment fragment = null;
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

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actionbarsherlock.app.ActionBar.OnNavigationListener#
	 * onNavigationItemSelected(int, long)
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		Bundle bundle = new Bundle();
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
			intent = new Intent(getApplicationContext(),
					UserNotification.class);                                
			intent.putExtras(bundle);
			startActivity(intent);
		} else if (mDisplayist[itemPosition].equals("Calendar view")) {
		}
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
}
