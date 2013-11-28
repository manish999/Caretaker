package com.rampgreen.caretakermobile.ui;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
//import android.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import android.widget.GridView;
import android.widget.TabHost.OnTabChangeListener;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.UserCaretakerBean;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.TabPageIndicator;

//import com.example.testfragment2.MainActivity.MyAdapter;

public class UsersCaretakers extends BaseActivity implements
OnPageChangeListener, OnTabChangeListener {
	GridView gridView;
	Menu menuItem;
	private static final String[] CONTENT = new String[] { "My Users",
	"My Caretakers" };
	private UserCaretakerAdapter adapter;
	private MyRequestQueue queue;

	String token = BeanController.getLoginBean().getAccessToken();// "b1916c6daa00b1d5d2297166008f3a7c4825e6f8";
	ArrayList<UserCaretakerBean> lstCaretakers = new ArrayList<UserCaretakerBean>();
	ArrayList<UserCaretakerBean> lstUsers = new ArrayList<UserCaretakerBean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_screen);
		adapter = new UserCaretakerAdapter(getSupportFragmentManager());

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);

		indicator.setViewPager(pager);
		indicator.setOnPageChangeListener(this);
		indicator.notifyDataSetChanged();

		setTabs();
		setOnTabChangeListener(this);
		mTabHost.setCurrentTab(2);
	}

	@Override
	protected void onStart() {
		super.onStart();
		queue = MyVolley.getRequestQueue();
		String token = BeanController.getLoginBean().getAccessToken();
		Map<String, String> loginParam = QueryHelper
				.getMyCaretakersUsersQuery(token);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam, UsersCaretakers.this,
				UsersCaretakers.this);
		customRequest.setTag(this);
		queue.add(customRequest);

	}
	@Override
	protected void onStop() {
		super.onStop();
		if (queue != null){
			queue.cancelAll(this);
		}
	}

	@Override
	public void onResponse(JSONObject response) {
		int code = Integer.parseInt(response.optString("code"));
		// String msg = response.optString("message");
		switch (code) {
		case ParserError.CODE_ACTION_NOT_FOUND:
			break;
		case ParserError.CODE_MISSING_ACTION:
			break;
		case ParserError.CODE_MISSING_TASK:

			break;
		case ParserError.CODE_CLIENT_AUTHORIZATION_FAILED:

			break;
		case ParserError.CODE_TOKEN_GENERATION_FAILED:

		case ParserError.CODE_INVALID_TOKEN:

			break;
		case ParserError.CODE_TOKEN_EXPIRED:

			break;
		case ParserError.CODE_INTERNAL_SERVER_ERROR:

			break;
		case ParserError.CODE_USER_ALREADY_REGISTERED:

			break;
		case ParserError.CODE_SUCCESS:
			try {
				GetCareTakers(response.toString());
				adapter.refreshAdapter(lstUsers, 0);
				adapter.refreshAdapter(lstCaretakers, 1);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

		if (code != ParserError.CODE_SUCCESS) {
			AppLog.logToast(this, "error web service response code - " + code);
		}

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}

	private void GetCareTakers(String jsonObject) throws JSONException {
		try {
			lstCaretakers.clear();
			lstUsers.clear();

			JSONObject j = new JSONObject(jsonObject);
			UserCaretakerBean user;
			JSONArray jArray = null;
			JSONObject json_data = new JSONObject();

			try {
				jArray = j.getJSONArray("caretaker");
				if (jArray != null) {
					for (int i = 0; i < jArray.length(); i++) {
						user = new UserCaretakerBean();
						json_data = jArray.getJSONObject(i);
						user.setUserName(json_data.getString("firstname"));
						user.setUid(json_data.getString("u_id"));
						lstCaretakers.add(user);

					}
				}
			} catch (Exception e1) {
				AppLog.e(e1.getMessage());
			}

			try {
				jArray = null;
				jArray = j.getJSONArray("users");
				JSONObject json_data_users = new JSONObject();

				for (int i = 0; i < jArray.length(); i++) {

					user = new UserCaretakerBean();
					json_data_users = jArray.getJSONObject(i);
					user.setUserName(json_data_users.getString("firstname"));
					user.setUid(json_data_users.getString("u_id"));
					lstUsers.add(user);

				}
			} catch (Exception e) {
				AppLog.e(e.getMessage());
			}

			// return lstUsers;
		} catch (Exception e) {
			AppLog.e(e.getMessage());
		}
		// return lstUsers;
	}

	class UserCaretakerAdapter extends FragmentPagerAdapter {
		FragmentUserCaretaker fragmentUsers = null;
		FragmentUserCaretaker fragmentCaretakers = null;

		public UserCaretakerAdapter(FragmentManager fm) {
			super(fm);

			fragmentUsers = FragmentUserCaretaker.newInstance();
			fragmentCaretakers = FragmentUserCaretaker.newInstance();

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

		public void refreshAdapter(ArrayList<UserCaretakerBean> users, int pos) {
			if (pos == 0)
				((FragmentUserCaretaker) getItem(0)).refreshAdapter(users);
			else
				((FragmentUserCaretaker) getItem(1)).refreshAdapter(users);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int index) {
		if (index == 0 && this.menuItem != null) {
			this.menuItem.getItem(0).setVisible(Boolean.TRUE);
		} else if (index == 1 && this.menuItem != null) {
			this.menuItem.getItem(0).setVisible(Boolean.FALSE);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		this.menuItem = menu;
		inflater.inflate(R.menu.users_caretakers, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.caretakers:
			Bundle b = new Bundle();
			b.putBoolean("POPUP",Boolean.TRUE);
			Intent intent = new Intent();

			intent.setClass(this, SendReceived_Request.class);
			intent.putExtras(b);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

			break;

		case 3:
			intent = new Intent(this, SendReceived_Request.class);
			startActivity(intent);
			break;

		case 4:
			intent = new Intent(this, Rainbow.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		//				AppLog.logToast(FragmentTabBottom.this.getSherlockActivity(), "Tab click"+tabId);
	}
}