package com.rampgreen.caretakermobile.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.NotificationArrayAdapter;

public class ActivityAlert extends BaseActivity implements OnItemClickListener {
	public static final String[] titles = new String[] {
			"You've been sitting for too long. Maybe you should stretch",
			"Your heart rate is 120/Min", "Your temperature is now 101" };

	ListView listView;
	List<String> rowItems;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_alert);

		rowItems = new ArrayList<String>();
		for (int i = 0; i < titles.length; i++) {
			rowItems.add(titles[i]);
		}

		listView = (ListView) findViewById(R.id.listview);
		NotificationArrayAdapter adapter = new NotificationArrayAdapter(this,
				R.layout.notification, rowItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
		setHeader("Alert", true, true, false, R.drawable.profile, R.drawable.mydata);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void btnHomeClick(View v)
	{
		super.btnHomeClick(v);
		//profile
		startActivity(new Intent(this, ActivityProfile.class));
		
	}
	
	@Override
	public void btnSettingClick(View v)
	{
		super.btnSettingClick(v);
		//mydata
		startActivity(new Intent(this, SelfScreen.class));
	}

}
