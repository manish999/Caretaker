package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost.OnTabChangeListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.NotificationArrayAdapter;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class ActivityAlert extends BaseActivity implements OnItemClickListener ,OnTabChangeListener{
	public static final String[] titles = new String[] {
			"You've been sitting for too long. Maybe you should stretch",
			"Your heart rate is 120/Min", "Your temperature is now 101" };

	private ListView listView;
	private List<String> rowItems;
	private Bundle myBundle;
	private ViewPager pager;
	private TabPageIndicator indicator;	
	private FragmentPagerAdapter adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_alert);

		Intent myLocalIntent = getIntent();
		myBundle = myLocalIntent.getExtras();
		
		adapter = new AlertAdapter(getSupportFragmentManager());	
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		setTabs();
		super.setOnTabChangeListener(ActivityAlert.this);
		super.mTabHost.setCurrentTab(1);
		
		rowItems = new ArrayList<String>();
		for (int i = 0; i < titles.length; i++) {
			rowItems.add(titles[i]);
		}

		listView = (ListView) findViewById(R.id.listview);
		NotificationArrayAdapter adapterl = new NotificationArrayAdapter(this,
				R.layout.notification, rowItems);
		listView.setAdapter(adapterl);
		listView.setOnItemClickListener(this);
		
//		setHeader("Alert", true, true, false, R.drawable.profile, R.drawable.mydata);
	
	}

	/**
	 * The Class UsernotificationAdapter.
	 */
	class AlertAdapter extends FragmentPagerAdapter implements
	IconPagerAdapter {
		public AlertAdapter(FragmentManager fm) {
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
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void btnHomeClick(View v)
	{
		super.btnHomeClick(v);
		//profile
		startActivity(new Intent(this, ActivityProfile.class));
		
	}
	
	@Override
	public void btnSettingClick(View v)
	{
		super.btnSettingClick(v);
		//mydata
		startActivity(new Intent(this, SelfScreen.class));
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
