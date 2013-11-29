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
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.andreabaccega.widget.FormEditText;
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

public class SendReceived_Request extends BaseActivity implements OnTabChangeListener {

	private static final String[] CONTENT = new String[] { "Received", "Sent" };
	//	private static final int[] ICONS = new int[] { R.drawable.received,
	//			R.drawable.sent };
	private GoogleMusicAdapter adapter;
	private ViewPager pager;
	private TabPageIndicator indicator;
	final Context context = this;
	String token = BeanController.getLoginBean().getAccessToken();//"b1916c6daa00b1d5d2297166008f3a7c4825e6f8";
	private String Email = "";
	MyRequestQueue queue;
	private boolean flag;
	private boolean mIsValid;
	private FormEditText mEtUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Requests");
		setContentView(R.layout.sendrecived_request);
		queue = MyVolley.getRequestQueue();
		adapter = new GoogleMusicAdapter(getSupportFragmentManager());		

		pager = (ViewPager) findViewById(R.id.pager1);
		pager.setAdapter(adapter);

		indicator = (TabPageIndicator) findViewById(R.id.indicator1);
		indicator.setViewPager(pager);

		indicator.notifyDataSetChanged();
		adapter.notifyDataSetChanged();		  
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.getBoolean("POPUP")){
			showDialog();
			flag=Boolean.TRUE;
		}else{
			flag=Boolean.FALSE;
		}
		setTabs();
		setOnTabChangeListener(this);
		mTabHost.setCurrentTab(3);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
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
			showDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onClickNext(View v)
	{
		mIsValid = true;
		FormEditText[] allFields = { mEtUserName};
		
		for (FormEditText field : allFields)
		{
			mIsValid = field.testValidity() && mIsValid;
		}

		//for debug purpose
		if (mIsValid)
		{
			mIsValid = true;
			AppLog.logToast(this, "Valid");
		} else
		{
			mIsValid = false;
			AppLog.logToast(this, "inValid");			
		}
	}
	
	private void showDialog(){
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.requestsent_prompts, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set requestsent_prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		mEtUserName = (FormEditText) promptsView
				.findViewById(R.id.SentrequestEditText);	

		// set dialog message
		alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("Send",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int id) {
				
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int id) {
				System.out.println("Cancel Called....");
				if (flag){
					finish();
				}
				dialog.cancel();
			}
		});

		// create alert dialog
		final AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
		
		//Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
		alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
		      {            
		          @Override
		          public void onClick(View v)
		          {
		        	  onClickNext(v);
						if(mIsValid){
							sendrequest(mEtUserName.getText().toString());	
							alertDialog.dismiss();
							}		             
		          }
		      });

	}

	@Override
	protected void onStop()
	{
		super.onStop();
		queue.cancelAll(this);
	}

	private void sendrequest(String emailid) {
		Map<String, String> loginParam = QueryHelper.caretakerRequestSendQuery(
				token, emailid);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam, this, this);
		customRequest.setTag(this);
		queue.add(customRequest);
		Email = emailid;
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
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 1:
			intent = new Intent(this, SelfScreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 2:
			intent = new Intent(this, UsersCaretakers.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 3:
			
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
