package com.rampgreen.caretakermobile.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.UsersCaretakersImageAdapter;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersCaretakers extends BaseActivity implements OnPageChangeListener {
	GridView gridView;
	private static final String[] CONTENT = new String[] { "My Caretakers", "My Users" };
	private static final int[] ICONS = new int[] { R.drawable.received,
		R.drawable.sent };
	private GoogleMusicAdapter adapter;
	private ViewPager pager;
	private TabPageIndicator indicator;
	final Context context = this;
	String token = BeanController.getLoginBean().getAccessToken();//"b1916c6daa00b1d5d2297166008f3a7c4825e6f8";
	private String Email = "";		
	//private ListView userList;	
	//private ViewPager pager;
	//private TabPageIndicator indicator;
	//private static final String[] CONTENT = new String[] { "Users", "Caretakers" };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendrecived_request);
		// Fragment code Start 

		//setUserView(new String[]{"ABC","XYZ"});

		adapter = new GoogleMusicAdapter(getSupportFragmentManager());		

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		indicator.notifyDataSetChanged();

		
		String token = BeanController.getLoginBean().getAccessToken();
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.getMyCaretakersQuery(token);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam, UsersCaretakers.this,
				UsersCaretakers.this);
		queue.add(customRequest);

		//
		//	pager = (ViewPager)findViewById(R.id.pager);
		//	pager.setAdapter(adapter);
		//
		//	indicator = (TabPageIndicator)findViewById(R.id.indicator);
		//	indicator.setViewPager(pager);
		//	pager.setOnPageChangeListener(sendrecived_request.this);
		//	*/
	}

	@Override
	public void onResponse(JSONObject response) {
		String[] users = null;
		System.out.println("response" + response);

		//************************************************

		int code = Integer.parseInt(response.optString("code"));
		String msg = response.optString("message");
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
				ArrayList<User> lstUsers = GetCareTakers(response.toString());
				adapter.refreshAdapter(lstUsers);
//				setUserView(lstUsers);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		

			break;

		default:
			break;
		}

		if (code != ParserError.CODE_SUCCESS)
		{
			AppLog.logToast(this, "error web service response code - " + code);
		}


		//************************************************
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}

	private ArrayList<User> GetCareTakers(String jsonObject) throws JSONException {
		ArrayList<User> lstUsers = null;
		try {
			JSONObject j = new JSONObject(jsonObject);
			JSONArray jArray = j.getJSONArray("caretaker");
			JSONObject json_data = new JSONObject();
			lstUsers = new ArrayList<User>();
			//users = new String[jArray.length()];
			User user;
			for (int i = 0; i < jArray.length(); i++) {
				user = new User();
				json_data = jArray.getJSONObject(i);
				user.setUsername(json_data.getString("firstname"));
				user.setUid(json_data.getString("u_id"));
				lstUsers.add(user);

			}

			return lstUsers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstUsers;
	}

	private void setUserView(List<User> users) {
//		setContentView(R.layout.grid);

//		gridView = (GridView) findViewById(R.id.users_caretakers);
//
//		gridView.setAdapter(new UsersCaretakersImageAdapter(this, users));
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View v,
//					int position, long id) {
//				Toast.makeText(
//						getApplicationContext(),
//						((TextView) v.findViewById(R.id.grid_item_label))
//						.getText(), Toast.LENGTH_SHORT).show();
//
//			}
//		});
		//PagerAdapter pm = new PagerAdapter(getSupportFragmentManager(), gridFragments);
		//pager.setAdapter(new UsersCaretakersImageAdapter(this, users));
		//i//ndicator.setViewPager( gridView);

	}


	class GoogleMusicAdapter extends FragmentPagerAdapter {
		FragmentUserCaretaker fragment =null;
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {		
			/*return SendReceivedFragment.newInstance(lvr);*/
			switch (position) {
			case 0:
				fragment = new FragmentUserCaretaker();
				break;
			case 1:
				fragment = new FragmentUserCaretaker();
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
		
		public void refreshAdapter(ArrayList< User> users) {
			if(fragment != null)
				fragment.refreshAdapter(users);
		}
	}



	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}	
}