package com.rampgreen.caretakermobile.ui;

import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.Constants;

public class LegalTerms extends BaseActivity {
	String userid;
	String token = "b1916c6daa00b1d5d2297166008f3a7c4825e6f8";
	String UserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userid = this.getIntent().getStringExtra("userid");
		UserName = this.getIntent().getStringExtra("UserName");
		setTitle(UserName);
		setContentView(R.layout.legal_terms);		

		TextView tv1 = (TextView) this.findViewById(R.id.TVlegalterms);
		tv1.setText(Html
				.fromHtml("<h2 style = \"align:center; vertical-align:middle;\">" + " Legal Terms</h2><br><p>This web page represents a legal document that serves as our Terms of Service and it governs the legal terms of our website, http://androidexample.com, sub-domains, and any associated web-based and mobile applications (collectively, Website), as owned and operated by AndroidExample.Capitalized terms, unless otherwise defined, have the meaning specified within the Definitions section below. This Terms of Service, along with our Privacy Policy, any mobile license agreement, and other posted guidelines within our Website, collectively Legal Terms, constitute the entire and only agreement between you and AndroidExample, and supersede all other agreements, representations, warranties and understandings with respect to our Website and the subject matter contained herein. We may amend our Legal Terms at any time without specific notice to you. The latest copies of our Legal Terms will be posted on our Website, and you should review all Legal Terms prior to using our Website. After any revisions to our Legal Terms are posted, you agree to be bound to any such changes to them. Therefore, it is important for you to periodically review our Legal Terms to make sure you still agree to them. By using our Website, you agree to fully comply with and be bound by our Legal Terms. Please review them carefully. If you do not accept our Legal Terms, do not access and use our Website. If you have already accessed our Website and do not accept our Legal Terms, you should immediately discontinue use of our Website.</p>"));

		Button btnAgree = (Button) this.findViewById(R.id.btnAgree);
		btnAgree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// if every field is valid, send request to RTNeuroserver
				response_accept(token, userid);
			}
		});
	}

	private void response_accept(String token, String request_id) {
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.responseAcceptQuery(token,
				request_id);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam, this, this);

		queue.add(customRequest);
	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub
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
			/* AppLog.showToast(this, "wrong password"); */
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
			Toast.makeText(LegalTerms.this, "Record Accept", Toast.LENGTH_LONG)
					.show();
			intent = new Intent(getApplicationContext(),
					SendReceived_Request.class);
			startActivity(intent);
			break;

		default:
			break;
		}

		if (code != ParserError.CODE_SUCCESS) {
			/*
			 * AppLog.logToast(this, "error web service response code - " +
			 * code);
			 */
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}

}