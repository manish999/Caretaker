package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.LoginBean;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.ui.util.WidgetUtil;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;
import com.rampgreen.caretakermobile.util.DateUtils;

import java.util.Map;

public class RegistrationActivity extends BaseActivity implements OnDateSetListener
{
	private Button mBtnRegistration;
	private FormEditText mEtUserName;
	private FormEditText mEtFirstName;
	private FormEditText mEtMiddleName;
	private FormEditText mEtLastName;
	private FormEditText mEtPassword;
	private FormEditText mEtAge;
	private FormEditText mEtHeight;
	private FormEditText mEtWeight;

	private String emailId;
	private String mSalutation;
	private Spinner mSpinner;
	private boolean mIsValid;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		// get all FormEditText field
		mEtUserName = (FormEditText) findViewById(R.id.et_email);
		mEtPassword = (FormEditText) findViewById(R.id.et_password);
		mEtFirstName = (FormEditText) findViewById(R.id.et_first_name);
		mEtMiddleName = (FormEditText) findViewById(R.id.et_middle_name);
		mEtLastName = (FormEditText) findViewById(R.id.et_last_name);
		mEtAge = (FormEditText) findViewById(R.id.et_age);
		mEtHeight = (FormEditText) findViewById(R.id.et_height);
		mEtWeight = (FormEditText) findViewById(R.id.et_weight);
		mBtnRegistration = (Button) findViewById(R.id.btn_register);

		// get email id from preference store and set to registration page.
		emailId = (String)AppSettings.getPrefernce(this, null, AppSettings.USER_SELECTED_MAIL_ID, "");
		mEtUserName.setText(emailId);

		// configure the spinner for salutation
		mSpinner = ((Spinner) findViewById(R.id.spinner_salutation));
		ArrayAdapter<CharSequence> localArrayAdapter = ArrayAdapter.createFromResource(this, R.array.salutation_array,
				android.R.layout.simple_spinner_item);
		localArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.mSpinner.setAdapter(localArrayAdapter);

		// handle the spinner 
		this.mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				AppSettings.setPreference(RegistrationActivity.this, null, AppSettings.SALUTATION, String.valueOf(position));
				mSalutation =  getResources().getStringArray(R.array.salutation_array)[position];
				mSpinner.setSelection(position);
				mSpinner.setSelected(true);
				AppLog.logToast(getApplicationContext(), "Spinner1: position="
						+ position + " id=" + id);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				mSalutation =  getResources().getStringArray(R.array.salutation_array)[0];
				AppLog.logToast(getApplicationContext(), "Spinner1: unselected");
			}
		});

		// open date picker dialog on click of age field
		mEtAge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				WidgetUtil.callDatePicker(RegistrationActivity.this, 0, RegistrationActivity.this);
			}
		});

		// hide the keyboared on lcik of age text field
		mEtAge.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					in.hideSoftInputFromWindow(mEtAge.getApplicationWindowToken(), 0);
				}
				return false;
			}
		});

		// click on register button
		mBtnRegistration.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onClickNext(arg0);
				if(mIsValid) {
					String userName = mEtUserName.getText().toString();
					String firstName = mEtFirstName.getText().toString();
					String middleName = mEtMiddleName.getText().toString();
					String lastName = mEtLastName.getText().toString();
					String password = mEtPassword.getText().toString();
					String age = mEtAge.getText().toString();
					String height = mEtHeight.getText().toString();
					String weight = mEtWeight.getText().toString();
					
					if(! WidgetUtil.checkInternetConnection(RegistrationActivity.this)) {
						WidgetUtil.showSettingDialog(RegistrationActivity.this);
						return;
					}
					// request registration web service 
					RequestQueue queue = MyVolley.getRequestQueue();
					Map<String, String> registerParam = QueryHelper.createRegistrationQuery(userName, mSalutation, 
							firstName, middleName , lastName, password, age,height, weight, "");
					CustomRequest customRequest = new CustomRequest(Method.POST,
							Constants.URL_WEB_SERVICE, registerParam, RegistrationActivity.this, RegistrationActivity.this);
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
			AppLog.showToast(this, "wrong password");
			break;
		case ParserError.CODE_USER_NOT_REGISTERED:

			break;
		case ParserError.CODE_INVALID_TOKEN:

			break;
		case ParserError.CODE_TOKEN_EXPIRED:

			break;
		case ParserError.CODE_INTERNAL_SERVER_ERROR:
			AppLog.showToast(this, "Internal server error, please contact to administrator.");
			break;
		case ParserError.CODE_USER_ALREADY_REGISTERED:

			break;
		case ParserError.CODE_SUCCESS:
			LoginBean login = BeanController.getLoginBean();
			login.populateBean(response);
			AppLog.logToast(this,  response.toString());
			// open home activity 
			Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(intent);
			break;

		default:
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
		Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
	}

	public void onClickNext(View v)
	{
		mIsValid = true;
		FormEditText[] allFields = { mEtUserName , mEtFirstName, mEtLastName, mEtPassword};
		for (FormEditText field : allFields)
		{
			mIsValid = field.testValidity() && mIsValid;
		}
		// for debug purpose
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

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		String dateString = DateUtils.convertToString(dayOfMonth, monthOfYear, year, DateUtils.FORMAT_YYYYMMDD_DASHESH);
		mEtAge.setText(dateString);
		AppSettings.setPreference(this, null, AppSettings.DATE_OF_BIRTH, dateString);
		//		WidgetUtil.callDatePicker(this, 0, this);
	}
}
