package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FragmentChangeActivity extends BaseSliderActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

	/******************GCM constants**************************/
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";

	private static final String SENDER_ID = "171549290833";
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	String regid;
	/*************************************************************/
	Fragment mContent;
	private String mProfileName = "manuish";
	public static final String TAG_BACKSTACK = "backstacks";
	ArrayList<String> profileTitles = new ArrayList<String>();

	public FragmentChangeActivity() {
		super(R.string.changing_fragments);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		//if savedinstancestate is not null that means activity is recreating on orientation changes
		User user = (User)getIntent().getSerializableExtra(Constants.BUNDLE_KEY_USERS);
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
			mSectionListContent = mContent;
		}
		if (mContent == null) {
			Bundle bundle = new Bundle();
			mContent = new FragmentTabBottom();
			bundle.putSerializable(Constants.BUNDLE_KEY_USERS, user.getUserList());
			bundle.putInt(Constants.ActivityConstants.FRAGMENT_CALLER, Constants.ActivityConstants.FragmentHomeMenuUser);
			mContent.setArguments(bundle);
		}

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();

		// set onCreatethe Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new FragmentMenuColor())
		.commit();
		setSlidingActionBarEnabled(false);

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// navigation callback should call here
		profileTitles.clear();
		profileTitles.add(mProfileName);
		profileTitles.add("Change profile");

		/****************************gcm*****************************************/
		// Check device for Play Services APK. If check succeeds, proceed with GCM registration.
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(this);

			if (regid != null && regid.length() == 0) {
				registerInBackground();
			}
		} else {
			AppLog.d("No valid Google Play Services APK found.");
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// Check device for Play Services APK.
		checkPlayServices();
	}
	//	@Override
	//	public void onSaveInstanceState(Bundle outState) {
	//		outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
	//		super.onSaveInstanceState(outState);
	//		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	//	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// open application , cick visit , back , rotate... BUG:  Fragment visit is not in fragment manager
		outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
		if(mSectionListContent != null) {
			getSupportFragmentManager().putFragment(outState, "mContent", mContent);	
		}
		//		if(mContent != null && (!(mContent instanceof FragmentVisit))) {
		//			getSupportFragmentManager().putFragment(outState, "mContent", mContent);
		//			if(mSectionListContent != null) {
		//				getSupportFragmentManager().putFragment(outState, "sectionlistFrag", mSectionListContent);
		//			}
		//		} else {
		//			getSupportFragmentManager().putFragment(outState, "mContent", mSectionListContent);
		//		}
		super.onSaveInstanceState(outState);
	}

	Fragment mSectionListContent;
	public void switchContent(Fragment fragment) {
		//		if(mContent instanceof FragmentSectionList) {
		//			mSectionListContent = mContent;
		//		}
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();

		//		Fragment instanceFragment =
		//				getSupportFragmentManager().findFragmentById(R.id.content_frame);
		//		FragmentTransaction ft = getSupportFragmentManager()
		//				.beginTransaction();
		//		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		//
		//		if (instanceFragment instanceof FragmentRealTime && fragment instanceof FragmentRealTime) {
		//
		//		} else if (instanceFragment instanceof FragmentVisit && fragment instanceof FragmentVisit) {
		//
		//		} else {
		//			ft.hide(mContent)
		//			.replace(R.id.content_frame, fragment)
		//			.commit();
		//		} 
		//		getSlidingMenu().showContent();


		//		FragmentTransaction ft = getSupportFragmentManager()
		//				.beginTransaction();
		//		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		//		ft.hide(mContent)
		//		.replace(R.id.content_frame, fragment)
		//		.commit();
		//
		//		getSlidingMenu().showContent();


		//		if((mContent instanceof FragmentVisit && fragment instanceof FragmentVisit)
		//				||(mContent instanceof FragmentRealTime && fragment instanceof FragmentRealTime)) {
		//			ft.hide(fragment)
		//			.commit();
		//			getSlidingMenu().showContent();
		//			return;
		//		}
		//		mContent = fragment;
		//		ft.show(fragment)
		//		.replace(R.id.content_frame, fragment)
		//		.commit();
		//
		//		getSlidingMenu().showContent();
	}

	public void switchMenuFragment(Fragment fragment) {

		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, fragment)
		.commit();

		//			getSupportFragmentManager()
		//			.beginTransaction()
		//			.replace(R.id.content_frame, fragment)
		//			.addToBackStack(null)
		//			.commit();
		//			//		getSupportFragmentManager().findFragmentByTag(arg0)
		//			getSlidingMenu().showContent();
	}

	//	public void onSectionItemPressed (Bundle bundle) {
	//		Fragment newContent = FragmentVisit.newInstance();
	//		newContent.setArguments(bundle);
	//		mContent = newContent;
	//
	//		getSupportFragmentManager()
	//		.beginTransaction()
	//		.replace(R.id.content_frame, newContent)
	//		.addToBackStack(null)
	//		.commit();
	//		//		getSupportFragmentManager().findFragmentByTag(arg0)
	//		getSlidingMenu().showContent();
	//	}

	@Override
	protected void setupActionBarAndMenuBar() {
	}

	@Override

	public void onBackPressed() {
		//		int count = getSupportFragmentManager().getBackStackEntryCount();
		//		AppLog.e("**************************"+count+"********************");
		//		if(count < 1) {
		//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//			builder.setTitle("Exit");
		//			builder.setMessage("Are You Sure?");
		//
		//			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		//				public void onClick(DialogInterface dialog, int which) {
		//					dialog.dismiss();
		//					Constants.CLOSE_ALL_ACTIVITIES = true;
		//					finish();
		//				}
		//			});
		//
		//			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
		//				@Override
		//				public void onClick(DialogInterface dialog, int which) {
		//					dialog.dismiss();
		//				}
		//			});
		//			AlertDialog alert = builder.create();
		//			alert.show();
		//		}
		//		else
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem p_item) {

		switch (p_item.getItemId()) {
		case 0:
			return true;
		case android.R.id.home:
			mContent.getActivity();
			//refersh the list on click 
			break;
		case R.id.action_alert:
			openNotificationList();
			return true;
		}
		return super.onOptionsItemSelected(p_item);
	}

	private void openNotificationList()
	{
		Bundle bundle = new Bundle();
		bundle.putString(Constants.CALLED_COMPONENT, Constants.ACTIVITY_FRAGMENT_CHANGE_ACTIVITY);

		Intent intent = new Intent(this, ActivityNotification.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/****************************gcm*****************************************/
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				AppLog.showToast(this, "This device is not supported.");
				Log.i(AppLog.APP_TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Stores the registration ID and the app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGcmPreferences(context);
		int appVersion = getAppVersion(context);
		AppLog.d("Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Gets the current registration ID for application on GCM service, if there is one.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGcmPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId != null && registrationId.length() == 0) {
			AppLog.d("Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			AppLog.d("App version changed.");
			return "";
		}
		Log.e("Registration ID", registrationId);
		return registrationId;
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and the app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(FragmentChangeActivity.this);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					// You should send the registration ID to your server over HTTP, so it
					// can use GCM/HTTP or CCS to send messages to your app.
					sendRegistrationIdToBackend(regid);

					// For this demo: we don't need to send it because the device will send
					// upstream messages to a server that echo back the message using the
					// 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(FragmentChangeActivity.this, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}

				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				AppLog.logToast(FragmentChangeActivity.this, msg);
				//				mDisplay.append(msg + "\n");
			}
		}.execute(null, null, null);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGcmPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(DemoActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}
	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
	 * messages to your app. Not needed for this demo since the device sends upstream messages
	 * to a server that echoes back the message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend(final String regId) {
		// Your implementation here.
		//		http://121.240.116.173/mwservice/index.php?action=gcm&task=register_gcm_user&name=param&email=paramgir@gmail.com&registration_id=dabcdef&token=c632cebd15d3b5a9896227d0526ad46eecc8d451
		String email = (String)AppSettings.getPrefernce(this, null, AppSettings.USER_SELECTED_MAIL_ID,"");
		// get network queue and add request to the queue
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> gcmParam = QueryHelper.createGCMRegQuery(BeanController.getLoginBean().getAccessToken(), "", email, regId);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, gcmParam,
				this, this);
		queue.add(customRequest);
	}

	@Override
	public void onErrorResponse(VolleyError error)
	{
		AppLog.d(AppLog.APP_TAG, "regid is not sent to webserver."+ error.getMessage());
	}

	@Override
	public void onResponse(JSONObject response)
	{
		AppLog.d(AppLog.APP_TAG, "gcm registration successfully  "+response.toString());
	}
}