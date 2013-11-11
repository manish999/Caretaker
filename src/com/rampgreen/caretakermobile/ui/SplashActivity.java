//package com.rampgreen.caretakermobile.ui;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//
//import com.rampgreen.caretakermobile.util.AppLog;
//import com.rampgreen.caretakermobile.util.AppSettings;
//import com.rampgreen.caretakermobile.util.DeviceUtil;
//import com.rampgreen.caretakermobile.util.StringUtils;
//
//public class SplashActivity extends Activity {
//
//	//stopping splash screen starting home activity.
//	private static final int STOPSPLASH = 0;
//	//time duration in millisecond for which your splash screen should visible to
//	//user. here i have taken half second
//	private static final long SPLASHTIME = 10;
////	String selectedMailID = "";
//	String accountToken = "";
//	private Handler splashHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case STOPSPLASH:
//				Intent intent = new Intent();
//				selectedMailID = (String) AppSettings.getPrefernce(SplashActivity.this, null, AppSettings.USER_SELECTED_MAIL_ID, "");
//				accountToken = (String) AppSettings.getPrefernce(SplashActivity.this, null, AppSettings.ACCESS_TOKEN, "");
//				//Generating and Starting new intent on splash time out	
//				if( StringUtils.notEmpty(accountToken) ){
//					intent.setClass(getApplicationContext(), FragmentChangeActivity.class);
//				} else {
//					intent.setClass(getApplicationContext(), LoginActivity.class);
//				}
////				intent.putExtra(Constants.CALLING_ACTIVITY_TYPE, Constants.CALLING_ACTIVITY_SPLASH);
//				startActivity(intent);
//				SplashActivity.this.finish(); 
//				break;
//			}
//			super.handleMessage(msg);
//		}
//	};
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		// first time preinitialization
//		long lastVersionInstalled = DeviceUtil.getVersionInstalled(this);
//		long current_updated_v = DeviceUtil.getVersion(this);
//		if( lastVersionInstalled  != current_updated_v ){
//			AppLog.d("MOV", "version is updated so some pre initialization can be done here");
//			// whenever user update app , there should be a fresh start.
//			AppSettings.setPreference(this, null, AppSettings.USER_SELECTED_MAIL_ID, "");
//			AppSettings.setPreference(this, null, AppSettings.ACCESS_TOKEN, "");
//		}
//
//		Message msg = new Message();
//		msg.what = STOPSPLASH;
//		splashHandler.sendMessageDelayed(msg, SPLASHTIME);	
//	}
//
//	public void onBackPressed() {
//	}
//}
