package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

import java.util.Map;

public class SendReceived_Request extends BaseActivity {

	private static final String[] CONTENT = new String[] { "Received", "Sent" };
	private static final int[] ICONS = new int[] { R.drawable.received,
			R.drawable.sent };
	private GoogleMusicAdapter adapter;
	private ViewPager pager;
	private TabPageIndicator indicator;
	final Context context = this;
	String token = BeanController.getLoginBean().getAccessToken();//"b1916c6daa00b1d5d2297166008f3a7c4825e6f8";
	private String Email = "";		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendrecived_request);

		adapter = new GoogleMusicAdapter(getSupportFragmentManager());		
		
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		
		indicator.notifyDataSetChanged();
		adapter.notifyDataSetChanged();		  
	
	}

	class GoogleMusicAdapter extends FragmentPagerAdapter implements
			IconPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {		
			/*return SendReceivedFragment.newInstance(lvr);*/
			Fragment fragment =null;
	        switch (position) {
	            case 0:
	                fragment = new Received_request();
	                break;
	            case 1:
	                fragment = new Sent_request();
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
			// TODO Auto-generated method stub
			return ICONS[index];
		}		
		 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.requestsentmenu, menu);
		return true;
	}

	@Override
	public void onResponse(JSONObject response) {
		int code = Integer.parseInt(response.optString("code"));
		String msg = response.optString("message");
		Intent intent;
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

			break;
		case ParserError.CODE_USERNAME_REQUIRED:

			break;
		case ParserError.CODE_PASSWORD_REQUIRED:

			break;
		case ParserError.CODE_PASSWORD_WRONG:
			AppLog.showToast(this, "wrong password");
			break;
		case ParserError.CODE_USER_NOT_REGISTERED:
			Toast.makeText(this, "USER NOT REGISTERED: ", Toast.LENGTH_SHORT)
					.show();
			break;
		case ParserError.CODE_INVALID_TOKEN:

			break;
		case ParserError.CODE_TOKEN_EXPIRED:

			break;
		case ParserError.CODE_INTERNAL_SERVER_ERROR:

			break;
		case ParserError.CODE_USER_ALREADY_REGISTERED:
			Toast.makeText(this, "USER ALREADY REGISTERED: ",
					Toast.LENGTH_SHORT).show();
			break;
		case ParserError.CODE_NO_REQUEST_RECEIVED:
			break;
		case ParserError.CODE_NO_CARETAKER_REQUEST_SENT_IS_PENDING:
			AppLog.showToast(this, "No Request pending");
			break;
		case ParserError.CODE_SUCCESS:

			if (Email != "") {
				Toast.makeText(this,
						"Request has been sent to: " + Email.toString(),
						Toast.LENGTH_SHORT).show();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.new_request:

			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.requestsent_prompts, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			// set requestsent_prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			final EditText userInput = (EditText) promptsView
					.findViewById(R.id.SentrequestEditText);

			// set dialog message
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Send",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									sendrequest(userInput.getText().toString());
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void sendrequest(String emailid) {
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.caretakerRequestSendQuery(
				token, emailid);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam, this, this);

		queue.add(customRequest);
		Email = emailid;
	}

}
