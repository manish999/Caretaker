package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;

public class FragmentGraphicalDisplay extends Fragment implements
Response.Listener<JSONObject>, Response.ErrorListener,OnTabChangeListener{

	String token = BeanController.getLoginBean().getAccessToken();
	private TabHost mTabHostDays;
	FragmentPagerAdapter adapter;
	ViewPager pager;
	Bundle myBundle;
	
	public FragmentGraphicalDisplay() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.simple_tabs, null); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		

	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		MyVolley.getRequestQueue().cancelAll(this);
	}
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		
	}

	class GraphicalDisplayAdapter extends FragmentPagerAdapter {

		public GraphicalDisplayAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		
	}
}
