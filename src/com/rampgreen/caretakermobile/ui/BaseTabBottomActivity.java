package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.Response;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.TextDisplayAdapter;
import com.rampgreen.caretakermobile.adapter.VisualDisplayAdapter;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.ui.util.ExpandableHeightGridView;
import com.rampgreen.caretakermobile.ui.util.ExpandableListView;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;

/**
 * Base activity class is used for future purposes. we can add functionality to all classes that will extend it. 
 * 
 * @author Manish Pathak
 *
 */
abstract public class BaseTabBottomActivity extends SherlockFragmentActivity implements Response.Listener<JSONObject>, Response.ErrorListener{


	private static final String TAG_1 = "0";
	private static final String TAG_2 = "1";
	private static final String TAG_3 = "2";
	private static final String TAG_4 = "3";
	private static final String TAG_5 = "4";

	static String[] letters = new String[] {
		"A", "B", "C", "D", "E",
	};
	static String[] letters1 = new String[] {
		"1", "2", "3", "4", "5",
	};
	TabHost mTabHost;
	//	LinearLayout homeLinearLayout, homeContentTextDisplay, homeContentChartDisplay;
	//	ExpandableHeightGridView gridView;
	ExpandableHeightGridView gridView;
	private LayoutInflater mInflater;
	private View smsInboxDetailView;
	private TextView txtInboxSmsDetail;
	private ArrayList<User> userList;
	private UserListProvider userListProvider;
	private ImageAdapter imageAdapter;
	private ExpandableListView homeTextDisplayListView;
	private TextDisplayAdapter mTextDisplayAdapter;
	private ExpandableListView homeChartDisplayListView;
	private String mClickedMenuDisease = Constants.DISEASE_GSR;
	private int mFragmentCaller;
	private int mFragmentCalledByMenuOption;
	private VisualDisplayAdapter mVisualDisplayAdapter;
	private ArrayList<User> muserList;


	private ProgressDialog progressDialog;
	private static int numAsyncTasks;
	protected RelativeLayout rlMasterView;
	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		LayoutInflater inflater = LayoutInflater.from(this);
		// this is the master view with a container and the footer, you can
		// as well add the header
		rlMasterView = (RelativeLayout) inflater.inflate(R.layout.activity_rainbow, null);
		setContentView(rlMasterView);
		setTitle("Find Rainbow");
	}
	protected void showLoadingBar(){
		numAsyncTasks++;
		progressDialog = ProgressDialog.show(this, null, "Loading...", true);
	}

	protected void closeLoadingBar(){
		if (0 == --numAsyncTasks) {
			if(progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}
	}

	@Override
	public void onBackPressed()
	{
		// cancel all requests when user press back button while loading bar is shown. 
		if(progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			MyRequestQueue queue = MyVolley.getRequestQueue();
			queue.cancelAll();
		}
		super.onBackPressed();

	}

	public void setActionBarTitle(String title) {
		setTitle(title);
	}

	@Override
	public void setContentView(View view) {

		// get master
		LayoutInflater inflater = LayoutInflater.from(this);

		// this is the master view with a container and the footer, you can
		// as well add the header
		TabHost rlMasterView = (TabHost) inflater.inflate(R.layout.master_footer, null);
		mTabHost = (TabHost) rlMasterView.findViewById(android.R.id.tabhost);
		rlMasterView.addView(view);

		super.setContentView(rlMasterView);
//		setTabs();
	}

	


}
