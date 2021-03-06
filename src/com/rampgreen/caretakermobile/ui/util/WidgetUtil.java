package com.rampgreen.caretakermobile.ui.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.DateUtils;
import com.rampgreen.caretakermobile.util.StringUtils;

import java.util.Calendar;

public class WidgetUtil
{
	public static final String INTERNET_NOT_AVAILABLE = "internet";
	
	public static void callDatePicker(Context context, int date, OnDateSetListener callBack) {
		int mYear , mMonth, mDay;
		Calendar c = Calendar.getInstance();
		String startdate = (String) AppSettings.getPrefernce(context, null, AppSettings.DATE_OF_BIRTH, "");
		
		if(StringUtils.notEmpty(startdate))
			c= DateUtils.convertToCalender(startdate, DateUtils.FORMAT_YYYYMMDD_DASHESH);
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		MyDatePickerDialog myDatePickerDialog = new MyDatePickerDialog(context, callBack, mYear, mMonth, mDay);
		myDatePickerDialog.setTitle("Select Date");
		myDatePickerDialog.show();
	}
	
	public static void showSettingDialog(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("You need a network connection to use this application. please turn on mobile network or Wi-Fi settings.")
		.setCancelable(false)
		.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	public static boolean checkInternetConnection(Context paramContext) {
		//need manifest permission <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}
	
	public static String getMacAddress(Context paramContext) {
		String address = null;
		//need manifest permission <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		if(haveConnectedWifi) {
			WifiManager manager = (WifiManager) paramContext.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = manager.getConnectionInfo();
			address = info.getMacAddress();
		}
		return address;
	}
}

