package com.rampgreen.caretakermobile.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.TextDisplayAdapter;
import com.rampgreen.caretakermobile.interfaces.ParserError;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.SensorData;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.ui.util.TabBitmap;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;
import com.rampgreen.caretakermobile.util.StringUtils;
import com.viewpagerindicator.TabPageIndicator;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfScreen.
 */
public class SelfScreen extends BaseActivity implements OnTabChangeListener, OnNavigationListener {

	/** The Constant HOUR. */
	private static final long HOUR = 3600 * 1000;

	/** The Constant DAY. */
	private static final long DAY = HOUR * 24;

	/** The Constant HOURS. */
	private static final int HOURS = 24;

	/** The ly gsr. */
	private static LinearLayout lyGsr;

	/** The view gsr. */
	private static GraphicalView viewGsr;

	private static ArrayList<SensorData> ALIST_Temp;
	String token = BeanController.getLoginBean().getAccessToken(); // "1348c8cd897edc2925815ff158b55164a32209fc";

	/** The Constant CONTENT. */
	public static final String[] CONTENT = new String[] { "GSR", "HR", "Acc",
			"Temp" };

	private static final User User = null;

	/** The m tab host days. */
	private TabHost mTabHostDays;

	/** The adapter. */
	FragmentPagerAdapter adapter;

	/** The pager. */
	ViewPager pager;
	Bundle myBundle;	
	private String[] mDisplayist;
	TabPageIndicator indicator;
	private FragmentCalendarView mCalendarDisplayAdapter;
	
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);		
		
		Intent myLocalIntent = getIntent();
		myBundle = myLocalIntent.getExtras();
		String title = "Self";
		if (myBundle != null) {
			title = myBundle.getString("title");
		}

		setContentView(R.layout.simple_tabs);
		TextView txtDisplayName = (TextView) findViewById(R.id.text_view_title);
		txtDisplayName.setText(title + " Graphical Data");
		
//		setHeader(title, true, true, false, R.drawable.alerts,
//				R.drawable.profile);		
		
		adapter = new GoogleMusicAdapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		setTabs();	
		
		mDisplayist = getResources().getStringArray(R.array.SelfScreenDisplay);
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(
				this, R.array.SelfScreenDisplay,
				R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);		
		
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);

		getSupportActionBar().show();
//		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);		
	}
	

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());
		 formattedDate = "2014-01-29";
		String StartDate = formattedDate + " 00:00:00";
		String EndDate = formattedDate + " 23:59:59";
		
		String userID = "";
		if(myBundle != null) {
			userID = myBundle.getString("userid");
			if(StringUtils.isEmpty(userID)) {
				userID = "";
			}
		}
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> loginParam = QueryHelper.getSensorDataQuery(token,
				userID, StartDate, EndDate);
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, loginParam, this, this);
		// showLoadingBar();
		customRequest.setTag(this);
		// customRequest.setPriority(Priority.IMMEDIATE);
		queue.add(customRequest);
		queue.start();

		super.setOnTabChangeListener(this);
		super.mTabHost.setCurrentTab(1);
	}

	/**
	 * The Class GoogleMusicAdapter.
	 */
	class GoogleMusicAdapter extends FragmentPagerAdapter {

		/**
		 * Instantiates a new google music adapter.
		 * 
		 * @param fm
		 *            the fm
		 */
		public GoogleMusicAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return TestFragment.newInstance(CONTENT[position
						% CONTENT.length]);

			case 1:
				return HeartFragment.newInstance(CONTENT[position
						% CONTENT.length]);

			case 2:
				return AccFragment.newInstance(CONTENT[position
						% CONTENT.length]);

			case 3:
				return TempFragment.newInstance(CONTENT[position
						% CONTENT.length]);

			default:
				return TestFragment.newInstance(CONTENT[position
						% CONTENT.length]);

			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}

	/**
	 * Make fragment name.
	 * 
	 * @param viewId
	 *            the view id
	 * @param position
	 *            the position
	 * @return the string
	 */
	private static String makeFragmentName(int viewId, int position) {
		return "android:switcher:" + viewId + ":" + position;
	}

	/**
	 * Setup tab host.
	 */
	private void setupTabHost() {		
		mTabHostDays = (TabHost) findViewById(android.R.id.tabhost);
		mTabHostDays.setup();		
	}

	/**
	 * Setup tab.
	 * 
	 * @param view
	 *            the view
	 * @param tag
	 *            the tag
	 */
	private void setupTab(final View view, final String tag) {
		View tabview = createTabView(mTabHostDays.getContext(), tag);

		TabSpec setContent = mTabHostDays.newTabSpec(tag).setIndicator(tabview)
				.setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return view;
					}
				});
		mTabHostDays.addTab(setContent);

	}

	/**
	 * Creates the tab view.
	 * 
	 * @param context
	 *            the context
	 * @param text
	 *            the text
	 * @return the view
	 */
	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	

	/**
	 * Creates the tab drawable.
	 * 
	 * @param resId
	 *            the res id
	 * @return the drawable
	 */
	private Drawable createTabDrawable(int resId) {
		Resources res = getResources();
		StateListDrawable states = new StateListDrawable();

		final Options options = new Options();
		options.inPreferredConfig = Config.ARGB_8888;

		Bitmap icon = BitmapFactory.decodeResource(res, resId, options);

		Bitmap unselected = TabBitmap.createUnselectedBitmap(res, icon);
		Bitmap selected = TabBitmap.createSelectedBitmap(res, icon);

		icon.recycle();

		states.addState(new int[] { android.R.attr.state_selected },
				new BitmapDrawable(res, selected));
		states.addState(new int[] { android.R.attr.state_enabled },
				new BitmapDrawable(res, unselected));

		return states;
	}

	/**
	 * Creates the tab indicator.
	 * 
	 * @param label
	 *            the label
	 * @param drawable
	 *            the drawable
	 * @return the view
	 */
	private View createTabIndicator(String label, Drawable drawable) {
		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, mTabHostDays.getTabWidget(), false);

		TextView txtTitle = (TextView) tabIndicator
				.findViewById(R.id.text_view_tab_title);
		txtTitle.setText(label);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) txtTitle
				.getLayoutParams();
		txtTitle.setLayoutParams(params);

		ImageView imgIcon = (ImageView) tabIndicator
				.findViewById(R.id.image_view_tab_icon);
		imgIcon.setImageDrawable(drawable);

		return tabIndicator;
	}

	/**
	 * The Class TestFragment.
	 */
	public final static class TestFragment extends Fragment {

		/** The Constant KEY_CONTENT. */
		private static final String KEY_CONTENT = "TestFragment:Content";

		/** The image view. */
		ImageView imageView;

		/** The content type. */
		static String contentType;

		/**
		 * New instance.
		 * 
		 * @param content
		 *            the content
		 * @return the test fragment
		 */
		public static TestFragment newInstance(String content) {
			TestFragment fragment = new TestFragment();
			contentType = content;
			return fragment;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater
		 * , android.view.ViewGroup, android.os.Bundle)
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.self_gsr_graph, null);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
		 */
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			if (ALIST_Temp != null) {
				lyGsr = (LinearLayout) getActivity().findViewById(
						R.id.chart_container);
				String[] titles = new String[] { "Inside" };

				List<Date[]> x = new ArrayList<Date[]>();
				List<double[]> values = new ArrayList<double[]>();
				Date[] dates = new Date[ALIST_Temp.size()];
				double[] str = new double[ALIST_Temp.size()];

				for (int j = 0; j < ALIST_Temp.size(); j++) {
					try {//
						dates[j] = getDateCurrentTimeZone(ALIST_Temp.get(j).updated_on);
						str[j] = ALIST_Temp.get(j).getgsr();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				x.add(dates);
				values.add(str);

				int[] colors = new int[] { Color.BLUE };
				PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };
				XYMultipleSeriesRenderer renderer = buildRenderer(colors,
						styles);
				int length = renderer.getSeriesRendererCount();
				for (int i = 0; i < length; i++) {
					((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
							.setFillPoints(true);
				}
				setChartSettings(renderer, "Sensor GSR", "Hour", "Ohms",
						x.get(0)[0].getTime(),
						x.get(0)[x.get(0).length - 1].getTime(), 100000,
						30000000, Color.BLACK, Color.BLACK);

				viewGsr = ChartFactory
						.getTimeChartView(getActivity(),
								buildDateDataset(titles, x, values), renderer,
								"h:mm a");
				viewGsr.refreshDrawableState();
				viewGsr.repaint();
				lyGsr.addView(viewGsr);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.app.Fragment#onStart()
		 */
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.app.Fragment#onStop()
		 */
		@Override
		public void onStop() {
			super.onStop();
		}

		/**
		 * Sets the graph image.
		 * 
		 * @param resId
		 *            the new graph image
		 */
		public void setGraphImage(int resId) {
			imageView.setImageResource(resId);
		}
	}

	/**
	 * ******************************** 2 *************************************.
	 */
	public final static class HeartFragment extends Fragment {

		/** The Constant KEY_CONTENT. */
		private static final String KEY_CONTENT = "TestFragment:Content";

		/** The image view. */
		ImageView imageView;

		/** The content type. */
		static String contentType;

		/**
		 * New instance.
		 * 
		 * @param content
		 *            the content
		 * @return the heart fragment
		 */
		public static HeartFragment newInstance(String content) {
			HeartFragment fragment = new HeartFragment();
			contentType = content;
			return fragment;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater
		 * , android.view.ViewGroup, android.os.Bundle)
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.self_heart_graph, null);
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
		 */
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			if (ALIST_Temp != null) {
				lyGsr = (LinearLayout) getActivity().findViewById(
						R.id.chart_containerHr);
				String[] titles = new String[] { "HR", "Pulse" };

				List<Date[]> x = new ArrayList<Date[]>();

				for (int i = 0; i < titles.length; i++) {
					Date[] dates = new Date[ALIST_Temp.size()];
					for (int j = 0; j < ALIST_Temp.size(); j++) {
						try {//
							dates[j] = getDateCurrentTimeZone(ALIST_Temp.get(j).updated_on);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					x.add(dates);
				}

				List<double[]> values = new ArrayList<double[]>();

				double[] str = new double[ALIST_Temp.size()];
				double[] str_pluse = new double[ALIST_Temp.size()];

				for (int j = 0; j < ALIST_Temp.size(); j++) {
					str[j] = ALIST_Temp.get(j).getpox_hr();
					str_pluse[j] = ALIST_Temp.get(j).getpox_pulse();
				}

				values.add(str);
				values.add(str_pluse);

				int[] colors = new int[] { Color.BLUE, Color.GREEN };
				PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
						PointStyle.DIAMOND };
				XYMultipleSeriesRenderer renderer = buildRenderer(colors,
						styles);
				int length = renderer.getSeriesRendererCount();
				for (int i = 0; i < length; i++) {
					((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
							.setFillPoints(true);
				}
				setChartSettings(renderer, "Sensor HR", "Hour", "Beats/min",
						x.get(0)[0].getTime(),
						x.get(0)[x.get(0).length - 1].getTime(), 30, 300,
						Color.BLACK, Color.BLACK);

				viewGsr = ChartFactory
						.getTimeChartView(getActivity(),
								buildDateDataset(titles, x, values), renderer,
								"h:mm a");
				viewGsr.refreshDrawableState();
				viewGsr.repaint();
				lyGsr.addView(viewGsr);
			}
		}

		/**
		 * Sets the graph image.
		 * 
		 * @param resId
		 *            the new graph image
		 */
		public void setGraphImage(int resId) {
			imageView.setImageResource(resId);
		}
	}

	/**
	 * ******************************************* 3
	 * ***********************************.
	 */
	public final static class AccFragment extends Fragment {

		/** The Constant KEY_CONTENT. */
		private static final String KEY_CONTENT = "TestFragment:Content";

		/** The image view. */
		ImageView imageView;

		/** The content type. */
		static String contentType;

		/**
		 * New instance.
		 * 
		 * @param content
		 *            the content
		 * @return the acc fragment
		 */
		public static AccFragment newInstance(String content) {
			AccFragment fragment = new AccFragment();
			contentType = content;
			return fragment;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater
		 * , android.view.ViewGroup, android.os.Bundle)
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.self_acc_graph, null);
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
		 */
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			if (ALIST_Temp != null) {
				lyGsr = (LinearLayout) getActivity().findViewById(
						R.id.chart_containerAcc);
				String[] titles = new String[] { "acc_X", "acc_Y", "acc_Z" };

				List<Date[]> x = new ArrayList<Date[]>();

				for (int i = 0; i < titles.length; i++) {
					Date[] dates = new Date[ALIST_Temp.size()];
					for (int j = 0; j < ALIST_Temp.size(); j++) {
						try {//
							dates[j] = getDateCurrentTimeZone(ALIST_Temp.get(j).updated_on);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					x.add(dates);
				}

				List<double[]> values = new ArrayList<double[]>();

				double[] str_x = new double[ALIST_Temp.size()];
				double[] str_y = new double[ALIST_Temp.size()];
				double[] str_z = new double[ALIST_Temp.size()];

				for (int j = 0; j < ALIST_Temp.size(); j++) {
					str_x[j] = ALIST_Temp.get(j).getacc_x();
					str_y[j] = ALIST_Temp.get(j).getacc_y();
					str_z[j] = ALIST_Temp.get(j).getacc_z();
				}

				values.add(str_x);
				values.add(str_y);
				values.add(str_z);

				int[] colors = new int[] { Color.GREEN, Color.BLUE, Color.RED };
				PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
						PointStyle.DIAMOND, PointStyle.TRIANGLE };
				XYMultipleSeriesRenderer renderer = buildRenderer(colors,
						styles);
				int length = renderer.getSeriesRendererCount();
				for (int i = 0; i < length; i++) {
					((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
							.setFillPoints(true);
				}
				setChartSettings(renderer, "Sensor Accelerometer", "Hour",
						"m/s", x.get(0)[0].getTime(),
						x.get(0)[x.get(0).length - 1].getTime(), -19.8, 19.8,
						Color.BLACK, Color.BLACK);

				viewGsr = ChartFactory
						.getTimeChartView(getActivity(),
								buildDateDataset(titles, x, values), renderer,
								"h:mm a");
				viewGsr.refreshDrawableState();
				viewGsr.repaint();
				lyGsr.addView(viewGsr);
			}

		}

		/**
		 * Sets the graph image.
		 * 
		 * @param resId
		 *            the new graph image
		 */
		public void setGraphImage(int resId) {
			imageView.setImageResource(resId);
		}
	}

	/**
	 * ***************************************************************.
	 */

	public final static class TempFragment extends Fragment {

		/** The Constant KEY_CONTENT. */
		private static final String KEY_CONTENT = "TestFragment:Content";

		/** The image view. */
		ImageView imageView;

		/** The content type. */
		static String contentType;

		/**
		 * New instance.
		 * 
		 * @param content
		 *            the content
		 * @return the temp fragment
		 */
		public static TempFragment newInstance(String content) {
			TempFragment fragment = new TempFragment();
			contentType = content;
			return fragment;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater
		 * , android.view.ViewGroup, android.os.Bundle)
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.self_temp_graph, null);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
		 */
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			if (ALIST_Temp != null) {
				lyGsr = (LinearLayout) getActivity().findViewById(
						R.id.chart_containerTemp);
				String[] titles = new String[] { "Ambient", "Skin" };

				List<Date[]> x = new ArrayList<Date[]>();

				for (int i = 0; i < titles.length; i++) {
					Date[] dates = new Date[ALIST_Temp.size()];
					for (int j = 0; j < ALIST_Temp.size(); j++) {
						try {//
							dates[j] = getDateCurrentTimeZone(ALIST_Temp.get(j).updated_on);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					x.add(dates);
				}

				List<double[]> values = new ArrayList<double[]>();

				double[] str_Ambient = new double[ALIST_Temp.size()];
				double[] str_Skin = new double[ALIST_Temp.size()];

				for (int j = 0; j < ALIST_Temp.size(); j++) {
					str_Ambient[j] = ALIST_Temp.get(j).gettemp_ambient();
					str_Skin[j] = ALIST_Temp.get(j).gettemp_skin();
				}

				values.add(str_Ambient);
				values.add(str_Skin);

				int[] colors = new int[] { Color.GREEN, Color.BLUE };
				PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
						PointStyle.DIAMOND };
				XYMultipleSeriesRenderer renderer = buildRenderer(colors,
						styles);
				int length = renderer.getSeriesRendererCount();
				for (int i = 0; i < length; i++) {
					((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
							.setFillPoints(true);
				}
				setChartSettings(renderer, "Sensor Temperature", "Hour",
						"Celsius", x.get(0)[0].getTime(),
						x.get(0)[x.get(0).length - 1].getTime(), 25, 85,
						Color.BLACK, Color.BLACK);

				viewGsr = ChartFactory
						.getTimeChartView(getActivity(),
								buildDateDataset(titles, x, values), renderer,
								"h:mm a");
				viewGsr.refreshDrawableState();
				viewGsr.repaint();
				lyGsr.addView(viewGsr);
			}

		}

		/**
		 * Sets the graph image.
		 * 
		 * @param resId
		 *            the new graph image
		 */
		public void setGraphImage(int resId) {
			imageView.setImageResource(resId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.app.Fragment#onStart()
		 */
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.support.v4.app.Fragment#onStop()
		 */
		@Override
		public void onStop() {
			super.onStop();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Response.Listener#onResponse(java.lang.Object)
	 */
	@Override
	public void onResponse(JSONObject response) {
		int code = Integer.parseInt(response.optString("code"));
		String msg = response.optString("message");
		switch (code) {
		case ParserError.CODE_ACTION_NOT_FOUND:
			// closeLoadingBar();
			break;
		case ParserError.CODE_MISSING_ACTION:
			// closeLoadingBar();
			break;
		case ParserError.CODE_MISSING_TASK:
			// closeLoadingBar();
			break;
		case ParserError.CODE_CLIENT_AUTHORIZATION_FAILED:
			// closeLoadingBar();
			break;
		case ParserError.CODE_TOKEN_GENERATION_FAILED:
			// closeLoadingBar();
			break;
		case ParserError.CODE_USERNAME_REQUIRED:
			// closeLoadingBar();
			break;
		case ParserError.CODE_PASSWORD_REQUIRED:
			// closeLoadingBar();
			break;
		case ParserError.CODE_PASSWORD_WRONG:
			// closeLoadingBar();
			break;
		case ParserError.CODE_USER_NOT_REGISTERED:
			// closeLoadingBar();
			break;
		case ParserError.CODE_INVALID_TOKEN:
			// closeLoadingBar();
			break;
		case ParserError.CODE_TOKEN_EXPIRED:
			// closeLoadingBar();
			break;
		case ParserError.CODE_INTERNAL_SERVER_ERROR:
			// closeLoadingBar();
			break;
		case ParserError.CODE_USER_ALREADY_REGISTERED:
			// closeLoadingBar();
			break;
		case ParserError.CODE_NO_REQUEST_RECEIVED:
			// closeLoadingBar();
			break;
		case ParserError.CODE_NO_CARETAKER_REQUEST_SENT_IS_PENDING:
			// closeLoadingBar();
			break;
		case ParserError.CODE_SENSOR:
			AppLog.showToast(this, "No sensor data available for this user");
			// closeLoadingBar();
			break;
		case ParserError.CODE_SUCCESS:
			GetSensor_Data(response);
			adapter = new GoogleMusicAdapter(getSupportFragmentManager());

			pager = (ViewPager) findViewById(R.id.pager);
			pager.setAdapter(adapter);

			closeLoadingBar();

			break;

		default:
			break;

		}

		if (code != ParserError.CODE_SUCCESS) {
			AppLog.logToast(this, "error web service response code - " + code);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android.volley.Response.ErrorListener#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	@Override
	public void onTabChanged(String tabId) {
		Intent intent;
		int tabNum = Integer.parseInt(tabId);

		switch (tabNum) {
		case 0:
			intent = new Intent(getApplicationContext(),
					FragmentChangeActivity.class);
			intent.putExtra(Constants.BUNDLE_KEY_USERS,
					BeanController.getUserBean());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rampgreen.caretakermobile.ui.BaseActivity#btnHomeClick(android.view
	 * .View)
	 */
	@Override
	public void btnHomeClick(View v) {
		super.btnHomeClick(v);
		startActivity(new Intent(getApplicationContext(), ActivityAlert.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rampgreen.caretakermobile.ui.BaseActivity#btnSettingClick(android
	 * .view.View)
	 */
	@Override
	public void btnSettingClick(View v) {
		super.btnSettingClick(v);
		startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
		// activity profile is to be open
	}

	// added by ishu
	/**
	 * Builds the date dataset.
	 * 
	 * @param titles
	 *            the titles
	 * @param xValues
	 *            the x values
	 * @param yValues
	 *            the y values
	 * @return the xY multiple series dataset
	 */
	protected static XYMultipleSeriesDataset buildDateDataset(String[] titles,
			List<Date[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			TimeSeries series = new TimeSeries(titles[i]);
			Date[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	/**
	 * Sets the chart settings.
	 * 
	 * @param renderer
	 *            the renderer
	 * @param title
	 *            the title
	 * @param xTitle
	 *            the x title
	 * @param yTitle
	 *            the y title
	 * @param xMin
	 *            the x min
	 * @param xMax
	 *            the x max
	 * @param yMin
	 *            the y min
	 * @param yMax
	 *            the y max
	 * @param axesColor
	 *            the axes color
	 * @param labelsColor
	 *            the labels color
	 */
	protected static void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 80, 15, 20 });

		renderer.setXLabels(10);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
		renderer.setPanEnabled(false, false);
		renderer.setZoomRate(0.2f);
		renderer.setZoomEnabled(false, false);
		renderer.setZoomButtonsVisible(true);

	}

	/**
	 * Builds the renderer.
	 * 
	 * @param colors
	 *            the colors
	 * @param styles
	 *            the styles
	 * @return the xY multiple series renderer
	 */
	protected static XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	/**
	 * Sets the renderer.
	 * 
	 * @param renderer
	 *            the renderer
	 * @param colors
	 *            the colors
	 * @param styles
	 *            the styles
	 */
	protected static void setRenderer(XYMultipleSeriesRenderer renderer,
			int[] colors, PointStyle[] styles) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	/**
	 * Gets the sensor_ data.
	 * 
	 * @param jsonObject
	 *            the json object
	 * @return the array list
	 */
	private ArrayList<SensorData> GetSensor_Data(JSONObject jsonObject) {
		ALIST_Temp = new ArrayList<SensorData>();
		try {
			JSONArray jArray = jsonObject.getJSONArray("sensor_data");
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject objJson = jArray.getJSONObject(i);
				jsonObject = jArray.getJSONObject(i);
				SensorData Obj_Temp = new SensorData();
				Obj_Temp.temp_ambient = Double.parseDouble(objJson
						.getString("temp_ambient"));
				Obj_Temp.temp_skin = Double.parseDouble(objJson
						.getString("temp_skin"));
				Obj_Temp.pox_hr = Double.parseDouble(objJson
						.getString("pox_hr"));
				Obj_Temp.pox_pulse = Double.parseDouble(objJson
						.getString("pox_pulse"));
				Obj_Temp.acc_x = Double.parseDouble(objJson.getString("acc_x"));
				Obj_Temp.acc_y = Double.parseDouble(objJson.getString("acc_y"));
				Obj_Temp.acc_z = Double.parseDouble(objJson.getString("acc_z"));
				Obj_Temp.gsr = Double.parseDouble(objJson.getString("gsr"));
				Obj_Temp.led1ac = Double.parseDouble(objJson
						.getString("led1ac"));
				Obj_Temp.led2ac = Double.parseDouble(objJson
						.getString("led2ac"));
				Obj_Temp.led1dc = Double.parseDouble(objJson
						.getString("led1dc"));
				Obj_Temp.led2dc = Double.parseDouble(objJson
						.getString("led2dc"));
				Obj_Temp.updated_on = objJson.getString("updated_on");
				ALIST_Temp.add(Obj_Temp);
			}
		} catch (Exception e) {
			// need to handle later
			// TODO: handle exception
		}
		return ALIST_Temp;
	}

	/**
	 * Gets the date current time zone.
	 *
	 * @param timestamp the timestamp
	 * @return the date current time zone
	 * @throws ParseException the parse exception
	 */
	public static Date getDateCurrentTimeZone(String timestamp)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(timestamp);
		return date;
	}


	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.ActionBar.OnNavigationListener#onNavigationItemSelected(int, long)
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		Bundle bundle = new Bundle();		
		 Intent intent = null;
		 if(myBundle == null) {
			String userID = (String) AppSettings.getPrefernce(this, null, AppSettings.TEMP_JUGAD_SELF_SCREEN, "");
			 bundle.putString("userid", userID);	
				bundle.putString("title", "Self");
				bundle.putString("lvitempos", "1");	 
		 } else {
			 bundle.putString("userid", myBundle.getString("userid"));	
				bundle.putString("title", myBundle.getString("title"));
				bundle.putString("lvitempos", String.valueOf(itemPosition));	 
		 }
		
		
		if (mDisplayist[itemPosition].equals("Graphical view")) {
		} else if (mDisplayist[itemPosition].equals("Text view")) {	
			 intent = new Intent(getApplicationContext(),
					 UserNotification.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);		
           
		} else if (mDisplayist[itemPosition].equals("Calendar view")) {
			 intent = new Intent(getApplicationContext(),
					 FragmentCalendarView.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);	
		}
		return true;
	}
  
	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onCreateOptionsMenu(android.view.Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    SubMenu subMenu1 = menu.addSubMenu(0, 0, Menu.NONE,"Action Item");
        subMenu1.add(0, 1, Menu.NONE,"Alerts").setIcon(R.drawable.bell);
        subMenu1.add(0, 2, Menu.NONE,"Setting").setIcon(R.drawable.settings);       

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_light);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	    return super.onCreateOptionsMenu(menu);
	}
	
	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Bundle bundle = new Bundle();	
		Intent intent = null;
		bundle.putString("userid", myBundle.getString("userid"));	
		bundle.putString("title", myBundle.getString("title"));
		switch (item.getItemId()) {
		case 1:
			intent = new Intent(getApplicationContext(),
					 ActivityAlert.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);	
			return true;
		case 2:
			intent = new Intent(getApplicationContext(),
					 AlertSettings.class);				
			 intent.putExtras(bundle);
			 startActivity(intent);	
			return true;
		default:		
			return super.onOptionsItemSelected(item);
		}
	}
}
