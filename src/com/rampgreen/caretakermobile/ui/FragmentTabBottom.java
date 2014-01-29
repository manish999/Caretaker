package com.rampgreen.caretakermobile.ui;

import org.json.JSONArray;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
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
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.ListHolder;
import com.rampgreen.caretakermobile.model.SensorData;
import com.rampgreen.caretakermobile.model.TextDisplaySettings;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.model.VisualDisplaySettings;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.ui.util.ExpandableHeightGridView;
import com.rampgreen.caretakermobile.ui.util.ExpandableListView;
import com.rampgreen.caretakermobile.ui.util.TabBitmap;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;
import com.rampgreen.caretakermobile.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
		gridView = (ExpandableHeightGridView) getSherlockActivity().findViewById(R.id.grid_view);
		homeTextDisplayListView = (ExpandableListView) getSherlockActivity().findViewById(R.id.homeContentTextDisplayList1);
		homeChartDisplayListView = (ExpandableListView) getSherlockActivity().findViewById(R.id.homeContentChartDisplayList);
		gridView.setExpanded(true);
		homeChartDisplayListView.setExpanded(true);
		homeTextDisplayListView.setExpanded(true);
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

		userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.ADD_USER_ICON, false);
		imageAdapter = new ImageAdapter(getSherlockActivity(), userList);
		gridView.setAdapter(imageAdapter);

		tempJugadSelfScreen(); // will remove it, it's temprary
		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.ADD_USER_ICON, false);
				Bundle bundle = new Bundle();
				Intent intent = new Intent();
				bundle.putString("title", userList.get(position).getUsername());
				//added by ishu
				bundle.putString("userid", userList.get(position).getUid());
				//end
//				intent.setClass(getSherlockActivity(), UserNotification.class);
				intent.setClass(getSherlockActivity(), SelfScreen.class);
				intent.putExtras(bundle);
				startActivity(intent);
				AppLog.logToast(getSherlockActivity(), "posi"+position);
			}
		});

		gridView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.ADD_USER_ICON, false);
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
		//		userList = userListProvider.getList(UserListProvider.FOR_HOME_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.NOTIFICATION, false);
		//		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_row, R.id.TextView, letters);
		//		homeTextDisplayListView.setAdapter(adapter);
		ArrayList<TextDisplaySettings> textDisplaySettings = ListHolder.getTextDisplaySettingList();//userListProvider.getTextDisplayList();
		mTextDisplayAdapter = new TextDisplayAdapter(getSherlockActivity(), textDisplaySettings);
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
				//				final UserListProvider userListProvider = new UserListProvider();
				//				userList = userListProvider.getTextDisplayList();
				final ArrayList<TextDisplaySettings> textDisplaySettingList  = ListHolder.getTextDisplaySettingList();
				final TextDisplaySettings textDisplaySettings = textDisplaySettingList.get(position);
				String userID = textDisplaySettings.getUserID();
				final User user = userListProvider.getUser(userID);
				AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
				builder.setTitle("Delete");
				builder.setMessage("Are You Sure to delete "+user.getUsername()+ "'s " + textDisplaySettings.getBiometricID()+" from home screen ?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// set the value on server
						String rquestId = user.getRequestId();
						String biometricId = getAndSetBiometricId(textDisplaySettings, textDisplaySettings.getBiometricID(), false, Constants.ADD_TEXT_DISPLAY);
						MyRequestQueue queue = MyVolley.getRequestQueue();
						Map<String, String> paramMap = QueryHelper.createAddTextDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, biometricId, "0");
						CustomRequest customRequest = new CustomRequest(Method.POST,
								Constants.URL_WEB_SERVICE, paramMap, FragmentTabBottom.this, FragmentTabBottom.this);
						queue.add(customRequest);

						//						userList = getUserListByDisease(mClickedMenuDisease);
						//						userList = userListProvider.getTextDisplayList();
						refreshTextDisplayListAdapter(textDisplaySettingList);
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
		//		userList = userListProvider.getVisualDisplayList();
		ArrayList<VisualDisplaySettings> visualDisplaySettings = ListHolder.getVisualDisplaySettingsList();//userListProvider.getTextDisplayList();
		mVisualDisplayAdapter = new VisualDisplayAdapter(getSherlockActivity(), visualDisplaySettings);
		homeChartDisplayListView.setAdapter(mVisualDisplayAdapter);

//		refreshVisualDisplayListAdapter(visualDisplaySettings);
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
				final ArrayList<VisualDisplaySettings> visualDisplaySettingList  = ListHolder.getVisualDisplaySettingsList();
				final VisualDisplaySettings visualDisplaySettings = visualDisplaySettingList.get(position);
				String userID = visualDisplaySettings.getUserID();
				final User user = userListProvider.getUser(userID);
				//				final User user = userList.get(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
				builder.setTitle("Delete");
				builder.setMessage("Are You Sure to delete "+user.getUsername()+ "'s " + visualDisplaySettings.getBiometricID()+" from home screen ?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// set the value on server
						String rquestId = user.getRequestId();
						String biometricId = getAndSetBiometricId(visualDisplaySettings, visualDisplaySettings.getBiometricID(), false, Constants.ADD_TEXT_VISUALEXPLORER);

						MyRequestQueue queue = MyVolley.getRequestQueue();
						Map<String, String> paramMap = QueryHelper.createAddVisualDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId , biometricId, "0");
						CustomRequest customRequest = new CustomRequest(Method.POST,
								Constants.URL_WEB_SERVICE, paramMap, FragmentTabBottom.this, FragmentTabBottom.this);
						queue.add(customRequest);

						//						UserListProvider userListProvider = new UserListProvider();
						//						userList = userListProvider.getVisualDisplayList();
//						ArrayList<VisualDisplaySettings> visualDisplaySettings = ListHolder.getVisualDisplaySettingsList();//userListProvider.getTextDisplayList();
//						mVisualDisplayAdapter = new VisualDisplayAdapter(getSherlockActivity(), visualDisplaySettings);
//						homeChartDisplayListView.setAdapter(mVisualDisplayAdapter);
//						refreshVisualDisplayListAdapter(visualDisplaySettingList);
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
		
		ArrayList<VisualDisplaySettings> visualDisplaySettings = ListHolder.getVisualDisplaySettingsList();
		fetchSensorData(visualDisplaySettings);
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

	private void refreshTextDisplayListAdapter(ArrayList<TextDisplaySettings> userList2) {
		mTextDisplayAdapter.setList(userList2);
		mTextDisplayAdapter.notifyDataSetChanged();
	}

	private void refreshVisualDisplayListAdapter(ArrayList<VisualDisplaySettings> userList2) {
		mVisualDisplayAdapter.setList(userList2);
		mVisualDisplayAdapter.notifyDataSetChanged();
	}
	private void refreshVisualDisplayGraphListAdapter(ArrayList<SensorData> graphDataList) {
		mVisualDisplayAdapter.setGraphDataList(graphDataList);
		mVisualDisplayAdapter.notifyDataSetChanged();
	}

	private void setTabs() {
		mTabHost = (TabHost) getSherlockActivity().findViewById(android.R.id.tabhost);
		mTabHost.setup();

		addTab("Home", TAG_1, createTabDrawable(R.drawable.home), R.id.scroller);
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
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					String UserID = "";
					String title = "";
					Bundle bundle = new Bundle();
					for(int i=0;i<userList.size();i++){
						String strusename;
						strusename = userList.get(i).getUsername();
						if(strusename.equals("Self")){
							UserID = userList.get(i).getUid();
							title=strusename;
						}
					}	
					
					if(StringUtils.isEmpty(title)) {
						title = "Self";
					}
					bundle.putString("userid", UserID);	
					bundle.putString("title",title);
					intent.putExtras(bundle);
					startActivity(intent);
					break;

				case 2:
					intent = new Intent(getSherlockActivity(), UsersCaretakers.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					break;

				case 3:
					intent = new Intent(getSherlockActivity(), SendReceived_Request.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					break;

				case 4:
					intent = new Intent(getSherlockActivity(), Rainbow.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
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
		AppLog.e(error.toString());
	}

	@Override
	public void onResponse(JSONObject response)
	{
		AppLog.e(response.toString());

	}

	private String getAndSetBiometricId(Object user, String diseaseType, boolean setterValue, int menuType) {
		if(menuType == Constants.ADD_TEXT_DISPLAY) {
			ListHolder.getTextDisplaySettingList().remove((TextDisplaySettings)user);
			if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
				//				user.setGsrTextDisplay(setterValue);
				return "1";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
				//				user.setHeartRateTextDisplay(setterValue);
				return "2";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
				//				user.setAccelerometerTextDisplay(setterValue);
				return "3";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
				//				user.setTempratureTextDisplay(setterValue);
				return "4";
			}
		}else if(menuType == Constants.ADD_TEXT_VISUALEXPLORER){
			ListHolder.getVisualDisplaySettingsList().remove((VisualDisplaySettings)user);
			if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
				//				user.setGsrVisualDisplay(setterValue);
				return "1";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
				//				user.setHeartRateVisualDisplay(setterValue);
				return "2";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
				//				user.setAccelerometerVisualDisplay(setterValue);
				return "3";

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
				//				user.setTempratureVisualDisplay(setterValue);
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

	private class ResponseListener implements Response.Listener<JSONObject> {
		private ArrayList<SensorData> mSensorDataList;

		@Override
		public void onResponse(JSONObject response){
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
			case ParserError.CODE_DEVICE:	
				AppLog.showToast(getActivity(), "No device mapped with this user");	
				break;
			case ParserError.CODE_USER_ALREADY_REGISTERED:

				break;
			case ParserError.CODE_SENSOR:
				Toast.makeText(getSherlockActivity(), "No sensor data available for this user",
						Toast.LENGTH_LONG).show();
				break;
			case ParserError.CODE_SUCCESS:
				mSensorDataList = parseSensorData(response);
				refreshVisualDisplayGraphListAdapter(mSensorDataList);
				break;

			default:
				break;
			}
		}
	}


	private class ErrorListener implements Response.ErrorListener{
		@Override
		public void onErrorResponse(VolleyError error){
//			closeLoadingBar();
			AppLog.logToast(getSherlockActivity(), error.toString());
		}
	}
	
	/**
	 * Gets the sensor data.
	 * 
	 * @param userArray
	 *            the user array
	 */
	private void fetchSensorData(ArrayList<VisualDisplaySettings> userArray) {
		for (int i = 0; i < userArray.size(); i++) {
			VisualDisplaySettings setting = userArray.get(i);
			String userID = setting.getUserID();
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = df.format(c.getTime());
			 formattedDate = "2014-01-29";
			String StartDate = formattedDate + " 00:00:00";
			String EndDate = formattedDate + " 23:59:59";

			MyRequestQueue queue = MyVolley.getRequestQueue();
			String token = BeanController.getLoginBean().getAccessToken();
			Map<String, String> loginParam = QueryHelper.getSensorDataQuery(
					token, userID, StartDate, EndDate);
			CustomRequest customRequest = new CustomRequest(Method.POST,
					Constants.URL_WEB_SERVICE, loginParam, 
					new ResponseListener(), new ErrorListener());

			customRequest.setTag(this);
			queue.add(customRequest);
			queue.start();
		}
	}

	/**
	 * Gets the sensor_ data.
	 * 
	 * @param jsonObject
	 *            the json object
	 * @return the array list
	 */
	private ArrayList<SensorData> parseSensorData(JSONObject jsonObject) {
		ArrayList<SensorData> sensorDataList = new ArrayList<SensorData>();
		try {
			JSONArray jArrayuser = jsonObject.getJSONArray("user_id");
			JSONArray jArray = jsonObject.getJSONArray("sensor_data");		

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject objJson = jArray.getJSONObject(i);
				jsonObject = jArray.getJSONObject(i);

				SensorData sensorData = new SensorData();
				sensorData.UserID = jArrayuser.getString(0);
				sensorData.temp_ambient = Double.parseDouble(objJson.getString("temp_ambient"));
				sensorData.temp_skin = Double.parseDouble(objJson.getString("temp_skin"));
				sensorData.pox_hr = Double.parseDouble(objJson.getString("pox_hr"));
				sensorData.pox_pulse = Double.parseDouble(objJson.getString("pox_pulse"));
				sensorData.acc_x = Double.parseDouble(objJson.getString("acc_x"));
				sensorData.acc_y = Double.parseDouble(objJson.getString("acc_y"));
				sensorData.acc_z = Double.parseDouble(objJson.getString("acc_z"));
				sensorData.gsr = Double.parseDouble(objJson.getString("gsr"));
				sensorData.led1ac = Double.parseDouble(objJson.getString("led1ac"));
				sensorData.led2ac = Double.parseDouble(objJson.getString("led2ac"));
				sensorData.led1dc = Double.parseDouble(objJson.getString("led1dc"));
				sensorData.led2dc = Double.parseDouble(objJson.getString("led2dc"));
				sensorData.updated_on = objJson.getString("updated_on");
				sensorDataList.add(sensorData);
			}
		} catch (Exception e) {
			AppLog.e(e.getMessage());
		}
		return sensorDataList;
	}

	
	private void tempJugadSelfScreen() {
		for(int i=0;i<userList.size();i++){
			String strusename;
			strusename = userList.get(i).getUsername();
			if(strusename.equals("Self")){
				String UserID = userList.get(i).getUid();
				AppSettings.setPreference(getSherlockActivity(), null, AppSettings.TEMP_JUGAD_SELF_SCREEN, UserID);
//				title=strusename;
			}
		}	
	}
}