package com.rampgreen.caretakermobile.ui.util;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 *  @author Manish Pathak
 * 
 */
public class MyDatePickerDialog extends DatePickerDialog{
	public static final int START_DATE = 0;
	int mYear, mMonth,mDay;
	Context mContext;

	/**
	 * Constructor for MyDatePickerDialog.
	 * @param context Context
	 * @param callBack OnDateSetListener
	 * @param year int
	 * @param monthOfYear int
	 * @param dayOfMonth int
	 */
	public MyDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		mContext = context;
	}

	/**
	 * Constructor for MyDatePickerDialog.
	 * @param context Context
	 * @param callBack OnDateSetListener
	 * @param year int
	 * @param monthOfYear int
	 * @param dayOfMonth int
	 * @param title String
	 */
	public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth, String title) {
		this(context, callBack, year, monthOfYear, dayOfMonth);
			setTitle(title);
		mContext = context;
	}
	
	/**
	 * Constructor for MyDatePickerDialog.
	 * @param context Context
	 * @param theme int
	 * @param callBack OnDateSetListener
	 * @param year int
	 * @param monthOfYear int
	 * @param dayOfMonth int
	 */
	public MyDatePickerDialog(Context context, int theme,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, theme, callBack, year, monthOfYear, dayOfMonth);
		mContext = context;
	}
}
