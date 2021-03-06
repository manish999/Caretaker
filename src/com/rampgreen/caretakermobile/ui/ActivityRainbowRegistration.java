package com.rampgreen.caretakermobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;

public class ActivityRainbowRegistration extends SherlockFragmentActivity
{
	public static final int RED_ON = 4; 
	public static final int GREEN_ON = 2;
	public static final int YELLOW_ON = 1;
	
	int yellowValue = 0;
	int greenValue = 0;
	int redValue = 0;

	boolean firstClicked = false;
	ToggleButton  redButton;
	ToggleButton  yellowButton;
	ToggleButton  greenButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rainbow_registration);

		redButton = (ToggleButton)findViewById(R.id.toggle_red);
		yellowButton = (ToggleButton)findViewById(R.id.toggle_yellowish);
		greenButton = (ToggleButton)findViewById(R.id.toggle_greeno);
		
		getSupportActionBar().setHomeButtonEnabled (true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		final TextView  textInstruction = (TextView)findViewById(R.id.textView1);
		final Button  okButton = (Button)findViewById(R.id.btn_ok);
		okButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if(firstClicked) {
					// calculation after second time userclick on button
					calculateRightHexDigit(redValue+yellowValue+greenValue);
					firstClicked = false;
					okButton.setText("Ok");
					textInstruction.setText("Press Sensor Button on the device and press corresponding LEDs below");
				} else {
					// calculation after first time userclick on button 
					calculateLeftHexDigit(redValue+yellowValue+greenValue);
					firstClicked = true;
					okButton.setText("Submit");
					textInstruction.setText("Press Sensor Button again on the device and press corresponding LEDs below");
				}
				resetToggleButton();
			}
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onYellowClicked(View view) {
		if(((ToggleButton) view).isChecked()) {
			yellowValue = 1;
		} else {
			yellowValue = 0;
		}    
	}

	public void onGreenClicked(View view) {
		if(((ToggleButton) view).isChecked()) {
			greenValue = 2;
		} else {
			greenValue = 0;
		}    
	}
	
	public void onRedClicked(View view) {
		if(((ToggleButton) view).isChecked()) {
			redValue = 4;
		} else {
			redValue = 0;
		}    
	}

	private void calculateLeftHexDigit(int firstDigit)
	{
		AppSettings.setPreference(this, null, AppSettings.FIRST_LEFT_HEXDIGIT, firstDigit+"");
	}

	private void calculateRightHexDigit(int secondDigit)
	{
		String firstHexDigit = (String)AppSettings.getPrefernce(this, null, AppSettings.FIRST_LEFT_HEXDIGIT, "");
		AppSettings.setPreference(this, null, AppSettings.FIRST_RIGHT_HEXDIGIT, secondDigit+"");
		AppLog.showToast(this, "Rainbow registration is completed successfully.");
//		Toast.makeText(this, firstHexDigit+secondDigit, Toast.LENGTH_SHORT).show();
		sendDeviceID();
//		Intent	routerSetupIntent= new Intent(ActivityRainbowRegistration.this, RouterService.class);
//		ActivityRainbowRegistration.this.startService(routerSetupIntent);
		finish();
	}
	
	void sendDeviceID()
	{
		//C=C1C2 , 2 last digit of MAC id of the Health device
//		String C1 = (String) AppSettings.getPrefernce(this, null, AppSettings.FIRST_LEFT_HEXDIGIT, "");
//		String C2 =	(String) AppSettings.getPrefernce(this, null, AppSettings.FIRST_RIGHT_HEXDIGIT, "");
//		String deviceID =	(String) AppSettings.getPrefernce(this, null, AppSettings.DEVICE_ID, "");
////		AppLog.d(AppLog.APP_TAG, "deviceID with out 2 digit: " + deviceID);
////		deviceID = deviceID+C1+C2;
//		AppLog.d(AppLog.APP_TAG, "deviceID : " + deviceID);
//		String macID = WidgetUtil.getMacAddress(this);
//		String jsonString = GsonUtil.createDeviceIdJsonString(macID, deviceID, C1+C2);
//		
//		boolean isSend = RTnInternetRouter.sendToRTnCloudServer(jsonString);
//		AppLog.d(AppLog.APP_TAG, "Is Json sent to cloud ? : "+isSend+"****if false the connection is lost.");
	}

	private void resetToggleButton()
	{
		redButton.setChecked(false);
		yellowButton.setChecked(false);
		greenButton.setChecked(false);
		redValue = 0;
		yellowValue = 0;
		greenValue = 0;
	}

}
