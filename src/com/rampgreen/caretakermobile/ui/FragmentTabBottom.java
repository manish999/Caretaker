package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.ListItemDetails;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.ui.util.ExpandableHeightGridView;
import com.rampgreen.caretakermobile.ui.util.TabBitmap;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Manish Pathak
 *
 */
public class FragmentTabBottom extends SherlockFragment implements  Response.Listener<JSONObject>, Response.ErrorListener{

	private static final String TAG_1 = "0";
	private static final String TAG_2 = "1";
	private static final String TAG_3 = "2";
	private static final String TAG_4 = "3";
	private static final String TAG_5 = "4";

	String[] text = { "Afghanistan", "Algeria", "Australia", "Bermuda", "Bhutan", "Canada", "China","India","Japan",
			"Kuwait", "Malaysia", "Mexico" };

	int[] image = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher,  R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };

	TabHost mTabHost;
	LinearLayout homeLinearLayout, homeContentTextDisplay, homeContentChartDisplay;
	ExpandableHeightGridView gridView;
	private LayoutInflater mInflater;
	private View smsInboxDetailView;
	private TextView txtInboxSmsDetail;
	private ArrayList<User> userList;
	private UserListProvider userListProvider;
	private ImageAdapter imageAdapter;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		Bundle localBundle = getArguments();
		//		Bundle localBundle = getActivity().getIntent().getExtras();
		if (localBundle != null) {
//			user  = (ArrayList<User>)localBundle.getSerializable(Constants.BUNDLE_KEY_USERS);
		}
		mInflater = (LayoutInflater) getSherlockActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.userListProvider = new UserListProvider();
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		Bundle localBundle = getArguments();
		setTabs();
		Button button = new Button(getSherlockActivity());
		// create the scrollview buttons 
		homeContentTextDisplay = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentTextDisplay);
		smsInboxDetailView = mInflater.inflate(R.layout.threaded_view_list_item_rec, null);//mInflater.inflate(R.layout.threaded_view_list_item_rec, homeContentTextDisplay, true);
		//		smsInboxDetailView = mInflater.inflate(R.layout.main_list, homeContentTextDisplay, true);
		//		txtInboxSmsDetail = (TextView) smsInboxDetailView
		//				.findViewById(R.id.TextViewMsg);
		homeContentTextDisplay.removeAllViews();
		//		txtInboxSmsDetail.setText("mm mmmmm mmmmmmm mmmmmmmm mmmmmmmmmmm mmmmmmmm mmmmm11 mmmmmmm mmmmmmmmm mmmmmmm mmm");
		homeContentTextDisplay.addView(smsInboxDetailView);

		//		homeLinearLayout = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentUser);
		gridView = (ExpandableHeightGridView) getSherlockActivity().findViewById(R.id.grid_view);
		gridView.setExpanded(true);

		//		gridView = (GridView) getSherlockActivity().findViewById(R.id.grid_view);
		// Instance of ImageAdapter Class

		//		String	totalUser = (String)AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.TEMP_TOTAL_USER, "0");
		//		int totuser= Integer.parseInt(totalUser);

		String	dashUser = (String)AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.TEMP_DASHBOARD_USER, "00000");
		char[] arr = dashUser.toCharArray();
		int totalUSers = 0;
		for (int i = 0; i < dashUser.toCharArray().length; i++)
		{
			if( arr[i] == '1') {
				totalUSers ++;
			}
		}
		
		userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.ADD_USER_ICON, false);
		imageAdapter = new ImageAdapter(getSherlockActivity(), userList);
		gridView.setAdapter(imageAdapter);

		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Sending image id to FullScreenActivity
				//				Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
				// passing array index
				Bundle bundle = new Bundle();
				Intent intent = new Intent();
				bundle.putString("title", userList.get(position).getUsername());
				intent.setClass(getSherlockActivity(), HomeActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				AppLog.logToast(getSherlockActivity(), "posi"+position);
				//				i.putExtra("id", position);	
				//				startActivity(i);
			}
		});

		gridView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
										int position, long id) {
				final User user = userList.get(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
				builder.setTitle("Exit");
				builder.setMessage("Are You Sure to delete "+user.getUsername()+" from home screen ?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						user.setUserOnHomeScreen(false);
						// set the value on server
						MyRequestQueue queue = MyVolley.getRequestQueue();
						Map<String, String> paramMap = QueryHelper.createAddUserIconQuery(BeanController.getLoginBean().getAccessToken(), user.getRequestId(), "0");
						CustomRequest customRequest = new CustomRequest(Method.POST,
								Constants.URL_WEB_SERVICE, paramMap, FragmentTabBottom.this, FragmentTabBottom.this);
						queue.add(customRequest);
						
						UserListProvider userListProvider = new UserListProvider();
						userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.ADD_USER_ICON, false);
//						userList.remove(user);
						// updat
						refreshUserListAdapter(userList);
//						Constants.CLOSE_ALL_ACTIVITIES = true;
//						finish();
					}
				});

				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
//				AppLog.logToast(getSherlockActivity(), "Long click posi"+position);
				return false;
			}
		});

		//		ArrayList<ListItemDetails> result = GetSearchResults();
		//        ListView lv = (ListView)smsInboxDetailView.findViewById(R.id.listView1);
		//        lv.setAdapter(new CustomListAdapter(result,getSherlockActivity()));
	}
	ListItemDetails item_details;
	private ArrayList<ListItemDetails> GetSearchResults() {
		// TODO Auto-generated method stub
		ArrayList<ListItemDetails> results = new ArrayList<ListItemDetails>();

		for(int i=0;i<text.length;i++)
		{
			item_details = new ListItemDetails();
			item_details.setName(text[i]);
			item_details.setImage(image[i]);
			results.add(item_details);
		}

		return results;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		//		Activity act = getSherlockActivity();
		//		AppLog.logToast(act, act.getClass().toString());
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		Activity act = getSherlockActivity();
		AppLog.logToast(act, act.getClass().toString());

		//		String	totalUser = (String)AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.TEMP_TOTAL_USER, "0");
		//		int totuser= Integer.parseInt(totalUser);
		//		ImageAdapter imageAdapter = new ImageAdapter(getSherlockActivity(), totuser);
		//		gridView.setAdapter(imageAdapter);
		//		imageAdapter.notifyDataSetChanged();

	}
	
	private void refreshUserListAdapter(ArrayList<User> userList2) {
		imageAdapter.setList(userList2);
		imageAdapter.notifyDataSetChanged();
	}
	
	private void setTabs() {
		mTabHost = (TabHost) getSherlockActivity().findViewById(android.R.id.tabhost);
		mTabHost.setup();

		addTab("Home", TAG_1, createTabDrawable(R.drawable.home), R.id.text_view_home);
		addTab("Self", TAG_2, createTabDrawable(R.drawable.self), R.id.text_view_search);
		addTab("Network", TAG_3, createTabDrawable(R.drawable.network), R.id.text_view_favourites);
		addTab("Request", TAG_4, createTabDrawable(R.drawable.request), R.id.text_view_settings);
		addTab("Rainbow", TAG_5, createTabDrawable(R.drawable.rainbow), R.id.text_view_rainbow);
	}

	private Drawable createTabDrawable(int resId) {
		Resources res = getResources();
		StateListDrawable states = new StateListDrawable();

		final Options options = new Options();
		options.inPreferredConfig = Config.ARGB_8888;

		Bitmap icon = BitmapFactory.decodeResource(res, resId, options);

		Bitmap unselected = TabBitmap.createUnselectedBitmap(res, icon);
		Bitmap selected = TabBitmap.createSelectedBitmap(res, icon);

		icon.recycle();

		states.addState(new int[] { android.R.attr.state_selected }, new BitmapDrawable(res, selected));
		states.addState(new int[] { android.R.attr.state_enabled }, new BitmapDrawable(res, unselected));

		return states;
	}

	private View createTabIndicator(String label, Drawable drawable) {
		View tabIndicator = LayoutInflater.from(getSherlockActivity()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);

		TextView txtTitle = (TextView) tabIndicator.findViewById(R.id.text_view_tab_title);
		txtTitle.setText(label);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) txtTitle.getLayoutParams();
		txtTitle.setLayoutParams(params);

		ImageView imgIcon = (ImageView) tabIndicator.findViewById(R.id.image_view_tab_icon);
		imgIcon.setImageDrawable(drawable);

		return tabIndicator;
	}

	private void addTab(String label, String tag, Drawable drawable, int id) {
		TabHost.TabSpec spec = mTabHost.newTabSpec(tag);
		spec.setIndicator(createTabIndicator(label, drawable));
		spec.setContent(id);

		mTabHost.addTab(spec);
		mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
			Intent intent;
			public void onTabChanged(String tabId) {
				int tabNum = Integer.parseInt(tabId);
				//				homeLinearLayout = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentUser);
				homeContentTextDisplay = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentTextDisplay);
				homeContentChartDisplay = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentChartDisplay);
				ScrollView scrollView = (ScrollView) getSherlockActivity().findViewById(R.id.scroller);

				switch (tabNum) {
				case 0:
					scrollView.setVisibility(View.VISIBLE);

					//					homeLinearLayout.removeAllViews();
					//					homeContentTextDisplay.removeAllViews();
					//					homeContentChartDisplay.removeAllViews();
					//					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					//							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					//					for(int i=0; i< buttonNo ; i++) {
					//						Button button = new Button(getSherlockActivity());
					//						button.setText("ok");
					////						homeLinearLayout.addView(new Button(getSherlockActivity()), params);
					//					}
					break;

				case 1:
					
					scrollView.setVisibility(View.INVISIBLE);
					break;

				case 2:
					intent = new Intent(getSherlockActivity(), UsersCaretakers.class);
					startActivity(intent);
					scrollView.setVisibility(View.INVISIBLE);
					break;

				case 3:
					intent = new Intent(getSherlockActivity(), SendReceived_Request.class);
					startActivity(intent);
					scrollView.setVisibility(View.INVISIBLE);
					break;

				case 4:
					scrollView.setVisibility(View.INVISIBLE);
					break;

				default:
					break;
				}
				// create the scrollview buttons 

				//				UiUtil.setTabColor(getSherlockActivity(),tabHost);
				if (getSherlockActivity() instanceof FragmentChangeActivity) {
					//					Bundle bundle = new Bundle();
					//					bundle.putString(com.rampgreen.caretakermobile.util.Constants.POSITION, tabId);
					//					((FragmentChangeActivity)getSherlockActivity()).mContent.setArguments(bundle);
				}

				AppLog.logToast(FragmentTabBottom.this.getSherlockActivity(), "Tab click"+tabId);
			}});
	}

	/**
	 * @param label
	 * @param tag
	 * @param drawable
	 * @param intent The activity that need to start on click of particular tab
	 */
	private void addTab(String label, String tag, Drawable drawable, Intent intent) {
		TabHost.TabSpec spec = mTabHost.newTabSpec(tag);
		spec.setIndicator(createTabIndicator(label, drawable));
		spec.setContent(intent);

		mTabHost.addTab(spec);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// home click
			return true;
//	    case R.id.activity_menu_item:
//	        // Not implemented here
//	        return false;
//	    case R.id.fragment_menu_item:
//	        // Do Fragment menu item stuff here
//	        return true;
	    default:
	        break;
	    }

	    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onErrorResponse(VolleyError error)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(JSONObject response)
	{
		// TODO Auto-generated method stub
		
	}
}