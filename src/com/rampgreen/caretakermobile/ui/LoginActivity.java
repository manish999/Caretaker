package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andreabaccega.widget.FormEditText;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.LoginBean;
import com.rampgreen.caretakermobile.model.TextDisplaySettings;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.ui.util.WidgetUtil;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.Map;

public class LoginActivity extends BaseActivity
{

	private Button mLoginButton;
	private FormEditText mEtUserName;
	private FormEditText mEtPassword;

	private boolean mIsValid;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// obtain form field from xml
		mEtUserName = (FormEditText) findViewById(R.id.et_email);
		mEtPassword = (FormEditText) findViewById(R.id.et_password);
		mLoginButton = (Button) findViewById(R.id.btn_login);

		// handle login button click 
		mLoginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) {
				onClickNext(arg0);
				// if every field is valid, send request to RTNeuroserver
				if(mIsValid) {
					String username = mEtUserName.getText().toString();
					String password = mEtPassword.getText().toString();

					AppSettings.setPreference(LoginActivity.this, null, AppSettings.USER_SELECTED_MAIL_ID, username);

					if(! WidgetUtil.checkInternetConnection(LoginActivity.this)) {
						WidgetUtil.showSettingDialog(LoginActivity.this);
						return;
					}
					// get network queue and add request to the queue
					MyRequestQueue queue = MyVolley.getRequestQueue();
					Map<String, String> loginParam = QueryHelper.createLoginQuery(
							username, password);
					CustomRequest customRequest = new CustomRequest(Method.POST,
							Constants.URL_WEB_SERVICE, loginParam,
							LoginActivity.this, LoginActivity.this);
					showLoadingBar();
					queue.add(customRequest);
				}
			}
		});
	}

	@Override
	public void onResponse(JSONObject response)
	{

		int code = Integer.parseInt(response.optString("code"));
		String msg = response.optString("message");
		Intent intent;
		switch (code) {
		case ParserError.CODE_ACTION_NOT_FOUND:
			closeLoadingBar();
			break;
		case ParserError.CODE_MISSING_ACTION:
			closeLoadingBar();
			break;
		case ParserError.CODE_MISSING_TASK:
			closeLoadingBar();
			break;
		case ParserError.CODE_CLIENT_AUTHORIZATION_FAILED:
			closeLoadingBar();
			break;
		case ParserError.CODE_TOKEN_GENERATION_FAILED:
			closeLoadingBar();
			break;
		case ParserError.CODE_USERNAME_REQUIRED:

			break;
		case ParserError.CODE_PASSWORD_REQUIRED:
			closeLoadingBar();
			break;
		case ParserError.CODE_PASSWORD_WRONG:
			AppLog.showToast(this, "wrong password");
			closeLoadingBar();
			break;
		case ParserError.CODE_USER_NOT_REGISTERED:
			intent = new Intent(getApplicationContext(),
					RegistrationActivity.class);
			intent.putExtra(Constants.LOGIN_EMAIL, mEtUserName.getText().toString());
			startActivity(intent);
			closeLoadingBar();
			break;
		case ParserError.CODE_INVALID_TOKEN:
			closeLoadingBar();
			break;
		case ParserError.CODE_TOKEN_EXPIRED:
			closeLoadingBar();
			break;
		case ParserError.CODE_INTERNAL_SERVER_ERROR:
			closeLoadingBar();
			break;
		case ParserError.CODE_USER_ALREADY_REGISTERED:
			closeLoadingBar();
			break;
		case ParserError.CODE_SUCCESS:
			LoginBean login = BeanController.getLoginBean();
			login.populateBean(response);
			AppLog.logString(response.toString());

			callUserListWebService();
			break;

		default:
			closeLoadingBar();
			break;
		}

		if (code != ParserError.CODE_SUCCESS)
		{
			AppLog.logToast(this, "error web service response code - " + code);
		}
	}

	@Override
	public void onErrorResponse(VolleyError error)
	{
		closeLoadingBar();
		AppLog.logToast(this, error.toString());
	}

	public void onClickNext(View v)
	{
		mIsValid = true;
		FormEditText[] allFields = { mEtUserName , mEtPassword};

		for (FormEditText field : allFields)
		{
			mIsValid = field.testValidity() && mIsValid;
		}

		//for debug purpose
		if (mIsValid)
		{
			mIsValid = true;
			AppLog.logToast(this, "Valid");
		} else
		{
			mIsValid = false;
			AppLog.logToast(this, "inValid");
			// EditText are going to appear with an exclamation mark and an
			// explicative message.
		}
	}

	private void callUserListWebService () {
		String accessToken = BeanController.getLoginBean().getAccessToken();
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.createAllUsersQuery(accessToken);

		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam,
				new ResponseListener(), new ErrorListener());
		queue.add(customRequest);
	}

	private class ResponseListener implements Response.Listener<JSONObject> {
		@Override
		public void onResponse(JSONObject response){
			int code = Integer.parseInt(response.optString("code"));
			String msg = response.optString("message");
			switch (code) {
			case ParserError.CODE_NO_USER_FOUND:
				closeLoadingBar();
				break;
			case ParserError.CODE_SUCCESS:
				User userBean = BeanController.getUserBean();
				userBean.populateBean(response);
				AppLog.logString(response.toString());
				
				TextDisplaySettings textSetting = BeanController.getTextDisplaySettings();
				textSetting.populateBean(response);
				
				// on success , call Home screen
				Intent intent = new Intent(getApplicationContext(), FragmentChangeActivity.class);
				intent.putExtra(Constants.BUNDLE_KEY_USERS, userBean);
				startActivity(intent);
				// to close the activity
				finish();
				closeLoadingBar();
				break;

			default:
				closeLoadingBar();
				break;
			}

			if (code != ParserError.CODE_SUCCESS)
			{
				AppLog.logToast(LoginActivity.this, "error web service response code - " + code);
			}
		}
	}


	private class ErrorListener implements Response.ErrorListener{
		@Override
		public void onErrorResponse(VolleyError error){
			closeLoadingBar();
			AppLog.logToast(LoginActivity.this, error.toString());
		}
	}


}
