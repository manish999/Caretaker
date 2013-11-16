package com.rampgreen.caretakermobile.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.UserCustomAdapterRecived;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.Recived;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;
import java.util.Map;

public class Received_request extends Fragment implements
Response.Listener<JSONObject>, Response.ErrorListener{

	String token = BeanController.getLoginBean().getAccessToken();//"b1916c6daa00b1d5d2297166008f3a7c4825e6f8";
	private UserCustomAdapterRecived userAdapter;
	private ListView lvr;
	
	public Received_request() {
		// TODO Auto-generated method stub

	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		return inflater.inflate(R.layout.received_request, null); 
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.getRecivedQuery(token);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam, this, this);		
		queue.add(customRequest);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(JSONObject response) {
		int code = Integer.parseInt(response.optString("code"));
		String msg = response.optString("message");
		Intent intent;
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
			AppLog.showToast(getActivity(), "wrong password");
			break;
		case ParserError.CODE_USER_NOT_REGISTERED:
			Toast.makeText(getActivity(), "USER NOT REGISTERED: ",
					Toast.LENGTH_SHORT).show();
			break;
		case ParserError.CODE_INVALID_TOKEN:

			break;
		case ParserError.CODE_TOKEN_EXPIRED:

			break;
		case ParserError.CODE_INTERNAL_SERVER_ERROR:

			break;
		case ParserError.CODE_USER_ALREADY_REGISTERED:
			Toast.makeText(getActivity(), "USER ALREADY REGISTERED: ",
					Toast.LENGTH_SHORT).show();
			break;
		case ParserError.CODE_NO_REQUEST_RECEIVED:			
				AppLog.showToast(getActivity(), "No request recieved");			
			break;
		case ParserError.CODE_NO_CARETAKER_REQUEST_SENT_IS_PENDING:
			userAdapter.clear();
			AppLog.showToast(getActivity(), "No Request pending");
			break;
		case ParserError.CODE_SUCCESS:

			try {
					userAdapter = new UserCustomAdapterRecived(getActivity(),
							R.layout.row_ishu, 0, GetRecivedDetails(response));
					userAdapter.notifyDataSetChanged();					

					lvr = (ListView) getActivity()
							.findViewById(R.id.lstRecived);					
					lvr.setAdapter(userAdapter);
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;

		}

		if (code != ParserError.CODE_SUCCESS) {
			AppLog.logToast(getActivity(), "error web service response code - "
					+ code);
		}
		
	}
	
	private ArrayList<Recived> GetRecivedDetails(JSONObject jsonObject)
			throws JSONException {

		JSONArray jArray = jsonObject.getJSONArray("caretaker_profile");

		ArrayList<Recived> ALIST_RD = new ArrayList<Recived>();

		for (int i = 0; i < jArray.length(); i++) {
			JSONObject objJson = jArray.getJSONObject(i);
			jsonObject = jArray.getJSONObject(i);
			Recived ObjRD = new Recived();

			ObjRD.userid = objJson.getString("request_id");
			ObjRD.name = objJson.getString("firstname");
			ObjRD.ispending = objJson.getString("rejectedaccepted");
			ObjRD.isignore = objJson.getString("currentstatus");
			ALIST_RD.add(ObjRD);
		}
		return ALIST_RD;
	}

}
