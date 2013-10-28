package com.rampgreen.caretakermobile.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class UiUtil {

//	public static void setTabColor(Context context, TabHost tabhost) {
//		int colorTabSel = context.getResources().getColor(R.color.tab_selected);
//		int colorTabUnSel = context.getResources().getColor(R.color.tab_unselected);
//	    for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
//	    {
//	        tabhost.getTabWidget().getChildAt(i).setBackgroundColor(colorTabUnSel); //unselected
//	    }
//	    tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(colorTabSel); // selected
//	}
	
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

}
