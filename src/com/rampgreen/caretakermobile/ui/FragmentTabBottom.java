package com.rampgreen.caretakermobile.ui;

import android.app.Activity;
import android.content.Context;
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
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.ListItemDetails;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.ui.util.ExpandableHeightGridView;
import com.rampgreen.caretakermobile.ui.util.TabBitmap;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;

/**
 * @author Manish Pathak
 *
 */
public class FragmentTabBottom extends SherlockFragment {

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

	int buttonNo =10;
	TabHost mTabHost;
	LinearLayout homeLinearLayout, homeContentTextDisplay, homeContentChartDisplay;
	ExpandableHeightGridView gridView;
	private LayoutInflater mInflater;
	private View smsInboxDetailView;
	private TextView txtInboxSmsDetail;
	private ArrayList<User> user;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		Bundle localBundle = getArguments();
		//		Bundle localBundle = getActivity().getIntent().getExtras();
		if (localBundle != null) {
			user  = (ArrayList<User>)localBundle.getSerializable(Constants.BUNDLE_KEY_USERS);
			buttonNo = localBundle.getInt(com.rampgreen.caretakermobile.util.Constants.BUNDLE_KEY_POSITION);
		}
		mInflater = (LayoutInflater) getSherlockActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		gridView.setAdapter(new ImageAdapter(getSherlockActivity(), user));
		//		gridView.setAdapter(new ImageAdapter(getSherlockActivity()));


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
				bundle.putString("title", user.get(position+1).getName());
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
					scrollView.setVisibility(View.INVISIBLE);
					break;

				case 3:
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
}