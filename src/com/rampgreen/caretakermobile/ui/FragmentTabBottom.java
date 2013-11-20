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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.rampgreen.caretakermobile.adapter.TextDisplayAdapter;
import com.rampgreen.caretakermobile.adapter.VisualDisplayAdapter;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.ui.util.TabBitmap;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;
import com.rampgreen.caretakermobile.util.UiUtil;

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
	
	TabHost mTabHost;
//	LinearLayout homeLinearLayout, homeContentTextDisplay, homeContentChartDisplay;
//	ExpandableHeightGridView gridView;
	GridView gridView;
	private LayoutInflater mInflater;
	private View smsInboxDetailView;
	private TextView txtInboxSmsDetail;
	private ArrayList<User> userList;
	private UserListProvider userListProvider;
	private ImageAdapter imageAdapter;
	private ListView homeTextDisplayListView;
	private TextDisplayAdapter mTextDisplayAdapter;
	private ListView homeChartDisplayListView;
	private String mClickedMenuDisease = Constants.DISEASE_GSR;
	private int mFragmentCaller;
	private int mFragmentCalledByMenuOption;
	private VisualDisplayAdapter mVisualDisplayAdapter;
	private ArrayList<User> muserList;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mFragmentCalledByMenuOption = bundle.getInt(Constants.FRAGMENT_ADD_MENU_CALLER);
			mFragmentCaller = bundle.getInt(Constants.ActivityConstants.FRAGMENT_CALLER);
			mClickedMenuDisease = bundle.getString(Constants.BUNDLE_KEY_DISEASE);
			muserList = (ArrayList<User>)bundle.getSerializable(Constants.BUNDLE_KEY_USERS);
			
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
		// create the scrollview buttons 
//		homeContentTextDisplay = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentTextDisplay);
		homeTextDisplayListView = (ListView) getSherlockActivity().findViewById(R.id.homeContentTextDisplayList1);
		homeChartDisplayListView = (ListView) getSherlockActivity().findViewById(R.id.homeContentChartDisplayList);
//		ImageView imgViewTemp = (ImageView) getSherlockActivity().findViewById(R.id.text_view_home12);
		if(muserList != null && muserList.size() == 0) {
			AppLog.showToast(getSherlockActivity(), "No user is associated with the account.");
//			imgViewTemp.setVisibility(View.INVISIBLE);
		} else {
//			imgViewTemp.setVisibility(View.VISIBLE);
		}
		//		smsInboxDetailView = mInflater.inflate(R.layout.threaded_view_list_item_rec, null);//mInflater.inflate(R.layout.threaded_view_list_item_rec, homeContentTextDisplay, true);
		//		smsInboxDetailView = mInflater.inflate(R.layout.main_list, homeContentTextDisplay, true);
		//		txtInboxSmsDetail = (TextView) smsInboxDetailView
		//				.findViewById(R.id.TextViewMsg);
		//		homeContentTextDisplay.removeAllViews();
		//		txtInboxSmsDetail.setText("mm mmmmm mmmmmmm mmmmmmmm mmmmmmmmmmm mmmmmmmm mmmmm11 mmmmmmm mmmmmmmmm mmmmmmm mmm");
		//		homeContentTextDisplay.addView(smsInboxDetailView);

		//		homeLinearLayout = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentUser);
//		gridView = (ExpandableHeightGridView) getSherlockActivity().findViewById(R.id.grid_view);
//		gridView.setExpanded(true);

				gridView = (GridView) getSherlockActivity().findViewById(R.id.grid_view);
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
				builder.setTitle("Delete");
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
//		userList = getUserListByDisease(mClickedMenuDisease);
//		userList = getUserListByDisease(Constants.DISEASE_GSR);
		userList = userListProvider.getTextDisplayList();
		//userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.NOTIFICATION, false);
		mTextDisplayAdapter = new TextDisplayAdapter(getSherlockActivity(), userList);
		homeTextDisplayListView.setAdapter(mTextDisplayAdapter);
		
		
		homeTextDisplayListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				AppLog.showToast(getSherlockActivity(), position+"");
			}
		});

		homeTextDisplayListView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id)
			{
				userList = userListProvider.getTextDisplayList();
				final User user = userList.get(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
				builder.setTitle("Delete");
				builder.setMessage("Are You Sure to delete "+user.getUsername()+" from home screen ?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// set the value on server
						String biometricId = getAndSetBiometricId(user, mClickedMenuDisease, false, Constants.ADD_TEXT_DISPLAY);
						MyRequestQueue queue = MyVolley.getRequestQueue();
						Map<String, String> paramMap = QueryHelper.createAddTextDisplayQuery(BeanController.getLoginBean().getAccessToken(), user.getRequestId(), biometricId, "0");
						CustomRequest customRequest = new CustomRequest(Method.POST,
								Constants.URL_WEB_SERVICE, paramMap, FragmentTabBottom.this, FragmentTabBottom.this);
						queue.add(customRequest);

						userList = getUserListByDisease(mClickedMenuDisease);
						
						//						userList.remove(user);
						// updat
						refreshTextDisplayListAdapter(userList);
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
		
//		userList = getUserListByDisease(Constants.DISEASE_GSR);
//		userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.NOTIFICATION, false);
		//userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.VISUAL_DISPLAY, false);
		userList = userListProvider.getVisualDisplayList();
		mVisualDisplayAdapter = new VisualDisplayAdapter(getSherlockActivity(), userList);
		homeChartDisplayListView.setAdapter(mVisualDisplayAdapter);

		homeChartDisplayListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				AppLog.showToast(getSherlockActivity(), position+"");
			}
		});

		homeChartDisplayListView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id)
			{
				userList = userListProvider.getVisualDisplayList();
				final User user = userList.get(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
				builder.setTitle("Delete");
				builder.setMessage("Are You Sure to delete "+user.getUsername()+" from home screen ?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						//						user.setNotification(false);
						// set the value on server
						String biometricId = getAndSetBiometricId(user, mClickedMenuDisease, false, Constants.ADD_TEXT_VISUALEXPLORER);
						MyRequestQueue queue = MyVolley.getRequestQueue();
						Map<String, String> paramMap = QueryHelper.createAddVisualDisplayQuery(BeanController.getLoginBean().getAccessToken(), user.getRequestId(),biometricId, "0");
						CustomRequest customRequest = new CustomRequest(Method.POST,
								Constants.URL_WEB_SERVICE, paramMap, FragmentTabBottom.this, FragmentTabBottom.this);
						queue.add(customRequest);

						UserListProvider userListProvider = new UserListProvider();
						userList = userListProvider.getVisualDisplayList();
						refreshVisualDisplayListAdapter(userList);
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
		
		UiUtil.setListViewHeightBasedOnChildren(homeTextDisplayListView);
		UiUtil.setListViewHeightBasedOnChildren(homeChartDisplayListView);

	}
	ListItemDetails item_details;

	@Override
	public void onStart()
	{
		super.onStart();
		if(mTabHost != null) {
			mTabHost.setCurrentTab(0);
		}
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
	
	private void refreshTextDisplayListAdapter(ArrayList<User> userList2) {
		mTextDisplayAdapter.setList(userList2);
		mTextDisplayAdapter.notifyDataSetChanged();
	}
	
	private void refreshVisualDisplayListAdapter(ArrayList<User> userList2) {
		mVisualDisplayAdapter.setList(userList2);
		mVisualDisplayAdapter.notifyDataSetChanged();
	}

	private void setTabs() {
		mTabHost = (TabHost) getSherlockActivity().findViewById(android.R.id.tabhost);
		mTabHost.setup();

		addTab("Home", TAG_1, createTabDrawable(R.drawable.home), R.id.linear_view);
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
//				homeContentTextDisplay = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentTextDisplay);
//				homeContentChartDisplay = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentChartDisplay);
//				ScrollView scrollView = (ScrollView) getSherlockActivity().findViewById(R.id.scroller);

				switch (tabNum) {
				case 0:
					break;

				case 1:
					intent = new Intent(getSherlockActivity(), SelfScreen.class);
					startActivity(intent);
					break;

				case 2:
					intent = new Intent(getSherlockActivity(), UsersCaretakers.class);
					startActivity(intent);
					break;

				case 3:
					intent = new Intent(getSherlockActivity(), SendReceived_Request.class);
					startActivity(intent);
					break;

				case 4:
					break;

				default:
					break;
				}
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

	private String getAndSetBiometricId(User user, String diseaseType, boolean setterValue, int menuType) {
		if(menuType == Constants.ADD_TEXT_DISPLAY) {
			if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
				user.setGsrTextDisplay(setterValue);
				return "1";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
				user.setHeartRateTextDisplay(setterValue);
				return "2";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
				user.setAccelerometerTextDisplay(setterValue);
				return "3";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
				user.setTempratureTextDisplay(setterValue);
				return "4";
			}
		}else if(menuType == Constants.ADD_TEXT_VISUALEXPLORER){
			if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
				user.setGsrVisualDisplay(setterValue);
				return "1";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
				user.setHeartRateVisualDisplay(setterValue);
				return "2";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
				user.setAccelerometerVisualDisplay(setterValue);
				return "3";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
				user.setTempratureVisualDisplay(setterValue);
				return "4";
			}
		}
		return "-1";
	}
	
	private ArrayList<User> getUserListByDisease(String diseaseType) {
		ArrayList<User> userList = null;
		int menuType = mFragmentCalledByMenuOption;
		if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_DISPLAY) {
			menuType = UserListProvider.TEXT_DISPLAY;
		} else if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_VISUALEXPLORER){
			menuType = UserListProvider.VISUAL_DISPLAY;
		}

		if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
			userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.DISEASE_GSR, menuType, false);
		} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
			userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.DISEASE_HEART_RATE, menuType, false);
		} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
			userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.DISEASE_ACCELEROMETER, menuType, false);
		} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
			userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.DISEASE_TEMPRATURE, menuType, false);
		}
		return userList;
	}

}