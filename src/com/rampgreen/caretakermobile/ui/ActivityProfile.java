package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.TabHost.OnTabChangeListener;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.TabPageIndicator;

public class ActivityProfile extends BaseActivity implements OnTabChangeListener {

	private static final String[] CONTENT = new String[] { "Timeline",
	"Summary" };
	private UserCaretakerAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_profile);
//		setTitle("Activity Profile");
		setHeader("Activity Profile", true, true, false, R.drawable.mydata, R.drawable.alerts);
		
		adapter = new UserCaretakerAdapter(getSupportFragmentManager());

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);

		indicator.setViewPager(pager);
//		indicator.setOnPageChangeListener(this);
		indicator.notifyDataSetChanged();

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
	
	class UserCaretakerAdapter extends FragmentPagerAdapter {
		FragmentTimeline fragmentUsers = null;
		FragmentSummary fragmentCaretakers = null;

		public UserCaretakerAdapter(FragmentManager fm) {
			super(fm);
			fragmentUsers = FragmentTimeline.newInstance();
			fragmentCaretakers = FragmentSummary.newInstance();

		}

		@Override
		public Fragment getItem(int position) {

			switch (position) {
			case 0:

				return fragmentUsers;

			default:

				return fragmentCaretakers;
			}

		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length];
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}
	
	@Override
	public void btnHomeClick(View v)
	{
		super.btnHomeClick(v);
		// mydata
		startActivity(new Intent(this, SelfScreen.class));
	}
	
	@Override
	public void btnSettingClick(View v)
	{
		super.btnSettingClick(v);
		// alert
		startActivity(new Intent(this, SelfScreen.class));
	}
}
