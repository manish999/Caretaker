package com.rampgreen.caretakermobile.adapter;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.R.color;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.SensorData;
import com.rampgreen.caretakermobile.model.TextDisplaySettings;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;

/**
 * The Class TextDisplayAdapter.
 */
public class TextDisplayAdapter extends BaseAdapter {

	private ArrayList<TextDisplaySettings> caretakersUsers;
	private LayoutInflater inflater;
	private UserListProvider userListProvider;
	String accessToken = BeanController.getLoginBean().getAccessToken();
	/** The LIS t_ sensor data. */
	private static ArrayList<SensorData> LIST_SensorData;

	/**
	 * Instantiates a new text display adapter.
	 *
	 * @param context the context
	 * @param caretakersUsers the caretakers users
	 */
	public TextDisplayAdapter(Context context,
			ArrayList<TextDisplaySettings> caretakersUsers) {
		this.caretakersUsers = caretakersUsers;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		userListProvider = new UserListProvider();
		GetSensorData(caretakersUsers);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.threaded_view_list_item_rec, null);
			holder = new ViewHolder();
			holder.accountName = (TextView) convertView
					.findViewById(R.id.TextViewMsg);		
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			holder = (ViewHolder) convertView.getTag();
		}
		User user = userListProvider.getUser(caretakersUsers.get(position)
				.getUserID());
		TextDisplaySettings setting = caretakersUsers.get(position);		
		Spanned spanned = Html.fromHtml(DisplayTextValue(user.getUid(),
				user.getUsername(), setting.getBiometricID()));
		holder.accountName.setText(spanned);
		return convertView;
	}

	/**
	 * The Class ViewHolder.
	 */
	public static class ViewHolder {
		TextView accountName;
		ImageView imageIcon;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (caretakersUsers != null) {
			return caretakersUsers.size();
		} else {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Sets the list.
	 *
	 * @param caretakersUsers the new list
	 */
	public void setList(ArrayList<TextDisplaySettings> caretakersUsers) {
		this.caretakersUsers = caretakersUsers;
		notifyDataSetChanged();
	}

	/**
	 * Gets the sensor data.
	 * 
	 * @param caretakersUsers2
	 *            the user array
	 */
	private void GetSensorData(ArrayList<TextDisplaySettings> caretakersUsers2) {
		for (int i = 0; i < caretakersUsers2.size(); i++) {
			MyRequestQueue queue = MyVolley.getRequestQueue();
			Map<String, String> loginParam = QueryHelper
					.createAllUsersQuery(accessToken);

			CustomRequest customRequest = new CustomRequest(Method.POST,
					Constants.URL_WEB_SERVICE, loginParam,
					new ResponseListener(), new ErrorListener());

			customRequest.setTag(this);
			queue.add(customRequest);
			queue.start();
		}
	}

	/**
	 * The listener interface for receiving response events.
	 * The class that is interested in processing a response
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addResponseListener<code> method. When
	 * the response event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ResponseEvent
	 */
	private class ResponseListener implements Response.Listener<JSONObject> {
		@Override
		public void onResponse(JSONObject response) {
			int code = Integer.parseInt(response.optString("code"));
			String msg = response.optString("message");
			switch (code) {
			case ParserError.CODE_NO_USER_FOUND:
				// closeLoadingBar();
				break;
			case ParserError.CODE_SUCCESS:
				GetSensor_Data(response);
				notifyDataSetChanged();
				break;

			default:
				break;
			}

			if (code != ParserError.CODE_SUCCESS) {

			}
		}
	}

	/**
	 * The listener interface for receiving error events.
	 * The class that is interested in processing a error
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addErrorListener<code> method. When
	 * the error event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ErrorEvent
	 */
	private class ErrorListener implements Response.ErrorListener {
		@Override
		public void onErrorResponse(VolleyError error) {
		}
	}

	/**
	 * Gets the sensor_ data.
	 * 
	 * @param jsonObject
	 *            the json object
	 * @return the array list
	 */
	private ArrayList<SensorData> GetSensor_Data(JSONObject jsonObject) {
		LIST_SensorData = new ArrayList<SensorData>();
		JSONArray jsonUserArray;
		try {
			jsonUserArray = jsonObject.getJSONArray("users");
			for (int i = 0; i < jsonUserArray.length(); i++) {
				JSONObject jsonObjUserAndSetting = jsonUserArray
						.getJSONObject(i);
				JSONObject jsonObjUser = jsonObjUserAndSetting
						.getJSONObject("user");
				String userId = jsonObjUser.getString("u_id");
				if (!jsonObjUserAndSetting.isNull("sensor_values")) {
					JSONObject jsonBio = jsonObjUserAndSetting
							.getJSONObject("sensor_values");
					JSONArray jArray = new JSONArray();
					jArray.put(jsonBio);
					for (int i1 = 0; i1 < jArray.length(); i1++) {
						JSONObject objJson = jArray.getJSONObject(i1);
						jsonObject = jArray.getJSONObject(i1);
						SensorData Obj_Temp = new SensorData();
						Obj_Temp.UserID = userId;
						Obj_Temp.temp_ambient = Double.parseDouble(objJson
								.getString("temp_ambient"));
						Obj_Temp.temp_skin = Double.parseDouble(objJson
								.getString("temp_skin"));
						Obj_Temp.pox_hr = Double.parseDouble(objJson
								.getString("pox_hr"));
						Obj_Temp.pox_pulse = Double.parseDouble(objJson
								.getString("pox_pulse"));
						Obj_Temp.acc_x = Double.parseDouble(objJson
								.getString("acc_x"));
						Obj_Temp.acc_y = Double.parseDouble(objJson
								.getString("acc_y"));
						Obj_Temp.acc_z = Double.parseDouble(objJson
								.getString("acc_z"));
						Obj_Temp.gsr = Double.parseDouble(objJson
								.getString("gsr"));
						Obj_Temp.led1ac = Double.parseDouble(objJson
								.getString("led1ac"));
						Obj_Temp.led2ac = Double.parseDouble(objJson
								.getString("led2ac"));
						Obj_Temp.led1dc = Double.parseDouble(objJson
								.getString("led1dc"));
						Obj_Temp.led2dc = Double.parseDouble(objJson
								.getString("led2dc"));
						Obj_Temp.updated_on = objJson.getString("timestamp");
						LIST_SensorData.add(Obj_Temp);
					}
				}
			}
		} catch (Exception e) {
			AppLog.e(e.getMessage());
			// need to handle later
			// TODO: handle exception
		}
		return LIST_SensorData;
	}

	/**
	 * Display text value.
	 *
	 * @param UserID the user id
	 * @param UserName the user name
	 * @param BiometricName the biometric name
	 * @return the string
	 */
	private String DisplayTextValue(String UserID, String UserName,
			String BiometricName) {
		if (LIST_SensorData != null) {
			for (int i = 0; i < LIST_SensorData.size(); i++) {
				if (UserID.equals(LIST_SensorData.get(i).GetUserID())) {
					if (BiometricName.equals("GSR")) {
						BiometricName = UserName + " GSR <b><font color=\"red\">" + LIST_SensorData.get(i).updated_on +"</font></b><br>"
								+ "<b><font color=\"green\">GSR</font></b> : "
								+ LIST_SensorData.get(i).getacc_x()
								+ " Ohms";
					} else if (BiometricName.equals("Heart Rate")) {
						BiometricName = UserName
								+ " Heart Rate <b><font color=\"red\">" + LIST_SensorData.get(i).updated_on +"</font></b><br>"
								+ "<b><font color=\"green\">Pulse</font></b> : "
								+ LIST_SensorData.get(i).getpox_pulse()
								+ " Beats/min"
								+ "<br><b><font color=\"blue\">HR</font></b> : "
								+ LIST_SensorData.get(i).getpox_hr()
								+ " Beats/min";
					} else if (BiometricName.equals("SPO2")) {
						BiometricName = UserName
								+ " Accelerometer <b><font color=\"red\">" + LIST_SensorData.get(i).updated_on +"</font></b><br>"
								+ "<b><font color=\"green\">X axis</font></b> : "
								+ LIST_SensorData.get(i).getacc_x()
								+ " m/s<sup>2</sup>"
								+ "<br><b><font color=\"blue\">Y axis</font></b> : "
								+ LIST_SensorData.get(i).getacc_y()
								+ " m/s<sup>2</sup>"
								+ "<br><b><font color=\"red\">Z axis</font></b> : "
								+ LIST_SensorData.get(i).getacc_z()
								+ " m/s<sup>2</sup>";
					} else if (BiometricName.equals("Temperature_ambient")) {
						BiometricName = UserName
								+ " Temperature <b><font color=\"red\">" + LIST_SensorData.get(i).updated_on +"</font></b><br>"
								+ "<b><font color=\"green\">Ambient</font></b> : "
								+ LIST_SensorData.get(i).gettemp_ambient()
								+ " Celsius"
								+ "<br><b><font color=\"blue\">Skin</font></b> : "
								+ LIST_SensorData.get(i).gettemp_skin()
								+ " Celsius";
					}
				} else {
					if (BiometricName.equals("GSR")) {
						BiometricName = UserName + " GSR ";
					} else if (BiometricName.equals("Heart Rate")) {
						BiometricName = UserName + " Heart Rate ";
					} else if (BiometricName.equals("SPO2")) {
						BiometricName = UserName + " Accelerometer ";
					} else if (BiometricName.equals("Temperature_ambient")) {
						BiometricName = UserName + " Temperature ";
					}

				}
			}
		}
		return BiometricName;
	}
}
