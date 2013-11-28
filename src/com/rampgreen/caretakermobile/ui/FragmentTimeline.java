package com.rampgreen.caretakermobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.rampgreen.caretakermobile.R;

public class FragmentTimeline extends SherlockFragment {

	public static FragmentTimeline newInstance() {
		return new FragmentTimeline();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_profile_timeline, null);
	}
}