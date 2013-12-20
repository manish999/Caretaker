package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class UserNotification extends BaseActivity {

	private static final String[] CONTENT = new String[] { "Body Data" };
	private UsernotificationAdapter adapter;
	private ViewPager pager;
	private TabPageIndicator indicator;
	private Bundle myBundle;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usernotificationmain);	

		Intent myLocalIntent = getIntent();
		myBundle = myLocalIntent.getExtras();
		String title = myBundle.getString("title");
		// added by ishu		
		myBundle.putString("userid", myBundle.getString("userid"));
		// end
		setHeader(title, true, true, false, R.drawable.network, R.drawable.user_white);
		adapter = new UsernotificationAdapter(getSupportFragmentManager());		

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
	}

	class UsernotificationAdapter extends FragmentPagerAdapter implements
	IconPagerAdapter {
		public UsernotificationAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment =null;
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
		public int getIconResId(int index)
		{
			return 0;
		}
	}
	public void btnHomeClick(View v)
	{
		Intent intent = new Intent(getApplicationContext(),
				UsersCaretakers.class);
		startActivity(intent);
	}

	public void btnSettingClick(View v)
	{
		isActivePopup();
	}

	public boolean isActivePopup()
	{
		boolean  isActivePopup=false;
		Bundle b = new Bundle();
		b.putBoolean("POPUP",Boolean.TRUE);
		Intent intent = new Intent();

		intent.setClass(this, SendReceived_Request.class);
		intent.putExtras(b);
		startActivity(intent);
		isActivePopup=true;
		return isActivePopup;
	}

	@Override
	public void onResponse(JSONObject response) {
	}

	@Override
	public void onErrorResponse(VolleyError error) {
	}
}
