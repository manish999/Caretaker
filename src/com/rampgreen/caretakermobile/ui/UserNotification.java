package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.AppLog;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class UserNotification extends BaseActivity {

	/*private ExpandableListView mExpandableListView;
	private List<UserNotificationEntity> mGroupCollection;*/
	private static final String[] CONTENT = new String[] { "Notification", "Activity Profile","Body Data" };
	private UsernotificationAdapter adapter;
	private ViewPager pager;
	private TabPageIndicator indicator;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usernotificationmain);	

		Intent myLocalIntent = getIntent();
		Bundle myBundle = myLocalIntent.getExtras();

		String title = myBundle.getString("title");
		/* setTitle(title);*/
		setHeader(title, true, true, false, R.drawable.network, R.drawable.user_white);

		adapter = new UsernotificationAdapter(getSupportFragmentManager());		

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		/*prepareResource();
		initPage();	*/
	}

	class UsernotificationAdapter extends FragmentPagerAdapter implements
	IconPagerAdapter {
		public UsernotificationAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {		
			/*return SendReceivedFragment.newInstance(lvr);*/
			Fragment fragment =null;
			switch (position) {
			case 0:
				fragment = new FragmentUserNotification();
				break;
			case 1:
				fragment = new FragmentUserNotification();
				break;
			case 2:
				fragment = new FragmentUserNotification();
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
			// TODO Auto-generated method stub
			return 0;
		}

		//		@Override
		//		public int getIconResId(int index) {
		//			// TODO Auto-generated method stub
		//			return ICONS[index];
		//		}		

	}

	/*private void prepareResource() {

		mGroupCollection = new ArrayList<UserNotificationEntity>();

		UserNotificationEntity ge = new UserNotificationEntity();
		GroupItemEntity gi = ge.new GroupItemEntity();

		ge.Name = "GSR";		
		gi.Name = "heart rate is now 130/min";
		ge.GroupItemCollection.add(gi);
		mGroupCollection.add(ge);

		ge = new UserNotificationEntity();
		gi = ge.new GroupItemEntity();
		ge.Name = "Heart Rate";		
		gi.Name = "Heart Rate: 130 /min Normal Range: 60-80 /min";
		ge.GroupItemCollection.add(gi);
		mGroupCollection.add(ge);

		ge = new UserNotificationEntity();
		gi = ge.new GroupItemEntity();
		ge.Name = "Accelerometer";
		gi.Name = "Accelerometer";
		ge.GroupItemCollection.add(gi);
		mGroupCollection.add(ge);

		ge = new UserNotificationEntity();
		gi = ge.new GroupItemEntity();
		ge.Name = "Temperature";
		gi.Name = "Temprature";
		ge.GroupItemCollection.add(gi);
		mGroupCollection.add(ge);


		for (int i = 1; i < 6; i++) {
			GroupEntity ge = new GroupEntity();
			ge.Name = "Group" + i;

			for (int j = 1; j < 5; j++) {
				GroupItemEntity gi = ge.new GroupItemEntity();
				gi.Name = "Child" + j;
				ge.GroupItemCollection.add(gi);
			}

			mGroupCollection.add(ge);
		}

	}

	private void initPage() {
		mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		mExpandableListView.setDividerHeight(2);
		mExpandableListView.setChildDivider(getResources().getDrawable(R.color.gray));
		UserNotificationExpandableListAdapter adapter = new UserNotificationExpandableListAdapter(this,
				mExpandableListView, mGroupCollection);

		mExpandableListView.setAdapter(adapter);
	}*/	

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
		boolean  Isactivepopup=false;
		Bundle b = new Bundle();
		b.putBoolean("POPUP",Boolean.TRUE);
		Intent intent = new Intent();

		intent.setClass(this, SendReceived_Request.class);
		intent.putExtras(b);
		startActivity(intent);
		Isactivepopup=true;
		return Isactivepopup;
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
