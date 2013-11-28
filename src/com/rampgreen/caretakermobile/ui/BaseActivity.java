package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.Response;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.ui.util.TabBitmap;
import com.rampgreen.caretakermobile.util.Constants;

/**
 * Base activity class is used for future purposes. we can add functionality to all classes that will extend it. 
 * 
 * @author Manish Pathak
 *
 */
abstract public class BaseActivity extends SherlockFragmentActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
	private static final String TAG_1 = "0";
	private static final String TAG_2 = "1";
	private static final String TAG_3 = "2";
	private static final String TAG_4 = "3";
	private static final String TAG_5 = "4";

	private ProgressDialog progressDialog;
	protected TabHost mTabHost;
	private static int numAsyncTasks;

	
	protected void showLoadingBar(){
		numAsyncTasks++;
		progressDialog = ProgressDialog.show(this, null, "Loading...", true);
	}

	protected void closeLoadingBar(){
		if (0 == --numAsyncTasks) {
			if(progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}
	}

	@Override
	public void onBackPressed()
	{
		// cancel all requests when user press back button while loading bar is shown. 
		if(progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			MyRequestQueue queue = MyVolley.getRequestQueue();
			queue.cancelAll();
		}
		super.onBackPressed();

	}

	public void setActionBarTitle(String title) {
		setTitle(title);
	}

	protected void setTabs() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		addTab("Home", TAG_1, createTabDrawable(R.drawable.home), R.id.text_view_home);
		addTab("Self", TAG_2, createTabDrawable(R.drawable.self), R.id.text_view_search);
		addTab("Network", TAG_3, createTabDrawable(R.drawable.network), R.id.text_view_favourites);
		addTab("Request", TAG_4, createTabDrawable(R.drawable.request), R.id.text_view_settings);
		addTab("Rainbow", TAG_5, createTabDrawable(R.drawable.rainbow), R.id.text_view_rainbow);
	}

	private Drawable createTabDrawable(int resId) {
		Resources res = getResources();
		StateListDrawable states = new StateListDrawable();

		final Options options = new Options();
		options.inPreferredConfig = Config.ARGB_8888;

		Bitmap icon = BitmapFactory.decodeResource(res, resId, options);

		Bitmap unselected = TabBitmap.createUnselectedBitmap(res, icon);
		Bitmap selected = TabBitmap.createSelectedBitmap(res, icon);

		icon.recycle();

		states.addState(new int[] { android.R.attr.state_selected }, new BitmapDrawable(res, selected));
		states.addState(new int[] { android.R.attr.state_enabled }, new BitmapDrawable(res, unselected));

		return states;
	}

	private View createTabIndicator(String label, Drawable drawable) {
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);

		TextView txtTitle = (TextView) tabIndicator.findViewById(R.id.text_view_tab_title);
		txtTitle.setText(label);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) txtTitle.getLayoutParams();
		txtTitle.setLayoutParams(params);

		ImageView imgIcon = (ImageView) tabIndicator.findViewById(R.id.image_view_tab_icon);
		imgIcon.setImageDrawable(drawable);

		return tabIndicator;
	}

	private void addTab(String label, String tag, Drawable drawable, int id) {
		TabHost.TabSpec spec = mTabHost.newTabSpec(tag);
		spec.setIndicator(createTabIndicator(label, drawable));
		spec.setContent(id);

		mTabHost.addTab(spec);
		mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
			Intent intent;
			public void onTabChanged(String tabId) {
				int tabNum = Integer.parseInt(tabId);

				switch (tabNum) {
				case 0:
//					intent = new Intent(getApplicationContext(), FragmentChangeActivity.class);
//					intent.putExtra(Constants.BUNDLE_KEY_USERS, BeanController.getUserBean());
//					startActivity(intent);
					break;

				case 1:
					break;

				case 2:
//					intent = new Intent(getApplicationContext(), UsersCaretakers.class);
//					startActivity(intent);
					break;

				case 3:
//					intent = new Intent(getApplicationContext(), SendReceived_Request.class);
//					startActivity(intent);
					break;

				case 4:
//					intent = new Intent(getApplicationContext(), Rainbow.class);
//					startActivity(intent);
					break;

				default:
					break;
				}
				//				UiUtil.setTabColor(getSherlockActivity(),tabHost);
				//				if (getSherlockActivity() instanceof FragmentChangeActivity) {
				//					Bundle bundle = new Bundle();
				//					bundle.putString(com.rampgreen.caretakermobile.util.Constants.POSITION, tabId);
				//					((FragmentChangeActivity)getSherlockActivity()).mContent.setArguments(bundle);
				//				}

				//				AppLog.logToast(FragmentTabBottom.this.getSherlockActivity(), "Tab click"+tabId);
			}});
	}

	/**
	 * @param label
	 * @param tag
	 * @param drawable
	 * @param intent The activity that need to start on click of particular tab
	 */
	private void addTab(String label, String tag, Drawable drawable, Intent intent) {
		TabHost.TabSpec spec = mTabHost.newTabSpec(tag);
		spec.setIndicator(createTabIndicator(label, drawable));
		spec.setContent(intent);

		mTabHost.addTab(spec);
	}
	
	protected void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
		mTabHost.setOnTabChangedListener(onTabChangeListener);
	}
	
	protected void setHeader(String title, boolean btnHomeVisible, boolean btnFeedbackVisible, boolean headerDetailVisible)
	{
		ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
		View inflated = stub.inflate();
		TextView txtTitle = (TextView) inflated.findViewById(R.id.txvheader);
		txtTitle.setText(title);

//		final ImageButton btnHome = (ImageButton) inflated.findViewById(R.id.btn_home);
//		btnHome.setBackgroundResource(R.drawable.icon_account);
//		ImageView btnHomeSeperator = (ImageView) inflated.findViewById(R.id.id_seperator_home);
//		if(!btnHomeVisible) {
//			btnHome.setVisibility(View.INVISIBLE);
//			btnHomeSeperator.setVisibility(View.INVISIBLE);
//		}

//		ImageButton btnSetting= (ImageButton) inflated.findViewById(R.id.btn_setting);
//		ImageView btnSettingSeperator = (ImageView) inflated.findViewById(R.id.id_seperator_setting);
//		if(!btnFeedbackVisible) {
//			btnSetting.setVisibility(View.INVISIBLE);
//			btnSettingSeperator.setVisibility(View.INVISIBLE);
//		}
		
//		TextView headerDetail= (TextView) inflated.findViewById(R.id.txvheader_detail);
//		if(!headerDetailVisible) {
//			headerDetail.setVisibility(View.INVISIBLE);
//		}
	}

	/**
	 * Home button click handler
	 * @param v
	 */
	 public void btnHomeClick(View v) {
//		Intent callIntent = new Intent(this, OldIntro.class);
//		callIntent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(callIntent);
//		registerEvent("btn home click");
	 }

	 /**
	  * Feedback button click handler
	  * @param v
	  */
	 public void btnSettingClick(View v) {
//		 Intent callIntent = new Intent(this, PreferencesActivity.class);
//		 startActivity(callIntent);
//		 registerEvent("btn setting click");
	 }
}
