package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.RainbowPacketModel;
import com.rampgreen.caretakermobile.model.RainbowPacketModel.Packet;
import com.rampgreen.caretakermobile.socket.RTnInternetRouter;
import com.rampgreen.caretakermobile.socket.RTnInternetRouter.OnMessageCallback;
import com.rampgreen.caretakermobile.socket.RTnRouter;
import com.rampgreen.caretakermobile.socket.model.GsonUtil;
import com.rampgreen.caretakermobile.socket.model.StatusData;
import com.rampgreen.caretakermobile.ui.util.WidgetUtil;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

public class Rainbow extends BaseActivity implements OnTabChangeListener, OnClickListener {

	private StatusData mStatusData;
	private TextView mBatteryPercentage;
	private TextView mDeviceStatus;
	private TextView mTransducerStatus;
	private TextView mSensor;
	private TextView mTTS;
	private TextView mTRS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rainbow);
		setTitle("Find Rainbow");

		setTabs();
		setOnTabChangeListener(this);
		mTabHost.setCurrentTab(4);

		// getting the id from xml
		mBatteryPercentage = (TextView)findViewById(R.id.tv_battery);
		mDeviceStatus = (TextView)findViewById(R.id.tv_device_status);
		mTransducerStatus = (TextView)findViewById(R.id.tv_transducer);
		mSensor = (TextView)findViewById(R.id.tv_sensor);
		mTTS = (TextView)findViewById(R.id.tv_tts);
		mTRS = (TextView)findViewById(R.id.tv_trs);
		
		Button  sensorRegistraionButton = (Button)findViewById(R.id.btn_sensor_registration);
		Button  batteryButton = (Button)findViewById(R.id.btn_refresh_battery);
		Button  deviceButton = (Button)findViewById(R.id.btn_refresh_device);
		Button  transducerButton = (Button)findViewById(R.id.btn_refresh_transducer);
		Button  sensorButton = (Button)findViewById(R.id.btn_refresh_sensor);
		Button  ttsButton = (Button)findViewById(R.id.btn_refresh_tts);
		Button  trsButton = (Button)findViewById(R.id.btn_refresh_trs);

		// register onclicklistener on each button
		sensorRegistraionButton.setOnClickListener(this);
		batteryButton.setOnClickListener(this);
		deviceButton.setOnClickListener(this);
		transducerButton.setOnClickListener(this);
		sensorButton.setOnClickListener(this);
		ttsButton.setOnClickListener(this);
		trsButton.setOnClickListener(this);
		resetVisibility();
		//		Packet a = RainbowPacketModel.Packet.INSTRUCTION_PACKET;
		//		String b = a.getValue();
	}

	@Override
	public void onResponse(JSONObject response){}

	@Override
	public void onErrorResponse(VolleyError error){}

	private void sendToCloudServer()
	{
		// register callback so that we could refresh ui
		RTnInternetRouter.OnMessageCallback onMessageCallback = new RTnInternetRouter.OnMessageCallback()
		{
			@Override
			public void receiveData(String jsonString)
			{
				AppLog.d(AppLog.APP_TAG, "WS response in Rainbow: " + jsonString);
				Gson gson = new Gson();
				mStatusData = gson.fromJson(jsonString, StatusData.class);
				refreshUi();
			}
		};
		// device ID required to cloud in each packet
		String deviceID =	(String) AppSettings.getPrefernce(Rainbow.this, null, AppSettings.DEVICE_ID, "");
		if (deviceID.length() == 10) {
			String C1 = (String) AppSettings.getPrefernce(Rainbow.this, null, AppSettings.FIRST_LEFT_HEXDIGIT, "");
			String C2 =	(String) AppSettings.getPrefernce(Rainbow.this, null, AppSettings.FIRST_RIGHT_HEXDIGIT, "");
			AppLog.d(AppLog.APP_TAG, "URID: " + deviceID);
//			deviceID = deviceID+C1+C2;	
			String statusDataJSon = GsonUtil.createStatusDataJsonString(deviceID, C1+C2);

			RTnInternetRouter.setOnMsgCallback(onMessageCallback);
			boolean isSend = RTnInternetRouter.sendToRTnCloudServer(statusDataJSon);
			AppLog.d(AppLog.APP_TAG, "Is Json sent to cloud ? : "+isSend+"****if false the connection is lost.");
			//		} else if(deviceID.length() == 12){
			//			AppLog.d(AppLog.APP_TAG, "deviceID : " + deviceID);
			//			String statusDataJSon = GsonUtil.createStatusDataJsonString(deviceID, "");
			//
			//			RTnInternetRouter.setOnMsgCallback(onMessageCallback);
			//			RTnInternetRouter.sendToRTnCloudServer(statusDataJSon);
		}
	}

	@Override
	public void onTabChanged(String tabId)
	{
		Intent intent;
		int tabNum = Integer.parseInt(tabId);

		switch (tabNum) {
		case 0:
			intent = new Intent(getApplicationContext(), FragmentChangeActivity.class);
			intent.putExtra(Constants.BUNDLE_KEY_USERS, BeanController.getUserBean());
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 1:
			intent = new Intent(this, SelfScreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 2:
			intent = new Intent(this, UsersCaretakers.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 3:
			intent = new Intent(this, SendReceived_Request.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 4:

			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v)
	{
		if(! WidgetUtil.checkInternetConnection(Rainbow.this)) {
			WidgetUtil.showSettingDialog(Rainbow.this);
			return;
		}

		switch(v.getId()) {
		case R.id.btn_sensor_registration:
			Intent intent= new Intent(Rainbow.this, ActivityRainbowRegistration.class);
			Rainbow.this.startActivity(intent); 
			break;

		case R.id.btn_refresh_battery:
			mBatteryPercentage.setVisibility(View.VISIBLE);
			sendToCloudServer();
			break;

		case R.id.btn_refresh_device:
			mDeviceStatus.setVisibility(View.VISIBLE);
			sendToCloudServer();
			break;

		case R.id.btn_refresh_transducer:
			mTransducerStatus.setVisibility(View.VISIBLE);
			sendToCloudServer();
			break;

		case R.id.btn_refresh_sensor:
			mSensor.setVisibility(View.VISIBLE);
			sendToCloudServer();
			break;

		case R.id.btn_refresh_tts:
			mTTS.setVisibility(View.VISIBLE);
			sendToCloudServer();
			break;

		case R.id.btn_refresh_trs:
			mTRS.setVisibility(View.VISIBLE);
			sendToCloudServer();
			break;
		}
	}
	
	private void resetVisibility() {
		mBatteryPercentage.setVisibility(View.INVISIBLE);
		mDeviceStatus.setVisibility(View.INVISIBLE);
		mTransducerStatus.setVisibility(View.INVISIBLE);
		mSensor.setVisibility(View.INVISIBLE);
		mTRS.setVisibility(View.INVISIBLE);
		mTTS.setVisibility(View.INVISIBLE);
	}
	
	private void refreshUi()
	{
		mBatteryPercentage.setText(mStatusData.getBatteryPercentage());
		mDeviceStatus.setText(mStatusData.getDeviceState());
		mTransducerStatus.setText(mStatusData.getTransducerState());
		mSensor.setText(mStatusData.getSensorState());
		mTTS.setText(mStatusData.getTts());
		mTRS.setText(mStatusData.getTrs());
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		resetVisibility();
	}
}
