package com.rampgreen.caretakermobile.ui;

import com.rampgreen.caretakermobile.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Rainbow extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rainbow);
		setTitle("Find Rainbow");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.rainbow, menu);
		
		return true;
	}

}
