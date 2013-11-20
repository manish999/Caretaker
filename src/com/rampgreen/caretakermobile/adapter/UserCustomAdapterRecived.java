package com.rampgreen.caretakermobile.adapter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.Recived;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.ui.LegalTerms;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;
import java.util.Map;

public class UserCustomAdapterRecived extends ArrayAdapter<Recived> implements Response.Listener<JSONObject>, Response.ErrorListener{
	Context context;
	int layoutResourceId;
	ArrayList<Recived> data = new ArrayList<Recived>();
	int pos;
	String strmsg;
	String token = BeanController.getLoginBean().getAccessToken();//"b1916c6daa00b1d5d2297166008f3a7c4825e6f8";	
	private UserCustomAdapterRecived userAdapter;

	public UserCustomAdapterRecived(Context context, int layoutResourceId, int pos,
			ArrayList<Recived> data) {
		super(context, layoutResourceId, pos, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.pos = pos;
	}

	public void setAdapterList(ArrayList<Recived> data, int position) {
		this.data = data;
		this.pos = position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		UserHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new UserHolder();
			holder.textUserid = (TextView) row.findViewById(R.id.textUserid);
			holder.textdot = (TextView) row.findViewById(R.id.textViewdot);
			holder.textName = (TextView) row.findViewById(R.id.textView1);
			holder.textAddress = (TextView) row.findViewById(R.id.textView2);
			holder.textLocation = (TextView) row.findViewById(R.id.textView3);
			holder.btnAccept = (Button) row.findViewById(R.id.button1);
			holder.btnIgnore = (Button) row.findViewById(R.id.button2);
			holder.btnDelete = (Button) row.findViewById(R.id.button3);

			row.setTag(holder);

		} else {
			holder = (UserHolder) row.getTag();
		}

		Recived user = data.get(position);
		holder.textdot.setText("");
		holder.textUserid.setText(user.getUserid());
		Getlistdata(pos, holder, user, position);

		return row;
	}

	static class UserHolder {
		TextView textUserid;
		TextView textName;
		TextView textAddress;
		TextView textLocation;
		TextView textdot;
		Button btnAccept;
		Button btnIgnore;
		Button btnDelete;
	}

	private void Getlistdata(int pos, UserHolder holder, final Recived userrs, final int position) {
		if (pos == 0) {

			holder.textName.setText(userrs.getName());
			holder.textAddress.setText("|");
			holder.textLocation.setText("|");

			if (userrs.getIsignore().equals("0")) {				
				holder.textdot.setVisibility(View.VISIBLE);
				holder.btnIgnore.setVisibility(View.GONE);
				holder.textAddress.setVisibility(View.GONE);
			} /*else if(userrs.getIsignore().equals("0")){
				holder.textdot.setVisibility(View.VISIBLE);
				holder.btnIgnore.setVisibility(View.GONE);
				holder.textAddress.setVisibility(View.GONE);
			}*/


			holder.btnAccept.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,LegalTerms.class);                                  
					intent.putExtra("userid",userrs.getUserid());
					intent.putExtra("UserName",userrs.getName());//here you will add the data into intent to pass bw activites
					context.startActivity(intent); 
				}
			});

			holder.btnIgnore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					response_ignor(token,userrs.getUserid());
					Toast.makeText(context, "Record Ignor",
							Toast.LENGTH_LONG).show();
					((Recived)data.get(position)).isignore = "0";
					notifyDataSetChanged();
				}
			});

			holder.btnDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					response_delete(token,userrs.getUserid());
					Toast.makeText(context, "Record Deleted",
							Toast.LENGTH_LONG).show();
					data.remove(position);
					notifyDataSetChanged(); 
				}
			});
		} else if (pos == 1) {

			holder.textName.setText(userrs.getName());
			/*holder.textAddress.setText("|");*/

			holder.textAddress.setVisibility(View.GONE);
			holder.textLocation.setVisibility(View.GONE);

			holder.btnAccept.setText("");
			holder.btnIgnore.setText("");
			if (userrs.getIsignore().equals("null")) {
				holder.btnDelete.setText("Unseen");
				holder.textdot.setText(" ");
			} else if(userrs.getIsignore().equals("0")){
				holder.btnDelete.setText("Pending");
				holder.btnDelete.setTextColor(Color.parseColor("#0099CC"));
			}
		}

	}

	private void response_accept(String token,String request_id){
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.responseAcceptQuery(token, request_id);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam,
				this, this);

		queue.add(customRequest);		
	}

	private void response_ignor(String token,String request_id){
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.responseIgnoreQuery(token, request_id);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam,
				this, this);

		queue.add(customRequest);		
	}

	private void response_delete(String token,String request_id){
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.responseDeleteQuery(token, request_id);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam,
				this, this);

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
			/*AppLog.showToast(this, "wrong password");*/
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
		case ParserError.CODE_SUCCESS:			
			strmsg = response.toString();
			break;

		default:
			break;
		}

		if (code != ParserError.CODE_SUCCESS)
		{
			/*AppLog.logToast(this, "error web service response code - " + code);*/
		}

	}
}