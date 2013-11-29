package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.ui.util.TabBitmap;
import com.rampgreen.caretakermobile.util.Constants;
import com.viewpagerindicator.TabPageIndicator;

public class SelfScreen extends BaseActivity  implements OnTabChangeListener {
	public static final String[] CONTENT = new String[] { "GSR", "HR", "Acc", "Temp"};
	private TabHost mTabHostDays;
	FragmentPagerAdapter adapter;
	ViewPager pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setTitle("My Data");
		setContentView(R.layout.simple_tabs);
		setHeader("My Data", true, true, false, R.drawable.alerts, R.drawable.profile);
		//		setupTabHost();
		//		mTabHostDays.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
		//
		//		setupTab(new TextView(this), "Hour");
		//		setupTab(new TextView(this), "Day");
		//		setupTab(new TextView(this), "Week");
		//		setupTab(new TextView(this), "Month");

		adapter = new GoogleMusicAdapter(getSupportFragmentManager());

		pager = (ViewPager)findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		setTabs();
		super.setOnTabChangeListener(this);
		super.mTabHost.setCurrentTab(1);
	}

	class GoogleMusicAdapter extends FragmentPagerAdapter {

		public GoogleMusicAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return TestFragment.newInstance(CONTENT[position % CONTENT.length]);

			case 1:
				return HeartFragment.newInstance(CONTENT[position % CONTENT.length]);

			case 2:
				return AccFragment.newInstance(CONTENT[position % CONTENT.length]);

			case 3:
				return TempFragment.newInstance(CONTENT[position % CONTENT.length]);


			default:
				return TestFragment.newInstance(CONTENT[position % CONTENT.length]);

			}

		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}

		//        public Fragment findFragmentByPosition(int position) {
		//    	    FragmentPagerAdapter fragmentPagerAdapter = getFragmentPagerAdapter();
		//    	    return getSupportFragmentManager().findFragmentByTag(
		//    	            "android:switcher:" + getViewPager().getId() + ":"
		//    	                    + fragmentPagerAdapter.getItemId(position));
		//    	}
	}

	private static String makeFragmentName(int viewId, int position)
	{
		return "android:switcher:" + viewId + ":" + position;
	}

	private void setupTabHost() {
		//		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hourlylinearView);
		mTabHostDays = (TabHost) findViewById(android.R.id.tabhost);
		mTabHostDays.setup();
		//		mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
		//			Intent intent;
		//			public void onTabChanged(String tabId) {
		////				int tabNum = Integer.parseInt(tabId);
		//				//				homeLinearLayout = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentUser);
		//				//				homeContentTextDisplay = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentTextDisplay);
		//				//				homeContentChartDisplay = (LinearLayout) getSherlockActivity().findViewById(R.id.homeContentChartDisplay);
		//				//				ScrollView scrollView = (ScrollView) getSherlockActivity().findViewById(R.id.scroller);
		//				Fragment page;
		//				if(tabId.equalsIgnoreCase("hour")) {
		//					page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + pager.getCurrentItem());
		//					((TestFragment)page).setGraphImage(R.drawable.ic_launcher);
		//				} else if(tabId.equalsIgnoreCase("day")){
		//					page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + pager.getCurrentItem());
		//					((TestFragment)page).setGraphImage(R.drawable.abs__spinner_ab_pressed_holo_light);
		//				}else if(tabId.equalsIgnoreCase("week")){
		//					
		//				}else if(tabId.equalsIgnoreCase("month")){
		//					
		//				}
		//			}});
	}


	private void setupTab(final View view, final String tag) {
		View tabview = createTabView(mTabHostDays.getContext(), tag);

		TabSpec setContent = mTabHostDays.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
			public View createTabContent(String tag) {return view;}
		});
		mTabHostDays.addTab(setContent);

	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}


	//	private void setTabs() {
	//		mTabHost = (TabHost) this.findViewById(android.R.id.tabhost);
	//		mTabHost.setup();
	//
	////		addTab("Home", TAG_1, createTabDrawable(R.drawable.ic_launcher), R.id.linear_view);
	////		addTab("Self", TAG_2, createTabDrawable(R.drawable.ic_launcher), R.id.text_view_search);
	////		addTab("Network", TAG_3, createTabDrawable(R.drawable.ic_launcher), R.id.text_view_favourites);
	////		addTab("Request", TAG_4, createTabDrawable(R.drawable.ic_launcher), R.id.text_view_settings);
	////		addTab("Rainbow", TAG_5, createTabDrawable(R.drawable.ic_launcher), R.id.text_view_rainbow);
	//	}

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
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, mTabHostDays.getTabWidget(), false);

		TextView txtTitle = (TextView) tabIndicator.findViewById(R.id.text_view_tab_title);
		txtTitle.setText(label);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) txtTitle.getLayoutParams();
		txtTitle.setLayoutParams(params);

		ImageView imgIcon = (ImageView) tabIndicator.findViewById(R.id.image_view_tab_icon);
		imgIcon.setImageDrawable(drawable);

		return tabIndicator;
	}

	public final static class TestFragment extends Fragment {
		private static final String KEY_CONTENT = "TestFragment:Content";
		ImageView imageView;
		static String contentType;

		public static TestFragment newInstance(String content) {
			TestFragment fragment = new TestFragment();
			contentType = content;
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.self_gsr_graph, null);
		}

//		@Override
//		public void onActivityCreated(Bundle savedInstanceState)
//		{
//			super.onActivityCreated(savedInstanceState);
//			imageView = (ImageView) getActivity().findViewById(R.id.graph);
//			imageView.setImageResource(R.drawable.gsr);
//
//		}

		public void setGraphImage(int resId) {
			imageView.setImageResource(resId);
		}
	}
	/**********************************2**************************************/
	public final static class HeartFragment extends Fragment {
		private static final String KEY_CONTENT = "TestFragment:Content";
		ImageView imageView;
		static String contentType;

		public static HeartFragment newInstance(String content) {
			HeartFragment fragment = new HeartFragment();
			contentType = content;
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.self_heart_graph, null);
		}

//		@Override
//		public void onActivityCreated(Bundle savedInstanceState)
//		{
//			super.onActivityCreated(savedInstanceState);
//			imageView = (ImageView) getActivity().findViewById(R.id.graph);
//			imageView.setImageResource(R.drawable.heart);
//
//		}

		public void setGraphImage(int resId) {
			imageView.setImageResource(resId);
		}
	}
	/*********************************************3************************************/
	public final static class AccFragment extends Fragment {
		private static final String KEY_CONTENT = "TestFragment:Content";
		ImageView imageView;
		static String contentType;

		public static AccFragment newInstance(String content) {
			AccFragment fragment = new AccFragment();
			contentType = content;
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.self_acc_graph, null);
		}

//		@Override
//		public void onActivityCreated(Bundle savedInstanceState)
//		{
//			super.onActivityCreated(savedInstanceState);
//			imageView = (ImageView) getActivity().findViewById(R.id.graph);
//			imageView.setImageResource(R.drawable.acc);
//		}

		public void setGraphImage(int resId) {
			imageView.setImageResource(resId);
		}
	}

	/******************************************************************/

	public final static class TempFragment extends Fragment {
		private static final String KEY_CONTENT = "TestFragment:Content";
		ImageView imageView;
		static String contentType;

		public static TempFragment newInstance(String content) {
			TempFragment fragment = new TempFragment();
			contentType = content;
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.self_temp_graph, null);
		}

//		@Override
//		public void onActivityCreated(Bundle savedInstanceState)
//		{
//			super.onActivityCreated(savedInstanceState);
//			imageView = (ImageView) getActivity().findViewById(R.id.graph);
//			imageView.setImageResource(R.drawable.temp);
//
//		}

		public void setGraphImage(int resId) {
			imageView.setImageResource(resId);
		}
	}

	@Override
	public void onResponse(JSONObject response)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onErrorResponse(VolleyError error)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void onTabChanged(String tabId)
	{
		Intent intent;
		int tabNum = Integer.parseInt(tabId);

		switch (tabNum) {
		case 0:
			intent = new Intent(getApplicationContext(), FragmentChangeActivity.class);
			intent.putExtra(Constants.BUNDLE_KEY_USERS, BeanController.getUserBean());
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 1:

			break;

		case 2:
			intent = new Intent(this, UsersCaretakers.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			break;

		case 3:
			intent = new Intent(this, SendReceived_Request.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case 4:
			intent = new Intent(this, Rainbow.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	public void btnHomeClick(View v)
	{
		super.btnHomeClick(v);
		startActivity(new Intent(getApplicationContext(), ActivityAlert.class));
	}

	@Override
	public void btnSettingClick(View v)
	{
		super.btnSettingClick(v);
		startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
		// activity profile is to be open
	}
}
