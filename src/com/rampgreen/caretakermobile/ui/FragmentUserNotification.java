package com.rampgreen.caretakermobile.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.UserNotificationExpandableListAdapter;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.SensorData;
import com.rampgreen.caretakermobile.model.UserNotificationEntity;
import com.rampgreen.caretakermobile.model.UserNotificationEntity.GroupItemEntity;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;

public class FragmentUserNotification extends SherlockFragment implements
Response.Listener<JSONObject>, Response.ErrorListener{
	
	private ExpandableListView mExpandableListView;
	private List<UserNotificationEntity> mGroupCollection;
	private static ArrayList<SensorData> sensorDataList;
	private String token = BeanController.getLoginBean().getAccessToken();
	private String strUserId = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		 
		return inflater.inflate(R.layout.usernotification, null); 
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		 
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
//        formattedDate = "2013-12-18";
        String startDate = formattedDate + " 00:00:00";
        String endDate = formattedDate + " 23:59:59";
        strUserId = getArguments().getString("userid");      
		
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.getSensorDataQuery(token,strUserId, startDate, endDate);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam, this, this);	
		
		customRequest.setTag(this);			
//		customRequest.setPriority(Priority.IMMEDIATE);
		queue.add(customRequest);			
		queue.start();
	}
	
	private void prepareResource() {

		mGroupCollection = new ArrayList<UserNotificationEntity>();
		
		UserNotificationEntity ge = new UserNotificationEntity();
		GroupItemEntity gi = ge.new GroupItemEntity();
		
		ge.Name = "GSR";
		if(sensorDataList != null){
			for (int j = 0; j < sensorDataList.size(); j++){
				gi = ge.new GroupItemEntity();
				try {
					gi.Name = "GSR at -" + getDateCurrentTimeZone(sensorDataList.get(j).updated_on).toString() + " is " + String.valueOf(sensorDataList.get(j).getgsr());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ge.GroupItemCollection.add(gi);
			}
		}		
		mGroupCollection.add(ge);
		
		ge = new UserNotificationEntity();		
		ge.Name = "Heart Rate";	
		if(sensorDataList != null){
			for (int j = 0; j < sensorDataList.size(); j++){
				gi = ge.new GroupItemEntity();
				try {
					gi.Name = "Heart Rate at -" + getDateCurrentTimeZone(sensorDataList.get(j).updated_on).toString() + " is " + String.valueOf(sensorDataList.get(j).getpox_hr()) + " and Pulse is " + String.valueOf(sensorDataList.get(j).getpox_pulse());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ge.GroupItemCollection.add(gi);
			}
		}			
		mGroupCollection.add(ge);
		
		ge = new UserNotificationEntity();		
		ge.Name = "Accelerometer";
		if(sensorDataList != null){
			for (int j = 0; j < sensorDataList.size(); j++){
				gi = ge.new GroupItemEntity();
				try {
					gi.Name = "Accelerometer at -" + getDateCurrentTimeZone(sensorDataList.get(j).updated_on).toString() + " x is  " + String.valueOf(sensorDataList.get(j).getacc_x()) + " and y is " + String.valueOf(sensorDataList.get(j).getacc_y()) + " and z is " + String.valueOf(sensorDataList.get(j).getacc_z());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ge.GroupItemCollection.add(gi);
			}
		}			
		mGroupCollection.add(ge);
		
		ge = new UserNotificationEntity();
		gi = ge.new GroupItemEntity();
		ge.Name = "Temperature";
		if(sensorDataList != null){
			for (int j = 0; j < sensorDataList.size(); j++){
				gi = ge.new GroupItemEntity();
				try {
					gi.Name = "Temperature at -" + getDateCurrentTimeZone(sensorDataList.get(j).updated_on).toString() + " Ambient  " + String.valueOf(sensorDataList.get(j).gettemp_ambient()) + " Skin is " + String.valueOf(sensorDataList.get(j).gettemp_skin());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ge.GroupItemCollection.add(gi);
			}
		}		
		mGroupCollection.add(ge);
	}

	private void initPage() {
		mExpandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandableListView);
		/*mExpandableListView.setDividerHeight(2);*/
		/*mExpandableListView.setChildDivider(getResources().getDrawable(R.color.gray));*/
		UserNotificationExpandableListAdapter adapter = new UserNotificationExpandableListAdapter(getActivity(),
				mExpandableListView, mGroupCollection);

		mExpandableListView.setAdapter(adapter);
	}		
	

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(JSONObject response) {
		int code = Integer.parseInt(response.optString("code"));
		String msg = response.optString("message");		
		switch (code) {
		case ParserError.CODE_ACTION_NOT_FOUND:			
			break;
		case ParserError.CODE_MISSING_ACTION:			
			break;
		case ParserError.CODE_MISSING_TASK:			
			break;
		case ParserError.CODE_CLIENT_AUTHORIZATION_FAILED:			
			break;
		case ParserError.CODE_TOKEN_GENERATION_FAILED:			
			break;
		case ParserError.CODE_USERNAME_REQUIRED:			
			break;
		case ParserError.CODE_PASSWORD_REQUIRED:			
			break;
		case ParserError.CODE_PASSWORD_WRONG:				
			break;
		case ParserError.CODE_USER_NOT_REGISTERED:			
			break;
		case ParserError.CODE_INVALID_TOKEN:			
			break;
		case ParserError.CODE_TOKEN_EXPIRED:			
			break;
		case ParserError.CODE_INTERNAL_SERVER_ERROR:			
			break;
		case ParserError.CODE_USER_ALREADY_REGISTERED:			
			break;
		case ParserError.CODE_NO_REQUEST_RECEIVED:			
			break;
		case ParserError.CODE_NO_CARETAKER_REQUEST_SENT_IS_PENDING:				
			break;
		case ParserError.CODE_DEVICE:	
			AppLog.showToast(getActivity(), "No device mapped with this user");	
			break;
		case ParserError.CODE_SENSOR:
			AppLog.showToast(getActivity(), "No sensor data available for this user");			
			break;
		case ParserError.CODE_SUCCESS:
			getSensorData(response);	
			prepareResource();
			initPage();	
			break;

		default:
			break;

		}

		if (code != ParserError.CODE_SUCCESS) {
			AppLog.logToast(getActivity(), "error web service response code - "
					+ code);
		}		
	}

	/**
	 * Gets the sensor_ data.
	 * 
	 * @param jsonObject
	 *            the json object
	 * @return the array list
	 */
	private ArrayList<SensorData> getSensorData(JSONObject jsonObject) {
		sensorDataList = new ArrayList<SensorData>();
		try {
			JSONArray jArray = jsonObject.getJSONArray("sensor_data");
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject objJson = jArray.getJSONObject(i);
				jsonObject = jArray.getJSONObject(i);
				SensorData sensorData = new SensorData();
				sensorData.temp_ambient = Double.parseDouble(objJson
						.getString("temp_ambient"));
				sensorData.temp_skin = Double.parseDouble(objJson
						.getString("temp_skin"));
				sensorData.pox_hr = Double.parseDouble(objJson
						.getString("pox_hr"));
				sensorData.pox_pulse = Double.parseDouble(objJson
						.getString("pox_pulse"));
				sensorData.acc_x = Double.parseDouble(objJson.getString("acc_x"));
				sensorData.acc_y = Double.parseDouble(objJson.getString("acc_y"));
				sensorData.acc_z = Double.parseDouble(objJson.getString("acc_z"));
				sensorData.gsr = Double.parseDouble(objJson.getString("gsr"));
				sensorData.led1ac = Double.parseDouble(objJson
						.getString("led1ac"));
				sensorData.led2ac = Double.parseDouble(objJson
						.getString("led2ac"));
				sensorData.led1dc = Double.parseDouble(objJson
						.getString("led1dc"));
				sensorData.led2dc = Double.parseDouble(objJson
						.getString("led2dc"));
				sensorData.updated_on = objJson.getString("updated_on");
				sensorDataList.add(sensorData);
			}
		} catch (Exception e) {
			// need to handle later
			// TODO: handle exception
		}
		return sensorDataList;
	}

	public static Date getDateCurrentTimeZone(String timestamp)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(timestamp);
		return date;
	}
}
